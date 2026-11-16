package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bilhete;
import model.ModelException;
import model.Viagem;
import model.dao.BilheteDAO;
import model.dao.DAOFactory;
import model.dao.ViagemDAO;

@WebServlet(urlPatterns = {"/bilhetes", "/bilhetes/form", "/bilhetes/delete", "/bilhetes/insert", "/bilhetes/update"})
public class BilhetesController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = ControllerUtil.path(req);

		switch (action) {

		case "/bilhetes": {
			listBilhetes(req);
			// nomes dos passageiros e descrição das viagens para mostrar na tabela
			CommonsController.listPassageiros(req);
			CommonsController.listViagens(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/bilhetes.jsp");
			break;
		}
		case "/bilhetes/form": {
			CommonsController.listPassageiros(req);
			CommonsController.listViagens(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-bilhete.jsp");
			break;
		}
		case "/bilhetes/update": {
			Bilhete bilhete = loadBilhete(req);
			if (bilhete == null) {
				ControllerUtil.redirect(resp, req.getContextPath() + "/bilhetes");
				return;
			}
			CommonsController.listPassageiros(req);
			CommonsController.listViagens(req);
			req.setAttribute("bilhete", bilhete);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-bilhete.jsp");
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
		case "/bilhetes/delete":
			deleteBilhete(req);
			break;
		case "/bilhetes/insert":
			insertBilhete(req);
			break;
		case "/bilhetes/update":
			updateBilhete(req);
			break;
		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/bilhetes");
	}

	private Bilhete loadBilhete(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("bilheteId"));

		BilheteDAO dao = DAOFactory.createDAO(BilheteDAO.class);

		try {
			Bilhete bilhete = dao.findById(id);

			if (bilhete == null)
				throw new ModelException("Bilhete não encontrado para alteração.");

			return bilhete;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listBilhetes(HttpServletRequest req) {
		BilheteDAO dao = DAOFactory.createDAO(BilheteDAO.class);

		try {
			List<Bilhete> bilhetes = dao.listAll();
			req.setAttribute("bilhetes", bilhetes);
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	// Preenche o bilhete com os dados do formulário.
	// Retorna a mensagem de erro se algo estiver inválido, ou null se estiver tudo certo.
	private String fillBilhete(HttpServletRequest req, Bilhete bilhete) {
		try {
			bilhete.setPassageiroId(Integer.parseInt(req.getParameter("passageiroId")));
			bilhete.setViagemId(Integer.parseInt(req.getParameter("viagemId")));
			bilhete.setNumeroPoltrona(Integer.parseInt(req.getParameter("numeroPoltrona")));
		} catch (NumberFormatException e) {
			return "Dados do bilhete inválidos.";
		}

		String status = req.getParameter("status");
		if (!"PENDENTE".equals(status) && !"PAGO".equals(status) && !"CANCELADO".equals(status))
			return "Status de pagamento inválido.";
		bilhete.setStatus(status);

		// a poltrona precisa existir na viagem escolhida
		ViagemDAO viagemDao = DAOFactory.createDAO(ViagemDAO.class);
		try {
			Viagem viagem = viagemDao.findById(bilhete.getViagemId());

			if (viagem == null)
				return "Viagem não encontrada.";
			if (bilhete.getNumeroPoltrona() < 1 || bilhete.getNumeroPoltrona() > viagem.getAssentosTotais())
				return "A poltrona deve estar entre 1 e " + viagem.getAssentosTotais() + ".";
		} catch (ModelException e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return null;
	}

	private void insertBilhete(HttpServletRequest req) {
		Bilhete bilhete = new Bilhete();

		String erro = fillBilhete(req, bilhete);
		if (erro != null) {
			ControllerUtil.errorMessage(req, erro);
			return;
		}

		BilheteDAO dao = DAOFactory.createDAO(BilheteDAO.class);

		try {
			if (dao.save(bilhete))
				ControllerUtil.sucessMessage(req, "Bilhete salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "O bilhete não pode ser salvo.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "A poltrona " + bilhete.getNumeroPoltrona() + " já foi vendida para essa viagem.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateBilhete(HttpServletRequest req) {
		Bilhete bilhete = loadBilhete(req);
		if (bilhete == null)
			return;

		String erro = fillBilhete(req, bilhete);
		if (erro != null) {
			ControllerUtil.errorMessage(req, erro);
			return;
		}

		BilheteDAO dao = DAOFactory.createDAO(BilheteDAO.class);

		try {
			if (dao.update(bilhete))
				ControllerUtil.sucessMessage(req, "Bilhete atualizado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "O bilhete não pode ser atualizado.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "A poltrona " + bilhete.getNumeroPoltrona() + " já foi vendida para essa viagem.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteBilhete(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("id"));

		BilheteDAO dao = DAOFactory.createDAO(BilheteDAO.class);

		try {
			Bilhete bilhete = dao.findById(id);

			if (bilhete == null)
				throw new ModelException("Bilhete não encontrado para deleção.");

			if (dao.delete(bilhete))
				ControllerUtil.sucessMessage(req, "Bilhete #" + bilhete.getId() + " deletado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "O bilhete #" + bilhete.getId() + " não pode ser deletado.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
