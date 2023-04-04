package umu.tds.persistence;

import java.util.List;

public interface IAdaptadorUserDAO {
	public void createUser(User user);
	public void readUser(int userCode);
	public void updateUser(User user);
	public void deleteUser(User user);
	public List<User> readAllUsers();
}
