package org.formation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MoneyBag implements IMoney {
	
	Map<String,Money> moneys = new HashMap<>();
	
	private ConvertService convertService;

	public MoneyBag(Money... array) {
		for ( Money money : array ) {
			this.put(money);
		}
		
	}
	
	@Override
	public IMoney add(IMoney iMoney) {
		MoneyBag ret = new MoneyBag();
		if ( iMoney instanceof MoneyBag ) {
			for ( Money m : moneys.values() ) {
				ret.put(m);
			}
			for ( Money m : ((MoneyBag)iMoney).getMoneys() ) {
				ret = ret.addMoney(m);
			}
			return ret;
		} else {
			ret = addMoney((Money)iMoney);
		}
		return normalize(ret);
	}
	
	public Money convertInto(String destinationCurrency) {
		
		double result = getCurrencies().stream()
				 			.filter(currency -> !currency.equals(destinationCurrency) )
				            .map(currency -> convertService.convert(moneys.get(currency).getAmount(), currency, destinationCurrency))
				            .reduce(0d, (a, b) -> a + b);
		return new Money(result + get(destinationCurrency),destinationCurrency);
	}
	
	private IMoney normalize(MoneyBag ret) {
		if ( ret.getCurrencies().size() == 1 ) {
			return ret.getMoneys().iterator().next();
		}
		return ret;
	}
	
	private MoneyBag addMoney(Money money) {
		MoneyBag ret = new MoneyBag();
		if (get(money.getCurrency()) > 0 ) {			
			for ( Money m : moneys.values() ) {
				if ( m.getCurrency().equals(money.getCurrency()) ) {
					ret.put(new Money(m.getAmount()+ money.getAmount(),m.getCurrency()));
				} else {
					ret.put(m);
				}
			}
		} else {
			for ( Money m : moneys.values() ) {
				ret.put(m);
			}
			ret.put(money);
		}
		return ret;
	}
	public void put(Money money) {
		moneys.put(money.getCurrency(), money);
	}

	public double get(String currency) {
		return moneys.get(currency) != null ? moneys.get(currency).getAmount() : 0d;
	}

	public Collection<Money> getMoneys() {
		return moneys.values();
	}
	public Collection<String> getCurrencies() {
		return moneys.keySet();
	}

}
