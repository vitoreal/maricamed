package br.com.maricamed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.maricamed.entities.Clinica;

/**
* <h1>Marica Med - controle de clinica!</h1>
* Usuario Controller - 
* Tratamento do login / logout / cadastro do usuário
* <p>
* Controle de clinicas
* 
*
* @author  Vitor Almeida
* @version 1.0
* @since   2020-03-18 
*/

@Controller
@RequestMapping("clinicas")
public class ClinicaController {
	

    @GetMapping("/dados")
    public String abrirPorClinica(Clinica clinica, ModelMap model) {

        return "clinica/cadastro";
    }
    
    @GetMapping("/salvar")
    public String salvar(Clinica clinica, ModelMap model) {
    	
        return "clinica/cadastro";
    }
    
    @GetMapping("/editar")
    public String editar(Clinica clinica, ModelMap model) {

        return "clinica/cadastro";
    }
    

}
