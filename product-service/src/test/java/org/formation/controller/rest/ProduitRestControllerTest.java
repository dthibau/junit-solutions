package org.formation.controller.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.formation.jwt.TokenProvider;
import org.formation.model.FournisseurRepository;
import org.formation.model.Produit;
import org.formation.model.ProduitRepository;
import org.formation.service.ImportProduitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProduitRestController.class)
public class ProduitRestControllerTest {

	@MockBean
	ProduitRepository produitRepository;
	
	@MockBean
	FournisseurRepository fournisseurRepository;
	
	@MockBean
	TokenProvider tokenProvider;
	
	@MockBean
	UserDetailsService userDetailsService;
	
	@MockBean
	ImportProduitService importProduitService;
	
	@Autowired
	MockMvc mockMvc;
	
	
	
	@Test
	@WithMockUser
	public void testFindAll() throws Exception {
		List<Produit> retProduits  = new ArrayList<>();
		
		retProduits.add(Produit.builder().id(1).build());
		retProduits.add(Produit.builder().id(2).build());
		
		when(produitRepository.findAll()).thenReturn(retProduits);
		
		
		mockMvc.perform(get("/api/produits")).andExpect(status().isOk());
	}
	
}
