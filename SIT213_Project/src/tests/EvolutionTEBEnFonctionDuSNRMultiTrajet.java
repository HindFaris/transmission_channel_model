package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

/**
 * teste l'evolution du TEB en fonction du multi trajet
 * @author 33663
 *
 */
public class EvolutionTEBEnFonctionDuSNRMultiTrajet {
	
	/**
	 * a
	 */
	public EvolutionTEBEnFonctionDuSNRMultiTrajet() {
		// TODO Auto-generated constructor stub
	}
	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\NRZ_et_RZ_multitrajet_code_et_non_code.txt";	//Please respect this format

	/**
	 * tester
	 * @param args du main 
	 * @throws ArgumentsException exception
	 * @throws Exception exception
	 */
	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 200000 avec seed");
		System.out.println("SNR par bit (dB);NRZ avec 5 multitrajets;RZ avec 5 multitrajets;NRZ avec codeur et 5 multitrajets;RZ avec codeur et 5 multitrajets");
		for(float indice = -1; indice<=25; indice += 1) {
			Simulateur simulateurNRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice),"-ti","90","0.8","120","0.6","75","0.3","12","0.6","19","0.2","-seed","1"});
			simulateurNRZ.execute();
			Simulateur simulateurRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(indice),"-ti","90","0.8","120","0.6","75","0.3","12","0.6","19","0.2","-seed","1"});
			simulateurRZ.execute();
			Simulateur simulateurNRZcodeur =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(indice),"-ti","90","0.8","120","0.6","75","0.3","12","0.6","19","0.2","-seed","1","-codeur"});
			simulateurNRZcodeur.execute();
			Simulateur simulateurRZcodeur =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(indice),"-ti","90","0.8","120","0.6","75","0.3","12","0.6","19","0.2","-seed","1","-codeur"});
			simulateurRZcodeur.execute();
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurRZ.calculTauxErreurBinaire()) + ";" + Float.toString(simulateurNRZcodeur.calculTauxErreurBinaire())
					+ ";" + Float.toString(simulateurRZcodeur.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
