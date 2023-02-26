package com.example.telephonebook.service

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Timeout

@Slf4j
@Timeout(1)
@ContextConfiguration(classes = WalletService)
class WalletServiceTest extends Specification {
    @Autowired
    WalletService walletService

    private final BigInteger COINS = 3

    def "MakePayment"() {
        when:
        def status = walletService.makePayment(COINS)

        then:
        status
        noExceptionThrown()

    }
}
