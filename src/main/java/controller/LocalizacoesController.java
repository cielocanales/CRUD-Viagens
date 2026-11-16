package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Localizacao;
import model.ModelException;
import model.dao.DAOFactory;
import model.dao.LocalizacaoDAO;

@WebServlet(urlPatterns = {"/localizacoes", "/localizacoes/form", "/localizacoes/delete", "/localizacoes/insert", "/localizacoes/update"})
public class LocalizacoesController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = ControllerUtil.path(req);

		switch (action) {

		case "/localizacoes": {
			listLocalizacoes(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/localizacoes.jsp");
			break;
		}
		case "/localizacoes/form": {
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-localizacao.jsp");
			break;
		}
		case "/localizacoes/update": {
			Localizacao localizacao = loadLocalizacao(req);
			if (localizacao == null) {
				ControllerUtil.redirect(resp, req.getContextPath() + "/localizacoes");
				return;
			}
			req.setAttribute("localizacao", localizacao);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-localizacao.jsp");
			break;
		}
		default:
			throw new IllegalArgumentException("URL não suportada: " + action + ".");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = ControllerUtil.path(req);

		switch (action) {
		case "/localizacoes/delete":
			deleteLocalizacao(req);
			break;
		case "/localizacoes/insert":
			insertLocalizacao(req);
			break;
		case "/localizacoes/update":
			updateLocalizacao(req);
			break;
		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/localizacoes");
	}

	private Localizacao loadLocalizacao(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("localizacaoId"));

		LocalizacaoDAO dao = DAOFactory.createDAO(LocalizacaoDAO.class);

		try {
			Localizacao localizacao = dao.findById(id);

			if (localizacao == null)
				throw new ModelException("Localização não encontrada para alteração.");

			return localizacao;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listLocalizacoes(HttpServletRequest req) {
		LocalizacaoDAO dao = DAOFactory.createDAO(LocalizacaoDAO.class);

		try {
			List<Localizacao> localizacoes = dao.listAll();
			req.setAttribute("localizacoes", localizacoes);
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void fillLocalizacao(HttpServletRequest req, Localizacao localizacao) {
		String estado = req.getParameter("estado");

		localizacao.setCidade(req.getParameter("cidade"));
		localizacao.setEstado(estado == null ? null : estado.trim().toUpperCase());
	}

	private void insertLocalizacao(HttpServletRequest req) {
		Localizacao localizacao = new Localizacao();
		fillLocalizacao(req, localizacao);

		LocalizacaoDAO dao = DAOFactory.createDAO(LocalizacaoDAO.class);

		try {
			if (dao.save(localizacao))
				ControllerUtil.sucessMessage(req, "Localização '" + localizacao.getCidade() + "' salva com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Localização '" + localizacao.getCidade() + "' não pode ser salva.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateLocalizacao(HttpServletRequest req) {
		Localizacao localizacao = loadLocalizacao(req);
		if (localizacao == null)
			return;

		fillLocalizacao(req, localizacao);

		LocalizacaoDAO dao = DAOFactory.createDAO(LocalizacaoDAO.class);

		try {
			if (dao.update(localizacao))
				ControllerUtil.sucessMessage(req, "Localização '" + localizacao.getCidade() + "' atualizada com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Localização '" + localizacao.getCidade() + "' não pode ser atualizada.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteLocalizacao(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("id"));

		LocalizacaoDAO dao = DAOFactory.createDAO(LocalizacaoDAO.class);

		try {
			Localizacao localizacao = dao.findById(id);

			if (localizacao == null)
				throw new ModelException("Localização não encontrada para deleção.");

			if (dao.delete(localizacao))
				ControllerUtil.sucessMessage(req, "Localização '" + localizacao.getCidade() + "' deletada com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Localização '" + localizacao.getCidade() + "' não pode ser deletada.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "A localização não pode ser deletada: há viagens relacionadas a ela.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
