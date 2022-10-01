package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import emetteur.EmetteurAnalogique;


public class EmetteurAnalogiqueTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public EmetteurAnalogiqueTest(){}
	
	@Test
	public void EmetteurAnalogiqueInitTest(String typeEmetteur, int nbEchantillons, float min, float max) {
		nbErrors+=4;
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max);
		assertEquals(Emetteur.getTypeEmetteur(), typeEmetteur , "Le type emetteur ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getNbEchantillons(), nbEchantillons, "La valeur de seed ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getMin(), min, "La valeur de min ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getMax(), max, "La valeur de max ne correspond pas");
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
