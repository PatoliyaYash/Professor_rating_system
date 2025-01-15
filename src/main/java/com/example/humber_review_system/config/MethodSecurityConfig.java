// src/main/java/com/example/humberreviewsystem/config/MethodSecurityConfig.java
package com.example.humber_review_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig {
}