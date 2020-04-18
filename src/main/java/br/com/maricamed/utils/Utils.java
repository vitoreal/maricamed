package br.com.maricamed.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.com.maricamed.entities.PerfilTipo;
import br.com.maricamed.entities.Usuario;

public final class Utils {
	
	public static String converteInstantToDate(Instant dateInstant) {
		
		Date dtInscricaoInstant = Date.from(dateInstant);
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		return formatter.format(dtInscricaoInstant);
	}
	
	
	 // Verifica se o perfil diferente do admin mudou o param passado para tentar mudar o dado de outro usuario
	public static boolean verificaSeUserSessionIgualUserParam(Long iduser, HttpSession session) {
		
		Usuario userSession = (Usuario) session.getAttribute("user");
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		boolean isAdmin = false;
		
		for (GrantedAuthority grantedAuthority : user.getAuthorities()){
			session.setAttribute("perfil", grantedAuthority.getAuthority());
	        if (grantedAuthority.getAuthority().equals(PerfilTipo.ADMIN.getDesc())) {
	        	isAdmin = true;
	        }
	    }
		if (isAdmin == false) {
			if (userSession != null && userSession.getId() != iduser) {
				return true;
			}
		}
		
		return false;
	}

}
