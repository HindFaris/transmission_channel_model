package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import recepteur.Recepteur;


public class RecepteurTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;

	
	//@Test
	public RecepteurTest() {
	}


	@Test
	public void RecepteurInitTest(int nbEchantillons, float min, float max, String form) {
		nbErrors+=4;
		Recepteur Recepteur = new Recepteur(nbEchantillons, min, max, form);
		assertEquals("La valeur du nombre d'echantillon ne correspond pas", Recepteur.getNbEchantillons(), nbEchantillons);
		nbErrors--;
		assertEquals( "La valeur de min ne correspond pas",Recepteur.getMin(), min);
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas",Recepteur.getMax(), max);
		nbErrors--;
		assertEquals(Recepteur.getFormeSignal(), form, "Le type ne correspond pas");
		nbErrors--;
	}
	
	public static Tests testReport() {		
		Tests tr;
		RecepteurTest S = new RecepteurTest();
		
		S.RecepteurInitTest(10000, -5, 5, "RZ");
		//Il faudra rajouter d'autres cas de tests
		//TODO
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}