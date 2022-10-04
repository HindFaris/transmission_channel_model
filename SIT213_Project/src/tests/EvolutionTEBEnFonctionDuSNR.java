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
		
		System.out.println("Simulations pour un nombre d'echantillons de 1000");
		System.out.println("SNR par bit (dB);TEB signal NRZ bruite;TEB signal NRZ bruite avec codage;TEB signal NRZ multi trajets; TEB signal NRZ multi trajets avec codage");
		for(float indice = -50; indice<=20; indice += 0.5) {
			Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","10000", "-form", "NRZ", "-snrpb", String.valueOf(indice)});
			simulateur1.execute();
			Simulateur simulateur2 =  new Simulateur(new String[] {"-mess","10000", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-codeur"});
			simulateur2.execute();
			Simulateur simulateur3 =  new Simulateur(new String[] {"-mess","10000", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-ti", "15", "0.7", "25", "0.4"});
			simulateur3.execute();
			Simulateur simulateur4 =  new Simulateur(new String[] {"-mess","10000", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-ti", "15", "0.7", "25", "0.4", "-codeur"});
			simulateur4.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateur1.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateur2.calculTauxErreurBinaire()) + ";" + Float.toString(simulateur3.calculTauxErreurBinaire())
					+ ";" + Float.toString(simulateur4.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
