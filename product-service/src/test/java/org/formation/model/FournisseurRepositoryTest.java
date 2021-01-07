package org.formation.model;

import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

@DataJpaTest(properties = {"spring.jpa.properties.hibernate.hbm2ddl.import_files=import_fournisseur_repository_test.sql"})
public class FournisseurRepositoryTest {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	FournisseurRepository fournisseurRepository;

	@Test
	void contextLoads() {
		Arrays.stream(context.getBeanDefinitionNames())
		   .map(bn -> context.getBean(bn))
		   .forEach(System.out::println);
	}

	@Test
	void whenExactReferenceThenFound() {
		Optional<Fournisseur> result = fournisseurRepository.findByReference("GOOD_REF");
		
	  assertAll("When Exact Ref Then Found Fournisseur",
				() -> assertTrue(result.isPresent()));
	}
	
	@Test
	void whenInexactReferenceThenNotFound() {
		Optional<Fournisseur> result = fournisseurRepository.findByReference(" GOOD_REF");
		
	  assertAll("When InExact Ref Then NotFound ",
				() -> assertTrue(!result.isPresent()));
	}
	
}
