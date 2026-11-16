package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import model.ModelException;
import model.dao.DAOFactory;
import model.dao.EmpresaDAO;

@WebServlet(urlPatterns = {"/empresas", "/empresas/form", "/empresas/delete", "/empresas/insert", "/empresas/update"})
public class EmpresasController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = ControllerUtil.path(req);

		switch (action) {

		case "/empresas": {
			listEmpresas(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/empresas.jsp");
			break;
		}
		case "/empresas/form": {
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-empresa.jsp");
			break;
		}
		case "/empresas/update": {
			Empresa empresa = loadEmpresa(req);
			if (empresa == null) {
				ControllerUtil.redirect(resp, req.getContextPath() + "/empresas");
				return;
			}
			req.setAttribute("empresa", empresa);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-empresa.jsp");
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
		case "/empresas/delete":
			deleteEmpresa(req);
			break;
		case "/empresas/insert":
			insertEmpresa(req);
			break;
		case "/empresas/update":
			updateEmpresa(req);
			break;
		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/empresas");
	}

	private Empresa loadEmpresa(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("empresaId"));

		EmpresaDAO dao = DAOFactory.createDAO(EmpresaDAO.class);

		try {
			Empresa empresa = dao.findById(id);

			if (empresa == null)
				throw new ModelException("Empresa não encontrada para alteração.");

			return empresa;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listEmpresas(HttpServletRequest req) {
		EmpresaDAO dao = DAOFactory.createDAO(EmpresaDAO.class);

		try {
			List<Empresa> empresas = dao.listAll();
			req.setAttribute("empresas", empresas);
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertEmpresa(HttpServletRequest req) {
		Empresa empresa = new Empresa();
		empresa.setNome(req.getParameter("nome"));
		empresa.setTipo(req.getParameter("tipo"));

		EmpresaDAO dao = DAOFactory.createDAO(EmpresaDAO.class);

		try {
			if (dao.save(empresa))
				ControllerUtil.sucessMessage(req, "Empresa '" + empresa.getNome() + "' salva com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Empresa '" + empresa.getNome() + "' não pode ser salva.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateEmpresa(HttpServletRequest req) {
		Empresa empresa = loadEmpresa(req);
		if (empresa == null)
			return;

		empresa.setNome(req.getParameter("nome"));
		empresa.setTipo(req.getParameter("tipo"));

		EmpresaDAO dao = DAOFactory.createDAO(EmpresaDAO.class);

		try {
			if (dao.update(empresa))
				ControllerUtil.sucessMessage(req, "Empresa '" + empresa.getNome() + "' atualizada com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Empresa '" + empresa.getNome() + "' não pode ser atualizada.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteEmpresa(HttpServletRequest req) {
		int id = ControllerUtil.parseInt(req.getParameter("id"));

		EmpresaDAO dao = DAOFactory.createDAO(EmpresaDAO.class);

		try {
			Empresa empresa = dao.findById(id);

			if (empresa == null)
				throw new ModelException("Empresa não encontrada para deleção.");

			if (dao.delete(empresa))
				ControllerUtil.sucessMessage(req, "Empresa '" + empresa.getNome() + "' deletada com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Empresa '" + empresa.getNome() + "' não pode ser deletada.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (ControllerUtil.isConstraintViolation(e))
				ControllerUtil.errorMessage(req, "A empresa não pode ser deletada: há viagens relacionadas a ela.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
