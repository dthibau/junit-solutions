package org.formation.controller.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.formation.jwt.TokenProvider;
import org.formation.model.FournisseurRepository;
import org.formation.model.Produit;
import org.formation.model.ProduitRepository;
import org.formation.service.ImportProduitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value=ProduitRestController.class)
public class ProduitRestControllerTest {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private ProduitRepository produitRepository;
	
	@MockBean
	private FournisseurRepository fournisseurRepository;
	
	@MockBean
	ImportProduitService importProduitService;
	
	@MockBean
	TokenProvider tokenProvider;
	
	@MockBean
	UserDetailsService userDetailsService;
	
	private List<Produit> produits = new ArrayList<>();
	@Test
	void contextLoads() {
		Arrays.stream(context.getBeanDefinitionNames())
		   .map(bn -> context.getBean(bn))
		   .forEach(System.out::println);
	}
	
	@BeforeEach
	public void initProduits() {
		produits.add(Produit.builder().id(1).nom("Produit1").build());
		produits.add(Produit.builder().id(1).nom("Produit2").build());
	}
	
	@Test
	@WithMockUser
	void whenGetShouldReturn200() throws Exception {
		given(this.produitRepository.findAll()).willReturn(produits);
		mvc.perform(get("/api/produits")).andExpect(status().isOk()).andExpect(jsonPath("[0].nom").value("Produit1"));
	}
}
