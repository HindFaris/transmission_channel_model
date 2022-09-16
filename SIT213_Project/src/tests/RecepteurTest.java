package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import recepteur.Recepteur;


public class RecepteurTest {
	
	private int nbEchantillons = 10000; //TODO test nbEchantillons = 0
	private float min = -5;
	private float max = 5;
	
	//@Test
	public RecepteurTest(int nbEchantillons, float min, float max) {
		Recepteur Recepteur = new Recepteur(nbEchantillons, min,  max);
		//asserEquals(Recepteur.getNbEchantillons, int 10000, "La valeur du nombre d'echantillon ne correspond pas");
		//asserEquals(Recepteur.getMin, int -5, "La valeur de min ne correspond pas");
		//asserEquals(Recepteur.getMax, int 5, "La valeur de max ne correspond pas");
	}
}