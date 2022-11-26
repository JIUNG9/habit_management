package com.module.repository;

import com.module.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<com.module.entity.EmailToken,Integer> {
    EmailToken findByConfirmationToken(String confirmationToken);

}
