package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

/**
 * methode de l'avancament du TEB en fct du signal
 * @author 33663
 *
 */
public class EvolutionTEBEnFonctionDuSNRTestMultiTrajetsansBruit {

	/**
	 * a
	 */
	public EvolutionTEBEnFonctionDuSNRTestMultiTrajetsansBruit(){

	}
	private final static String CHEMIN = "C:\\Users\\33663\\Documents\\2eme année ing\\SIT200\\SIT213\\evolution_TEB_SNR_multitrajet_sansBruit_NRZ.txt";	//Please respect this format
	/**
	 * a
	 * @param args du main
	 * @throws ArgumentsException exception
	 * @throws Exception exception
	 */
	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 100000 avec seed");
		System.out.println("SNR par bit (dB);NRZ multitrajet (A=0,7, dt=10) et (A=0,8, dt=30) ;NRZ multitrajet (A=0,3, dt=10) et (A=0,4, dt=30)");
		for(float indice = -1; indice<=25; indice += 1) {
			Simulateur simulateurNRZ1 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "1"});
			simulateurNRZ1.execute();
			Simulateur simulateurNRZ2 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "2"});
			simulateurNRZ2.execute();
			Simulateur simulateurRZ1 =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "1"});
			simulateurNRZ1.execute();
			Simulateur simulateurRZ2 =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "2"});
			simulateurNRZ2.execute();

			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ1.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurNRZ2.calculTauxErreurBinaire()) + ";" + Float.toString(simulateurRZ1.calculTauxErreurBinaire()) +
					";" + Float.toString(simulateurRZ2.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
