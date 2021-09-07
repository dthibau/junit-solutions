package org.formation;

import java.util.*;
import java.util.stream.Collectors;

public class MoneyBag implements IMoney {

    private Map<String,Money> moneys = new HashMap<>();
    
    private ConvertService convertService;
    


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
    public IMoney add(IMoney imoney) {
      if ( imoney instanceof Money ) {
          Money m = (Money)imoney;
          addMoney(m);
          return this.normalize();
      } else if (imoney instanceof MoneyBag) {
          MoneyBag mb = (MoneyBag) imoney;
          mb.getCurrencies().stream().forEach(currency -> addMoney(mb.getCurrencyAmount(currency), currency));
        return  this.normalize();
      }
      throw new IllegalArgumentException("Not implemented");

    }
    
    public Money convertInto(String destinationCurrency) {
    	double result = getCurrencies().stream()
    			.filter(currency -> !currency.equals(destinationCurrency) )
                .map(currency -> convertService.convert(moneys.get(currency).getAmount(), currency, destinationCurrency))
                .reduce(0d, (a, b) -> a + b);

    	return new Money(result + getCurrencyAmount(destinationCurrency),destinationCurrency);
    }
    
    private void addMoney(Money money) {
        if (moneys.containsKey(money.getCurrency()) ) {
            moneys.put(money.getCurrency(), (Money)moneys.get(money.getCurrency()).add(money));
        } else {
            moneys.put(money.getCurrency(), money);
        }
    }
    private void addMoney(double amount, String currency) {
        addMoney(new Money(amount,currency));
    }
    private IMoney normalize() {
        List<String> toRemove = getCurrencies().stream().filter(c -> moneys.get(c).getAmount() == 0).collect(Collectors.toList());
        for ( String currency : toRemove ) {
            moneys.remove(currency);
        }
        if ( getCurrencies().size() == 1) {
            return moneys.values().iterator().next();
        }
        return this;
    }

	public ConvertService getConvertService() {
		return convertService;
	}

	public void setConvertService(ConvertService convertService) {
		this.convertService = convertService;
		
	}
   
}
