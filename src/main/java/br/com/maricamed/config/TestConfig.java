package br.com.maricamed.config;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.maricamed.entities.Perfil;
import br.com.maricamed.entities.Usuario;
import br.com.maricamed.repositories.PerfilRepository;
import br.com.maricamed.repositories.UsuarioRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Perfil pe1 = new Perfil();
		Perfil pe2 = new Perfil();
		Perfil pe3 = new Perfil();
		
		pe1.setId(null);
		pe1.setDesc("ADMIN");
		
		pe2.setId(null);
		pe2.setDesc("CLINICA");
		
		pe3.setId(null);
		pe3.setDesc("PACIENTE");
		
		perfilRepository.saveAll(Arrays.asList(pe1, pe2, pe3));
		
		List<Perfil> lpe = new ArrayList<Perfil>();
		
		Optional<Perfil> perf = perfilRepository.findById(1L);
		lpe.add(perf.get());
		
		Usuario user = new Usuario();
		user.setId(null);
		user.setDtCadastro(Instant.now());
		user.setNome("Root");
		user.setDtNascimento(Instant.parse("1981-11-14T19:53:07Z"));
		user.setEmail("vitoreselecao@gmail.com");
		user.setSenha("$2a$10$N.hRxj.F4LkpLwpslOqUTOGQYoU3vuKkfv1NYbs4vOloAjUmRuQLy");
		user.setAtivo(true);
		user.setPerfis(lpe);
		
		userRepository.save(user);
		
	}

}
