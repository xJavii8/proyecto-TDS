package umu.tds.model;

import java.util.LinkedList;
import java.util.List;

public abstract class Discount {

	public static List<Discount> getDiscountTypes() {
		List<Discount> tipos = new LinkedList<>();
		tipos.add(new AgeDiscount());
		tipos.add(new LikesDiscount());
		return tipos;
	}

	public abstract boolean validDiscountForUser(User user);

	public abstract int getPremiumDiscount(int price, User user);
}
