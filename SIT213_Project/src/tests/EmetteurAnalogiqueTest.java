package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import emetteur.EmetteurAnalogique;


public class EmetteurAnalogiqueTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public EmetteurAnalogiqueTest(){}
	
	@Test
	public void EmetteurAnalogiqueInitTest(String typeEmetteur, int nbEchantillons, float min, float max) {
		nbErrors+=4;
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max);
		assertEquals("Le type emetteur ne correspond pas", Emetteur.getTypeEmetteur(), typeEmetteur );
		nbErrors--;
		assertEquals("La valeur de seed ne correspond pas", Emetteur.getNbEchantillons(), nbEchantillons);
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas", Emetteur.getMin(), min, 0.0f);
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas", Emetteur.getMax(), max, 0.0f);
		nbErrors--;
	}
	
	@Test
	public static Tests testReport(String typeEmetteur) {
		Tests tr;
		int nbEchantillons = 10000;
		float min = -5;
		float max = 5;
		EmetteurAnalogiqueTest E = new EmetteurAnalogiqueTest();
		
		nbTests+=4;
		E.EmetteurAnalogiqueInitTest(typeEmetteur, nbEchantillons, min, max);
		
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
