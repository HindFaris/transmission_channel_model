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
	private final static String CHEMIN = "C:\\Professionnel\\IMT Atlantique\\Info\\Java\\projet2A\\courbes\\environnement2.txt";	//Please respect this format

	/**
	 * Methode qui genere les tests pour l'environnement
	 * @param args du main
	 * @throws ArgumentsException probleme d'exception
	 * @throws Exception exception
	 */
	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 999999 avec seed");
		System.out.println("SNR par bit (dB);NRZ Te=1*10e-6s;NRZ Te=2*10e-6s;RZ Te=1*10e-6s;RZ Te=2*10e-6s");
		for(float indice = 0; indice<=25; indice += 1) {
			Simulateur simulateurNRZ1 =  new Simulateur(new String[] {"-mess","999999", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "1","-seed","1"});
			simulateurNRZ1.execute();
			Simulateur simulateurNRZ2 =  new Simulateur(new String[] {"-mess","999999", "-form", "NRZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "2"});
			simulateurNRZ2.execute();
			Simulateur simulateurRZ1 =  new Simulateur(new String[] {"-mess","999999", "-form", "RZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "1"});
			simulateurRZ1.execute();
			Simulateur simulateurRZ2 =  new Simulateur(new String[] {"-mess","999999", "-form", "RZ", "-snrpb", String.valueOf(indice), "-ti", "10", "0.5", "-nbEch", "2"});
			simulateurRZ2.execute();
			
			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ1.calculTauxErreurBinaire()) + ";" + 
					Float.toString(simulateurNRZ2.calculTauxErreurBinaire()) + ";" + Float.toString(simulateurRZ1.calculTauxErreurBinaire()) +
					";" + Float.toString(simulateurRZ2.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
