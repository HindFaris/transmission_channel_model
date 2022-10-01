package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import signaux.*;
import sources.SourceFixe;
import information.Information;

//TO DO//
public class SignalTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public SignalTest(){}

	@Test
	public void SignalInitTest(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		nbErrors+=9;
		Signal SignalNRZ = new SignalNRZ(informationRecue,nbEchantillons, min,  max);
		
		assertEquals("Le nombre d'Echantillons ne correspond pas", nbEchantillons, SignalNRZ.getNbEchantillon() );
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas", min , SignalNRZ.getMin());
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas", max , SignalNRZ.getMax());
		nbErrors--;
		
		Signal SignalNRZT = new SignalNRZT(informationRecue,nbEchantillons, min,  max);
		
		assertEquals("Le nombre d'Echantillons ne correspond pas", nbEchantillons, SignalNRZT.getNbEchantillon());
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas", min , SignalNRZT.getMin());
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas", max, SignalNRZT.getMax());
		nbErrors--;
		
		Signal SignalRZ = new SignalRZ(informationRecue,nbEchantillons, min,  max);
		
		assertEquals("Le nombre d'Echantillons ne correspond pas", nbEchantillons, SignalRZ.getNbEchantillon() );
		nbErrors--;
		assertEquals( "La valeur de min ne correspond pas", min, SignalRZ.getMin());
		nbErrors--;
		assertEquals( "La valeur de max ne correspond pas", max, SignalRZ.getMax());
		nbErrors--;
		
	}
	
	@Test
	public static Tests testReport() {
		Tests tr;
		
		SourceFixe Source = new SourceFixe("0111000111");
		Information<Boolean> informationRecue = new Information<Boolean>();
		informationRecue = Source.getInformationGeneree();
		
		int nbEchantillons = 10;
		float min = -5;
		float max = 5;
		SignalTest S = new SignalTest();
		
		nbTests+=9;
		S.SignalInitTest(informationRecue, nbEchantillons, min, max);
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
