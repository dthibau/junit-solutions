package org.formation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MoneyBag implements IMoney {

    private Map<String,Money> moneys = new HashMap<>();


    public void put(Money money) {
        moneys.put(money.getCurrency(),money);
    }

    public double getCurrencyAmount(String currency) {
       Money money = moneys.get(currency);
       return money != null ? money.getAmount() : 0;
    }
    public Set<String> getCurrencies() {
        return moneys.keySet();
    }
    @Override
    public IMoney add(IMoney money) {
        return new MoneyBag();
    }
}
