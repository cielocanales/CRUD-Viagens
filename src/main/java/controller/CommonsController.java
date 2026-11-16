package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import model.Empresa;
import model.Localizacao;
import model.ModelException;
import model.Passageiro;
import model.Viagem;
import model.dao.DAOFactory;
import model.dao.EmpresaDAO;
import model.dao.LocalizacaoDAO;
import model.dao.PassageiroDAO;
import model.dao.ViagemDAO;

// Carrega listas usadas nos <select> dos formulários e para mostrar nomes nas tabelas
public class CommonsController {

	// atributos: "empresas" (lista) e "empresasNomes" (mapa id -> nome)
	public static Map<Integer, String> listEmpresas(HttpServletRequest req) {
		Map<Integer, String> nomes = new HashMap<Integer, String>();
		EmpresaDAO dao = DAOFactory.createDAO(EmpresaDAO.class);

		try {
			List<Empresa> empresas = dao.listAll();
			for (Empresa e : empresas)
				nomes.put(e.getId(), e.getNome());
			req.setAttribute("empresas", empresas);
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		req.setAttribute("empresasNomes", nomes);
		return nomes;
	}

	// atributos: "localizacoes" (lista) e "localizacoesNomes" (mapa id -> "Cidade - UF")
	public static Map<Integer, String> listLocalizacoes(HttpServletRequest req) {
		Map<Integer, String> nomes = new HashMap<Integer, String>();
		LocalizacaoDAO dao = DAOFactory.createDAO(LocalizacaoDAO.class);

		try {
			List<Localizacao> localizacoes = dao.listAll();
			for (Localizacao l : localizacoes)
				nomes.put(l.getId(), l.getCidade() + " - " + l.getEstado());
			req.setAttribute("localizacoes", localizacoes);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		req.setAttribute("localizacoesNomes", nomes);
		return nomes;
	}

	// atributos: "passageiros" (lista) e "passageirosNomes" (mapa id -> nome)
	public static Map<Integer, String> listPassageiros(HttpServletRequest req) {
		Map<Integer, String> nomes = new HashMap<Integer, String>();
		PassageiroDAO dao = DAOFactory.createDAO(PassageiroDAO.class);

		try {
			List<Passageiro> passageiros = dao.listAll();
			for (Passageiro p : passageiros)
				nomes.put(p.getId(), p.getNome());
			req.setAttribute("passageiros", passageiros);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		req.setAttribute("passageirosNomes", nomes);
		return nomes;
	}
	
	// atributos: "viagens" (lista) e "viagensDescricao" (mapa id -> texto da viagem)
	public static void listViagens(HttpServletRequest req) {
		Map<Integer, String> empresas = listEmpresas(req);
		Map<Integer, String> locais = listLocalizacoes(req);
		Map<Integer, String> descricoes = new HashMap<Integer, String>();
		ViagemDAO dao = DAOFactory.createDAO(ViagemDAO.class);

		try {
			List<Viagem> viagens = dao.listAll();
			for (Viagem v : viagens) {
				String saida = v.getDataSaida().toString().replace('T', ' ');
				descricoes.put(v.getId(), empresas.get(v.getEmpresaId()) + ": "
						+ locais.get(v.getOrigemId()) + " para " + locais.get(v.getDestinoId())
						+ " (" + saida + ")");
			}
			req.setAttribute("viagens", viagens);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		req.setAttribute("viagensDescricao", descricoes);
	}
}
