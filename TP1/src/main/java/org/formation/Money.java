package org.formation;

public class Money implements IMoney{
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
    public IMoney add(IMoney imoney) {
       if ( imoney instanceof Money ) {
           Money m = (Money)imoney;
           if ( m.getCurrency().equals(getCurrency()) ) {
               return new Money(getAmount()+m.getAmount(),getCurrency());
           } else {
               MoneyBag mb = new MoneyBag();
               mb.put(this);
               mb.put(m);
               return mb;
           }
       }
       return imoney.add(this);
    }

}
