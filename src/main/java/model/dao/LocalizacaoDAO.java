// LocalizacaoDAO.java
package model.dao;

import java.util.List;
import model.ModelException;
import model.Localizacao;

public interface LocalizacaoDAO {
	boolean save(Localizacao localizacao) throws ModelException;
	boolean update(Localizacao localizacao) throws ModelException;
	boolean delete(Localizacao localizacao) throws ModelException;
	List<Localizacao> listAll() throws ModelException;
	Localizacao findById(int id) throws ModelException;
}