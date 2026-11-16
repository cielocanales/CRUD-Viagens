
package model.dao;

import java.util.List;
import model.ModelException;
import model.Passageiro;

public interface PassageiroDAO {
	boolean save(Passageiro passageiro) throws ModelException;
	boolean update(Passageiro passageiro) throws ModelException;
	boolean delete(Passageiro passageiro) throws ModelException;
	List<Passageiro> listAll() throws ModelException;
	Passageiro findById(int id) throws ModelException;
}