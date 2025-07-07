package umu.tds.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import umu.tds.view.Constantes;

public class AgeDiscount extends Discount {

	@Override
	public boolean validDiscountForUser(User user) {
		int age = Period
				.between(user.getBirthDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())
				.getYears();
		if (age >= Constantes.MIN_AGE_PREMIUM_DISCOUNT && age <= Constantes.MAX_AGE_PREMIUM_DISCOUNT)
			return true;
		else
			return false;
	}

	@Override
	public int getPremiumDiscount(int price, User user) {
		int age = Constantes.MAX_AGE_PREMIUM_DISCOUNT - Period
				.between(user.getBirthDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())
				.getYears();
		return Math.toIntExact(Math.round(age * price * Constantes.DISCOUNT_PER_AGE));
	}

}
