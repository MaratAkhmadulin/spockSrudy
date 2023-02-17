package com.example.telephonebook.repository;

import com.example.telephonebook.domain.TelephoneBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelephoneBookRepository extends JpaRepository<TelephoneBookEntity, Long> {

    List<TelephoneBookEntity> findAll();
}
