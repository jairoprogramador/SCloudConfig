package com.jairoprogramador.sc.config.client.controller;

import com.jairoprogramador.sc.config.client.properties.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationController {

    private final AppProperties properties;

    public InformationController(AppProperties properties) {
        this.properties = properties;
    }

    @GetMapping("properties")
    public String viewDiscounts() {
        return "properties is = " + properties.getData();
    }
}
