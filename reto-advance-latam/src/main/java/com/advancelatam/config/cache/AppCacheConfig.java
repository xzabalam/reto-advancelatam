package com.advancelatam.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class AppCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                // Usuarios
                new ConcurrentMapCache("PICO_PLACA"),
                new ConcurrentMapCache("TIPO_AUTO"),
                new ConcurrentMapCache("TIPO_AUTO_POR_TIPO"),
                new ConcurrentMapCache("TIPO_AUTO_POR_ID")
        ));

        return cacheManager;
    }

}
