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
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max, SNRParBit, bruitActif);
		assertEquals("Le type emetteur ne correspond pas", Emetteur.getTypeEmetteur(), typeEmetteur );
		nbErrors--;
		assertEquals( "La valeur de seed ne correspond pas",Emetteur.getNbEchantillons(), nbEchantillons);
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas", (float) Emetteur.getMin(), (float) min);
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas",Emetteur.getMax(), max);
		nbErrors--;
		assertEquals( "La valeur du SNR ne correspond pas",Emetteur.getSNRParBit(), SNRParBit);
		nbErrors--;
		assertEquals("La valeur du bruit actif ne correspond pas", Emetteur.getBruitActif(), bruitActif);
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
