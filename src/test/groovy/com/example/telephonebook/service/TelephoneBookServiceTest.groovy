package com.example.telephonebook.service

import com.example.telephonebook.domain.TelephoneBookEntity
import com.example.telephonebook.exeption.ApiException
import com.example.telephonebook.repository.TelephoneBookRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = TelephoneBookService)
class TelephoneBookServiceTest extends Specification {

    static final PHONE_NUM = '+7-912-111-11-11'
    static final PHONE_BOOK = new TelephoneBookEntity(numberPhone: PHONE_NUM)
    List<TelephoneBookEntity> phoneBookList = new ArrayList<>()
    Optional<List<TelephoneBookEntity>> phoneBookListOptional

    @Autowired
    TelephoneBookService telephoneBookService

    @SpringBean
    TelephoneBookRepository telephoneBookRepository = Stub()

    def "Add One phoneNumber"() {
        given:
        telephoneBookRepository.saveAndFlush(_) >> PHONE_BOOK

        when:
        def telephoneBook = telephoneBookService.addTelephoneNumber(PHONE_BOOK)
        then:
        telephoneBook
        telephoneBook == PHONE_BOOK
        noExceptionThrown()
    }

    def "Add Two phoneNumber"() {
        given:
        telephoneBookRepository.saveAndFlush(_) >> PHONE_BOOK

        when:
        def telephoneBook1 = telephoneBookService.addTelephoneNumber(PHONE_BOOK)
        def telephoneBook2 = telephoneBookService.addTelephoneNumber(PHONE_BOOK)
        then:
        telephoneBook1 == PHONE_BOOK
        telephoneBook2 == PHONE_BOOK
        noExceptionThrown()
    }

    def "Add phoneNumber with Exception"() {
        given:
        telephoneBookRepository.saveAndFlush(_) >> { throw new DataIntegrityViolationException("ouch") }

        when:
        def telephoneBook = telephoneBookService.addTelephoneNumber(PHONE_BOOK)

        then:
        !telephoneBook
        thrown(DataIntegrityViolationException)
    }

    def "Get one phoneNumber"() {
        given:
        phoneBookList.add(PHONE_BOOK)
        phoneBookListOptional = Optional.ofNullable(phoneBookList)
        telephoneBookRepository.getPhoneNumbers() >> phoneBookListOptional

        when:
        def telephoneBook = telephoneBookService.getTelephoneNumbers()

        then:
        telephoneBook
        telephoneBook.size() == 1
        telephoneBook == phoneBookList
        noExceptionThrown()
    }

    def "Get two phoneNumbers"() {
        given:
        phoneBookList.add(PHONE_BOOK)
        phoneBookList.add(PHONE_BOOK)
        phoneBookListOptional = Optional.ofNullable(phoneBookList)
        telephoneBookRepository.getPhoneNumbers() >> phoneBookListOptional

        when:
        def telephoneBook = telephoneBookService.getTelephoneNumbers()

        then:
        telephoneBook
        telephoneBook.size() == 2
        telephoneBook == phoneBookList
        noExceptionThrown()
    }

    def "Get phoneNumbers with exception not found"() {
        given:
        telephoneBookRepository.getPhoneNumbers() >> Optional.empty()

        when:
        def telephoneBook = telephoneBookService.getTelephoneNumbers()

        then:
        !telephoneBook
        def e = thrown(ApiException)
        e.getMessage() == "Not found telephone numbers"
    }
}
