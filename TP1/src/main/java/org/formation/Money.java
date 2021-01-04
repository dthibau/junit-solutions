package org.formation;


public class Money {
    private int amount;
    private String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money m) {
        if ( !getCurrency().equals(m.getCurrency()) ) {
            throw new IllegalArgumentException("Can't add money with different currencies");
        }
        return new Money(amount + m.amount, getCurrency() );
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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
