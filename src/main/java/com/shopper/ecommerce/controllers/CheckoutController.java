package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CheckoutException;
import com.shopper.ecommerce.exceptions.InvalidItemQuantityException;
import com.shopper.ecommerce.models.*;
import com.shopper.ecommerce.repositories.ProductRepository;
import com.shopper.ecommerce.repositories.SaleRepository;
import com.shopper.services.EmailService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class CheckoutController {

    @Autowired
    ProductRepository products;

    @Autowired
    CustomerController customers;

    @Autowired
    AddressController addressController;

    @Autowired
    SaleRepository sales;

    @Autowired
    EmailService emailService;

    /**
     * This method handles checkout processing, it includes serveral phases:
     * - Validate shipping address
     * - Check if items are ok, and not tampered by the user.
     * - Create a customer account if it does not exits.
     * - Finally persists the order to the database.
     * @param checkout
     * @return
     */
    @SneakyThrows
    @Transactional
    public Sale processCheckout(Checkout checkout) {

        Sale newSale = new Sale();

        // check if address is valid
        addressController.validate(checkout.getAddress());

        // check items
        checkItems(checkout);

        checkout.getCustomer().setAddress(checkout.getAddress());

        Customer customer = customers.createCustomerIfNotExists(checkout.getCustomer());

        newSale.setCustomer(customer);

        newSale.setItemCount((long) checkout.getItems().size());

        newSale.setItems(checkout.getItems());

        sum(newSale);

        sales.save(newSale);

        sendEmail(newSale);

        return newSale;
    }


    /**
     * Check data integrity from client. Never trust client data.
     * Check if items are active or deleted.
     * Check if prices are ok.
     * @param checkout
     */
    @SneakyThrows
    public void checkItems(Checkout checkout) {

        Long itemCount = 0L;
        BigDecimal total = ZERO;

        if(checkout.getItems() == null)
            throw new CheckoutException("Items required");

        if(checkout.getItems().isEmpty())
            throw new CheckoutException("Select at least one item");

        for(ShoppingCartItem item: checkout.getItems()){

            Optional<Product> productOpt = products.findById(item.getId());

            if(productOpt.isPresent()) {

                Product product = productOpt.get();

                if (product.getPrice().setScale(2).compareTo(item.getPrice().setScale(2)) != 0)

                    throw new CheckoutException("Item price changed", "0400", BAD_REQUEST);

                if (product.isActive())

                    throw new CheckoutException("Item '" + product.getName() + "' has been deleted", "0404", 404);

                else // data is safe, proceed
                {
                    item.setName(product.getName());

                    item.setDescription(product.getDescription());
                }
            }else{

                throw new CheckoutException("Item with id " + item.getId() + " not found", "0404", NOT_FOUND);

            }


            itemCount += item.getQty();

            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQty())));

        }

        if(itemCount.compareTo(0L) <= 0 || total.compareTo(ZERO) <= 0){

            throw new InvalidItemQuantityException("Invalid item count or price");

        }

    }




    /**
     * Sends confirmation email to customer
     * @param newSale
     */
    private void sendEmail(Sale newSale) {

        emailService.sendEmail(
                newSale.getCustomer().getEmail(),
                "Pedido " + newSale.getId() + " recebido",
                buildEmailBody(newSale));

    }


    /**
     * Build the email body in html
     * @param newSale
     * @return
     * TODO: implement a template system for emails
     */
    private String buildEmailBody(Sale newSale) {

        String firstName = newSale.getCustomer().getFullName().split(" ")[0];

        NumberFormat nf = DecimalFormat.getCurrencyInstance(new Locale.Builder().setLanguage("pt").setRegion("BR").build());

        StringBuilder html = new StringBuilder();
        html.append("<!doctype html>");
        html.append("<html>");
            html.append("<body style='background:#eee; padding: 5px;white-space:nowrap;border-radius: 15px;'>");
                html.append("<div style='text-align: center;padding: 25px;'>");
                html.append(" <img src=\"https://i.ibb.co/5vmp2nq/shopper-logo.png\" alt=\"shopper-logo\" border=\"0\" style='width:150px;'>");
                html.append("</div>");
                html.append("<div style='max-width: 700px; background: #fff;padding: 5px;border-radius: 5px;'>");
                    html.append("<h2>Olá "+firstName+"</h2>");
                    html.append("<p>Recebemos seu pedido no nosso site:</p>");
                    html.append("<table style='width:100%;text-align:left; background: #fff;font-size: 9px;'>");
                        html.append("<thead>");
                            html.append("<tr>");
                            html.append("<th>Código</th>");
                            html.append("<th style='width:32'></th>");
                            html.append("<th style='width:120px'>Descrição</th>");
                            html.append("<th>Qtde</th>");
                            html.append("<th>Preço</th>");
                            html.append("<th>Sub-total</th>");
                            html.append("</tr>");
                        html.append("</thead>");
                        html.append("<tbody>");
                        int i = 0;
                        for(ShoppingCartItem item: newSale.getItems()) {
                            html.append("<tr>");
                                html.append("<td style='padding: 5px'>"+item.getId()+"</td>");
                                html.append("<td style='width:32px;'><img src='"+item.getPictureUrl()+"' style='width:32px;border-radius: 4px;'></td>");
                                html.append("<td style='padding: 5px'>");
                                    html.append("<b>"+item.getName()+"</b><br>"+item.getDescription()+"</td>");
                                html.append("<td style='padding: 5px'>"+item.getQty()+"</td>");
                                html.append("<td style='padding: 5px'>"+nf.format(item.getPrice())+"</td>");
                                html.append("<td style='padding: 5px'>"+nf.format(item.getPrice().multiply(BigDecimal.valueOf(item.getQty())))+"</td>");
                            html.append("</tr>");
                            i++;
                        }
                        html.append("</tbody>");
                        html.append("<tfoot>");
                        html.append("<tr>");
                        html.append("<td style='padding: 5px'></td>");
                        html.append("<td style='padding: 5px'></td>");
                        html.append("<td style='padding: 5px'></td>");
                        html.append("<td style='padding: 5px'>"+newSale.getItemCount()+"</td>");
                        html.append("<td style='padding: 5px'>Total</td>");
                        html.append("<td style='padding: 5px'>"+nf.format(newSale.getTotal())+"</td>");
                        html.append("</tr>");
                        html.append("</tfoot>");
                html.append("</div>");
            html.append("</body>");
        html.append("</html>");
        return html.toString();
    }

    /**
     * Sums item price and qty
     * @param newSale
     * TODO: Write tests for this
     */
    void sum(Sale newSale) {

        newSale.setTotal(ZERO);

        newSale.setItemCount(0L);

        for(ShoppingCartItem item: newSale.getItems()){
            newSale.setItemCount(item.getQty() + newSale.getItemCount());
            newSale.setTotal(newSale.getTotal().add(item.getPrice().multiply(BigDecimal.valueOf(item.getQty()))));
        }
    }

}
