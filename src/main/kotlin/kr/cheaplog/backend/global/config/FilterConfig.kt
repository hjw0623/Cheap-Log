package kr.cheaplog.backend.global.config

import kr.cheaplog.backend.global.auth.FirebaseAuthFilter
import kr.cheaplog.backend.global.auth.InternalApiKeyFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig(
    private val firebaseAuthFilter: FirebaseAuthFilter,
    private val internalApiKeyFilter: InternalApiKeyFilter
) {

    @Bean
    fun internalApiKeyFilterRegistration(): FilterRegistrationBean<InternalApiKeyFilter> {
        return FilterRegistrationBean<InternalApiKeyFilter>().apply {
            filter = internalApiKeyFilter
            addUrlPatterns("/api/internal/*")
            order = 1
        }
    }

    @Bean
    fun firebaseAuthFilterRegistration(): FilterRegistrationBean<FirebaseAuthFilter> {
        return FilterRegistrationBean<FirebaseAuthFilter>().apply {
            filter = firebaseAuthFilter
            addUrlPatterns("/api/*")
            order = 2
        }
    }
}
