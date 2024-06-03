package libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import libreria.model.service.UsuarioDetailService;



@Configuration
public class SpringSecurityConfig {

    @Autowired
    UsuarioDetailService usuarioDetailService;
    
    @Bean
    static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // UserDetailsService userDetailsService() throws Exception {
    //     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

    //     manager.createUser(User.withUsername("jefe").password(passwordEncoder().encode("Abc123")).roles("ADMIN", "USER").build());
    //     manager.createUser(User.withUsername("luka").password(passwordEncoder().encode("Abc123")).roles("USER").build());

    //     return manager;
    // }

    @Autowired
    public void userDetailService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(usuarioDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/comercial/clienteslistar", "/comercial/productoslistar").permitAll()
                .requestMatchers("/comercial/clienteconsultar/**", "/comercial/productoconsultar/**").hasAnyRole("USER")
                .requestMatchers("/uploads/**").hasAnyRole("USER")
                .requestMatchers("/comercial/clientenuevo/**", "/comercial/clienteeliminar/**", "/comercial/clientemodificar/**").hasAnyRole("ADMIN")
                .requestMatchers("/comercial/productonuevo/**", "/comercial/productoeliminar/**", "/comercial/productomodificar/**").hasAnyRole("ADMIN")
                .requestMatchers("/comercial/facturanueva/**").hasAnyRole("ADMIN")
                //.requestMatchers("/mantenimiento").hasAnyRole("MMTO")
                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").permitAll())
                .logout(logout -> logout.permitAll())
                .exceptionHandling(handling -> handling.accessDeniedPage("/error_403"));
        return http.build();
    }


}
