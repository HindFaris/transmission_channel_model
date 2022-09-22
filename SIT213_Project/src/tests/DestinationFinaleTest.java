package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import destinations.*;


public class DestinationFinaleTest {
	
	//@Test
	 public DestinationFinaleTest () {
	}
	 
	public void DestinationFinaleInitTest (String Binary) {
			DestinationFinale DestinationF = new DestinationFinale();
			assertEquals(DestinationF.getInformationRecue(), Binary, 
					"L'information recue ne correspond pas a l'information emise");
	}
}
