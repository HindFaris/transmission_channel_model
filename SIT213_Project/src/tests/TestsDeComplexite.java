package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class TestsDeComplexite {
	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\complexite.txt";	//Please respect this format

	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		//System.out.println("Simulations pour un nombre d'echantillons de 10000");
		System.out.println("nb echantillon;nombre de bits");
		for(float indice = 10000; indice<=100000; indice += 5000) {
			Simulateur simulateur =  new Simulateur(new String[] {"-mess", String.valueOf(indice), "-form", "NRZ", "-snrpb", "20", "-ti", "15", "0.7", "25", "0.5", "1", "0.1", "9", "0.7", "64", "0.65"});
			long debut = System.currentTimeMillis();
			simulateur.execute();
			long fin = System.currentTimeMillis();
			System.out.println(Float.toString(indice) + ";" + (fin-debut));
		}
		myConsole.close();
	}
}
