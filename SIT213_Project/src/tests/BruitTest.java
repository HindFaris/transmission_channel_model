package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import emetteur.EmetteurAnalogique;


public class BruitTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public BruitTest(){}
	
	@Test
	public void BruitInitTest(String typeEmetteur, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		nbErrors+=6;
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max, SNRParBit, bruitActif);
		assertEquals(Emetteur.getTypeEmetteur(), typeEmetteur , "Le type emetteur ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getNbEchantillons(), nbEchantillons, "La valeur de seed ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getMin(), min, "La valeur de min ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getMax(), max, "La valeur de max ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getSNRParBit(), SNRParBit, "La valeur du SNR ne correspond pas");
		nbErrors--;
		assertEquals(Emetteur.getBruitActif(), bruitActif, "La valeur du bruit actif ne correspond pas");
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
		BruitTest E = new BruitTest();
		
		nbTests+=6;
		E.BruitInitTest(typeEmetteur, nbEchantillons, min, max, SNRParBit, bruitActif);
		
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
