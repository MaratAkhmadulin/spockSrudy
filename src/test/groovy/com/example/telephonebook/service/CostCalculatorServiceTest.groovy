package com.example.telephonebook.service

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification
import spock.lang.Timeout

import java.time.ZoneId
import java.time.ZonedDateTime

@Slf4j
@Timeout(1)
@ContextConfiguration(classes = CostCalculatorService)
@TestPropertySource(properties = ["mainmultiplier=100", "discountmultiplier=10"])
class CostCalculatorServiceTest extends Specification {
    @Autowired
    CostCalculatorService costCalculatorService
    @Value('${mainmultiplier}')
    private long mainMultiplier
    @Value('${discountmultiplier}')
    private long discountMultiplier

    private final int PHONE_COUNT = 2
    private final ZoneId ZONE_ID = ZoneId.of("Europe/Moscow")

    def "CalcCost beginning main tariff"() {
        given:
        def defaultTime = ZonedDateTime.of(2023, 1, 1, 6, 0, 1, 0, ZONE_ID)
        costCalculatorService.localTime = defaultTime.toLocalTime()
        println defaultTime.toLocalDateTime()

        when:
        def calcCost = costCalculatorService.calcCost(PHONE_COUNT)

        then:
        calcCost
        calcCost == PHONE_COUNT * mainMultiplier
        noExceptionThrown()
    }

    def "CalcCost end main tariff"() {
        given:
        def defaultTime = ZonedDateTime.of(2023, 1, 1, 23, 59, 59, 0, ZONE_ID)
        costCalculatorService.localTime = defaultTime.toLocalTime()
        println defaultTime.toLocalDateTime()

        when:
        def calcCost = costCalculatorService.calcCost(PHONE_COUNT)

        then:
        calcCost
        calcCost == PHONE_COUNT * mainMultiplier
        noExceptionThrown()
    }

    def "CalcCost beginning discount tariff"() {
        given:
        def defaultTime = ZonedDateTime.of(2023, 1, 1, 0, 0, 1, 0, ZONE_ID)
        costCalculatorService.localTime = defaultTime.toLocalTime()
        println defaultTime.toLocalDateTime()

        when:
        def calcCost = costCalculatorService.calcCost(PHONE_COUNT)

        then:
        calcCost
        calcCost == PHONE_COUNT * discountMultiplier
        noExceptionThrown()
    }

    def "CalcCost end discount tariff"() {
        given:
        def defaultTime = ZonedDateTime.of(2023, 1, 1, 6, 0, 0, 0, ZONE_ID)
        costCalculatorService.localTime = defaultTime.toLocalTime()
        println defaultTime.toLocalDateTime()

        when:
        def calcCost = costCalculatorService.calcCost(PHONE_COUNT)

        then:
        calcCost
        calcCost == PHONE_COUNT * discountMultiplier
        noExceptionThrown()
    }
}
