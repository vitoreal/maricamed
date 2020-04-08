package br.com.maricamed.converter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.maricamed.entities.Especialidade;
import br.com.maricamed.services.EspecialidadeService;

@Component
public class EspecialidadeConverter implements Converter<String[], Set<Especialidade>> {

	@Autowired
	private EspecialidadeService service;
	
	@Override
	public Set<Especialidade> convert(String[] titulos) {
		Set<Especialidade> especialidades = new HashSet<>();
		if(titulos != null && titulos.length > 0) {
			especialidades.addAll(service.buscarPorTitulo(titulos));
		}
		return especialidades;
	}

}
