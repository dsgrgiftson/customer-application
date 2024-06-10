package org.giftson.david.abnamro.repository;

import java.util.Optional;
import org.giftson.david.abnamro.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {
    Optional<CustomerDetails> findByUsernameAndPassword(String username, String password);

    Optional<CustomerDetails> findByUsername(String username);
}
