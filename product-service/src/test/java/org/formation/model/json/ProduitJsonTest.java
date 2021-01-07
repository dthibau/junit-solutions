package org.formation.model.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;


import org.formation.model.Dimension;
import org.formation.model.Fournisseur;
import org.formation.model.Produit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.ApplicationContext;

@JsonTest
public class ProduitJsonTest {

	@Autowired
	ApplicationContext context;
	
//	@Test
	void contextLoads() {
		Arrays.stream(context.getBeanDefinitionNames())
		   .map(bn -> context.getBean(bn))
		   .forEach(System.out::println);
	}
	
	@Autowired
    private JacksonTester<Produit> json;

	Produit unProduit;
	

	@BeforeEach
	public void setUp() {
		Dimension dimension = new Dimension();
		Fournisseur fournisseur = Fournisseur.builder().id(1).nom("Nom").reference("Reference").build();
		
		unProduit = Produit.builder().id(1).description("Description").nom("Nom").reference("Reference").prixUnitaire(1.0f).availability(1)
				.dimension(dimension)
				.fournisseur(fournisseur)
				.build();

	}
	
	 @Test
	    public void testSerialize() throws Exception {

	    	System.out.println(this.json.write(unProduit));

	        assertThat(this.json.write(unProduit))
	                  .hasJsonPathStringValue("@.nom")
	                  .hasJsonPathValue("@.fournisseur")
	                  .hasJsonPathValue("@.fournisseur")
	                  .extractingJsonPathStringValue("@.nom").isEqualTo("Nom");
	      
	    }

	    @Test
	    public void testDeserialize() throws Exception {
	        String content ="{\"id\":1,\"reference\":\"Reference\",\"nom\":\"Nom\",\"description\":\"Description\",\"prixUnitaire\":1.0,\"availability\":1,\"dimension\":{\"hauteur\":0.0,\"longueur\":0.0,\"largeur\":0.0},\"fournisseur\":{\"id\":1,\"reference\":\"Reference\",\"nom\":\"Nom\"}}";
//	        assertThat(this.json.parse(content))
//	                .isEqualTo(unProduit);

	        assertThat(this.json.parseObject(content).getNom()).isEqualTo("Nom");
	    }
}
