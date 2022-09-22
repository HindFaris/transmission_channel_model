package tests;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import information.Information;
import information.InformationNonConformeException;
import sources.*;

public class SourceFixeTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	@Test
	public void SourceFixeInitTest (String Binary) {
		nbErrors+=2;
		SourceFixe Source = new SourceFixe(Binary);
		Information <Boolean>  information = new Information<Boolean>();
        for (int index = 0; index < Binary.length(); index++) {
        	if (Binary.charAt(index) == '1') {
        		information.add(true);
        	}
        	else {
        		information.add(false);
        	}
        }
        try {
			Source.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
        
        assertEquals(Source.getInformationGeneree(),information, "ERR : L'information generee ne correspond pas au message entre");
		nbErrors--;
		assertEquals(Source.getInformationGeneree(), Source.getInformationEmise(), "ERR : L'information generee ne correspond pas a l'information emise");
		nbErrors--;
	}
	 
	@Test
	public void SourceFixeLengthTest (String Binary) {
		nbErrors++;
		SourceFixe Source = new SourceFixe(Binary);
		boolean result;
		if (Source.getInformationGeneree().nbElements()<7) {
			result =  false;
		}else result =  true;
		assertEquals(result, false, "ERR : La longueur information generee est inferieur a 7");
		nbErrors--;
	}
	
	@Test
	public static Tests testReport() {
		Tests tr;
		String longBinary = new String("0111000111");
		String shortBinary = new String("011");
		SourceFixeTest S = new SourceFixeTest();
		
		// test : message fixe
		S.SourceFixeInitTest(longBinary);
		nbTests++;
		// test : message fixe trop court -> Source Aleatoire
		S.SourceFixeLengthTest(shortBinary);
		nbTests++;
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
