package com.example.telephonebook.repository;

import com.example.telephonebook.domain.TelephoneBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TelephoneBookRepository extends JpaRepository<TelephoneBookEntity, String> {
    Optional<List<TelephoneBookEntity>> getPhoneNumbers();
}
