package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import emetteur.EmetteurAnalogique;


public class EmetteurTest {
	
	private String typeEmetteur = "NRZ"; //TODO tester les autre types (NRZT, RZ) et la valeur par defaut (null)
	private int nbEchantillons = 10000; //TODO test nbEchantillons = 0
	private float min = -5;
	private float max = 5;
	
	//@Test
	public EmetteurTest(String typeEmetteur, int nbEchantillons, float min, float max) {
		EmetteurAnalogique Emetteur = new EmetteurAnalogique(typeEmetteur, nbEchantillons, min,  max);
		//assertEquals(Emetteur.getTypeEmetteur, , "Le type emetteur ne correspond pas");
		//asserEquals(Emetteur.getNbEchantillons, int 10000, "La valeur de seed ne correspond pas");
		//asserEquals(Emetteur.getMin, int -5, "La valeur de min ne correspond pas");
		//asserEquals(Emetteur.getMax, int 5, "La valeur de max ne correspond pas");
	}
}
