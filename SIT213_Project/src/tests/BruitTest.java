package tests;

//import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import java.util.LinkedList;
import signaux.Bruit;

/**
 * classe qui teste le bruit
 * @author gaelc
 *
 */
public class BruitTest {
	
	/**
	 * a
	 */
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	/**
	 * a
	 */
	public BruitTest(){}
	
	/**
	 * test bruit
	 */
	@Test
	public void BruitInitTest() {
		//Arrange
		float ecartType = 5;
		int tailleBruit = 20;
		int seed = 20;
		
		//Act
		Bruit Bruit = new Bruit(ecartType,  tailleBruit,  seed);
		
		//Assert
		errorCollector.checkThat("L'ecart type du bruit ne correspond pas", Bruit.getEcartType(), is(ecartType));
		errorCollector.checkThat("La taille du signal d'entree ne correspond pas", Bruit.getTailleBruit(), is(tailleBruit));
		errorCollector.checkThat("La valeur de la seed ne correspond pas", Bruit.getSeed(), is(seed));
	}
	
	/**
	 * test bruit avec seed
	 */
	@Test
	public void BruitSeedTest() {
		//Arrange
		float ecartType = 5;
		int tailleBruit = 20;
		int seed = 20;
		
		//Act
		Bruit Bruit1 = new Bruit(ecartType,  tailleBruit,  seed);
		Bruit Bruit2 = new Bruit(ecartType,  tailleBruit,  seed);
		
		//Assert
		errorCollector.checkThat("La valeur de la seed ne correspond pas", Bruit1.getSeed(), is(Bruit2.getSeed()));
		errorCollector.checkThat("Les Bruits correspondant a la meme seed ne sont pas les memes", Bruit1.getSignalSortieInformation(), is(Bruit2.getSignalSortieInformation()));
	}
	
	/** Un essai infructueux d'une methode pour verifier que deux echantillons tres proches ne sont pas correles
	 * 
	 * @return output La valeur de l'autocorrelation en chaque point.... infructueux comme dit precedemment
	 */
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
	
	/**
	 * main qui rassemble l'ensemble des tests
	 * @param args  du main
	 */
	public static void main(String[] args) {
		BruitTest E = new BruitTest();
		
		
		try {
			E.BruitInitTest();
			E.BruitSeedTest();
			System.out.println(E.autoCorrelateTest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
