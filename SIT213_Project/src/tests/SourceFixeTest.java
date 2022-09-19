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
		assertEquals(Source.getInformationGeneree(),Binary, "ERR : L'information generee ne correspond pas au message entre");
		assertEquals(Source.getInformationGeneree(), Source.getInformationEmise(), "ERR : L'information generee ne correspond pas a l'information emise");
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
