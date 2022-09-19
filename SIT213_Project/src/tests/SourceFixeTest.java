package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import sources.*;

public class SourceFixeTest {
	
	 
	 /* ERREUR -> instanciation linkedlist ? 
	 protected LinkedList <Boolean> binMess = new LinkedList<Boolean>();
	 binMess.add(false);
	 binMess.add(true);
	 binMess.add(true);
	 binMess.add(true);
	 binMess.add(false);
	 binMess.add(false);
	 binMess.add(false);
	 binMess.add(true);
	 binMess.add(true);
	 binMess.add(true);
	 */
	
	//@Test
	public SourceFixeTest() {}
	
	public void SourceFixeInitTest (String Binary) {
		SourceFixe SourceF = new SourceFixe(Binary);
		assertEquals(SourceF.getInformationGeneree(),Binary, "L'information generee ne correspond pas au message entre");
	}
	 
	public void SourceFixeLengthTest (String Binary) {
		 SourceFixe Source = new SourceFixe(Binary);
		 boolean result;
		 if (Source.getInformationGeneree().nbElements()<7) {
			 result =  false;
		 }else result =  true;
		 assertEquals(result, false, "La longueur information generee est inferieur a 7");
	}

}
