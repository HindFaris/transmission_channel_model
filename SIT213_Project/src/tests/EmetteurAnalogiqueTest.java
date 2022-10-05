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
		assertEquals("Le type emetteur ne correspond pas", typeEmetteur, Emetteur.getTypeEmetteur() );
		nbErrors--;
		assertEquals("La valeur de seed ne correspond pas", nbEchantillons, Emetteur.getNbEchantillons());
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas", (double) min, (double) Emetteur.getMin(), (double)0.0); // expected/actual/delta
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas", max, Emetteur.getMax(),(double)0.0); 
		nbErrors--;
		
	}
	
	public static void main(String[] args) {
		int nbEchantillons = 10000;
		float min = -5;
		float max = 5;
		String typeEmetteur="NRZ";
		EmetteurAnalogiqueTest E = new EmetteurAnalogiqueTest();
		
		nbTests+=4;
		E.EmetteurAnalogiqueInitTest(typeEmetteur, nbEchantillons, min, max);

	}
}
