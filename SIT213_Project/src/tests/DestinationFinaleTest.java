package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import destinations.*;
import sources.SourceFixe;


public class DestinationFinaleTest {
	
	private static int nbTests=0;
	private static int nbErrors=0;
	
	 public DestinationFinaleTest () {
	}
	 
	 @Test
	public void DestinationFinaleInitTest (String Binary) {
		nbErrors++;
		/*
		DestinationFinale DF = new DestinationFinale();
		//Cela ne fonctionnera pas car on ne peut pas instancier la destination avec une valeur de message, il vaut mieux supprimer la classe de test...
		//(A voir avec tout le monde).
		
		assertEquals(DF.getInformationRecue(),Binary, "ERR : L'information recue ne correspond pas au message entre");
		*/
		nbErrors--;
	}

	public static Tests testReport() {
		Tests tr;
		
		
		tr = new Tests(nbTests,nbErrors);
		return tr; 
	}
}
