package com.shopper.ecommerce.controllers;

import com.shopper.ecommerce.exceptions.CustomerValidationException;
import com.shopper.ecommerce.exceptions.EmailInUseException;
import com.shopper.ecommerce.models.Customer;
import com.shopper.ecommerce.repositories.CustomerRepository;
import com.shopper.ecommerce.utils.NumberUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Alberto Miranda Dendowski
 * This controller handles everything related to customer accounts, it relays on
 * the customerRepository for fetching and persisting customer data.
 */
@Controller
public class CustomerController {

    NumberUtil numberUtil = new NumberUtil();

    @Autowired
    CustomerRepository customers;

    /**
     * This method creates a new customer if it's email doesn't exist in the customer database,
     * also it make sure the data to be inserted is clean and valid according to the specs.
     * @param customerData
     * @return
     * @throws CustomerValidationException
     */
    @SneakyThrows
    @Transactional
    Customer createCustomerIfNotExists(Customer customerData){

        if(customerData == null) {
            throw new CustomerValidationException("Invalid user data");
        }

        if(customerData.getId() != null && customerData.getId() > 0L) {

            Optional<Customer> customerOptional = customers.findById(customerData.getId());

            if(customerOptional.isPresent()) {

                return customerOptional.get(); // silently return the same customer if it exists

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
    public void checkForNull(Customer customerData) throws CustomerValidationException {

        if(customerData.getFullName() == null)
            throw new CustomerValidationException("Full name is required");

        if(customerData.getDocument() == null)
            throw new CustomerValidationException("Document is required");

        if(customerData.getEmail() == null)
            throw new CustomerValidationException("Email is required");

        if(customerData.getPhoneNumber() == null)
            throw new CustomerValidationException("Phone number is required");
    }

    /**
     * Remove unwanted chars from numeric fields and trims the whole model
     * @param customerData
     * @return
     */
    public Customer cleanUp(Customer customerData) {

        customerData.setDocument(numberUtil.replaceEverythingButNumbers(customerData.getDocument()));

        customerData.setPhoneNumber(numberUtil.replaceEverythingButNumbers(customerData.getPhoneNumber()));

        customerData.setFullName(customerData.getFullName().trim());

        customerData.setEmail(customerData.getEmail().trim());

        return customerData;
    }

    /**
     * Validates user data
     * @param customerData
     * @return
     * @throws CustomerValidationException
     * TODO: Proper validate emails
     * TODO: Proper validate cpf
     * TODO: Proper validate name
     */

    public  Customer validate(Customer customerData) throws CustomerValidationException {

        if(customerData.getFullName().trim().length() < 5)
            throw new CustomerValidationException("Full name is invalid");

        if(customerData.getDocument().trim().length() < 11)
            throw new CustomerValidationException("Invalid document length");

        if(customerData.getPhoneNumber().trim().length() != 11)
            throw new CustomerValidationException("Invalid phoneNumber length");

        if(!customerData.getEmail().contains("@"))
            throw new CustomerValidationException("Invalid email format");

        return customerData;
    }

    /**
     * Checks if user email is already taken, if so, throws an exception
     * @param customerData
     * @return
     */
    @SneakyThrows
    public Customer registerCustomer(Customer customerData) {

        // check for null pointers
        checkForNull(customerData);

        // cleanup data
        cleanUp(customerData);

        // validate data
        validate(customerData);

        // find users with the given email
        Optional<Customer> matching = customers.findByEmail(customerData.getEmail());

        if(matching.isPresent())
            throw new EmailInUseException("The given email is already in use by another account.");

        return createCustomerIfNotExists(customerData);
    }
}
