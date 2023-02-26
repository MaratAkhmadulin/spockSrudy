package com.example.telephonebook.service

import com.example.telephonebook.domain.TelephoneBookEntity
import com.example.telephonebook.exeption.ApiException
import lombok.extern.slf4j.Slf4j
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Timeout

@Slf4j
@Timeout(1)
@ContextConfiguration(classes = [PhoneAnalyzerService])
class PhoneAnalyzerServiceTest extends Specification {
    @Autowired
    PhoneAnalyzerService phoneAnalyzerService

    @SpringBean
    CostCalculatorService costCalculatorService = Mock()
    @SpringBean
    WalletService walletService = Stub(){
        makePayment(_) >> true
    }

    static final PHONE_NUM1 = '+7-912-111-11-11'
    static final PHONE_NUM2 = '+7-912-222-22-22'
    static final PHONE1 = new TelephoneBookEntity(numberPhone: PHONE_NUM1)
    static final PHONE2 = new TelephoneBookEntity(numberPhone: PHONE_NUM2)
    List<TelephoneBookEntity> phoneList1 = new ArrayList<>()
    List<TelephoneBookEntity> phoneList2 = new ArrayList<>()

    def "RemoveDoubles"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE1)

        when:
        def uniquePhoneList = phoneAnalyzerService.removeDoubles(phoneList1)

        then:
        uniquePhoneList
        uniquePhoneList.size() == 1
        uniquePhoneList =~ phoneList1
        1 * costCalculatorService.calcCost(2)
        noExceptionThrown()
    }

    def "RemoveDoubles with two unique phone"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE2)

        when:
        def uniquePhoneList = phoneAnalyzerService.removeDoubles(phoneList1)

        then:
        uniquePhoneList
        uniquePhoneList.size() == 2
        uniquePhoneList =~ phoneList1
        1 * costCalculatorService.calcCost(2)
        noExceptionThrown()
    }

    def "RemoveDoubles with empty list"() {
        when:
        def uniquePhoneList = phoneAnalyzerService.removeDoubles(phoneList1)

        then:
        !uniquePhoneList
        1 * costCalculatorService.calcCost(0)
        noExceptionThrown()
    }

    def "RemoveDoubles with null"() {
        given:
        phoneList1 = null

        when:
        def uniquePhoneList = phoneAnalyzerService.removeDoubles(phoneList1)

        then:
        !uniquePhoneList
        0 * costCalculatorService.calcCost(_)
        thrown(ApiException)
    }

    def "getBiggerPhoneList list1Size > list2Size"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE2)
        phoneList2.add(PHONE1)

        when:
        def biggerPhoneList = phoneAnalyzerService.getBiggerPhoneList(phoneList1, phoneList2)

        then:
        biggerPhoneList
        biggerPhoneList == phoneList1
        1 * costCalculatorService.calcCost(2)
        1 * costCalculatorService.calcCost(1)
        noExceptionThrown()
    }

    def "getBiggerPhoneList list1Size = list2Size"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE1)
        phoneList2.add(PHONE2)

        when:
        def biggerPhoneList = phoneAnalyzerService.getBiggerPhoneList(phoneList1, phoneList2)

        then:
        biggerPhoneList
        biggerPhoneList == phoneList1
        1 * costCalculatorService.calcCost(2)
        1 * costCalculatorService.calcCost(1)
        noExceptionThrown()
    }

    def "getBiggerPhoneList list1Size < list2Size"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE1)
        phoneList2.add(PHONE1)
        phoneList2.add(PHONE2)

        when:
        def biggerPhoneList = phoneAnalyzerService.getBiggerPhoneList(phoneList1, phoneList2)

        then:
        biggerPhoneList
        biggerPhoneList == phoneList2
        2 * costCalculatorService.calcCost(2)
        noExceptionThrown()
    }

    def "getBiggerPhoneList list1 = null"() {
        given:
        phoneList1 = null
        phoneList2.add(PHONE1)
        phoneList2.add(PHONE2)

        when:
        def biggerPhoneList = phoneAnalyzerService.getBiggerPhoneList(phoneList1, phoneList2)

        then:
        !biggerPhoneList
        0 * costCalculatorService.calcCost(_)
        thrown(ApiException)
    }

    def "getBiggerPhoneList list1 = empty list"() {
        given:
        phoneList2.add(PHONE1)

        when:
        def biggerPhoneList = phoneAnalyzerService.getBiggerPhoneList(phoneList1, phoneList2)

        then:
        biggerPhoneList
        biggerPhoneList == phoneList2
        1 * costCalculatorService.calcCost(0)
        1 * costCalculatorService.calcCost(1)
        noExceptionThrown()
    }


    def "getSmallerPhoneList list1Size > list2Size"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE2)
        phoneList2.add(PHONE1)

        when:
        def biggerPhoneList = phoneAnalyzerService.getSmallerPhoneList(phoneList1, phoneList2)

        then:
        biggerPhoneList
        biggerPhoneList == phoneList2
        1 * costCalculatorService.calcCost(2)
        1 * costCalculatorService.calcCost(1)
        noExceptionThrown()
    }

    def "getSmallerPhoneList list1Size = list2Size"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE1)
        phoneList2.add(PHONE2)

        when:
        def biggerPhoneList = phoneAnalyzerService.getSmallerPhoneList(phoneList1, phoneList2)

        then:
        biggerPhoneList
        biggerPhoneList == phoneList1
        1 * costCalculatorService.calcCost(2)
        1 * costCalculatorService.calcCost(1)
        noExceptionThrown()
    }

    def "getSmallerPhoneList list1Size < list2Size"() {
        given:
        phoneList1.add(PHONE1)
        phoneList1.add(PHONE1)
        phoneList2.add(PHONE1)
        phoneList2.add(PHONE2)

        when:
        def biggerPhoneList = phoneAnalyzerService.getSmallerPhoneList(phoneList1, phoneList2)

        then:
        biggerPhoneList
        biggerPhoneList == phoneList1
        2 * costCalculatorService.calcCost(2)
        noExceptionThrown()
    }

    def "getSmallerPhoneList list1 = null"() {
        given:
        phoneList1 = null
        phoneList2.add(PHONE1)
        phoneList2.add(PHONE2)

        when:
        def biggerPhoneList = phoneAnalyzerService.getSmallerPhoneList(phoneList1, phoneList2)

        then:
        !biggerPhoneList
        0 * costCalculatorService.calcCost(_)
        thrown(ApiException)
    }

    def "getSmallerPhoneList list1 = empty list"() {
        given:
        phoneList2.add(PHONE1)

        when:
        def biggerPhoneList = phoneAnalyzerService.getSmallerPhoneList(phoneList1, phoneList2)

        then:
        !biggerPhoneList
        biggerPhoneList == phoneList1
        1 * costCalculatorService.calcCost(0)
        1 * costCalculatorService.calcCost(1)
        noExceptionThrown()
    }

    def "Not money in the wallet"() {
        given:
        phoneList1.add(PHONE1)

        when:
        walletService.makePayment(_) >> false
        def uniquePhoneList = phoneAnalyzerService.removeDoubles(phoneList1)

        then:
        !uniquePhoneList
        1 * costCalculatorService.calcCost(1)
        thrown(ApiException)
    }
}
