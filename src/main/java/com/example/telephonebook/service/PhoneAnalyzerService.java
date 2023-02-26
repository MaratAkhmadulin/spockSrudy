package com.example.telephonebook.service;

import com.example.telephonebook.domain.TelephoneBookEntity;
import com.example.telephonebook.exeption.ApiException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
@Service
public class PhoneAnalyzerService {
    @Autowired
    CostCalculatorService costCalculatorService;
    @Autowired
    WalletService walletService;

    Set<TelephoneBookEntity> removeDoubles(List<TelephoneBookEntity> phones) {
        if (phones == null) {
            throw new ApiException("Ошибка, список номеров = null");
        } else {
            BigInteger cost = costCalculatorService.calcCost(phones.size());
            log.info("Стоимость = {}", cost);
            if (!walletService.makePayment(cost)) {
                throw new ApiException("Ошибка! Недостаточно средств на счете");
            }

            return new HashSet<>(phones);
        }
    }

    List<TelephoneBookEntity> getBiggerPhoneList(List<TelephoneBookEntity> phoneList1, List<TelephoneBookEntity> phoneList2) {
        return removeDoubles(phoneList1).size() >= removeDoubles(phoneList2).size() ? phoneList1 : phoneList2;
    }

    List<TelephoneBookEntity> getSmallerPhoneList(List<TelephoneBookEntity> phoneList1, List<TelephoneBookEntity> phoneList2) {
        return removeDoubles(phoneList1).size() <= removeDoubles(phoneList2).size() ? phoneList1 : phoneList2;
    }
}
