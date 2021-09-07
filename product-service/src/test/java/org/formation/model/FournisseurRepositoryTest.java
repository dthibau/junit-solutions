package org.formation.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

@DataJpaTest
public class FournisseurRepositoryTest {

	@Autowired
	ApplicationContext context;

	@Autowired
	FournisseurRepository fournisseurRepository;

//	@Test
//	void contextLoads() {		
//		for ( String beanName : context.getBeanDefinitionNames() ) {
//			System.out.println(beanName);
//		}
//	}

	@Test
	void whenExactReferenceThenFound() {
		
		Optional<Fournisseur> result = fournisseurRepository.findByReference("BELLE");

		assertAll("When Exact Ref Then Found Fournisseur", () -> assertTrue(result.isPresent()));
	}

	@Test
	void whenInexactReferenceThenNotFound() {
		Optional<Fournisseur> result = fournisseurRepository.findByReference(" BELLE");

		assertAll("When InExact Ref Then NotFound ", () -> assertTrue(!result.isPresent()));
	}

}
