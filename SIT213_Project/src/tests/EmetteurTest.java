package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import emetteur.EmetteurAnalogique;


public class EmetteurTest {
	
	
	//@Test
	public EmetteurTest(){}
	//tester chaque variable dans une methode de test?(aina)
	public void EmetteurInitTest(String typeEmetteur, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max, SNRParBit, bruitActif);
		assertEquals(Emetteur.getTypeEmetteur(), typeEmetteur , "Le type emetteur ne correspond pas");
		assertEquals(Emetteur.getNbEchantillons(), nbEchantillons, "La valeur de seed ne correspond pas");
		assertEquals(Emetteur.getMin(), min, "La valeur de min ne correspond pas");
		assertEquals(Emetteur.getMax(), max, "La valeur de max ne correspond pas");
		assertEquals(Emetteur.getSNRParBit(), SNRParBit, "La valeur du SNR ne correspond pas");
		assertEquals(Emetteur.getBruitActif(), bruitActif, "La valeur du bruit actif ne correspond pas");
	}
}
