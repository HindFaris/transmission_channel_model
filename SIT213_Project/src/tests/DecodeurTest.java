package tests;

//import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import decodage.Decodeur;
import information.Information;
import information.InformationNonConformeException;

import static org.hamcrest.CoreMatchers.is;
import sources.*;

/**
 * 
 * @author gaelc
 *
 */
public class DecodeurTest {
	
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	public DecodeurTest() {}


	@Test
	/**
	 * 
	 */
	public void DecodeurGlobalTest() {
		//Arrange
		SourceFixe s = new SourceFixe("010101010010101101101010");
		SourceFixe infoTraduite = new SourceFixe("01001110");
		Decodeur decodeur = new Decodeur();
		Information<Boolean> informationEntrante = s.getInformationGeneree();
		Information<Boolean> informationTraduite = infoTraduite.getInformationGeneree();
		s.connecter(decodeur);
		
		
		//Act
		
		try {
			s.emettre();
			decodeur.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		//Assert
		errorCollector.checkThat("L'information recue par le codeur ne correspond pas a l'information emise par la source", decodeur.getInformationRecue(),is(informationEntrante));
		errorCollector.checkThat("L'information traduite par le codeur ne correspond pas a la traduction attendue", decodeur.getInformationEmise(), is(informationTraduite));
		
	}
	
	public static void main(String[] args) {	
		DecodeurTest d = new DecodeurTest();
		
		d.DecodeurGlobalTest();
		
	}
}