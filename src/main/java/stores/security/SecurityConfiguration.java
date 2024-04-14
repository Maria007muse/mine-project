package stores.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(authorize -> authorize
                      .requestMatchers("/login", "/registration", "/", "/styles.css", "/style.css", "/images/**", "/registration/admin").permitAll()
                      .requestMatchers("/type/add", "/type/edit/**", "/type/delete/**", "/place/add", "/place/edit/**", "/place/delete/**", "/currency/add", "/currency/edit/**", "/currency/delete/**").hasRole("ADMIN")
                      .anyRequest().authenticated()
              )
              .formLogin((form) -> form
                      .loginPage("/login")
                      .permitAll()
                      .defaultSuccessUrl("/",true)
              )
              .logout((logout) -> logout
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/")
                      .invalidateHttpSession(true))
              .exceptionHandling(exception -> exception
                      .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                      .accessDeniedPage("/accessDenied"));
      return http.build();
   }
}