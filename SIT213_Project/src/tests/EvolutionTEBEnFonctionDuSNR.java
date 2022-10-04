package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class EvolutionTEBEnFonctionDuSNR {

	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\evolution_TEB_SNR.txt";	//Please respect this format
	
	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);
		
		System.out.println("Simulations pour un nombre d'echantillons de 10000");
		System.out.println("SNR par bit (dB);TEB signal NRZ;TEB signal NRZT;TEB signal RZ");
		for(float indice = -50; indice<=20; indice += 0.5) {
			Simulateur simulateurNRZ =  new Simulateur(new String[] {"-mess","10000", "-form", "NRZ", "-snrpb", String.valueOf(indice)});
			simulateurNRZ.execute();
			Simulateur simulateurNRZT =  new Simulateur(new String[] {"-mess","10000", "-form", "NRZT", "-snrpb", String.valueOf(indice)});
			simulateurNRZT.execute();
			Simulateur simulateurRZ =  new Simulateur(new String[] {"-mess","10000", "-form", "RZ", "-snrpb", String.valueOf(indice)});
			simulateurRZ.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurNRZT.calculTauxErreurBinaire()) + ";" + Float.toString(simulateurRZ.calculTauxErreurBinaire()));
		}//
		myConsole.close();
	}
}
