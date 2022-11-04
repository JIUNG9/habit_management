package com.module.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepo extends JpaRepository<EmailToken,Integer> {
    EmailToken findByConfirmationToken(String confirmationToken);

}
