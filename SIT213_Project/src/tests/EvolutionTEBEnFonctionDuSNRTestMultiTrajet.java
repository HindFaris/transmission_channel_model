package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class EvolutionTEBEnFonctionDuSNRTestMultiTrajet {
	private final static String CHEMIN = "C:\\Users\\33663\\Documents\\2eme année ing\\SIT200\\SIT213\\evolution_TEB_SNR_multitrajet_sansBruit.txt";	//Please respect this format

	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 10000");
		System.out.println("SNR par bit (dB);NRZ sans multitrajet;NRZ multitrajet (A=0.9, dt=20) et (A=1, dt=10) ;NRZ multitrajet (A=0.2, dt=20) et (A=0.3, dt=10)");
		for(float indice = -50; indice<=20; indice += 1) {
			Simulateur simulateurNRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice)});
			simulateurNRZ.execute();
			Simulateur simulateurNRZT =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice),"-ti","20","0.9", "10","1"});
			simulateurNRZT.execute();
			Simulateur simulateurRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice),"-ti","20","0.2","10","0.3"});
			simulateurRZ.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurNRZT.calculTauxErreurBinaire()) + ";" + Float.toString(simulateurRZ.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
