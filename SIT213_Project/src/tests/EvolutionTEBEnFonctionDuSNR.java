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
		
		System.out.println("Signal NRZ");
		System.out.println("SNR par bit (dB);Taux d'erreur binaire");
		for(float indice = -15; indice<=20; indice += 0.5) {
			Simulateur simulateur =  new Simulateur(new String[] {"-mess","999999", "-form", "NRZ", "-snrpb", String.valueOf(indice)});
			simulateur.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateur.calculTauxErreurBinaire()));
		}
		System.out.println("");
		
		System.out.println("Signal NRZT");
		System.out.println("SNR par bit (dB);Taux d'erreur binaire");
		for(float indice = -15; indice<=20; indice += 0.5) {
			Simulateur simulateur =  new Simulateur(new String[] {"-mess","999999", "-form", "NRZT", "-snrpb", String.valueOf(indice)});
			simulateur.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateur.calculTauxErreurBinaire()));
		}
		System.out.println("");
		
		System.out.println("Signal RZ");
		System.out.println("SNR par bit (dB);Taux d'erreur binaire");
		for(float indice = -15; indice<=20; indice += 0.5) {
			Simulateur simulateur =  new Simulateur(new String[] {"-mess","999999", "-form", "RZ", "-snrpb", String.valueOf(indice)});
			simulateur.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateur.calculTauxErreurBinaire()));
		}
		
		myConsole.close();
	}
}
