package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import information.InformationNonConformeException;
import sources.SourceAleatoire;


public class SourceAleatoireTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public SourceAleatoireTest() {}
	
	@Test
	public void SourceAleatoireInitTest(int nBitMess) {
		nbErrors+=2;
		SourceAleatoire Source = new SourceAleatoire(nBitMess, 0);
		
        try {
			Source.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		assertEquals("L'information generee ne correspond pas a l'information emise",Source.getInformationGeneree(), Source.getInformationEmise());
		nbErrors--;
		assertEquals("Le nombre de bit du message de correspond pas",Source.getInformationGeneree().nbElements(), nBitMess);
		nbErrors--;
	}
	
	@Test
	public void SourceAleatoireSeedTest(int seed) {
		nbErrors++;
		SourceAleatoire Source1 = new SourceAleatoire(50, seed);
		SourceAleatoire Source2 = new SourceAleatoire(50, seed);
		assertEquals("L'information de Source1 ne correspond pas a l'information de Source2 pour la meme seed",Source1.getInformationGeneree(), Source2.getInformationGeneree());
		nbErrors--;
		//Possibilite de getSeed depuis la classe Source ? -> seed dans Simulateur
	}

	@Test
	public static Tests testReport() {
		Tests tr;
		SourceAleatoireTest S = new SourceAleatoireTest();
		
		//Tests de la valeur nBitMess
		nbTests++;
		S.SourceAleatoireInitTest(23);
		nbTests++;
		S.SourceAleatoireInitTest(0);
		nbTests++;
		S.SourceAleatoireInitTest((int)0110); //Message binaire trop court
		
		//Tests de la valeur seed
		nbTests++;
		S.SourceAleatoireSeedTest(10);
		/*
		 * A tester plus tard
		 * nbTests++;
		 * S.SourceAleatoireSeedTest(0);
		 */
		
		
		tr = new Tests(nbTests,nbErrors);
		return tr;
	}
	
	
}
