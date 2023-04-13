package umu.tds.persistence;

public class TDSDAOFactory extends DAOFactory {
	public TDSDAOFactory() {}
	
	@Override
	public IAdaptadorUserDAO getUserDAO() {
		return AdaptadorUserTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorPublicationDAO getPublicationDAO() {
		return AdaptadorPublicationTDS.getUnicaInstancia();
	}
}
