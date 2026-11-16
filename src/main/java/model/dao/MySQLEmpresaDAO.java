// MySQLEmpresaDAO.java
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Empresa;

public class MySQLEmpresaDAO implements EmpresaDAO {

	@Override
	public boolean save(Empresa empresa) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO empresa (nome, tipo) VALUES "
				+ " (?, ?);";

		db.prepareStatement(sqlInsert);
		db.setString(1, empresa.getNome());
		db.setString(2, empresa.getTipo());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Empresa empresa) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE empresa "
				+ " SET nome = ?,"
				+ " tipo = ?"
				+ " WHERE id = ?";

		db.prepareStatement(sqlUpdate);
		db.setString(1, empresa.getNome());
		db.setString(2, empresa.getTipo());
		db.setInt(3, empresa.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Empresa empresa) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM empresa "
				+ " WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, empresa.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Empresa> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Empresa> empresas = new ArrayList<Empresa>();

		String sqlQuery = "SELECT * FROM empresa";

		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			empresas.add(createEmpresa(db));
		}

		return empresas;
	}

	@Override
	public Empresa findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM empresa WHERE id = ?;";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Empresa e = null;
		while (db.next()) {
			e = createEmpresa(db);
			break;
		}

		return e;
	}

	private Empresa createEmpresa(DBHandler db) throws ModelException {
		Empresa e = new Empresa();
		e.setId(db.getInt("id"));
		e.setNome(db.getString("nome"));
		e.setTipo(db.getString("tipo"));
		return e;
	}
}
