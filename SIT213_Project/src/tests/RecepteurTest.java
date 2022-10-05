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
		assertEquals("La valeur du nombre d'echantillon ne correspond pas", nbEchantillons, Recepteur.getNbEchantillons());
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas", min, Recepteur.getMin(), (double)0.0);
		nbErrors--;
		assertEquals( "La valeur de max ne correspond pas", max, Recepteur.getMax(), (double)0.0);
		nbErrors--;
		assertEquals("Le type ne correspond pas", form, Recepteur.getFormeSignal());
		nbErrors--;
	}
	
	public static void main(String[] args) {	
		RecepteurTest S = new RecepteurTest();
		
		S.RecepteurInitTest(10000, -5, 5, "RZ");
		//Il faudra rajouter d'autres cas de tests
		//TODO
		
	}
}