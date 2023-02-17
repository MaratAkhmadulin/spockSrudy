package com.example.telephonebook.controller;

import com.example.telephonebook.domain.TelephoneBookEntity;
import com.example.telephonebook.service.TelephoneBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TelephoneNumberController {
    @Autowired
    TelephoneBookService telephoneBook;

    @GetMapping(path = "/phone/list}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TelephoneBookEntity> getTelephoneNumbers() {
        return telephoneBook.getTelephoneNumbers();
    }

    @PostMapping("/phone/add")
    TelephoneBookEntity addPhoneNumber(@RequestBody TelephoneBookEntity phoneNumber) {
            return telephoneBook.addTelephoneNumber(phoneNumber);
    }
}
