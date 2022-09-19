package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sources.SourceAleatoire;


public class SourceAleatoireTest {
	
	
	//@Test
	public SourceAleatoireTest() {}
	
	public void SourceAleatoireInitTest(int nBitMess) {
		SourceAleatoire Source = new SourceAleatoire(nBitMess, 0);
		assertEquals(Source.getInformationGeneree(), Source.getInformationEmise(),
				"L'information generee ne correspond pas a l'information emise");
		assertEquals(Source.getInformationGeneree().nbElements(), nBitMess,
				"Le nombre de bit du message de correspond pas");
	}
	
	public void SourceAleatoireSeedTest(int seed) {
		SourceAleatoire Source1 = new SourceAleatoire(50, seed);
		SourceAleatoire Source2 = new SourceAleatoire(50, seed);
		assertEquals(Source1.getInformationGeneree(), Source2.getInformationGeneree(),
				"L'information de Source1 ne correspond pas a l'information de Source2 pour la meme seed");	
		//possibilite de getSeed depuis la classe Source ? -> seed dans Simulateur
	}
	
	
}
