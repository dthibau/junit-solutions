package org.formation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MoneyBagTest {

	@Mock
	ConvertService mockService;
	
	
	
	@Test
	public void givenEmptyMoneyBag_thenResultltIsZeroAndNoInteractionWithService() {
		MoneyBag mbEmpty = new MoneyBag();
		mbEmpty.setConvertService(mockService);
		
		Money result = mbEmpty.convertInto("EUR");
			
		assertThat(result).extracting("currency","amount").containsExactly("EUR",0.0);
		
		verifyNoInteractions(mockService);
		
	}
	
	@Test
	public void givenMoneyBagNotContainingTarget_thenCheckResultltAndVerifyTwoInteractionWithService() {
		
		MoneyBag mbEur12Yen14 = new MoneyBag();
		mbEur12Yen14.put(new Money(12,"EUR"));
		mbEur12Yen14.put(new Money(14,"YEN"));
		mbEur12Yen14.setConvertService(mockService);
		
		when(mockService.convert(anyDouble(), anyString(), anyString())).thenReturn(10.0);
		
		Money result = mbEur12Yen14.convertInto("USD");
		
		assertThat(result).extracting("currency","amount").containsExactly("USD",20.0);
		
		verify(mockService,times(2)).convert(anyDouble(), anyString(), anyString());
		
	}
	
	@Test
	public void givenMoneyBagContainingTarget_thenChackResultltAndVerifyOneInteractionWithService() {
		
		MoneyBag mbEur12Yen14 = new MoneyBag();
		mbEur12Yen14.put(new Money(12,"EUR"));
		mbEur12Yen14.put(new Money(14,"YEN"));
		mbEur12Yen14.setConvertService(mockService);
		
		when(mockService.convert(anyDouble(), anyString(), anyString())).thenReturn(10.0);
		
		Money result = mbEur12Yen14.convertInto("EUR");
		
		assertThat(result).extracting("currency","amount").containsExactly("EUR",22.0);
		
		verify(mockService,times(1)).convert(anyDouble(), anyString(), anyString());
		
	}
}
