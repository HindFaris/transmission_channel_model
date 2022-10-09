package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class TestsDeComplexite {
	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\complexiteaveccodeur.txt";	//Respecter ce format

	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		//System.out.println("Simulations pour un nombre d'echantillons de 10000");
		System.out.println("nb echantillon;temps (ms)");
		for(int indice = 40000; indice<=400000; indice += 20000) {
			Simulateur simulateur =  new Simulateur(new String[] {"-mess", String.valueOf(indice), "-form", "NRZ", "-snrpb", "20", "-codeur"});
			long debut = System.currentTimeMillis();
			simulateur.execute();
			long fin = System.currentTimeMillis();
			System.out.println(Float.toString(indice) + ";" + (fin-debut));
		}
		myConsole.close();
	}
}
