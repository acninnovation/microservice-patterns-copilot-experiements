package no.acntech.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import no.acntech.customer.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCustomerId(UUID customerId);

    List<CustomerEntity> findAllByFirstName(String firstName);

    List<CustomerEntity> findAllByLastName(String lastName);

    List<CustomerEntity> findAllByFirstNameAndLastName(String firstName, String lastName);
}
