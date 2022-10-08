package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class EvolutionTEBEnFonctionDuSNRTestMultiTrajet {
	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\codeurRZ.txt";	//Please respect this format

	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 100000");
		System.out.println("SNR par bit (dB);RZ sans codeur;RZ avec codeur");
		for(float indice = -10; indice<=25; indice += 1) {
			Simulateur simulateurNRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(indice),"-ti","90","0.8", "120","0.6","150","0.7","15","0.9","25","0.9"});
			simulateurNRZ.execute();
			Simulateur simulateurNRZT =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(indice),"-ti","90","0.8", "120","0.6","150","0.7","15","0.9","25","0.9","-codeur"});
			simulateurNRZT.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurNRZT.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
