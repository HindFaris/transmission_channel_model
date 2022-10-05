package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import emetteur.*;
import sources.SourceFixe;
import information.InformationNonConformeException;
import transmetteurs.*;

public class TransmetteurAnalogiqueBruiteTest {

	private static int nbTests=0;
	private static int nbErrors=0;
	SourceFixe Source = new SourceFixe("0111000111");

	public TransmetteurAnalogiqueBruiteTest(){}

	//tester chaque variable dans une methode de test?(aina)
	@Test
	public void TransmetteurAnalogiqueBruiteInitTest(int nbEchantillons, float SNRParBit, Integer seed) {
		nbErrors+=3;
		TransmetteurAnalogiqueBruite Tr = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		assertEquals("Le nombre d'echantillons ne correspond pas",Tr.getNbEchantillons(), nbEchantillons);
		nbErrors--;
		assertEquals("La valeur du SNR par Bit ne correspond pas",Tr.getSNRParBit(), SNRParBit);
		nbErrors--;
		assertEquals("La valeur de la seed ne correspond pas",Tr.getSeed(), seed);
		nbErrors--;
	}

	@Test
	public void TransmetteurAnalogiqueBruiteCalculPuissanceTest(int nbEchantillons, float min, float max, float SNRParBit, Integer seed) {

		nbErrors+=3;
		EmetteurAnalogique EmetteurNRZ = new EmetteurAnalogique("NRZ",nbEchantillons, min,  max);
		EmetteurAnalogique EmetteurNRZT = new EmetteurAnalogique("NRZT",nbEchantillons, min,  max);
		EmetteurAnalogique EmetteurRZ = new EmetteurAnalogique("RZ",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurNRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		TransmetteurAnalogiqueBruite TransmetteurNRZT = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		TransmetteurAnalogiqueBruite TransmetteurRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);

		Source.connecter(EmetteurNRZ);
		Source.connecter(EmetteurNRZT);
		Source.connecter(EmetteurRZ);
		EmetteurNRZ.connecter(TransmetteurNRZ);
		EmetteurNRZT.connecter(TransmetteurNRZT);
		EmetteurRZ.connecter(TransmetteurRZ);


		try {
			Source.emettre();
			EmetteurNRZ.emettre();
			EmetteurNRZT.emettre();
			EmetteurRZ.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}

		float puissance= TransmetteurNRZ.puissance();

		if((24.0f<puissance)&&(puissance<25.9f)) {
			nbErrors--;
		}
		else fail("La valeur de la puissance ne correspond pas sur le NRZ");

		puissance= TransmetteurNRZT.puissance();

		if((20f<puissance)&&(puissance<25.9f)) {
			nbErrors--;
		}
		else fail("La valeur de la puissance ne correspond pas sur le NRZT");

		puissance= TransmetteurRZ.puissance();

		if((24.0f<puissance)&&(puissance<25.9f)) {
			nbErrors--;
		}
		else fail("La valeur de la puissance ne correspond pas sur le RZ");
	}

	@Test
	public void TransmetteurAnalogiqueBruiteCalculEcartTypeTest(int nbEchantillons, float min, float max, float SNRParBit, Integer seed) {

		nbErrors+=3;

		EmetteurAnalogique EmetteurNRZ = new EmetteurAnalogique("NRZ",nbEchantillons, min,  max);
		EmetteurAnalogique EmetteurNRZT = new EmetteurAnalogique("NRZT",nbEchantillons, min,  max);
		EmetteurAnalogique EmetteurRZ = new EmetteurAnalogique("RZ",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurNRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		TransmetteurAnalogiqueBruite TransmetteurNRZT = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		TransmetteurAnalogiqueBruite TransmetteurRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);

		Source.connecter(EmetteurNRZ);
		Source.connecter(EmetteurNRZT);
		Source.connecter(EmetteurRZ);
		EmetteurNRZ.connecter(TransmetteurNRZ);
		EmetteurNRZT.connecter(TransmetteurNRZT);
		EmetteurRZ.connecter(TransmetteurRZ);


		try {
			Source.emettre();
			EmetteurNRZ.emettre();
			EmetteurNRZT.emettre();
			EmetteurRZ.emettre();


			float ecartType= TransmetteurNRZ.ecartType();

			if((1.0f<ecartType)&&(ecartType<2.0f)) {
				nbErrors--;
			}
			else fail("La valeur de l'ecart type ne correspond pas sur le NRZ");

			ecartType= TransmetteurNRZT.ecartType();

			if((1.0f<ecartType)&&(ecartType<2.0f)) {
				nbErrors--;
			}
			else fail("La valeur de l'ecart type ne correspond pas sur le NRZT");

			ecartType= TransmetteurRZ.ecartType();

			if((1.0f<ecartType)&&(ecartType<2.0f)) {
				nbErrors--;
			}
			else fail("La valeur de l'ecart type ne correspond pas sur le RZ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int nbEchantillons = 10;
		float SNRParBit=20;
		float max=5;
		float min=-5;
		Integer seed=10;
		TransmetteurAnalogiqueBruiteTest T = new TransmetteurAnalogiqueBruiteTest();

		nbTests+=15;
		T.TransmetteurAnalogiqueBruiteInitTest(nbEchantillons, SNRParBit, seed);
		nbTests+=3;
		T.TransmetteurAnalogiqueBruiteCalculPuissanceTest(nbEchantillons, min, max, SNRParBit, seed);
		nbTests+=3;
		T.TransmetteurAnalogiqueBruiteCalculEcartTypeTest(nbEchantillons, min, max, SNRParBit, seed);
	}
}
