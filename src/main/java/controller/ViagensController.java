package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Viagem;
import model.dao.DAOFactory;
import model.dao.ViagemDAO;

@WebServlet(urlPatterns = {"/viagens", "/viagens/form", "/viagens/delete", "/viagens/insert", "/viagens/update"})
public class ViagensController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = ControllerUtil.path(req);

		switch (action) {

		case "/viagens": {
			listViagens(req);
			// nomes de empresas e localizações para mostrar na tabela
			CommonsController.listEmpresas(req);
			CommonsController.listLocalizacoes(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/viagens.jsp");
			break;
		}
		case "/viagens/form": {
			// listas para os <select> do formulário
			CommonsController.listEmpresas(req);
			CommonsController.listLocalizacoes(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-viagem.jsp");
			break;
		}
		case "/viagens/update": {
			Viagem viagem = loadViagem(req);
			if (viagem == null) {
				ControllerUtil.redirect(resp, req.getContextPath() + "/viagens");
				return;
			}
			CommonsController.listEmpresas(req);
			CommonsController.listLocalizacoes(req);
			req.setAttribute("viagem", viagem);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-viagem.jsp");
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
		case "/viagens/delete":
			deleteViagem(req);
			break;
		case "/viagens/insert":
			insertViagem(req);
			break;
		case "/viagens/update":
			updateViagem(req);
			break;
		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/viagens");
	}

	private Viagem loadViagem(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("viagemId"));

		ViagemDAO dao = DAOFactory.createDAO(ViagemDAO.class);

		try {
			Viagem viagem = dao.findById(id);

			if (viagem == null)
				throw new ModelException("Viagem não encontrada para alteração.");

			return viagem;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listViagens(HttpServletRequest req) {
		ViagemDAO dao = DAOFactory.createDAO(ViagemDAO.class);

		try {
			List<Viagem> viagens = dao.listAll();
			req.setAttribute("viagens", viagens);
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	// Preenche a viagem com os dados do formulário.
	// Retorna a mensagem de erro se algo estiver inválido, ou null se estiver tudo certo.
	private String fillViagem(HttpServletRequest req, Viagem viagem) {
		try {
			viagem.setEmpresaId(Integer.parseInt(req.getParameter("empresaId")));
			viagem.setOrigemId(Integer.parseInt(req.getParameter("origemId")));
			viagem.setDestinoId(Integer.parseInt(req.getParameter("destinoId")));
			viagem.setPrecoBase(Double.parseDouble(req.getParameter("precoBase")));
			viagem.setAssentosTotais(Integer.parseInt(req.getParameter("assentosTotais")));
		} catch (NumberFormatException | NullPointerException e) {
			return "Dados da viagem inválidos.";
		}

		viagem.setDataSaida(ControllerUtil.parseLocalDateTime(req.getParameter("dataSaida")));

		if (viagem.getDataSaida() == null)
			return "Data de saída inválida.";
		if (viagem.getOrigemId() == viagem.getDestinoId())
			return "Origem e destino devem ser diferentes.";
		if (viagem.getPrecoBase() < 0)
			return "O preço não pode ser negativo.";
		if (viagem.getAssentosTotais() <= 0)
			return "A quantidade de assentos deve ser maior que zero.";

		return null;
	}

	private void insertViagem(HttpServletRequest req) {
		Viagem viagem = new Viagem();

		String erro = fillViagem(req, viagem);
		if (erro != null) {
			ControllerUtil.errorMessage(req, erro);
			return;
		}

		ViagemDAO dao = DAOFactory.createDAO(ViagemDAO.class);

		try {
			if (dao.save(viagem))
				ControllerUtil.sucessMessage(req, "Viagem salva com sucesso.");
			else
				ControllerUtil.errorMessage(req, "A viagem não pode ser salva.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateViagem(HttpServletRequest req) {
		Viagem viagem = loadViagem(req);
		if (viagem == null)
			return;

		String erro = fillViagem(req, viagem);
		if (erro != null) {
			ControllerUtil.errorMessage(req, erro);
			return;
		}

		ViagemDAO dao = DAOFactory.createDAO(ViagemDAO.class);

		try {
			if (dao.update(viagem))
				ControllerUtil.sucessMessage(req, "Viagem atualizada com sucesso.");
			else
				ControllerUtil.errorMessage(req, "A viagem não pode ser atualizada.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteViagem(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("id"));

		ViagemDAO dao = DAOFactory.createDAO(ViagemDAO.class);

		try {
			Viagem viagem = dao.findById(id);

			if (viagem == null)
				throw new ModelException("Viagem não encontrada para deleção.");

			if (dao.delete(viagem))
				ControllerUtil.sucessMessage(req, "Viagem deletada com sucesso.");
			else
				ControllerUtil.errorMessage(req, "A viagem não pode ser deletada.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "A viagem não pode ser deletada: há bilhetes vendidos para ela.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
