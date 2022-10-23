package tests;


import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

/**
 * Classe de test de l'evolution de TEB
 * @author 33663
 *
 */
public class Evolution_TEB {
	
	/**
	 * a
	 */
	public Evolution_TEB() {
		// TODO Auto-generated constructor stub
	}
private final static String CHEMIN = "C:\\Users\\33663\\Documents\\2eme année ing\\SIT200\\SIT213\\evolution_TEB_SNR_Forme_f";	//Please respect this format
	
/**
 * methode qui test evolution du TEB
 * @param args arguments du main
 * @throws ArgumentsException exception
 * @throws Exception exception
 */
	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);
		
		System.out.println("Simulations pour un nombre d'echantillons de 100 000");
		System.out.println("SNR par bit (dB);TEB signal NRZ avec codeur;TEB signal RZ avec codeur;TEB signal NRZT avec codeur");
		for(float indice = -1; indice<=20; indice += 0.5) {
			Simulateur simulateur1 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-codeur","-seed", "1"});
			simulateur1.execute();
			Simulateur simulateur2 =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(indice), "-codeur","-seed", "1"});
			simulateur2.execute();
			Simulateur simulateur3 =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZT", "-snrpb", String.valueOf(indice), "-codeur","-seed", "1"});
			simulateur3.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateur1.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateur2.calculTauxErreurBinaire()) + ";" + Float.toString(simulateur3.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
