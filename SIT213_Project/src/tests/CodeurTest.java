package tests;

//import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import decodage.Codeur;
import information.Information;
import information.InformationNonConformeException;

import static org.hamcrest.CoreMatchers.is;
import sources.*;

/**
 * 
 * @author gaelc
 *
 */
public class CodeurTest {
	
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	public CodeurTest() {}


	@Test
	/**
	 * 
	 */
	public void CodeurGlobalTest() {
		//Arrange
		SourceFixe s = new SourceFixe("01001110");
		SourceFixe infoTraduite = new SourceFixe("010101010010101101101010");
		Codeur codeur = new Codeur();
		Information<Boolean> informationEntrante = s.getInformationGeneree();
		Information<Boolean> informationTraduite = infoTraduite.getInformationGeneree();
		s.connecter(codeur);
		
		
		//Act
		
		try {
			s.emettre();
			codeur.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		//Assert
		errorCollector.checkThat("L'information recue par le codeur ne correspond pas a l'information emise par la source", codeur.getInformationRecue(),is(informationEntrante));
		errorCollector.checkThat("L'information traduite par le codeur ne correspond pas a la traduction attendue", codeur.getInformationEmise(), is(informationTraduite));
		
	}
	
	public static void main(String[] args) {	
		CodeurTest c = new CodeurTest();
		
		c.CodeurGlobalTest();
		
	}
}