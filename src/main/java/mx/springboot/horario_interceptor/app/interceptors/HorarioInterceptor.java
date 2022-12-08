package mx.springboot.horario_interceptor.app.interceptors;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("horarioInterceptor")
public class HorarioInterceptor implements HandlerInterceptor {

	@Value("${config.horario.apertura}")
	private Integer apertura;

	@Value("${config.horario.cierre}")
	private Integer cierre;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);

		if (hora >= apertura && hora < cierre) { // Si se encuentra dentro del horario establecido cumple
			StringBuilder mensaje = new StringBuilder("Bienvenido al horario de atencion clientes");
			mensaje.append(", brindando servicio desde las ");
			mensaje.append(apertura);
			mensaje.append("horas");
			mensaje.append(" hasta las");
			mensaje.append(cierre);
			mensaje.append(" horas.");
			mensaje.append(" Gracias por su visita");

			request.setAttribute("mensajeHorario", mensaje.toString()); // Pasar Data a la vista

			return true;
		}
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String msj = (String) request.getAttribute("mensajeHorario");

		if (modelAndView != null && handler instanceof HandlerMethod) {
			modelAndView.addObject("horario", msj);
		}

	}

}
