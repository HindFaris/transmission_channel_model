package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import signaux.*;
import sources.SourceFixe;
import information.Information;

//TO DO//
public class SignalTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	public SignalTest(){}
	
	//tester chaque variable dans une methode de test?(aina)
	@Test
	public void SignalInitTest(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		nbErrors+=9;
		Signal SignalNRZ = new SignalNRZ(informationRecue,nbEchantillons, min,  max);
		
		assertEquals(SignalNRZ.getNbEchantillon(), nbEchantillons, "Le nombre d'Echantillons ne correspond pas");
		nbErrors--;
		assertEquals(SignalNRZ.getMin(), min, "La valeur de min ne correspond pas");
		nbErrors--;
		assertEquals(SignalNRZ.getMax(), max, "La valeur de max ne correspond pas");
		nbErrors--;
		
		Signal SignalNRZT = new SignalNRZT(informationRecue,nbEchantillons, min,  max);
		
		assertEquals(SignalNRZT.getNbEchantillon(), nbEchantillons, "Le nombre d'Echantillons ne correspond pas");
		nbErrors--;
		assertEquals(SignalNRZT.getMin(), min, "La valeur de min ne correspond pas");
		nbErrors--;
		assertEquals(SignalNRZT.getMax(), max, "La valeur de max ne correspond pas");
		nbErrors--;
		
		Signal SignalRZ = new SignalRZ(informationRecue,nbEchantillons, min,  max);
		
		assertEquals(SignalRZ.getNbEchantillon(), nbEchantillons, "Le nombre d'Echantillons ne correspond pas");
		nbErrors--;
		assertEquals(SignalRZ.getMin(), min, "La valeur de min ne correspond pas");
		nbErrors--;
		assertEquals(SignalRZ.getMax(), max, "La valeur de max ne correspond pas");
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
