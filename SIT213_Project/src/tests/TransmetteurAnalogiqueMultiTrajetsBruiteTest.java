/**
 * 
 */
package tests;

/*import static org.junit.Assert.*;

import java.util.stream.Collector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

import information.Information;
import transmetteurs.*;
*/

/**
 * @author jerom
 *
 */
//class TransmetteurAnalogiqueMultiTrajetsBruiteTest {
//	
//	@Rule
//	public ErrorCollector collector = new ErrorCollector();
//	
//	/**
//	 * Test method for {@link transmetteurs.TransmetteurAnalogiqueMultiTrajetsBruite#ecartType()}.
//	 * @throws Exception 
//	 */
//	@Test
//	void testEcartType() throws Exception {
//		TransmetteurAnalogiqueMultiTrajetsBruite transmetteur = new TransmetteurAnalogiqueMultiTrajetsBruite(2, (float)0, (Integer)1, (float)0.2, 12);
//		Float [] signalRecu = {(float)5,(float)5,(float)-5,(float)-5};
//		Information<Float> InfomationRecue = new Information<Float>(signalRecu);
//		transmetteur.setInformationRecue(InfomationRecue);
//		collector.checkThat("L'ecart type n'est pas le bon ", transmetteur.ecartType(),is((float)2));
//	}
//
//	/**
//	 * Test method for {@link transmetteurs.TransmetteurAnalogiqueMultiTrajetsBruite#puissance()}.
//	 */
//	@Test
//	void testPuissance() {
//		TransmetteurAnalogiqueMultiTrajetsBruite transmetteur = new TransmetteurAnalogiqueMultiTrajetsBruite(2, (float)0, (Integer)1, (float)0.2, 12);
//		Float [] signalRecu = {(float)5,(float)5,(float)-5,(float)-5};
//		Information<Float> InfomationRecue = new Information<Float>(signalRecu);
//		transmetteur.setInformationRecue(InfomationRecue);
//		collector.checkThat("La puissance n'est pas la bonne", transmetteur.puissance(),is((float)Math.pow(5,8)));
//	}
//	
//}
