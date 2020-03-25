package br.com.maricamed.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	//@Autowired
	//private UsuarioRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		
		//User u1 = new User(null, "Maria Brown", "maria@gmail.com", true);
		//User u2 = new User(null, "Alex Green", "alex@gmail.com", true); 
		
		//userRepository.saveAll(Arrays.asList(u1, u2));
		
	}

}
