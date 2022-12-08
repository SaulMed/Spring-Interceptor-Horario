package mx.springboot.horario_interceptor.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@Value("${config.horario.apertura}")
	private Integer apertura;
	
	@Value("${config.horario.cierre}")
	private Integer cierre;

	@GetMapping({"/", "/index"})
	public String index(Model modelo) {
		modelo.addAttribute("titulo","Biendenivo al horario de atencion");
		return "index";
	}
	
	
	@GetMapping("/cerrado")
	public String cerrado(Model modelo) {
		
		StringBuilder msj = new StringBuilder("Cerrado, por favor visitenos desde las ");
		msj.append(apertura);
		msj.append(" horas ");
		msj.append("hasta las ");
		msj.append(cierre);
		msj.append(" horas");
		
		modelo.addAttribute("titulo","Fuera de horario de atención.");
		modelo.addAttribute("mensaje",msj);
		
		return "cerrado";
	}
	
}
