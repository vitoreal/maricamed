package br.com.maricamed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.maricamed.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService userService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
                .antMatchers("/home/",  "/").permitAll()
                
                // Acessos privado admin
                .antMatchers("/usuarios/**").hasAuthority("ADMIN")
                
                // Acessos privado medico
                .antMatchers("/medicos/**").hasAuthority("MEDICOS")
                
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login-error")
                .permitAll()
                .and()
            .logout()
            	.logoutSuccessUrl("/")
                .permitAll();
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
    
}