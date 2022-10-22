package tests;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.LinkedList;

import simulateur.*;

/**
 * Le Test sur la classe Simulateur, qui teste les arguments ainsi que le process complet
 * @author gaelc
 *
 */
public class SimulateurTest {

	@Rule
	public final ErrorCollector errorCollector= new ErrorCollector();
	
	/**
	 * Constructeur du Test
	 */
	public SimulateurTest(){}
	
	@Test
	/**
	 * Test pour tous les arguments par defaut
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentDefautTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {}); //Permet de tester toutes les valeurs par defaut
		
		//Assert
		errorCollector.checkThat("Les Arguments par defaut : La taille du message n'est pas de 100 comme prevu par defaut", simulateur.getNbBitsMess(), is(100));
		errorCollector.checkThat("Les Arguments par defaut : Le message est detecte comme non aleatoire alors qu'il devrait l'etre par defaut", simulateur.getMessageAleatoire(), is(true));
		errorCollector.checkThat("Les Arguments par defaut : L'afficheur est actif alors qu'il ne devrait pas l'etre par defaut", simulateur.getAffichage(), is(false));
		errorCollector.checkThat("Les Arguments par defaut : La seed est active alors qu'elle ne devrait pas l'etre par defaut", simulateur.getAleatoireAvecGerme(), is(false));
		errorCollector.checkThat("Les Arguments par defaut : La forme d'onde n'est pas RZ alors qu'elle devrait l'etre par defaut", simulateur.getFormSignal(), is("RZ"));
		errorCollector.checkThat("Les Arguments par defaut : Le nombre d'echantillons n'est pas de 30 alors qu'il devrait l'etre par defaut", simulateur.getNbEchantillon(), is(30));
		errorCollector.checkThat("Les Arguments par defaut : Le min n'est pas de 0.0f alors qu'il devrait l'etre par defaut", simulateur.getMin(), is(0.0f));
		errorCollector.checkThat("Les Arguments par defaut : Le max n'est pas de 1.0f alors qu'il devrait l'etre par defaut", simulateur.getMax(), is(1.0f));
		errorCollector.checkThat("Les Arguments par defaut : Le bruit est actif alors qu'il ne devrait pas l'etre par defaut", simulateur.getBruitActif(), is(false));
		errorCollector.checkThat("Les Arguments par defaut : Les trajets indirects sont actifs alors qu'ils ne devraient pas l'etre par defaut", simulateur.getTrajetIndirect(), is(false));
		errorCollector.checkThat("Les Arguments par defaut : Le codage est actif alors qu'il ne devrait pas l'etre par defaut", simulateur.getCodage(), is(false));
	}

	@Test
	/**
	 * Test pour l'argument Message
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentMessTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur2 =  new Simulateur(new String[] {"-mess","1010010001"});
		Simulateur simulateur4 =  new Simulateur(new String[] {"-mess","47"});
		
		//Assert
		errorCollector.checkThat("L'Argument -mess : La taille du message n'est pas de 10 comme prevu", simulateur2.getNbBitsMess(), is(10));
		errorCollector.checkThat("L'Argument -mess : Le message est detecte comme aleatoire alors qu'il ne devrait pas l'etre", simulateur2.getMessageAleatoire(), is(false));		
		
		errorCollector.checkThat("L'Argument -mess : La taille du message n'est pas de 10 comme prevu", simulateur4.getNbBitsMess(), is(47));
		errorCollector.checkThat("L'Argument -mess : Le message est detecte comme non aleatoire alors qu'il devrait l'etre", simulateur4.getMessageAleatoire(), is(true));
	}
	
	@Test(expected = ArgumentsException.class)
	/**
	 * Test pour l'argument Message avec un float
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentMessFloatFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-mess","1.1"});
	}

	@Test(expected = ArgumentsException.class)
	/**
	 * Test pour l'argument Message avec 0 
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentMessFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-mess","0"});
	}
	
	@Test
	/**
	 * Test pour l'argument Sonde
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentSondeTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-s"});;
		
		//Assert
		errorCollector.checkThat("L'Argument -s : L'afficheur n'est pas actif alors qu'il devrait l'etre par defaut", simulateur.getAffichage(), is(true));	
	}
	
	@Test
	/**
	 * Test pour l'argument Seed
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentSeedTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-seed","1"});
		Simulateur simulateur2 =  new Simulateur(new String[] {"-seed","-1"});
		
		//Assert		
		errorCollector.checkThat("L'Argument -seed : La seed n'a pas la valeur souhaitee", simulateur.getSeed(), is(1));
		errorCollector.checkThat("L'Argument -seed : La seed n'a pas la valeur souhaitee", simulateur2.getSeed(), is(-1));
	}
	
	@Test(expected = ArgumentsException.class)
	/**
	 *  Test pour l'argument Seed avec un float
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentSeedFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-seed","1.1"});
	}
	
	@Test
	/**
	 * Test pour l'argument form
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
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
	/**
	 * Test pour l'argument form avec une forme autre que les trois attendues
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentFormFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-form","AZERTY"});
	}
	
	@Test
	/**
	 * Test pour l'argument nbEch 
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentEchTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-nbEch","40"});
		
		//Assert		
		errorCollector.checkThat("L'Argument -nbEch : Le nombre d'echantillons n'est pas 40 alors qu'elle devrait l'etre", simulateur.getNbEchantillon(), is(40));
	}
	
	@Test(expected = ArgumentsException.class)
	/**
	 * Test pour l'argument nbEch avec un nb negatif
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentEchFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-nbEch","-1"});
	}
	
	@Test
	/**
	 * Test pour l'argument ampl
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentAmplTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-ampl","0","1"});
		Simulateur simulateur2 =  new Simulateur(new String[] {"-ampl","-10","10"}); //Symetrique
		Simulateur simulateur3 =  new Simulateur(new String[] {"-ampl","-12","10"}); //Asymetrique
		
		//Assert		
		errorCollector.checkThat("L'Argument -ampl : L'amplitude min n'est pas 0 alors qu'elle devrait l'etre", simulateur.getMin(), is((float)0));
		errorCollector.checkThat("L'Argument -ampl : L'amplitude max n'est pas 1 alors qu'elle devrait l'etre", simulateur.getMax(), is((float)1));
		
		errorCollector.checkThat("L'Argument -ampl : L'amplitude min n'est pas -10 alors qu'elle devrait l'etre", simulateur2.getMin(), is((float)-10));
		errorCollector.checkThat("L'Argument -ampl : L'amplitude max n'est pas 10 alors qu'elle devrait l'etre", simulateur2.getMax(), is((float)10));
		
		errorCollector.checkThat("L'Argument -ampl : L'amplitude min n'est pas -12 alors qu'elle devrait l'etre", simulateur3.getMin(), is((float)-12));
		errorCollector.checkThat("L'Argument -ampl : L'amplitude max n'est pas 10 alors qu'elle devrait l'etre", simulateur3.getMax(), is((float)10));
	}
	
	@Test(expected = ArgumentsException.class)
	/**
	 * Test pour l'argument ampl avec un min > max
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentAmplFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ampl","10","5"});
	}
	
	@Test
	/**
	 * Test pour l'argument snrpb
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentSnrpbTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-snrpb","10"}); //>0
		Simulateur simulateur2 =  new Simulateur(new String[] {"-snrpb","-10"}); //<0
		
		//Assert		
		errorCollector.checkThat("L'Argument -snrpb : Le SNR par bit n'est pas 10 alors qu'il devrait l'etre", simulateur.getSNRParBit(), is((float)10));
		errorCollector.checkThat("L'Argument -snrpb : Le SNR par bit n'est pas -10 alors qu'il devrait l'etre", simulateur2.getSNRParBit(), is((float)-10));
	}
	
	@Test
	/**
	 * Test pour l'argument ti
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
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
	/**
	 * Test pour l'argument ti avec plus de 5 couples de dt at
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentTiMoreThan5FailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ti","1","0.11","2","0.22","3","0.33","4","0.44","5","0.55","6","1"}); //Supposed to fail after 5 couples of dt at
	}
	
	@Test(expected = ArgumentsException.class)
	/**
	 * Test pour l'argument ti avec un alpha negatif
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentAlphaNegativeFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ti","0","-1"});
	}
	
	@Test(expected = ArgumentsException.class)
	/**
	 * Test pour l'argument ti avec un tau negatif
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentTiTauNegativeFailTest()  throws  ArgumentsException {
		//Arrange, Act and Assert
		new Simulateur(new String[] {"-ti","-10","1"}); //tau<0
	}
	
	@Test
	/**
	 * Test pour l'argument codeur
	 * @throws ArgumentsException si les arguments ne sont pas corrects
	 */
	public void analyseArgumentCodeurTest()  throws  ArgumentsException {
		//Arrange and Act
		Simulateur simulateur =  new Simulateur(new String[] {"-codeur"});
		
		//Assert		
		errorCollector.checkThat("L'Argument -codeur : Le codeur n'est pas active alors qu'il devrait l'etre", simulateur.getCodage(), is(true));
	}
	
	
	@Test
	/**
	 * Test pour la methode qui calcule le TEB en transmission parfaite
	 * @throws Exception si les arguments ne sont pas corrects
	 */
	public void calculTEBTransmissionParfaiteTest() throws Exception{
		//Arrange
		Simulateur simulateur =  new Simulateur(new String[] {"-mess","1010010001"});
		
		//Act
		simulateur.execute();
		
		//Assert
		errorCollector.checkThat("Le taux d'erreur binaire n'est pas de 0",  simulateur.calculTauxErreurBinaire(),is(0.0f) );
	}
	
	@Test
	/**
	 * Test pour la methode qui calcule le TEB en transmission bruitee
	 * @throws Exception quelconque 
	 */
	public void calculTEBTransmissionBruiteeTest() throws Exception{
		//Arrange
		Simulateur simulateur =  new Simulateur(new String[] {"-mess","48","-snrpb","-20"});
		
		//Act
		simulateur.execute();
		
		//Assert
		errorCollector.checkThat("Le taux d'erreur binaire est de 0 alors que la transmission est tres bien bruitee",  simulateur.calculTauxErreurBinaire(),not(0.0f) );
	}
	
	@Test
	/**
	 * Test pour voir si l'on recupere bien la meme taille d'information en sortie
	 * @throws Exception quelconque
	 */
	public void tailleMotRecuTest() throws Exception{
		//Arrange
		Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","1010010001"});
		
		//Act
		simulateur1.execute();
		
		//Assert
		errorCollector.checkThat( "Le mot recue ne peu pas etre bon car il ne fait pas la bonne taille", simulateur1.getTailleMotDestination(), is(10));
	}
	
	@Test
	/**
	 * Test de performance du Simulateur au global
	 * On viens afficher les sondes et utiliser TOUS les parametres, puis on calcule le temps que cela a pris
	 * @throws Exception quelconque
	 */
	public void mainPerformanceTest() throws Exception{
		//Arrange
		System.out.println("Testing the Whole Process with those arguments :\n");
		String[] args= new String[] {"-mess", "100", "-s", "-seed", "2", "-form", "NRZ", "-nbEch", "15", "-ampl", "-5.0", "5.0", "-snrpb", "20", "-ti","1","0.1","2","0.2","3","0.3","4","0.4","5","0.5", "-codeur"};
		String s = "java  Simulateur  ";
		for (int j = 0; j < args.length; j++) {
			s += args[j] + "  ";
		}
		System.out.println(s+"\n\n***************************************************************************************************\n");
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