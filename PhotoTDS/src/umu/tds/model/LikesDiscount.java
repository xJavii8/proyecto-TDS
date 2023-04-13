package umu.tds.model;

public class LikesDiscount extends Discount {

	@Override
	public boolean validDiscountForUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPremiumDiscount(int price, User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
