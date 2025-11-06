package org.formation;


public class Money implements IMoney {
    private double amount;
    private String currency;

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

	@Override
	public IMoney add(IMoney iMoney) {
		if ( iMoney instanceof MoneyBag ) {
			return iMoney.add(this);
		} else {
			return addMoney((Money)iMoney);
		}	
	}
	
    private IMoney addMoney(Money m) {
        if ( m == null ) {
            return new Money(amount,currency); // Retourne une copie
        }

    	if ( !getCurrency().equals(m.getCurrency()) ) {
            return new MoneyBag(this,m);
        }
        return new Money(amount + m.amount, getCurrency() );
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
        if ( arg0 instanceof Money ) {
            Money aMoney = (Money)arg0;
            return aMoney.getCurrency().equals(getCurrency()) &&
                    getAmount() == aMoney.getAmount();
        }
        return false;
    }



}
