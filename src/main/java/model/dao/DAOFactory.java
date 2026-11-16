package model.dao;

import java.util.HashMap;
import java.util.Map;

public class DAOFactory {

	private static Map<Class<?>, Object> listDAOsInterfaces = new HashMap<Class<?>, Object>();

	static {
		listDAOsInterfaces.put(PassageiroDAO.class, new MySQLPassageiroDAO());
		listDAOsInterfaces.put(EmpresaDAO.class, new MySQLEmpresaDAO());
		listDAOsInterfaces.put(LocalizacaoDAO.class, new MySQLLocalizacaoDAO());
		listDAOsInterfaces.put(ViagemDAO.class, new MySQLViagemDAO());
		listDAOsInterfaces.put(BilheteDAO.class, new MySQLBilheteDAO());
	}

	@SuppressWarnings("unchecked")
	public static <DAOInterface> DAOInterface createDAO(Class<?> entity){
		return (DAOInterface) listDAOsInterfaces.get(entity);
	}
}