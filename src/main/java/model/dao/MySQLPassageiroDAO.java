// MySQLPassageiroDAO.java
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Passageiro;

public class MySQLPassageiroDAO implements PassageiroDAO {

	@Override
	public boolean save(Passageiro passageiro) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO passageiro (nome, cpf, email, senha) VALUES "
				+ " (?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);
		db.setString(1, passageiro.getNome());
		db.setString(2, passageiro.getCpf());
		db.setString(3, passageiro.getEmail());
		db.setString(4, passageiro.getSenha());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Passageiro passageiro) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE passageiro "
				+ " SET nome = ?,"
				+ " cpf = ?,"
				+ " email = ?,"
				+ " senha = ?"
				+ " WHERE id = ?";

		db.prepareStatement(sqlUpdate);
		db.setString(1, passageiro.getNome());
		db.setString(2, passageiro.getCpf());
		db.setString(3, passageiro.getEmail());
		db.setString(4, passageiro.getSenha());
		db.setInt(5, passageiro.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Passageiro passageiro) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM passageiro "
				+ " WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, passageiro.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Passageiro> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Passageiro> passageiros = new ArrayList<Passageiro>();

		String sqlQuery = "SELECT * FROM passageiro";

		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			passageiros.add(createPassageiro(db));
		}

		return passageiros;
	}

	@Override
	public Passageiro findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM passageiro WHERE id = ?;";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Passageiro p = null;
		while (db.next()) {
			p = createPassageiro(db);
			break;
		}

		return p;
	}
	
	@Override
	public Passageiro findByEmail(String email) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM passageiro WHERE email = ?;";

		db.prepareStatement(sql);
		db.setString(1, email);
		db.executeQuery();

		Passageiro p = null;
		while (db.next()) {
			p = createPassageiro(db);
			break;
		}

		return p;
	}

	private Passageiro createPassageiro(DBHandler db) throws ModelException {
		Passageiro p = new Passageiro();
		p.setId(db.getInt("id"));
		p.setNome(db.getString("nome"));
		p.setCpf(db.getString("cpf"));
		p.setEmail(db.getString("email"));
		p.setSenha(db.getString("senha"));
		return p;
	}
}
