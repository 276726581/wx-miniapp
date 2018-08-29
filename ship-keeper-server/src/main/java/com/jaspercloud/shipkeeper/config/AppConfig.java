package com.jaspercloud.shipkeeper.config;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Configuration
public class AppConfig {

    @Bean
    public Gson gson() {
        Gson gson = new Gson();
        return gson;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .build();
        return okHttpClient;
    }

    @Bean
    public RestTemplate restTemplate(OkHttpClient okHttpClient) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .requestFactory(new Supplier<ClientHttpRequestFactory>() {
                    @Override
                    public ClientHttpRequestFactory get() {
                        return new OkHttp3ClientHttpRequestFactory(okHttpClient);
                    }
                })
                .setConnectTimeout(30 * 1000)
                .setReadTimeout(30 * 1000)
                .messageConverters()
                .build();
        return restTemplate;
    }
}
