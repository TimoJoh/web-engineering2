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

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        try {
            // SSL-Kontext, der alle Zertifikate akzeptiert
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(new TrustAllStrategy())
                    .build();

            // SSL-Socket-Factory mit deaktiviertem Hostname-Check
            var sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                    .setSslContext(sslContext)
                    .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();

            // Connection Manager mit SSL-Socket-Factory
            PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(sslSocketFactory)
                    .build();

            // HttpClient mit Connection Manager
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();

            // RestTemplate mit unsicherem HttpClient
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            return new RestTemplate(requestFactory);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Erstellen des unsicheren RestTemplate", e);
        }
    }
}
