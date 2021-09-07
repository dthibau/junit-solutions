package org.formation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

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
	
	@ParameterizedTest @ArgumentsSource(MoneyBagProvider.class)
	public void givenMoneyBagNotContainingTarget_thenCheckResultltAndVerifyInteractionWithService(
			MoneyBag mbInput,
			Money expectedMoney,
			int expectedTime) {
		
		mbInput.setConvertService(mockService);
		
		lenient().when(mockService.convert(anyDouble(), anyString(), anyString())).thenReturn(10.0);
		
		Money result = mbInput.convertInto("USD");
		
		assertThat(result).isEqualTo(expectedMoney);
		
		verify(mockService,times(expectedTime)).convert(anyDouble(), anyString(), anyString());
		
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
