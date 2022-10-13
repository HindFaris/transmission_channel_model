package tests;

import java.io.File;
import java.io.PrintStream;

import simulateur.ArgumentsException;
import simulateur.Simulateur;

public class environnement2 {
	private final static String CHEMIN = "C:\\Users\\33663\\Documents\\2eme année ing\\SIT200\\SIT213\\environnement2.txt";	//Please respect this format

	public static void main(String[] args) throws ArgumentsException, Exception{
		PrintStream myConsole = new PrintStream(new File(CHEMIN));
		System.setOut(myConsole);

		System.out.println("Simulations pour un nombre d'echantillons de 200000");
		System.out.println("Taille d'un bit = nombre d'échantillons;TEB;");
		for(int indice = 2; indice<= 400; indice += 2) {
			Simulateur simulateurNRZ =  new Simulateur(new String[] {"-mess","200000", "-form", "NRZ", "-ti","10","0.5","-nbEch" ,String.valueOf(indice) ,"-snrpb", "15" });
			simulateurNRZ.execute();
			Simulateur simulateurNRZC =  new Simulateur(new String[] {"-mess","200000", "-form", "NRZ", "-ti","10","0.5","-nbEch" ,String.valueOf(indice) ,"-snrpb", "15","-codeur" });
			simulateurNRZC.execute();
			Simulateur simulateurRZ =  new Simulateur(new String[] {"-mess","200000", "-form", "RZ", "-ti","10","0.5", "-nbEch" ,String.valueOf(indice), "-snrpb", "15"});
			simulateurRZ.execute();
			Simulateur simulateurRZC =  new Simulateur(new String[] {"-mess","200000", "-form", "RZ", "-ti","10","0.5", "-nbEch" ,String.valueOf(indice), "-snrpb", "15","-codeur"});
			simulateurRZC.execute();

			System.out.println(Float.toString(indice) + ";" + Float.toString(simulateurNRZ.calculTauxErreurBinaire()) + ";" + Float.toString(simulateurNRZC.calculTauxErreurBinaire()) + ";" 
					+ Float.toString(simulateurRZ.calculTauxErreurBinaire()) + ";" + Float.toString(simulateurRZC.calculTauxErreurBinaire()));
					//+ ";" + Float.toString(simulateurNRZT.calculTauxErreurBinaire()));
					//+ ";" + Float.toString(simulateurNRZTC.calculTauxErreurBinaire()));
		}
		myConsole.close();
	}
}
