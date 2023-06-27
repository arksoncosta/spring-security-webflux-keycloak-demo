package com.arkson.webfluxsecurityapi.controller;

import com.arkson.webfluxsecurityapi.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public Mono<Customer> findCustomer() {
        return Mono.just(Customer
                .builder()
                .id(UUID.randomUUID().toString())
                .firstName("Bilbo")
                .lastName("Baggagins")
                .build());
    }

}
