package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import simulateur.*;

class SimulateurTest {

	@Test
	void testCalculTauxErreurBinaire() throws Exception{

		//transmetteurLogique
		Simulateur simulateur =  new Simulateur(new String[] {"-mess","1010010001"});
		simulateur.execute();
		assertEquals( "Le taux d'erreur binaire n'est pas de 0",0.0 , simulateur.calculTauxErreurBinaire());

		//Nous ne pouvons pas tester un cas ou le taux d'erreur binaire est different de 0 car nous n'avons qu'un transmetteur parfait a disposition
	}

	@Test
	void testAnalyseArguments()  throws  ArgumentsException {
		Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","1010010001", "-s"});
		assertEquals("Le message est detecte comme aleatoire alors qu'il ne le devrait pas",simulateur1.getMessageAleatoire()  , false);
		assertEquals("L'afficheur n'est pas actif alors qu'il devrait l'etre",simulateur1.getAffichage()  , true );
		assertEquals("La seed est active alors qu'elle ne devrait pas l'etre",simulateur1.getAleatoireAvecGerme()  , false );
		
		Simulateur simulateur2 =  new Simulateur(new String[] {"-seed","35","-s"});
		assertEquals("Le message est detecte comme non aleatoire alors qu'il devrait l'etre",simulateur2.getMessageAleatoire()  , true );
		assertEquals("L'afficheur est actif alors qu'il ne devrait pas l'etre", simulateur2.getAffichage()  , true );
		assertEquals("La seed n'est pas active alor qu'elle devrait l'etre", simulateur2.getAleatoireAvecGerme()  , true  );
		
		Simulateur simulateur3 =  new Simulateur(new String[0]);
		assertEquals("Le message est detecte comme non aleatoire alors qu'il devrait l'etre", simulateur3.getMessageAleatoire()  , true);
		assertEquals("Le message est detecte comme non aleatoire alors qu'il devrait l'etre",simulateur3.getAffichage()  , false );
		assertEquals("La seed est active alors qu'elle ne devrait pas l'etre",simulateur3.getAleatoireAvecGerme()  , false );
		
		Simulateur simulateur4 =  new Simulateur(new String[] {"-mess","47"});
		assertEquals("Le message est detecte comme non aleatoire alors qu'il devrait l'etre",simulateur4.getMessageAleatoire()  , true );
		assertEquals("L'afficheur est actif alors qu'il ne devrait pas l'etre",simulateur4.getAffichage()  , false );
		assertEquals("La seed est active alors qu'elle ne devrait pas l'etre",simulateur4.getAleatoireAvecGerme()  , false );
	}
	
	@Test
	void testExecute() throws Exception{
		Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","1010010001"});
		simulateur1.execute();
		assertEquals("Le mot recue ne peu pas etre bon car il ne fait pas la bonne taille",simulateur1.getTailleMotDestination()  , 10 );
	}

}

