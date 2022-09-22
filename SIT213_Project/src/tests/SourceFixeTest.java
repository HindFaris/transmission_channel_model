package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import sources.*;

public class SourceFixeTest {
	
	//@Test
	public SourceFixeTest() {}
	
	public void SourceFixeInitTest (String Binary) {
		SourceFixe Source = new SourceFixe(Binary);
		LinkedList<Boolean> binaryList = new LinkedList<Boolean>();
		for (int i = 0; i < Binary.length(); i++) {
			if (Binary.charAt(i) == '1')
				binaryList.add(true);
			else if (Binary.charAt(i) == '0') 
				binaryList.add(false);
		}
		assertEquals(Source.getInformationGeneree(),binaryList, "ERR : L'information generee ne correspond pas au message entre");
		//besoin de creer une information et de l'emetre 
		//assertEquals(Source.getInformationGeneree(), Source.getInformationEmise(), "ERR : L'information generee ne correspond pas a l'information emise");
	}
	 
	public void SourceFixeLengthTest (String Binary) {
		 SourceFixe Source = new SourceFixe(Binary);
		 boolean result;
		 if (Source.getInformationGeneree().nbElements()<7) {
			 result =  false;
		 }else result =  true;
		 assertEquals(result, true, "ERR : La longueur information generee est inferieur a 7");
	}

}
