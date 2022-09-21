package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import simulateur.*;

class SimulateurTest {

	@Test
	void testCalculTauxErreurBinaire() throws Exception{

		//transmetteurLogique
		Simulateur simulateur =  new Simulateur(new String[] {"-mess","1010010001"});
		simulateur.execute();
		assertEquals( 0.0 , simulateur.calculTauxErreurBinaire() , "Le taux d'erreur binaire n'est pas de 0");

		//Nous ne pouvons pas tester un cas ou le taux d'erreur binaire est different de 0 car nous n'avons qu'un transmetteur parfait a disposition
	}

	@Test
	void testAnalyseArguments()  throws  ArgumentsException {
		Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","1010010001", "-s"});
		assertEquals(simulateur1.getMessageAleatoire()  , false , "Le message est detecte comme aleatoire alors qu'il ne le devrait pas");
		assertEquals(simulateur1.getAffichage()  , true , "L'afficheur n'est pas actif alors qu'il devrait l'etre");
		assertEquals(simulateur1.getAleatoireAvecGerme()  , false , "La seed est active alors qu'elle ne devrait pas l'etre");
		
		Simulateur simulateur2 =  new Simulateur(new String[] {"-seed","35","-s"});
		assertEquals(simulateur2.getMessageAleatoire()  , true , "Le message est detecte comme non aleatoire alors qu'il devrait l'etre");
		assertEquals(simulateur2.getAffichage()  , true , "L'afficheur est actif alors qu'il ne devrait pas l'etre");
		assertEquals(simulateur2.getAleatoireAvecGerme()  , true , "La seed n'est pas active alor qu'elle devrait l'etre");
		
		Simulateur simulateur3 =  new Simulateur(new String[0]);
		assertEquals(simulateur3.getMessageAleatoire()  , true , "Le message est detecte comme non aleatoire alors qu'il devrait l'etre");
		assertEquals(simulateur3.getAffichage()  , false , "L'afficheur est actif alors qu'il ne devrait pas l'etre");
		assertEquals(simulateur3.getAleatoireAvecGerme()  , false , "La seed est active alors qu'elle ne devrait pas l'etre");
		
		Simulateur simulateur4 =  new Simulateur(new String[] {"-mess","47"});
		assertEquals(simulateur4.getMessageAleatoire()  , true , "Le message est detecte comme non aleatoire alors qu'il devrait l'etre");
		assertEquals(simulateur4.getAffichage()  , false , "L'afficheur est actif alors qu'il ne devrait pas l'etre");
		assertEquals(simulateur4.getAleatoireAvecGerme()  , false , "La seed est active alors qu'elle ne devrait pas l'etre");
	}
	
	@Test
	void testExecute() throws Exception{
		Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","1010010001"});
		simulateur1.execute();
		assertEquals(simulateur1.getTailleMotDestination()  , 10 , "Le mot recue ne peu pas etre bon car il ne fait pas la bonne taille");
	}

}

