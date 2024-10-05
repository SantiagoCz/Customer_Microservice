package santiagoczarny.customers.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import santiagoczarny.customers.classes.OrderDto;
import santiagoczarny.customers.classes.Validations;
import santiagoczarny.customers.clients.OrderClient;
import santiagoczarny.customers.entities.Customer;
import santiagoczarny.customers.services.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderClient orderClient;

    @GetMapping("/all")
    public List<Customer> findAllOrders(){
        return customerService.findAllCustomers();
    }

    @GetMapping("/get-id={id}")
    public Customer getOrderById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findCustomerById(id);

        return customerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id));
    }

    @GetMapping("/get-all-orders-by-customer={id}")
    public List<OrderDto> getOrdersByCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findCustomerById(id);

        customerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id));

        Customer customer = customerOptional.get();
        return orderClient.getOrdersByCustomer(customer.getId());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid Customer request,
                                  BindingResult result) {
        ResponseEntity<?> validationResponse = Validations.handleValidationErrors(result);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            if (customerService.existsByIdNumber(request.getIdNumber())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id number must be unique.");
            }

            customerService.saveCustomer(request);
            return ResponseEntity.ok("Customer has been saved succesfully.");

        } catch (Exception e) {

            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/edit-{id}")
    public ResponseEntity<?> edit(@RequestBody @Valid Customer request,
                                  @PathVariable Long id,
                                  BindingResult result) {
        ResponseEntity<?> validationResponse = Validations.handleValidationErrors(result);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            // Find the current customer by ID
            Optional<Customer> optionalCustomer = customerService.findCustomerById(id);
            optionalCustomer.orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id));

            Customer customer = optionalCustomer.get();

            // Check if another customer with the same idNumber exists, excluding the current one
            if (customerService.existsByIdNumberAndIdNot(request.getIdNumber(), id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id number must be unique.");
            }

            // Update the customer fields
            customer.setIdNumber(request.getIdNumber());
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setPhoneNumber(request.getPhoneNumber());
            customer.setAddress(request.getAddress());

            // Save the updated customer
            customerService.saveCustomer(customer);
            return ResponseEntity.ok("Customer has been saved successfully.");

        } catch (Exception e) {
            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }


}
