package tests;

import java.util.LinkedList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
//import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import information.Information;
import transmetteurs.*;


/**
 * @author jerom
 *
 */
public class TransmetteurAnalogiqueMultiTrajetsBruiteTest {
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	/**
	 * Test method for {@link transmetteurs.TransmetteurAnalogiqueMultiTrajetsBruite#ecartType()}.
	 * @throws Exception 
	 */
	@Test
	public void testEcartType() throws Exception {
		//Arrange
		int nbEchantillons = 3;
		float SNRParBit = 10;
		Integer seed = 1;
		LinkedList<Float> alphas = new LinkedList<Float>();
		LinkedList<Integer> taus = new LinkedList<Integer>();
		alphas.add((float) 0.4);
		taus.add(2);
		boolean bruitActif = true;

		TransmetteurAnalogiqueMultiTrajets transmetteur = new TransmetteurAnalogiqueMultiTrajets(nbEchantillons, SNRParBit, seed, alphas, taus, bruitActif);
		Float [] signalRecu = {(float)5,(float)5,(float)-5,(float)-5};
		Information<Float> InfomationRecue = new Information<Float>(signalRecu);
		
		//Act
		transmetteur.setInformationRecue(InfomationRecue);
		
		//Assert
		assertEquals("L'ecart type n'est pas le bon",(float)2, transmetteur.ecartType(),0.1);
	}

	/**
	 * Test method for {@link transmetteurs.TransmetteurAnalogiqueMultiTrajetsBruite#puissance()}.
	 */
	@Test
	public void testPuissance() {
		//Arrange
		int nbEchantillons = 2;
		float SNRParBit = 10;
		Integer seed = 1;
		LinkedList<Float> alphas = new LinkedList<Float>();
		LinkedList<Integer> taus = new LinkedList<Integer>();
		alphas.add((float) 0.2);
		taus.add(12);
		boolean bruitActif = true;
		
		TransmetteurAnalogiqueMultiTrajets transmetteur = new TransmetteurAnalogiqueMultiTrajets(nbEchantillons, SNRParBit, seed, alphas, taus, bruitActif);
		Float [] signalRecu = {(float)5,(float)5,(float)-5,(float)-5};
		Information<Float> InfomationRecue = new Information<Float>(signalRecu);
		
		//Act
		transmetteur.setInformationRecue(InfomationRecue);
		//transmetteur.emettre();
		
		//Assert
		assertEquals("La puissance n'est pas la bonne", 25, transmetteur.puissance(),0.1);
	}
}
