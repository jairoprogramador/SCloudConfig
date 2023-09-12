package com.jairoprogramador.sc.config.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationController {
    @Value("${info.data:default}")
    private String data;

    @GetMapping("properties")
    public String viewDiscounts() {
        return "properties is = " + data;
    }
}
