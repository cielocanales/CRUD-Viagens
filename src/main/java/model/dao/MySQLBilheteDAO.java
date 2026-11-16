// MySQLBilheteDAO.java
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Bilhete;

public class MySQLBilheteDAO implements BilheteDAO {

	// data_compra não entra no INSERT/UPDATE: o banco preenche sozinho (DEFAULT CURRENT_TIMESTAMP)
	@Override
	public boolean save(Bilhete bilhete) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO bilhete (passageiro_id, viagem_id, numero_poltrona, status) VALUES "
				+ " (?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);
		db.setInt(1, bilhete.getPassageiroId());
		db.setInt(2, bilhete.getViagemId());
		db.setInt(3, bilhete.getNumeroPoltrona());
		db.setString(4, bilhete.getStatus());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Bilhete bilhete) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE bilhete "
				+ " SET passageiro_id = ?,"
				+ " viagem_id = ?,"
				+ " numero_poltrona = ?,"
				+ " status = ?"
				+ " WHERE id = ?";

		db.prepareStatement(sqlUpdate);
		db.setInt(1, bilhete.getPassageiroId());
		db.setInt(2, bilhete.getViagemId());
		db.setInt(3, bilhete.getNumeroPoltrona());
		db.setString(4, bilhete.getStatus());
		db.setInt(5, bilhete.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Bilhete bilhete) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM bilhete "
				+ " WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, bilhete.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Bilhete> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Bilhete> bilhetes = new ArrayList<Bilhete>();

		String sqlQuery = "SELECT * FROM bilhete";

		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			bilhetes.add(createBilhete(db));
		}

		return bilhetes;
	}

	@Override
	public Bilhete findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM bilhete WHERE id = ?;";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Bilhete b = null;
		while (db.next()) {
			b = createBilhete(db);
			break;
		}

		return b;
	}

	private Bilhete createBilhete(DBHandler db) throws ModelException {
		Bilhete b = new Bilhete();
		b.setId(db.getInt("id"));
		b.setPassageiroId(db.getInt("passageiro_id"));
		b.setViagemId(db.getInt("viagem_id"));
		b.setNumeroPoltrona(db.getInt("numero_poltrona"));
		b.setDataCompra(db.getLocalDateTime("data_compra"));
		b.setStatus(db.getString("status"));
		return b;
	}
}
