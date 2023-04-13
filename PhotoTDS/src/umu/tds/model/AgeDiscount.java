package umu.tds.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class AgeDiscount extends Discount {

	@Override
	public boolean validDiscountForUser(User user) {
		int age = Period
				.between(user.getBirthDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())
				.getYears();
		if (age >= 18 && age <= 25)
			return true;
		else
			return false;
	}

	@Override
	public int getPremiumDiscount(int price, User user) {
		int age = 25 - Period
				.between(user.getBirthDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())
				.getYears();
		return Math.toIntExact(Math.round(age * price * 0.04));
	}

}
