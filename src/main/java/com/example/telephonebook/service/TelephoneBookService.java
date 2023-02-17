package com.example.telephonebook.service;

import com.example.telephonebook.domain.TelephoneBookEntity;
import com.example.telephonebook.exeption.ApiException;
import com.example.telephonebook.repository.TelephoneBookRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Data
@Service
@Transactional
public class TelephoneBookService {
    @Autowired
    TelephoneBookRepository telephoneBookRepository;

    public TelephoneBookEntity addTelephoneNumber(TelephoneBookEntity telephoneBook) {
        return telephoneBookRepository.saveAndFlush(telephoneBook);
    }

    public List<TelephoneBookEntity> getTelephoneNumbers() {
        return telephoneBookRepository.getPhoneNumbers()
                .orElseThrow(() -> new ApiException("Not found telephone numbers"));
    }
}
