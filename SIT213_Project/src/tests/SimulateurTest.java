package tests;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.LinkedList;

import simulateur.*;

public class SimulateurTest {

	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	//TODO : JAVADOC
	
	public SimulateurTest(){}
	
	@Test
	public void analyseArgumentDefautTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {}); //Permet de tester toutes les valeurs par défaut
		
		//Assert
		errorCollector.checkThat("Les Arguments par défaut : La taille du message n'est pas de 100 comme prévu par défaut", simulateur.getNbBitsMess(), is(100));
		errorCollector.checkThat("Les Arguments par défaut : Le message est detecté comme non aleatoire alors qu'il devrait l'etre par défaut", simulateur.getMessageAleatoire(), is(true));
		errorCollector.checkThat("Les Arguments par défaut : L'afficheur est actif alors qu'il ne devrait pas l'etre par défaut", simulateur.getAffichage(), is(false));
		errorCollector.checkThat("Les Arguments par défaut : La seed est active alors qu'elle ne devrait pas l'etre par défaut", simulateur.getAleatoireAvecGerme(), is(false));
		errorCollector.checkThat("Les Arguments par défaut : La forme d'onde n'est pas RZ alors qu'elle devrait l'etre par défaut", simulateur.getFormSignal(), is("RZ"));
		errorCollector.checkThat("Les Arguments par défaut : Le nombre d'échantillons n'est pas de 30 alors qu'il devrait l'etre par défaut", simulateur.getNbEchantillon(), is(30));
		errorCollector.checkThat("Les Arguments par défaut : Le min n'est pas de 0.0f alors qu'il devrait l'etre par défaut", simulateur.getMin(), is(0.0f));
		errorCollector.checkThat("Les Arguments par défaut : Le max n'est pas de 1.0f alors qu'il devrait l'etre par défaut", simulateur.getMax(), is(1.0f));
		errorCollector.checkThat("Les Arguments par défaut : Le bruit est actif alors qu'il ne devrait pas l'etre par défaut", simulateur.getBruitActif(), is(false));
		errorCollector.checkThat("Les Arguments par défaut : Les trajets indirects sont actifs alors qu'ils ne devraient pas l'etre par défaut", simulateur.getTrajetIndirect(), is(false));
		errorCollector.checkThat("Les Arguments par défaut : Le codage est actif alors qu'il ne devrait pas l'etre par défaut", simulateur.getCodage(), is(false));
	}

	@Test
	public void analyseArgumentMessTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur2 =  new Simulateur(new String[] {"-mess","1010010001"});
		Simulateur simulateur4 =  new Simulateur(new String[] {"-mess","47"});
		
		//Assert
		errorCollector.checkThat("L'Argument -mess : La taille du message n'est pas de 10 comme prévu", simulateur2.getNbBitsMess(), is(10));
		errorCollector.checkThat("L'Argument -mess : Le message est detecté comme aleatoire alors qu'il ne devrait pas l'etre", simulateur2.getMessageAleatoire(), is(false));		
		
		errorCollector.checkThat("L'Argument -mess : La taille du message n'est pas de 10 comme prévu", simulateur4.getNbBitsMess(), is(47));
		errorCollector.checkThat("L'Argument -mess : Le message est detecté comme non aleatoire alors qu'il devrait l'etre", simulateur4.getMessageAleatoire(), is(true));
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentMessFloatFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-mess","1.1"});
	}

	@Test(expected = ArgumentsException.class)
	public void analyseArgumentMessFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-mess","0"});
	}
	
	@Test
	public void analyseArgumentSondeTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-s"});;
		
		//Assert
		errorCollector.checkThat("L'Argument -s : L'afficheur n'est pas actif alors qu'il devrait l'etre par défaut", simulateur.getAffichage(), is(true));	
	}
	
	@Test
	public void analyseArgumentSeedTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-seed","1"});
		Simulateur simulateur2 =  new Simulateur(new String[] {"-seed","-1"});
		
		//Assert		
		errorCollector.checkThat("L'Argument -seed : La seed n'a pas la valeur souhaitée", simulateur.getSeed(), is(1));
		errorCollector.checkThat("L'Argument -seed : La seed n'a pas la valeur souhaitée", simulateur2.getSeed(), is(-1));
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentSeedFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-seed","1.1"});
	}
	
	@Test
	public void analyseArgumentFormTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-form","NRZ"});
		Simulateur simulateur2 =  new Simulateur(new String[] {"-form","NRZT"});
		Simulateur simulateur3 =  new Simulateur(new String[] {"-form","RZ"});
		
		//Assert		
		errorCollector.checkThat("L'Argument -form : La forme d'onde n'est pas NRZ alors qu'elle devrait l'etre", simulateur.getFormSignal(), is("NRZ"));
		errorCollector.checkThat("L'Argument -form : La forme d'onde n'est pas NRZT alors qu'elle devrait l'etre", simulateur2.getFormSignal(), is("NRZT"));
		errorCollector.checkThat("L'Argument -form : La forme d'onde n'est pas RZ alors qu'elle devrait l'etre", simulateur3.getFormSignal(), is("RZ"));
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentFormFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-form","AZERTY"});
	}
	
	@Test
	public void analyseArgumentEchTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-nbEch","40"});
		
		//Assert		
		errorCollector.checkThat("L'Argument -nbEch : Le nombre d'echantillons n'est pas 40 alors qu'elle devrait l'etre", simulateur.getNbEchantillon(), is(40));
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentEchFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-nbEch","-1"});
	}
	
	@Test
	public void analyseArgumentAmplTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-ampl","0","1"});
		Simulateur simulateur2 =  new Simulateur(new String[] {"-ampl","-10","10"}); //Symétrique
		Simulateur simulateur3 =  new Simulateur(new String[] {"-ampl","-12","10"}); //Asymétrique
		
		//Assert		
		errorCollector.checkThat("L'Argument -ampl : L'amplitude min n'est pas 0 alors qu'elle devrait l'etre", simulateur.getMin(), is((float)0));
		errorCollector.checkThat("L'Argument -ampl : L'amplitude max n'est pas 1 alors qu'elle devrait l'etre", simulateur.getMax(), is((float)1));
		
		errorCollector.checkThat("L'Argument -ampl : L'amplitude min n'est pas -10 alors qu'elle devrait l'etre", simulateur2.getMin(), is((float)-10));
		errorCollector.checkThat("L'Argument -ampl : L'amplitude max n'est pas 10 alors qu'elle devrait l'etre", simulateur2.getMax(), is((float)10));
		
		errorCollector.checkThat("L'Argument -ampl : L'amplitude min n'est pas -12 alors qu'elle devrait l'etre", simulateur3.getMin(), is((float)-12));
		errorCollector.checkThat("L'Argument -ampl : L'amplitude max n'est pas 10 alors qu'elle devrait l'etre", simulateur3.getMax(), is((float)10));
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentAmplFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ampl","10","5"});
	}
	
	@Test
	public void analyseArgumentSnrpbTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-snrpb","10"}); //>0
		Simulateur simulateur2 =  new Simulateur(new String[] {"-snrpb","-10"}); //<0
		
		//Assert		
		errorCollector.checkThat("L'Argument -snrpb : Le SNR par bit n'est pas 10 alors qu'il devrait l'etre", simulateur.getSNRParBit(), is((float)10));
		errorCollector.checkThat("L'Argument -snrpb : Le SNR par bit n'est pas -10 alors qu'il devrait l'etre", simulateur2.getSNRParBit(), is((float)-10));
	}
	
	@Test
	public void analyseArgumentTiTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-ti","1","0.1"});
		LinkedList<Integer> taus = new LinkedList<Integer>();
		taus=simulateur.getTaus();
		LinkedList<Float> alphas = new LinkedList<Float>();
		alphas=simulateur.getAlphas();
		
		//Assert
		errorCollector.checkThat("L'Argument -ti : Le ti, partie tau n'est pas correct alors qu'il devrait l'etre", taus.get(0), is((int)1));
		errorCollector.checkThat("L'Argument -ti : Le ti, partie alpha n'est pas correct alors qu'il devrait l'etre", alphas.get(0), is((float)0.1));
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentTiMoreThan5FailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ti","1","0.11","2","0.22","3","0.33","4","0.44","5","0.55","6","1"}); //Supposed to fail after 5 couples of dt at
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentAlphaNegativeFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ti","0","-1"});
	}
	
	@Test(expected = ArgumentsException.class)
	public void analyseArgumentTiTauNegativeFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ti","-10","1"}); //tau<0
	}
	
	@Test
	public void analyseArgumentCodeurTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-codeur"});
		
		//Assert		
		errorCollector.checkThat("L'Argument -codeur : Le codeur n'est pas activé alors qu'il devrait l'etre", simulateur.getCodage(), is(true));
	}
	
	
	@Test
	public void calculTEBTransmissionParfaiteTest() throws Exception{
		//Arrange
		Simulateur simulateur =  new Simulateur(new String[] {"-mess","1010010001"});
		
		//Act
		simulateur.execute();
		
		//Assert
		errorCollector.checkThat("Le taux d'erreur binaire n'est pas de 0",  simulateur.calculTauxErreurBinaire(),is(0.0f) );
	}
	
	@Test
	public void calculTEBTransmissionBruiteeTest() throws Exception{
		//Arrange
		Simulateur simulateur =  new Simulateur(new String[] {"-mess","48","-snrpb","-20"});
		
		//Act
		simulateur.execute();
		
		//Assert
		errorCollector.checkThat("Le taux d'erreur binaire est de 0 alors que la transmission est très bien bruitée",  simulateur.calculTauxErreurBinaire(),not(0.0f) );
	}
	
	@Test
	public void tailleMotRecuTest() throws Exception{
		//Arrange
		Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","1010010001"});
		
		//Act
		simulateur1.execute();
		
		//Assert
		errorCollector.checkThat( "Le mot recue ne peu pas etre bon car il ne fait pas la bonne taille", simulateur1.getTailleMotDestination(), is(10));
	}
	
	@Test
	public void mainPerformanceTest() throws Exception{
		//Arrange
		System.out.println("Testing the Whole Process with those arguments :\n");
		String[] args= new String[] {"-mess", "100", "-s", "-seed", "2", "-form", "NRZ", "-nbEch", "15", "-ampl", "-5.0", "5.0", "-snrpb", "20", "-ti","1","0.1","2","0.2","3","0.3","4","0.4","5","0.5", "-codeur"};
		String s = "java  Simulateur  ";
		for (int j = 0; j < args.length; j++) {
			s += args[j] + "  ";
		}
		System.out.println(s+"\n");
		long debut = System.currentTimeMillis();
		
		//Act
		System.out.println("Now Executing 'Simulateur':\n");
		Simulateur.main(args);
		System.out.println("\n'Simulateur' has been executed!\n\n***************************************************************************************************\n");
		
		//Assert
		long fin = System.currentTimeMillis();
		System.out.println("Temps d'execution du test de Simulateur en millisecondes : " + (fin-debut)+"\n\n");
		assertEquals("La performance n'est pas celle attendue", 400, (fin-debut), 100);
	}
}