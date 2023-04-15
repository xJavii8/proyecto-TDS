package umu.tds.model;

import java.util.List;

import umu.tds.view.Constantes;

public class LikesDiscount extends Discount {

	@Override
	public boolean validDiscountForUser(User user) {
		List<Publication> allPubs = user.getPublications();
		return allPubs.stream().mapToInt(Publication::getLikes).sum() > Constantes.MIN_LIKES_PREMIUM_DISCOUNT;
	}

	@Override
	public int getPremiumDiscount(int price, User user) {
		return Constantes.LIKES_DISCOUNT;
	}

}
