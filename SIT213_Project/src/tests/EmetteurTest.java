package tests;

//import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import emetteur.EmetteurAnalogique;

/**
 * 
 * @author gaelc
 *
 */
public class EmetteurTest {
	
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	public EmetteurTest(){
	}
	
	@Test
	/**
	 * 
	 */
	public void EmetteurInitTest() {
		//Arrange
		int nbEchantillons = 10000;
		float min = -5;
		float max = 5;
		
		//Act
		EmetteurAnalogique emetteurNRZ = new EmetteurAnalogique("NRZ", nbEchantillons, min,  max);
		EmetteurAnalogique emetteurNRZT = new EmetteurAnalogique("NRZT", nbEchantillons, min,  max);
		EmetteurAnalogique emetteurRZ = new EmetteurAnalogique("RZ", nbEchantillons, min,  max);
		
		//Assert
		errorCollector.checkThat("Le type emetteur ne correspond pas en NRZ", emetteurNRZ.getTypeEmetteur() , is("NRZ") );
		errorCollector.checkThat( "Le nombre d'echantillons ne correspond pas",emetteurNRZ.getNbEchantillons() ,is(nbEchantillons));
		errorCollector.checkThat( "La valeur du min ne correspond pas",emetteurNRZ.getMin() ,is(min));
		errorCollector.checkThat( "La valeur du max ne correspond pas",emetteurNRZ.getMax() ,is(max));

		errorCollector.checkThat("Le type emetteur ne correspond pas en NRZT", emetteurNRZT.getTypeEmetteur() , is("NRZT") );
		errorCollector.checkThat( "Le nombre d'echantillons ne correspond pas",emetteurNRZT.getNbEchantillons() ,is(nbEchantillons));
		errorCollector.checkThat( "La valeur du min ne correspond pas",emetteurNRZT.getMin() ,is(min));
		errorCollector.checkThat( "La valeur du max ne correspond pas",emetteurNRZT.getMax() ,is(max));

		errorCollector.checkThat("Le type emetteur ne correspond pas en RZ", emetteurRZ.getTypeEmetteur() , is("RZ") );
		errorCollector.checkThat( "Le nombre d'echantillons ne correspond pas",emetteurRZ.getNbEchantillons() ,is(nbEchantillons));
		errorCollector.checkThat( "La valeur du min ne correspond pas",emetteurRZ.getMin() ,is(min));
		errorCollector.checkThat( "La valeur du max ne correspond pas",emetteurRZ.getMax() ,is(max));
	}

	public static void main(String[] args) {
		EmetteurTest E = new EmetteurTest();
		
		try {
			E.EmetteurInitTest();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
