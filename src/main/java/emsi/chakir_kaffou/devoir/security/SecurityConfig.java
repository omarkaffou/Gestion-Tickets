package emsi.chakir_kaffou.devoir.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import emsi.chakir_kaffou.devoir.utils.Constants;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests().antMatchers(Constants.API_PREFIX + "/devs/**").hasRole(Constants.getRole("dev"));
        // http.authorizeHttpRequests().antMatchers(Constants.API_PREFIX + "/admins/**").hasRole(Constants.getRole("admin"));
        // http.authorizeHttpRequests().antMatchers(Constants.API_PREFIX + "/clients/**").hasRole(Constants.getRole("client"));
        // http.authorizeRequests().anyRequest().authenticated();
        // http.httpBasic();
        http.cors().and().csrf().disable();
        // http.authorizeHttpRequests().anyRequest().permitAll();

        http.authorizeHttpRequests().antMatchers("/login/**", "/logout/**").permitAll();
        http.authorizeHttpRequests().antMatchers("/dev/**").hasRole(Constants.getRole("dev"));
        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole(Constants.getRole("admin"));
        http.authorizeHttpRequests().antMatchers("/client/**").hasRole(Constants.getRole("client"));
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.formLogin().and().logout().clearAuthentication(true).invalidateHttpSession(true);
    }
}