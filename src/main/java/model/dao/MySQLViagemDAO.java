// MySQLViagemDAO.java
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Viagem;

public class MySQLViagemDAO implements ViagemDAO {

	@Override
	public boolean save(Viagem viagem) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO viagem (empresa_id, origem_id, destino_id, data_saida, preco_base, assentos_totais) VALUES "
				+ " (?, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);
		db.setInt(1, viagem.getEmpresaId());
		db.setInt(2, viagem.getOrigemId());
		db.setInt(3, viagem.getDestinoId());
		db.setLocalDateTime(4, viagem.getDataSaida());
		db.setDouble(5, viagem.getPrecoBase());
		db.setInt(6, viagem.getAssentosTotais());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Viagem viagem) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE viagem "
				+ " SET empresa_id = ?,"
				+ " origem_id = ?,"
				+ " destino_id = ?,"
				+ " data_saida = ?,"
				+ " preco_base = ?,"
				+ " assentos_totais = ?"
				+ " WHERE id = ?";

		db.prepareStatement(sqlUpdate);
		db.setInt(1, viagem.getEmpresaId());
		db.setInt(2, viagem.getOrigemId());
		db.setInt(3, viagem.getDestinoId());
		db.setLocalDateTime(4, viagem.getDataSaida());
		db.setDouble(5, viagem.getPrecoBase());
		db.setInt(6, viagem.getAssentosTotais());
		db.setInt(7, viagem.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Viagem viagem) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM viagem "
				+ " WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, viagem.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Viagem> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Viagem> viagens = new ArrayList<Viagem>();

		String sqlQuery = "SELECT * FROM viagem";

		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			viagens.add(createViagem(db));
		}

		return viagens;
	}

	@Override
	public Viagem findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM viagem WHERE id = ?;";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Viagem v = null;
		while (db.next()) {
			v = createViagem(db);
			break;
		}

		return v;
	}

	private Viagem createViagem(DBHandler db) throws ModelException {
		Viagem v = new Viagem();
		v.setId(db.getInt("id"));
		v.setEmpresaId(db.getInt("empresa_id"));
		v.setOrigemId(db.getInt("origem_id"));
		v.setDestinoId(db.getInt("destino_id"));
		v.setDataSaida(db.getLocalDateTime("data_saida"));
		v.setPrecoBase(db.getDouble("preco_base"));
		v.setAssentosTotais(db.getInt("assentos_totais"));
		return v;
	}
}
