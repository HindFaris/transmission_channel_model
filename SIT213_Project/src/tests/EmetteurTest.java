package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import emetteur.EmetteurAnalogique;


public class EmetteurTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public EmetteurTest(){}
	
	@Test
	public void EmetteurInitTest(String typeEmetteur, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		nbErrors+=6;
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max);
		assertEquals("Le type emetteur ne correspond pas", typeEmetteur , Emetteur.getTypeEmetteur());
		nbErrors--;
		assertEquals( "La valeur de seed ne correspond pas", nbEchantillons, Emetteur.getNbEchantillons());
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas", min, Emetteur.getMin());
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas", max, Emetteur.getMax());
		nbErrors--;
	}
	
	@Test
	public static Tests testReport(String typeEmetteur) {
		Tests tr;
		int nbEchantillons = 10000;
		float min = -5;
		float max = 5;
		float SNRParBit = 0;
		boolean bruitActif = true;
		EmetteurTest E = new EmetteurTest();
		
		nbTests+=6;
		E.EmetteurInitTest(typeEmetteur, nbEchantillons, min, max, SNRParBit, bruitActif);
		
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
