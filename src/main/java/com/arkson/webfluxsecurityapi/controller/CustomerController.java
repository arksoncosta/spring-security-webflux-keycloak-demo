package com.arkson.webfluxsecurityapi.controller;

import com.arkson.webfluxsecurityapi.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @GetMapping
    public Flux<Customer> getAll() {
        return
                Flux.just(
                        Customer
                                .builder()
                                .id(UUID.randomUUID().toString())
                                .firstName("Arkson")
                                .lastName("Costa")
                                .email("email@email.com")
                                .build()
                );
    }

}
