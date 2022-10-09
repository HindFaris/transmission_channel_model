package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class EvolutionTEBEnFonctionDuSNRTestMultiTrajetsansBruit {
	private final static String CHEMIN = "C:\\Users\\33663\\Documents\\2eme année ing\\SIT200\\SIT213\\evolution_TEB_SNR_multitrajet_sansBruit_NRZ.txt";	//Please respect this format

	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 100000");
		System.out.println("SNR par bit (dB);NRZ multitrajet (A=0,7, dt=10) et (A=0,8, dt=30) ;NRZ multitrajet (A=0,3, dt=10) et (A=0,4, dt=30)");
		for(float indice = -1; indice<=25; indice += 1) {
			Simulateur simulateurNRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice)});
			simulateurNRZ.execute();
			Simulateur simulateurNRZmultitrajet1 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice),"-ti","10","0.7", "30","0.8"});
			simulateurNRZmultitrajet1.execute();
			Simulateur simulateurNRZmultitrajet2 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice),"-ti","10","0.3", "30","0.4"});
			simulateurNRZmultitrajet2.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurNRZmultitrajet1.calculTauxErreurBinaire()) + ";" + 
							Float.toString(simulateurNRZmultitrajet2.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
