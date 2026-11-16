package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Passageiro;
import model.dao.DAOFactory;
import model.dao.PassageiroDAO;

@WebServlet(urlPatterns = {"/passageiros", "/passageiros/form", "/passageiros/delete", "/passageiros/insert", "/passageiros/update"})
public class PassageirosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = ControllerUtil.path(req);

		switch (action) {

		case "/passageiros": {
			listPassageiros(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/passageiros.jsp");
			break;
		}
		case "/passageiros/form": {
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-passageiro.jsp");
			break;
		}
		case "/passageiros/update": {
			Passageiro passageiro = loadPassageiro(req);
			if (passageiro == null) {
				ControllerUtil.redirect(resp, req.getContextPath() + "/passageiros");
				return;
			}
			req.setAttribute("passageiro", passageiro);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-passageiro.jsp");
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
		case "/passageiros/delete":
			deletePassageiro(req);
			break;
		case "/passageiros/insert":
			insertPassageiro(req);
			break;
		case "/passageiros/update":
			updatePassageiro(req);
			break;
		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/passageiros");
	}

	private Passageiro loadPassageiro(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("passageiroId"));

		PassageiroDAO dao = DAOFactory.createDAO(PassageiroDAO.class);

		try {
			Passageiro passageiro = dao.findById(id);

			if (passageiro == null)
				throw new ModelException("Passageiro não encontrado para alteração.");

			return passageiro;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listPassageiros(HttpServletRequest req) {
		PassageiroDAO dao = DAOFactory.createDAO(PassageiroDAO.class);

		try {
			List<Passageiro> passageiros = dao.listAll();
			req.setAttribute("passageiros", passageiros);
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertPassageiro(HttpServletRequest req) {
		Passageiro passageiro = new Passageiro();
		passageiro.setNome(req.getParameter("nome"));
		passageiro.setCpf(req.getParameter("cpf"));
		passageiro.setEmail(req.getParameter("email"));
		// TODO: guardar um hash da senha (ex.: BCrypt) em vez do texto puro
		passageiro.setSenha(req.getParameter("senha"));

		PassageiroDAO dao = DAOFactory.createDAO(PassageiroDAO.class);

		try {
			if (dao.save(passageiro))
				ControllerUtil.sucessMessage(req, "Passageiro '" + passageiro.getNome() + "' salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Passageiro '" + passageiro.getNome() + "' não pode ser salvo.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "CPF ou e-mail já cadastrado.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updatePassageiro(HttpServletRequest req) {
		Passageiro passageiro = loadPassageiro(req);
		if (passageiro == null)
			return;

		passageiro.setNome(req.getParameter("nome"));
		passageiro.setCpf(req.getParameter("cpf"));
		passageiro.setEmail(req.getParameter("email"));

		// senha em branco no formulário = mantém a senha atual
		String senha = req.getParameter("senha");
		if (senha != null && !senha.isEmpty())
			passageiro.setSenha(senha);

		PassageiroDAO dao = DAOFactory.createDAO(PassageiroDAO.class);

		try {
			if (dao.update(passageiro))
				ControllerUtil.sucessMessage(req, "Passageiro '" + passageiro.getNome() + "' atualizado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Passageiro '" + passageiro.getNome() + "' não pode ser atualizado.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "CPF ou e-mail já cadastrado.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deletePassageiro(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("id"));

		PassageiroDAO dao = DAOFactory.createDAO(PassageiroDAO.class);

		try {
			Passageiro passageiro = dao.findById(id);

			if (passageiro == null)
				throw new ModelException("Passageiro não encontrado para deleção.");

			if (dao.delete(passageiro))
				ControllerUtil.sucessMessage(req, "Passageiro '" + passageiro.getNome() + "' deletado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Passageiro '" + passageiro.getNome() + "' não pode ser deletado.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "O passageiro não pode ser deletado: há bilhetes relacionados a ele.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
