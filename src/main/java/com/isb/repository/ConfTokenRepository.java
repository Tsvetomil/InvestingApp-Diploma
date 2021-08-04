package com.isb.repository;

import com.isb.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken getConfirmationTokenByCode(String code);
}
