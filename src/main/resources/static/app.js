
Vue.use(VueMaterial.default);

Vue.filter('currency', function(val) {
    return (new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })).format(val);
});

const app = new Vue({
    el: '#app',
    data: {
        products: [],
        second: false,
        first: false,
        active: 'first',
        user: {
            fullName: null,
            email: null,
            phoneNumber: null,
            document: null,
            password: null
        },
        address: {
            postalCode: null,
            state: null,
            city: null,
            district: null,
            address: null,
            complement: null,
            number: null
        },
        order: {
            id: null
        }
    },
    computed: {
      items () {
          return this.products;
      },
      qty () {
          return this.products.filter(r => !!r.qty).reduce((a, b) => a + b.qty, 0);
      },
      total () {
          return this.products.filter(r => !!r.qty).reduce((a, b) => a + (b.qty * b.price), 0);
      }
    },
    mounted () {
        fetch(`/api/products`).then(r => r.json()).then(r => {
           this.products = [...r.content].map(r => {r.qty = 0; return r;});
        });
    },
    methods: {
        test() {
          this.products = [];
        },
        addItem(item) {
            var indexOf = this.products.indexOf(item);
            console.log('add', item);
            if(!item.qty) {
                item.qty  = 0;
            }
            item.qty += 1;
            this.first = (this.qty > 0);
        },
        removeItem(item) {
            var indexOf = this.products.indexOf(item);
            console.log('add', item);
            if(!item.qty) {
                item.qty  = 0;
            }
            if(item.qty > 0)
                item.qty -= 1;

            this.first = (this.qty > 0);
        },
        signUp() {
            this.active = 'second';
            this.second = true;
        },
        async checkout() {
            try{
                await fetch(`/api/checkout`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        customer: this.user,
                        address: this.address,
                        items: this.products.filter(r => r.qty > 0)
                    })
                }).then(async r => {
                    if(r.status > 299){
                        throw new Error((await r.json()).error);
                    }
                    return r;
                }).catch((err) => {
                    console.error('on promise', err.message);
                    throw err;
                }).then(r => r.json()).then(r => {
                    if(!r.error) {
                        console.log(r);
                        this.active = 'third';
                        this.products = this.products.map(r => ({...r, qty: 0}));
                        this.order = r;
                    }
                });
            }catch (e) {
                console.error('on catch', e.message);
                alert(e.message);
            }
        }
    }
});
