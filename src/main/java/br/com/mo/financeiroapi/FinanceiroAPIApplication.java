package br.com.mo.financeiroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.RestController;

@EnableRetry
@SpringBootApplication
@RestController
public class FinanceiroAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceiroAPIApplication.class, args);
    }

}
