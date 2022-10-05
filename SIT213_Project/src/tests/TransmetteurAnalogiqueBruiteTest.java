package tests;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import emetteur.*;
import sources.SourceFixe;
import information.InformationNonConformeException;
import transmetteurs.*;

/**
 * 
 * @author gaelc
 *
 */
public class TransmetteurAnalogiqueBruiteTest {

	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	//La source qu'on envoie a chaque fois
	SourceFixe Source = new SourceFixe("0111000111");

	public TransmetteurAnalogiqueBruiteTest(){}

	@Test
	/**
	 * 
	 */
	public void TransmetteurAnalogiqueBruiteInitTest() {
		//Arrange
		int nbEchantillons = 10;
		float SNRParBit=20;
		Integer seed=10;
		
		//Act
		TransmetteurAnalogiqueBruite Tr = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		
		//Assert
		errorCollector.checkThat("Le nombre d'echantillons ne correspond pas",Tr.getNbEchantillons(), is(nbEchantillons));
		errorCollector.checkThat("La valeur du SNR par Bit ne correspond pas",Tr.getSNRParBit(), is(SNRParBit));
		errorCollector.checkThat("La valeur de la seed ne correspond pas",Tr.getSeed(), is(seed));

	}

	@Test
	/**
	 * 
	 */
	public void TransmetteurAnalogiqueBruiteCalculPuissanceNRZTest() {
		//Arrange
		int nbEchantillons = 10;
		float SNRParBit=20;
		float max=5;
		float min=-5;
		Integer seed=10;
	
		EmetteurAnalogique EmetteurNRZ = new EmetteurAnalogique("NRZ",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurNRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		Source.connecter(EmetteurNRZ);
		EmetteurNRZ.connecter(TransmetteurNRZ);
		
		//Act
		try {
			Source.emettre();
			EmetteurNRZ.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		float puissance= TransmetteurNRZ.puissance();

		//Assert
		assertEquals("La valeur de la puissance ne correspond pas sur le NRZ",25f,puissance,0.5f);
	}
	
	@Test
	/**
	 * 
	 */
	public void TransmetteurAnalogiqueBruiteCalculPuissanceNRZTTest() {
		//Arrange
		int nbEchantillons = 10;
		float SNRParBit=20;
		float max=5;
		float min=-5;
		Integer seed=10;
	
		EmetteurAnalogique EmetteurNRZT = new EmetteurAnalogique("NRZT",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurNRZT = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		Source.connecter(EmetteurNRZT);
		EmetteurNRZT.connecter(TransmetteurNRZT);
		
		//Act
		try {
			Source.emettre();
			EmetteurNRZT.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		float puissance= TransmetteurNRZT.puissance();

		//Assert
		//XXX La puissance est toujours inferieure a celle calculee, ici 20 a peu pres au lieu de 25
		assertEquals("La valeur de la puissance ne correspond pas sur le NRZT",20f,puissance,0.5f);
	}
	
	@Test
	/**
	 * 
	 */
	public void TransmetteurAnalogiqueBruiteCalculPuissanceRZTest() {
		//Arrange
		int nbEchantillons = 10;
		float SNRParBit=20;
		float max=5;
		float min=-5;
		Integer seed=10;
	
		EmetteurAnalogique EmetteurRZ = new EmetteurAnalogique("RZ",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		Source.connecter(EmetteurRZ);
		EmetteurRZ.connecter(TransmetteurRZ);
		
		//Act
		try {
			Source.emettre();
			EmetteurRZ.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		float puissance= TransmetteurRZ.puissance();

		//Assert
		assertEquals("La valeur de la puissance ne correspond pas sur le RZ",25f,puissance,0.5f);
	}

	@Test
	/**
	 * 
	 */
	public void TransmetteurAnalogiqueBruiteCalculEcartTypeNRZTest() {
		
		//Arrange
		int nbEchantillons = 10;
		float SNRParBit=20;
		float max=5;
		float min=-5;
		Integer seed=10;
	
		EmetteurAnalogique EmetteurNRZ = new EmetteurAnalogique("NRZ",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurNRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		Source.connecter(EmetteurNRZ);
		EmetteurNRZ.connecter(TransmetteurNRZ);
		
		//Act
		try {
			Source.emettre();
			EmetteurNRZ.emettre();
			
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		//Assert
		try {
			
			float ecartType= TransmetteurNRZ.ecartType();
			
			assertEquals("La valeur de l'ecart type ne correspond pas sur le NRZ",1.5f,ecartType,0.5f);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	/**
	 * 
	 */
	public void TransmetteurAnalogiqueBruiteCalculEcartTypeNRZTTest() {
		
		//Arrange
		int nbEchantillons = 10;
		float SNRParBit=20;
		float max=5;
		float min=-5;
		Integer seed=10;
	
		EmetteurAnalogique EmetteurNRZT = new EmetteurAnalogique("NRZT",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurNRZT = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		Source.connecter(EmetteurNRZT);
		EmetteurNRZT.connecter(TransmetteurNRZT);
		
		//Act
		try {
			Source.emettre();
			EmetteurNRZT.emettre();
			
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		//Assert
		try {
			
			float ecartType= TransmetteurNRZT.ecartType();
			
			assertEquals("La valeur de l'ecart type ne correspond pas sur le NRZT",1.5f,ecartType,0.5f);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	/**
	 * 
	 */
	public void TransmetteurAnalogiqueBruiteCalculEcartTypeRZTest() {
		
		//Arrange
		int nbEchantillons = 10;
		float SNRParBit=20;
		float max=5;
		float min=-5;
		Integer seed=10;
	
		EmetteurAnalogique EmetteurRZ = new EmetteurAnalogique("RZ",nbEchantillons, min,  max);
		TransmetteurAnalogiqueBruite TransmetteurRZ = new TransmetteurAnalogiqueBruite(nbEchantillons,  SNRParBit,  seed);
		Source.connecter(EmetteurRZ);
		EmetteurRZ.connecter(TransmetteurRZ);
		
		//Act
		try {
			Source.emettre();
			EmetteurRZ.emettre();
			
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
		//Assert
		try {
			
			float ecartType= TransmetteurRZ.ecartType();
			
			assertEquals("La valeur de l'ecart type ne correspond pas sur le RZ",1.5f,ecartType,0.5f);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public static void main(String[] args) {
		TransmetteurAnalogiqueBruiteTest T = new TransmetteurAnalogiqueBruiteTest();

		T.TransmetteurAnalogiqueBruiteInitTest();
		T.TransmetteurAnalogiqueBruiteCalculPuissanceNRZTest();
		T.TransmetteurAnalogiqueBruiteCalculPuissanceNRZTTest();
		T.TransmetteurAnalogiqueBruiteCalculPuissanceRZTest();
		T.TransmetteurAnalogiqueBruiteCalculEcartTypeNRZTest();
		T.TransmetteurAnalogiqueBruiteCalculEcartTypeNRZTTest();
		T.TransmetteurAnalogiqueBruiteCalculEcartTypeRZTest();
	}
}
