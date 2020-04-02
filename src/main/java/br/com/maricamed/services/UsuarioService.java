package br.com.maricamed.services;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.maricamed.datatables.Datatables;
import br.com.maricamed.datatables.DatatablesColunas;
import br.com.maricamed.entities.Perfil;
import br.com.maricamed.entities.Usuario;
import br.com.maricamed.repositories.UsuarioRepository;
import br.com.maricamed.service.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private Datatables datatables;
	
	@Transactional(readOnly = true)
	public List<Usuario> findAll(){
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Usuario findById(Long id){
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional(readOnly = true)
	public Usuario buscaPorEmail(String email){
		return repository.findByEmail(email);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		Usuario usuario = buscaPorEmail(username);
		if (usuario == null)
			throw new UsernameNotFoundException(username);
		

		return new User(usuario.getEmail(), usuario.getSenha(),
				AuthorityUtils.createAuthorityList(getAuthorities(usuario.getPerfis())));

	}
	
	private String[] getAuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDesc();
		}
		return authorities;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.USUARIOS);
		Page<?> page = datatables.getSearch().isEmpty() 
				? repository.findAll(datatables.getPageable()) 
				: repository.findAllByNamePerfilEmail(datatables.getSearch(), datatables.getPageable());
		
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = false)
	public void salvar(Usuario usuario) {
		
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		usuario.setDtCadastro(Instant.now());
		repository.save(usuario);
		
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorIdEPerfis(Long usuarioId, Long[] perfisId) {
		repository.findByIdAndPerfis(usuarioId, perfisId);
		return null;
	}
}
