package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import information.Information;
import signaux.Bruit;

//TO DO

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
	
	 public LinkedList<LinkedList <Float>> autoCorrelateTest() 
	 {
		 Bruit br = new Bruit(4, 8, 50);
		 LinkedList<Float> sum = new LinkedList <Float> (); 
		 LinkedList<LinkedList <Float>> output = new LinkedList<LinkedList <Float>> ();
		 float a = 0;
		 
	     for (int n = 0; n < br.getSignalSortieInformation().nbElements(); n++) 
	     {
	         for (int i = n; i < br.getSignalSortieInformation().nbElements(); i++) 
	         {
	        	a = (float) br.getSignalSortieInformation().iemeElement(i) * (float) br.getSignalSortieInformation().iemeElement(i-n);
				sum.add(a);
				
	         }
	         output.add(sum);
	         System.out.println(output);

	         sum.clear();
	     }
	     return output;

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
		E.autoCorrelateTest();
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
