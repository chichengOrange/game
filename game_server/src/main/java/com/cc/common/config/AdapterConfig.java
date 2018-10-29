//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cc.common.config;

import com.cc.common.interceptor.MyAdapterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
public class AdapterConfig implements WebMvcConfigurer {
    @Autowired
    private MyAdapterInterceptor myAdapterInterceptor;


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/static/**"}).addResourceLocations(new String[]{"classpath:/static/"});
        registry.addResourceHandler(new String[]{"/templates/**"}).addResourceLocations(new String[]{"classpath:/templates/"});
        registry.addResourceHandler(new String[]{"/swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.myAdapterInterceptor).addPathPatterns(new String[]{"/**"}).excludePathPatterns(new String[]{"/swagger*/**", "/webjars/**", "/v2*/**", "/swagger-ui.html"}).excludePathPatterns(new String[]{"/static/**", "/templates/**"});
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods(new String[]{"POST, GET, OPTIONS, HEAD,PUT,PATCH,DELETE"}).allowedOrigins(new String[]{"*"});
    }
}
