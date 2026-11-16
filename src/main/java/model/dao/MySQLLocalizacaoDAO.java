// MySQLLocalizacaoDAO.java
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Localizacao;

public class MySQLLocalizacaoDAO implements LocalizacaoDAO {

	@Override
	public boolean save(Localizacao localizacao) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO localizacao (cidade, estado) VALUES "
				+ " (?, ?);";

		db.prepareStatement(sqlInsert);
		db.setString(1, localizacao.getCidade());
		db.setString(2, localizacao.getEstado());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Localizacao localizacao) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE localizacao "
				+ " SET cidade = ?,"
				+ " estado = ?"
				+ " WHERE id = ?";

		db.prepareStatement(sqlUpdate);
		db.setString(1, localizacao.getCidade());
		db.setString(2, localizacao.getEstado());
		db.setInt(3, localizacao.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Localizacao localizacao) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM localizacao "
				+ " WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, localizacao.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Localizacao> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Localizacao> localizacoes = new ArrayList<Localizacao>();

		String sqlQuery = "SELECT * FROM localizacao";

		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			localizacoes.add(createLocalizacao(db));
		}

		return localizacoes;
	}

	@Override
	public Localizacao findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM localizacao WHERE id = ?;";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Localizacao l = null;
		while (db.next()) {
			l = createLocalizacao(db);
			break;
		}

		return l;
	}

	private Localizacao createLocalizacao(DBHandler db) throws ModelException {
		Localizacao l = new Localizacao();
		l.setId(db.getInt("id"));
		l.setCidade(db.getString("cidade"));
		l.setEstado(db.getString("estado"));
		return l;
	}
}