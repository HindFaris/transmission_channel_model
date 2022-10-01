package tests;
import static org.junit.Assert.*;
import org.junit.Test;


import java.util.LinkedList;

import signaux.Bruit;

//TO DO

public class BruitTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public BruitTest(){}
	
	@Test
	public void BruitInitTest(float ecartType, int tailleBruit, int seed) {
		nbErrors+=3;
		Bruit Bruit = new Bruit(ecartType,  tailleBruit,  seed);
		assertEquals("L'ecart type du bruit ne correspond pas",(float)ecartType, (float) Bruit.getEcartType());
		nbErrors--;
		assertEquals("La taille du signal d'entree ne correspond pas", tailleBruit, Bruit.getTailleBruit());
		nbErrors--;
		assertEquals("La valeur de la seed ne correspond pas",(int) seed , (int) Bruit.getSeed());
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
