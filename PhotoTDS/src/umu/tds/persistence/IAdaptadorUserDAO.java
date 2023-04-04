package umu.tds.persistence;

import java.util.List;

import umu.tds.model.User;

public interface IAdaptadorUserDAO {
	public void createUser(User user);
	public User readUser(int userCode);
	public void updateUser(User user);
	public void deleteUser(User user);
	public List<User> readAllUsers();
}
