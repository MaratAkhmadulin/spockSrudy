package com.example.telephonebook.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@Data
@Service
public class WalletService {
    public boolean makePayment(BigInteger coins){
        return true;
    }
}
