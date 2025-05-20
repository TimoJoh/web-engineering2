package com.weatherApp.WeatherWeb.api;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

/**
 * The {@code AppConfig} class is responsible for defining application-wide
 * configurations such as the creation of a custom {@link RestTemplate} bean.
 * This configuration explicitly disables SSL verification for the purpose of
 * enabling API communication from within secured work environments.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a {@link RestTemplate} bean with a custom HTTP client configuration
     * that disables SSL verification. This is intentionally done to facilitate API
     * communication on secured work computers that might otherwise block requests
     * due to strict SSL checks.
     *
     * @return a {@link RestTemplate} instance configured with an HTTP client that
     *         bypasses SSL verification.
     * @throws RuntimeException if there is an error during the creation of the RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        try {
            // Create an SSL context that accepts all certificates
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(new TrustAllStrategy()) // Trusts all certificates
                    .build();

            // Create an SSL socket factory with disabled hostname verification
            var sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                    .setSslContext(sslContext)
                    .setHostnameVerifier(NoopHostnameVerifier.INSTANCE) // Disables hostname verification
                    .build();

            // Create a connection manager that uses the custom SSL socket factory
            PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(sslSocketFactory)
                    .build();

            // Create an HTTP client with the custom connection manager
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();

            // Configure the RestTemplate to use the custom HTTP client
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            return new RestTemplate(requestFactory);

        } catch (Exception e) {
            // Throw a runtime exception if any configuration step fails
            throw new RuntimeException("Error while creating the insecure RestTemplate", e);
        }
    }
}