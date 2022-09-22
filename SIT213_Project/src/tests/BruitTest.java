package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import signaux.Bruit;


public class BruitTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public BruitTest(){}
	
	@Test
	public void BruitInitTest(float ecartType, int tailleSignalEntree, int nbEchantillons) {
		nbErrors+=3;
		Bruit Bruit = new Bruit(ecartType,  tailleSignalEntree,  nbEchantillons);
		assertEquals(Bruit.getEcartType(), ecartType , "L'ecart type du bruit ne correspond pas");
		nbErrors--;
		assertEquals(Bruit.getTailleSignalEntree(), tailleSignalEntree, "La taille du signal d'entree ne correspond pas");
		nbErrors--;
		assertEquals(Bruit.getNbEchantillons(), nbEchantillons, "Le nombre d'echantillon ne correspond pas");
		nbErrors--;
	}
	
	@Test
	public static Tests testReport() {
		Tests tr;
		
		float ecartType = 5;
		int tailleSignalEntree = 20;
		int nbEchantillons = 10000;
		
		BruitTest E = new BruitTest();
		
		nbTests+=3;
		E.BruitInitTest(ecartType, tailleSignalEntree, nbEchantillons);
		
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
