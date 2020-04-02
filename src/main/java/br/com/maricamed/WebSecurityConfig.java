package br.com.maricamed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.maricamed.entities.PerfilTipo;
import br.com.maricamed.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String ADMIN = PerfilTipo.ADMIN.getDesc();
	private static final String CLINICA = PerfilTipo.CLINICA.getDesc();
	private static final String MEDICO = PerfilTipo.MEDICO.getDesc();
	private static final String PACIENTE = PerfilTipo.PACIENTE.getDesc();
	
	@Autowired
	private UsuarioService userService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        
            .authorizeRequests()
                .antMatchers("/webjars/**", "/assets/**", "/css/**", "/image/**", "/img/**", "/js/**", "/util/**").permitAll()
                .antMatchers("/home/",  "/").permitAll()
                
                // Acessos privado admin
                .antMatchers("/usuarios/**, /especialidades/**").hasAuthority(ADMIN)
                
                // Acessos privado medico
                .antMatchers("/medicos/**").hasAuthority(MEDICO)
                
                // Acessos privado clinica
                .antMatchers("/clinicas/**").hasAuthority(CLINICA)
                
                // Acessos privado paciente
                .antMatchers("/pacientes/**").hasAuthority(PACIENTE)
                
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
                .permitAll()
                .and()
             .exceptionHandling()
             .accessDeniedPage("/acesso-negado");
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
    
}