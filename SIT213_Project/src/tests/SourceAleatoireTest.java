package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sources.SourceAleatoire;


public class SourceAleatoireTest {
	
	
	//@Test
	public SourceAleatoireTest(int nBitMess, int seed) {
		SourceAleatoire SourceA = new SourceAleatoire(nBitMess, seed);
		SourceAleatoire SourceB = new SourceAleatoire(nBitMess, seed);
		//assertEquals(SourceA.getInformationGeneree, , "L'information generee ne correspond pas");
		}
}
