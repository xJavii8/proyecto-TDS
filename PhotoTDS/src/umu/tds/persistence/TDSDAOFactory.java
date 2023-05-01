package umu.tds.persistence;

public class TDSDAOFactory extends DAOFactory {
	public TDSDAOFactory() {
	}

	@Override
	public IAdaptadorUserDAO getUserDAO() {
		return AdaptadorUserTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorPublicationDAO getPublicationDAO() {
		return AdaptadorPublicationTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorCommentDAO getCommentDAO() {
		return AdaptadorCommentTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorHashtagDAO getHashtagDAO() {
		return AdaptadorHashtagTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorNotificationDAO getNotificationDAO() {
		return AdaptadorNotificationTDS.getUnicaInstancia();
	}

}
