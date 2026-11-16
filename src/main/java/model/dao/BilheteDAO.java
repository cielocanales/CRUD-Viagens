// BilheteDAO.java
package model.dao;

import java.util.List;
import model.ModelException;
import model.Bilhete;

public interface BilheteDAO {
	boolean save(Bilhete bilhete) throws ModelException;
	boolean update(Bilhete bilhete) throws ModelException;
	boolean delete(Bilhete bilhete) throws ModelException;
	List<Bilhete> listAll() throws ModelException;
	Bilhete findById(int id) throws ModelException;
}