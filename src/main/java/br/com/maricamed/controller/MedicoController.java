package br.com.maricamed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.maricamed.entities.Medico;
import br.com.maricamed.entities.Usuario;

/**
* <h1>Marica Med - controle de usuario!</h1>
* Usuario Controller - 
* Tratamento do login / logout / cadastro do usuário
* <p>
* Controle de acesso da api
* 
*
* @author  Vitor Almeida
* @version 1.0
* @since   2020-03-18 
*/

@Controller
@RequestMapping("medicos")
public class MedicoController {
	

    @GetMapping("/dados")
    public String abrirPorMe(Medico medico, Model model) {
        model.addAttribute("userForm", new Usuario());

        return "medico/cadastro";
    }

    

}
