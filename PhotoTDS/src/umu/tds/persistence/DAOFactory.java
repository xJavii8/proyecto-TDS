package umu.tds.persistence;

public abstract class DAOFactory {
	private static DAOFactory unicaInstancia;
	public static final String DAO_TDS = "umu.tds.persistence.TDSDAOFactory";
	
	public static DAOFactory getInstancia(String tipo) throws DAOException {
		if(unicaInstancia == null) {
			try {
				unicaInstancia = (DAOFactory) Class.forName(tipo).newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		}
		return unicaInstancia;
	}
	
	public static DAOFactory getInstancia() throws  DAOException {
		if(unicaInstancia == null) {
			return getInstancia(DAOFactory.DAO_TDS);
		} else return unicaInstancia;
	}
	
	protected DAOFactory() {}

	public abstract IAdaptadorUserDAO getUserDAO();
	public abstract IAdaptadorPublicationDAO getPublicationDAO();
	public abstract IAdaptadorCommentDAO getCommentDAO();
	public abstract IAdaptadorHashtagDAO getHashtagDAO();
}
