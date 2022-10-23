package tests;

//import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import recepteur.Recepteur;

/**
 * Recepteur Test 
 * @author gaelc
 *
 */
public class RecepteurTest {

	/**
	 * a
	 */
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC

	/**
	 * a
	 */
	public RecepteurTest() {}

	/**
	 * a
	 */
	@Test
	public void RecepteurInitTest() {
		//Arrange
		int nbEchantillons =10000;
		float min =-5;
		float max =5;
		String form ="RZ";

		//Act
		Recepteur Recepteur = new Recepteur(nbEchantillons, min, max, form);

		//Assert
		errorCollector.checkThat("La valeur du nombre d'echantillons ne correspond pas", Recepteur.getNbEchantillons(),is(nbEchantillons));
		errorCollector.checkThat("La valeur de min ne correspond pas", Recepteur.getMin(), is(min));
		errorCollector.checkThat( "La valeur de max ne correspond pas", Recepteur.getMax(), is(max));
		errorCollector.checkThat("Le type ne correspond pas", Recepteur.getFormeSignal(), is(form));
	}

	/**
	 * main teste le recepteur 
	 * @param args argument du main
	 */
	public static void main(String[] args) {	
		RecepteurTest S = new RecepteurTest();

		S.RecepteurInitTest();
		//TODO : Il faut rajouter d'autres cas de tests

	}
}