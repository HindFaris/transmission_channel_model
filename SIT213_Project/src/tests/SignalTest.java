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
	
	//tester chaque variable dans une methode de test?(aina)
	@Test
	public void SignalInitTest(Information<Boolean> informationRecue, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		nbErrors+=15;
		Signal SignalNRZ = new SignalNRZ(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
		
		assertEquals("Le nombre d'Echantillons ne correspond pas", SignalNRZ.getNbEchantillon(), nbEchantillons);
		nbErrors--;
		assertEquals( "La valeur de min ne correspond pas",SignalNRZ.getMin(), min);
		nbErrors--;
		assertEquals( "La valeur de max ne correspond pas",SignalNRZ.getMax(), max);
		nbErrors--;
//		assertEquals("La valeur du SNR ne correspond pas",SignalNRZ.getSNRParBit(), SNRParBit );
		nbErrors--;
//		assertEquals( "La valeur du bruit actif ne correspond pas",SignalNRZ.isBruitActif(), bruitActif);
		nbErrors--;
		
		Signal SignalNRZT = new SignalNRZT(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
		
		assertEquals( "Le nombre d'Echantillons ne correspond pas",SignalNRZT.getNbEchantillon(), nbEchantillons);
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas",SignalNRZT.getMin(), min);
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas",SignalNRZT.getMax(), max );
		nbErrors--;
//		assertEquals("La valeur du SNR ne correspond pas",SignalNRZT.getSNRParBit(), SNRParBit );
		nbErrors--;
//		assertEquals(SignalNRZT.isBruitActif(), bruitActif, "La valeur du bruit actif ne correspond pas");
		nbErrors--;
		
		Signal SignalRZ = new SignalRZ(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
		
		assertEquals("Le nombre d'Echantillons ne correspond pas",SignalRZ.getNbEchantillon(), nbEchantillons);
		nbErrors--;
		assertEquals("La valeur de min ne correspond pas",SignalRZ.getMin(), min);
		nbErrors--;
		assertEquals("La valeur de max ne correspond pas",SignalRZ.getMax(), max);
		nbErrors--;
//		assertEquals(SignalRZ.getSNRParBit(), SNRParBit, "La valeur du SNR ne correspond pas");
		nbErrors--;
//		assertEquals(SignalRZ.isBruitActif(), bruitActif, "La valeur du bruit actif ne correspond pas");
		nbErrors--;
		
	}
	
//	@Test
//	public void SignalCalculPuissanceTest(Information<Boolean> informationRecue, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
//		
//		nbErrors+=3;
//		Signal SignalNRZ = new SignalNRZ(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
//		Signal SignalNRZT = new SignalNRZT(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
//		Signal SignalRZ = new SignalRZ(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
//		float puissance= SignalNRZ.puissance();
//		
//		if((24.0f<puissance)&&(puissance<25.9f)) {
//			nbErrors--;
//		}
//		else fail("La valeur de la puissance ne correspond pas sur le NRZ");
//		
//		puissance= SignalNRZT.puissance();
//		
//		if((20f<puissance)&&(puissance<25.9f)) {
//			nbErrors--;
//		}
//		else fail("La valeur de la puissance ne correspond pas sur le NRZT");
//		
//		puissance= SignalRZ.puissance();
//		
//		if((24.0f<puissance)&&(puissance<25.9f)) {
//			nbErrors--;
//		}
//		else fail("La valeur de la puissance ne correspond pas sur le RZ");
//	}
//	
//	@Test
//	public void SignalCalculEcartTypeTest(Information<Boolean> informationRecue, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
//		
//		nbErrors+=3;
//		Signal SignalNRZ = new SignalNRZ(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
//		Signal SignalNRZT = new SignalNRZT(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
//		Signal SignalRZ = new SignalRZ(informationRecue,nbEchantillons, min,  max, SNRParBit, bruitActif);
//		float ecartType= SignalNRZ.ecartType();
//		
//		if((3.0f<ecartType)&&(ecartType<3.9f)) {
//			nbErrors--;
//		}
//		else fail("La valeur de l'ecart type ne correspond pas sur le NRZ");
//		
//		ecartType= SignalNRZT.ecartType();
//		
//		if((3.0f<ecartType)&&(ecartType<3.9f)) {
//			nbErrors--;
//		}
//		else fail("La valeur de l'ecart type ne correspond pas sur le NRZT");
//		
//		ecartType= SignalRZ.ecartType();
//		
//		if((3.0f<ecartType)&&(ecartType<3.9f)) {
//			nbErrors--;
//		}
//		else fail("La valeur de l'ecart type ne correspond pas sur le RZ");
//	}
//	
	@Test
	public static Tests testReport() {
		Tests tr;
		
		SourceFixe Source = new SourceFixe("0111000111");
		Information<Boolean> informationRecue = new Information<Boolean>();
		informationRecue = Source.getInformationGeneree();
		
		int nbEchantillons = 10;
		float min = -5;
		float max = 5;
		float SNRParBit = 10;
		boolean bruitActif = false;
		SignalTest S = new SignalTest();
		
		nbTests+=15;
		S.SignalInitTest(informationRecue, nbEchantillons, min, max, SNRParBit, bruitActif);
		nbTests+=3;
		//S.SignalCalculPuissanceTest(informationRecue, nbEchantillons, min, max, SNRParBit, bruitActif);
		nbTests+=3;
		//S.SignalCalculEcartTypeTest(informationRecue, nbEchantillons, min, max, SNRParBit, bruitActif);
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
