package com.jairoprogramador.sc.config.client.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class AppProperties {
    @Value("${info.data:default}")
    private String data;

    public String getData() {
        return data;
    }

}
