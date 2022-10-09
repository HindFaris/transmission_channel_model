package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class EvolutionTEBEnFonctionDuSNRTMultiMonoTrajet {
	private final static String CHEMIN = "C:\\Users\\33663\\Documents\\2eme année ing\\SIT200\\SIT213\\NRZ_multitrajet_monotrajet.txt";	//Please respect this format

	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 100000");
		System.out.println("SNR par bit (dB);NRZ avec multitrajet ;NRZ sans multitrajet");
		for(float indice = -1; indice<=25; indice += 1) {
			Simulateur simulateurNRZ1 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice),"-ti","90","0.8", "120","0.6"});
			simulateurNRZ1.execute();
			Simulateur simulateurNRZ2 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice)});
			simulateurNRZ2.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ1.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurNRZ2.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
