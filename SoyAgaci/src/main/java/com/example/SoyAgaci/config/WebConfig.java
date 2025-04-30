package com.example.SoyAgaci.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm uç noktalar için
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500") // İzin verilen kaynaklar
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen HTTP metodları
                .allowedHeaders("*") // İzin verilen başlıklar
                .allowCredentials(true) // Kimlik bilgilerine izin ver (örneğin, çerezler)
                .maxAge(3600); // Ön uçuş (preflight) isteklerinin önbellek süresi (saniye)
    }
}