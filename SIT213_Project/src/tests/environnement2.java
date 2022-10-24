package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

/**
 * Classe de l'environnement 2
 * @author 33663
 *
 */
public class environnement2 {
	/**
	 * instancie environnement 2
	 */
	public environnement2() {
		// TODO Auto-generated constructor stub
	}
	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\environnement2V2.txt";	//Please respect this format

	/**
	 * Methode qui genere les tests pour l'environnement
	 * @param args du main
	 * @throws ArgumentsException probleme d'exception
	 * @throws Exception exception
	 */
	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);
		String aAfficher;
		
		System.out.println("Simulations pour un nombre d'echantillons de 100000 avec seed");
		System.out.println("SNR par bit (dB);NRZ avec 1 echantillon;RZ avec 1 echantillon;NRZ avec 11 echantillons;RZ avec 11 echantillons;"
				+ "NRZ avec 21 echantillons;RZ avec 21 echantillons;NRZ avec 31 echantillons;RZ avec 31 echantillons;"
				+ "NRZ avec 41 echantillons;RZ avec 41 echantillons;");
		for(float snrpb = 0; snrpb<=25; snrpb += 1) {
			
			aAfficher=Float.toString(snrpb);
			for(int nbEch  = 1; nbEch<42; nbEch+=10) {
				Simulateur simulateurNRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "NRZ", "-snrpb", String.valueOf(snrpb), "-ti", "10", "0.5", "-nbEch", String.valueOf(nbEch),"-seed","1"});
				simulateurNRZ.execute();

				Simulateur simulateurRZ =  new Simulateur(new String[] {"-mess","100000", "-form", "RZ", "-snrpb", String.valueOf(snrpb), "-ti", "10", "0.5", "-nbEch", String.valueOf(nbEch), "-seed", "1"});
				simulateurRZ.execute();
				aAfficher+=";"+Float.toString(simulateurNRZ.calculTauxErreurBinaire())+";"+Float.toString(simulateurRZ.calculTauxErreurBinaire());
			}
			System.out.println(aAfficher);
		}
		
	}
}
