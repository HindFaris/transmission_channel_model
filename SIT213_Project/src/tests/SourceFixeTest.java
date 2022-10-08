package tests;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceFixe;

/**
 * 
 * @author gaelc
 *
 */
public class SourceFixeTest {
	
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	public SourceFixeTest(){}
	
	@Test
	/**
	 * 
	 */
	public void SourceFixeInitTest () {
		//Arrange
		String longBinary = new String("0111000111");
		SourceFixe Source = new SourceFixe(longBinary);
		Information <Boolean>  information = new Information<Boolean>();
		
		//On traduit de nous meme de String a une information Binaire
        for (int index = 0; index < longBinary.length(); index++) {
        	if (longBinary.charAt(index) == '1') {
        		information.add(true);
        	}
        	else {
        		information.add(false);
        	}
        }
        
        //Act
        try {
			Source.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
        
        //Assert
        errorCollector.checkThat("ERR : L'information generee ne correspond pas au message entre",Source.getInformationGeneree(),is(information));
        errorCollector.checkThat("ERR : L'information generee ne correspond pas a l'information emise", Source.getInformationEmise(), is(Source.getInformationGeneree()));
	}
	 
	@Test
	/**
	 * 
	 */
	public void SourceFixeLengthTest () {
		//Arrange
		String shortBinary = new String("011");
		SourceFixe Source = new SourceFixe(shortBinary);
		boolean result;
		
		//Act
		if (Source.getInformationGeneree().nbElements()<7) {
			result =  false;
		}else result =  true;
		
		//Assert
		assertEquals("ERR : La longueur information generee est inferieur a 7", false, result  );
	}
	
	public static void main(String[] args) {
		SourceFixeTest S = new SourceFixeTest();
		
		try {
			// test : message fixe
			S.SourceFixeInitTest();
			// test : message fixe trop court -> Source Aleatoire
			S.SourceFixeLengthTest();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
