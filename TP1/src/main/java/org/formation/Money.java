package org.formation;

public class Money implements IMoney {
	private double amount;

	private String currency;

	public Money(double amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean equals(Object arg0) {
		if (arg0 instanceof Money) {
			Money aMoney = (Money) arg0;
			return aMoney.getCurrency().equals(getCurrency()) && getAmount() == aMoney.getAmount();
		}
		return false;
	}

	@Override
	public IMoney add(IMoney money) {
		return new Money(0, "EUR");
	}
}
