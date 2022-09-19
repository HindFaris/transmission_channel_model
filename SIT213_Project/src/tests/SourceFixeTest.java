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
		assertEquals(SourceF.getInformationGeneree(),Binary, "L'information generee ne correspond pas au message entre");
	}
	 
	 public boolean SourceFixeLengthTest (String Binary) throws InvalideParameterException {
		 SourceFixe Source = new SourceFixe(Binary);
		 if (Source.getInformationGeneree().nbElements()<7) {
			 //comment tester que ca devient une source aleatoire
			 //comment tester que la source fixe n'est pas creee
			 return true;
		 }else return false;	 
	 }

}
