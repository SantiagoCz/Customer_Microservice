package santiagoczarny.customers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santiagoczarny.customers.entities.Customer;
import santiagoczarny.customers.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Long id){
        return customerRepository.findById(id);
    }

    public boolean existsByIdNumber(String idNumber) {
        return customerRepository.existsByIdNumber(idNumber);
    }

    public boolean existsByIdNumberAndIdNot(String idNumber, Long id) {
        return customerRepository.existsByIdNumberAndIdNot(idNumber, id);
    }

}
