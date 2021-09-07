package org.formation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ProductServiceApplicationTests {

	@Autowired
	ApplicationContext context;
	
	@Test
	void contextLoads() {
		
		for ( String beanName : context.getBeanDefinitionNames() ) {
			System.out.println(beanName);
		}
	}

}
