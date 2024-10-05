package santiagoczarny.customers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santiagoczarny.customers.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByIdNumber(String idNumber);
    boolean existsByIdNumberAndIdNot(String idNumber, Long id);
}
