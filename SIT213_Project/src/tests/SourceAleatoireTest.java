package tests;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import information.InformationNonConformeException;
import sources.SourceAleatoire;

/**
 * la methode qui teste la source aleatoire
 * @author gaelc
 *
 */
public class SourceAleatoireTest {

	/**
	 * a
	 */
	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	/**
	 * a
	 */
	public SourceAleatoireTest() {
	}
	
	/**
	 * a
	 */
	@Test
	public void SourceAleatoireNbBitQuelconqueTest() {
		//Arrange
		int nBitMess=7;
		
		//Act
		SourceAleatoire Source = new SourceAleatoire(nBitMess, 0);
        try {
			Source.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
        //Assert
        errorCollector.checkThat("L'information generee ne correspond pas a l'information emise", 
				Source.getInformationEmise(),is(Source.getInformationGeneree()));
		
        errorCollector.checkThat("Le nombre de bit du message de correspond pas", 
				 Source.getInformationGeneree().nbElements(),is(nBitMess));
	}
	/**
	 * a
	 */
	@Test
	public void SourceAleatoireNbBitNulTest() {
		//Arrange
		int nBitMess=0;
		
		//Act
		SourceAleatoire Source = new SourceAleatoire(nBitMess, 0);
        try {
			Source.emettre();
		} catch (InformationNonConformeException e) {
			e.printStackTrace();
		}
		
        //Assert
        errorCollector.checkThat("L'information generee ne correspond pas a l'information emise", 
				Source.getInformationEmise(),is(Source.getInformationGeneree()));
		
        errorCollector.checkThat("Le nombre de bit du message de correspond pas", 
				Source.getInformationGeneree().nbElements(),is(nBitMess));
		
	}
	
	/**
	 * a
	 */
	@Test
	public void SourceAleatoireSeedQuelconqueTest() {
		//Arrange
		Integer seed=23;
		
		//Act
		SourceAleatoire Source1 = new SourceAleatoire(50, seed);
		SourceAleatoire Source2 = new SourceAleatoire(50, seed);
		
		//Assert
		assertEquals("L'information de Source1 ne correspond pas a l'information de Source2 pour la meme seed", 
				Source1.getInformationGeneree(), Source2.getInformationGeneree());
	}

	/**
	 *a 
	 */
	@Test
	public void SourceAleatoireSeedNegativeTest() {
		//Arrange
		Integer seed=-40;
		
		//Act
		SourceAleatoire Source1 = new SourceAleatoire(50, seed);
		SourceAleatoire Source2 = new SourceAleatoire(50, seed);
		
		//Assert
		assertEquals("L'information de Source1 ne correspond pas a l'information de Source2 pour la meme seed", 
				Source1.getInformationGeneree(), Source2.getInformationGeneree());

	}

	/**
	 * main qui teste
	 * @param args arguments de la methode
	 */

	public static void main(String[] args) {
		
		SourceAleatoireTest S = new SourceAleatoireTest(); 
		try {			
			S.SourceAleatoireNbBitQuelconqueTest();
			S.SourceAleatoireNbBitNulTest();
			S.SourceAleatoireSeedQuelconqueTest();
			S.SourceAleatoireSeedNegativeTest();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
