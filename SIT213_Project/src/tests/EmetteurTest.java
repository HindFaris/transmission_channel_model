package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import emetteur.EmetteurAnalogique;


public class EmetteurTest {
	
	
	//@Test
	public EmetteurTest(String typeEmetteur, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max, SNRParBit, bruitActif);
		//assertEquals(Emetteur.getTypeEmetteur, , "Le type emetteur ne correspond pas");
		//asserEquals(Emetteur.getNbEchantillons, int 10000, "La valeur de seed ne correspond pas");
		//asserEquals(Emetteur.getMin, int -5, "La valeur de min ne correspond pas");
		//asserEquals(Emetteur.getMax, int 5, "La valeur de max ne correspond pas");
	}
}
