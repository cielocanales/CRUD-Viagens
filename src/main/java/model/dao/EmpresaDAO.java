package model.dao;

import java.util.List;
import model.ModelException;
import model.Empresa;

public interface EmpresaDAO {
	boolean save(Empresa empresa) throws ModelException;
	boolean update(Empresa empresa) throws ModelException;
	boolean delete(Empresa empresa) throws ModelException;
	List<Empresa> listAll() throws ModelException;
	Empresa findById(int id) throws ModelException;
}