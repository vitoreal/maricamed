package br.com.maricamed.config;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.maricamed.entities.Especialidade;
import br.com.maricamed.entities.Perfil;
import br.com.maricamed.entities.Usuario;
import br.com.maricamed.repositories.EspecialidadeRepository;
import br.com.maricamed.repositories.PerfilRepository;
import br.com.maricamed.repositories.UsuarioRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private EspecialidadeRepository especialidadeRepository;

	@Override
	public void run(String... args) throws Exception {
		
		listaEspecialidades();
		listaPerfil();
		listaUsuario();
		
	}
	
	public void listaUsuario() {
		
		List<Perfil> lpe = new ArrayList<Perfil>();
		
		Optional<Perfil> perf = perfilRepository.findById(1L);
		lpe.add(perf.get());
		
		Usuario user = new Usuario();
		Usuario user2 = new Usuario();
		
		user.setId(null);
		user.setDtCadastro(Instant.now());
		user.setTelefone1("(21) 98807-7118");
		user.setNome("Root");
		//user.setDtNascimento(Instant.parse("1981-11-14T19:53:07Z"));
		user.setEmail("vitoreselecao@gmail.com");
		// senha 123456
		user.setSenha("$2a$10$N.hRxj.F4LkpLwpslOqUTOGQYoU3vuKkfv1NYbs4vOloAjUmRuQLy");
		user.setAtivo(true);
		user.setPerfis(lpe);
		
		user2.setId(null);
		user2.setDtCadastro(Instant.now());
		user2.setTelefone1("(21) 12347-7894");
		user2.setNome("Frank Lyra");
		//user.setDtNascimento(Instant.parse("1981-11-14T19:53:07Z"));
		user2.setEmail("frank.desenv@gmail.com");
		// senha 123456
		user2.setSenha("$2a$10$N.hRxj.F4LkpLwpslOqUTOGQYoU3vuKkfv1NYbs4vOloAjUmRuQLy");
		user2.setAtivo(true);
		user2.setPerfis(lpe);
		
		userRepository.save(user);
		userRepository.save(user2);
		
	}
	
	public void listaPerfil() {
		
		Perfil pe1 = new Perfil();
		Perfil pe2 = new Perfil();
		Perfil pe3 = new Perfil();
		Perfil pe4 = new Perfil();
		
		pe1.setId(null);
		pe1.setDesc("ADMIN");
		
		pe2.setId(null);
		pe2.setDesc("CLINICA");
		
		pe3.setId(null);
		pe3.setDesc("MEDICO");
		
		pe4.setId(null);
		pe4.setDesc("PACIENTE");
		
		perfilRepository.saveAll(Arrays.asList(pe1, pe2, pe3, pe4));
		
	}
	
	public void listaEspecialidades() {
		
		Map<String, String> nomeEsp = new HashMap<String, String>();
		nomeEsp.put("Alergia e Imunologia", "diagnóstico e tratamento das doenças alérgicas e do sistema imunológico.");
		nomeEsp.put("Anestesiologia", "Área da Medicina que envolve o tratamento da dor, a hipnose e o manejo intensivo do paciente sob intervenção cirúrgica ou procedimentos.");
		nomeEsp.put("Angiologia", "é a área da medicina que estuda o tratamento das doenças do aparelho circulatório.");
		nomeEsp.put("Oncologia", "é a especialidade que trata dos tumores malignos ou câncer.");
		nomeEsp.put("Cardiologia", "aborda as doenças relacionadas com o coração e sistema vascular.");
		nomeEsp.put("Cirurgia Cardiovascular", "tratamento cirúrgico de doenças do coração.");
		nomeEsp.put("Cirurgia da Mão", "cuida das doenças das mãos e dos punhos, incluindo os ossos, articulações, tendões, músculos, nervos, vasos e pele.");
		nomeEsp.put("Cirurgia de cabeça e pescoço", "tratamento cirúrgico de doenças da cabeça e do pescoço.");
		nomeEsp.put("Cirurgia do Aparelho Digestivo:", "tratamento clínico e cirúrgico dos órgãos do aparelho digestório, como o esôfago, estômago, intestinos, fígado e vias biliares, e pâncreas.");
		nomeEsp.put("Cirurgia Geral", "é a área que engloba todas as áreas cirúrgicas, sendo também subdividida.");
		nomeEsp.put("Cirurgia Pediátrica", "cirurgia geral em crianças.");
		nomeEsp.put("Cirurgia Plástica", "correção das deformidades, malformações ou lesões que comprometem funções dos órgãos através de cirurgia de caráter reparador ou cirurgias estéticas.");
		nomeEsp.put("Cirurgia Torácica", "atua na cirurgia da caixa torácica e vias aéreas.");
		nomeEsp.put("Cirurgia Vascular", "tratamento das veias e artérias, através de cirurgia, procedimentos endovasculares ou tratamentos clínicos.");
		nomeEsp.put("Clínica Médica (Medicina interna)", "é a área que engloba todas as áreas não cirúrgicas, sendo subdividida em várias outras especialidades.");
		
		for (Map.Entry<String, String> entry : nomeEsp.entrySet()) {
	        String key = entry.getKey();
	        String value = entry.getValue();
	        
	        Especialidade esp = new Especialidade();
			esp.setId(null);
			esp.setTitulo(key);
			esp.setDescricao(value);

			especialidadeRepository.save(esp);
	    }

	}

}
