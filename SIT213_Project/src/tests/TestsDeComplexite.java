package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

/**
 * classe qui teste la complexite
 * @author 33663
 *
 */
public class TestsDeComplexite {
	
	/**
	 * constructeur de complexite
	 */
	public TestsDeComplexite() {
		// TODO Auto-generated constructor stub
	}
	
	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\complexiteaveccodeur.txt";	//Respecter ce format

	/**
	 * teste la complexite
	 * @param args arguments de main
	 * @throws ArgumentsException exception
	 * @throws Exception exception
	 */
	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("nbEch=30, 5 multi-trajets, codage");
		System.out.println("nb echantillon;temps (ms)");
		for(int indice = 40000; indice<=400000; indice += 20000) {
			Simulateur simulateur =  new Simulateur(new String[] {"-mess", String.valueOf(indice), "-form", "NRZ", "-snrpb", "10", "-codeur","-ti","10","0.7","26","0.4","-64","0.2","91","0.3","56","0.1"});
			long debut = System.currentTimeMillis();
			simulateur.execute();
			long fin = System.currentTimeMillis();
			System.out.println(Float.toString(indice) + ";" + (fin-debut));
		}
		myConsole.close();
	}
}
