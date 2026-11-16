package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;

public class ControllerUtil {

	private static String MESSAGE_KEY = "message";
	private static int ERROR = 0;
	private static int SUCESS = 1;

	static void forward(HttpServletRequest req, HttpServletResponse resp, String target)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(target);

		dispatcher.forward(req, resp);
	}

	static void redirect(HttpServletResponse resp, String target) throws IOException {
		resp.sendRedirect(target);
	}

	static void sucessMessage(HttpServletRequest req, String message) {
		req.getSession().setAttribute(MESSAGE_KEY, message);
		req.getSession().setAttribute("alertType", SUCESS);
	}

	static void errorMessage(HttpServletRequest req, String message) {
		req.getSession().setAttribute(MESSAGE_KEY, message);
		req.getSession().setAttribute("alertType", ERROR);
	}

	static void transferSessionMessagesToRequest(HttpServletRequest req) {
		if (req.getSession().getAttribute(MESSAGE_KEY) != null) {
			String message = (String) req.getSession().getAttribute(MESSAGE_KEY);
			req.setAttribute(MESSAGE_KEY, message);
			req.getSession().removeAttribute(MESSAGE_KEY);
		}

		if (req.getSession().getAttribute("alertType") != null) {
			Integer message = (Integer) req.getSession().getAttribute("alertType");
			req.setAttribute("alertType", message);
			req.getSession().removeAttribute("alertType");
		}
	}

	public static Date formatDate(String stringDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return dateFormat.parse(stringDate);
		} catch (ParseException e) {
			return null;
		}
	}

	// ---------- Novos métodos ----------

	// Caminho da requisição sem o context path (ex.: "/empresas/form").
	// Assim o projeto funciona com qualquer nome de contexto.
	static String path(HttpServletRequest req) {
		return req.getRequestURI().substring(req.getContextPath().length());
	}

	// Converte para int; retorna -1 se o valor for inválido
	static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	// Converte o valor de um <input type="datetime-local"> (ex.: 2026-11-20T14:30)
	static LocalDateTime parseLocalDateTime(String value) {
		try {
			return LocalDateTime.parse(value);
		} catch (Exception e) {
			return null;
		}
	}

	// Erro de chave estrangeira / UNIQUE do MySQL
	static boolean isConstraintViolation(ModelException e) {
		return e.getCause() instanceof SQLIntegrityConstraintViolationException;
	}
}
