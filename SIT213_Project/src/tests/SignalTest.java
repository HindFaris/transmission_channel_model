package tests;

//import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import signaux.*;
import sources.SourceFixe;
import information.Information;

/**
 * 
 * @author gaelc
 *
 */
public class SignalTest {
	
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	public SignalTest(){}

	@Test
	public void SignalInitTest() {
		//Arrange
		int nbEchantillons = 10;
		float min = -5;
		float max = 5;
		SourceFixe Source = new SourceFixe("0111000111");
		Information<Boolean> informationRecue = new Information<Boolean>();
		informationRecue = Source.getInformationGeneree();
		
		//Act
		Signal SignalNRZ = new SignalNRZ(informationRecue,nbEchantillons, min,  max);
		Signal SignalNRZT = new SignalNRZT(informationRecue,nbEchantillons, min,  max);
		Signal SignalRZ = new SignalRZ(informationRecue,nbEchantillons, min,  max);
		
		//Assert
		errorCollector.checkThat( "L'Information entree ne correspond pas",SignalNRZ.getSignalEntree() ,is(informationRecue));
		errorCollector.checkThat( "Le nombre d'echantillons ne correspond pas",SignalNRZ.getNbEchantillons() ,is(nbEchantillons));
		errorCollector.checkThat( "La valeur du min ne correspond pas",SignalNRZ.getMin() ,is(min));
		errorCollector.checkThat( "La valeur du max ne correspond pas",SignalNRZ.getMax() ,is(max));
		
		errorCollector.checkThat( "L'Information entree ne correspond pas",SignalNRZT.getSignalEntree() ,is(informationRecue));
		errorCollector.checkThat( "Le nombre d'echantillons ne correspond pas",SignalNRZT.getNbEchantillons() ,is(nbEchantillons));
		errorCollector.checkThat( "La valeur du min ne correspond pas",SignalNRZT.getMin() ,is(min));
		errorCollector.checkThat( "La valeur du max ne correspond pas",SignalNRZT.getMax() ,is(max));
		
		errorCollector.checkThat( "L'Information entree ne correspond pas",SignalRZ.getSignalEntree() ,is(informationRecue));
		errorCollector.checkThat( "Le nombre d'echantillons ne correspond pas",SignalRZ.getNbEchantillons() ,is(nbEchantillons));
		errorCollector.checkThat( "La valeur du min ne correspond pas",SignalRZ.getMin() ,is(min));
		errorCollector.checkThat( "La valeur du max ne correspond pas",SignalRZ.getMax() ,is(max));
	}
	
	public static void main(String[] args) {
		
		SignalTest S = new SignalTest();
		try {
			S.SignalInitTest();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
