package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sources.SourceAleatoire;


public class SourceAleatoireTest {
	
	private int nBitMess = 23;
	private int seed = 10; //TODO test seed = 0
	
	//@Test
	public SourceAleatoireTest(int nBitMess, int seed) {
		SourceAleatoire SourceA = new SourceAleatoire(nBitMess, seed);
		//assertEquals(SourceA.getInformationGeneree, , "L'information generee ne correspond pas");
		//asserEquals(SourceA.getSeed, int 10, "La valeur de seed ne correspond pas");
	}
}
