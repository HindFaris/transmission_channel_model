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
	 public SourceFixeTest (String Binary) {
		SourceFixe SourceF = new SourceFixe(Binary);
		//assertEquals(SourceF.getInformationGeneree,binMess, "Linformation generee ne correspond pas au message entre");
	}
}
