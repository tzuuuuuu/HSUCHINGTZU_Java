package com.library.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允許所有從前端發過來的 API 請求
        registry.addMapping("/**")
                // 允許來自任何來源的網頁（Vue 預設常在 5173 或 8080，我們直接全開，開發最方便）
                .allowedOriginPatterns("*")
                // 允許的 HTTP 方法（GET 查書、POST 註冊、PUT 修改...等）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允許前端攜帶的 Header 資訊
                .allowedHeaders("*")
                // 允許攜帶 Cookie 等認證憑證
                .allowCredentials(true)
                // 這張通行證的有效時間（一小時內不用重複檢查）
                .maxAge(3600);
    }
}