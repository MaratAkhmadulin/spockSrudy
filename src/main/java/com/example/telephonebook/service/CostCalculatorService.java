package com.example.telephonebook.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;

@Slf4j
@Data
@Service
public class CostCalculatorService {
    @Value("${mainmultiplier}")
    private long mainMultiplier;
    @Value("${discountmultiplier}")
    private long discountMultiplier;

    private LocalTime localTime = LocalTime.now(Clock.system(ZoneId.of("Europe/Moscow")));

    public BigInteger calcCost(int phoneCount){
        BigInteger cost;
        if (localTime.isAfter(LocalTime.parse("00:00:00.000000000")) && localTime.isBefore(LocalTime.parse("06:00:00.000000001"))){
            cost = BigInteger.valueOf(phoneCount * discountMultiplier);
        }
        else{
            cost = BigInteger.valueOf(phoneCount * mainMultiplier);
        }
        return cost;
    }
}
