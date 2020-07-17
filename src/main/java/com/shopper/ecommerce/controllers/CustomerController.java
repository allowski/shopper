package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CustomerValidationException;
import com.shopper.ecommerce.models.Customer;
import com.shopper.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customers;

    /**
     * This method creates a new customer if it's email doesn't exist in the customer database,
     * also it make sure the data to be inserted is clean and valid according to the specs.
     * @param customerData
     * @return
     * @throws CustomerValidationException
     */
    @Transactional
    Customer createCustomerIfNotExists(Customer customerData) throws CustomerValidationException {

        if(customerData == null) {
            throw new CustomerValidationException("Invalid user data");
        }

        if(customerData.getId() != null && customerData.getId() > 0L) {

            Customer customer = customers.findById(customerData.getId()).get();

            if(customer != null) {

                return customer; // silently return the same customer if it exists

            }else{

                customerData.setId(null);

            }
        }

        // check for any null field
        checkForNull(customerData);

        // cleanUp numeric fields
        customerData = cleanUp(customerData);

        // validate data
        validate(customerData);

        try {

            // finally insert data after everything successfull
            return customers.save(customerData);

        }catch (Exception e){

            throw new CustomerValidationException("Unable to create this customer");

        }

    }

    /**
     * Avoid null pointers, this insures that there are no null properties before cleaning up.
     * @param customerData
     * @throws CustomerValidationException
     */
    private void checkForNull(Customer customerData) throws CustomerValidationException {

        if(customerData.getFullName() == null)
            throw new CustomerValidationException("Fullname is required");

        if(customerData.getDocument() == null)
            throw new CustomerValidationException("Document is required");

        if(customerData.getEmail() == null)
            throw new CustomerValidationException("Email is required");

        if(customerData.getPhoneNumber() == null)
            throw new CustomerValidationException("Phone number is required");

        if(customerData.getAddresses() == null)
            throw new CustomerValidationException("At least one address must be provided");
    }

    private Customer cleanUp(Customer customerData) {

        return customerData;
    }

    private  Customer validate(Customer customerData) throws CustomerValidationException {

        return customerData;
    }


}
