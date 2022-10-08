package signaux;

import java.util.LinkedList;

import information.Information;

public class SignalNRZT extends Signal{

	public SignalNRZT(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}

	public void generer() {

		float coefficientDirecteur = (max-((max+min)/2))/(nbEchantillons/3);
		float moyenne = (max+min)/2;
		signalSortieInformation  = new Information<Float>();
		LinkedList<Boolean> copieInformationRecue = new LinkedList<Boolean>();

		try {
			copieInformationRecue = signalEntree.cloneInformation();
		} catch (Exception e) {

		}


		//Le premier bit

		//Les deux premiers tiers des echantillons
		boolean premierBit = copieInformationRecue.get(0);
		boolean deuxiemeBit = copieInformationRecue.get(1);
		signalSortieInformation.add(moyenne);

		if(premierBit == true){
			for(int index = 1; index < (nbEchantillons)/3; index++) {
				signalSortieInformation.add(coefficientDirecteur*index + moyenne);
			}
			for(int index = (nbEchantillons)/3; index < 2*(nbEchantillons)/3; index++) {
				signalSortieInformation.add(max);
			}
		}
		else {
			for(int index = 1; index < (nbEchantillons)/3; index++) {
				signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
			}
			for(int index = (nbEchantillons)/3; index < 2*(nbEchantillons)/3; index++) {
				signalSortieInformation.add(min);
			}
		}
		
		//Le troisieme tiers des echantillons
		if (premierBit == true) {
			if(deuxiemeBit == true) {
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillons/3))+max);
				}
			}
		}
		else {
			if (deuxiemeBit == true) {
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillons/3))+min);
				}
			}
			else {
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(min);
				}
			}
		}	

		//Tous les bits du milieu (hors premier et dernier bit)
		for(int bit = 1; bit<tailleSignalEntree-1;bit++) {

			Boolean bitPrecedent = copieInformationRecue.get(0);
			Boolean bitActuel = copieInformationRecue.get(1);
			Boolean bitSuivant = copieInformationRecue.get(2);

			//Traitement du premier tiers des echantillons pour un bit donne
			if(bitPrecedent == true) {
				if(bitActuel == true) {
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(max);
					}
				}
				else {
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
					}
				}
			}
			else {
				if(bitActuel == true) {
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(coefficientDirecteur*index + moyenne);
					}
				}
				else {
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(min);
					}
				}
			}
			

			//Traitement du deuxieme tiers des echantillons pour un bit donne
			if (bitActuel == true) {
				for(int index = (int)(nbEchantillons/3); index < 2*nbEchantillons/3; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = (int)(nbEchantillons/3); index < 2*nbEchantillons/3; index++) {
					signalSortieInformation.add(min);
				}
			}


			//Traitement du troisieme tiers des echantillons pour un bit donne
			if(bitActuel == true) {
				if(bitSuivant == true) {
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(max);
					}
				}
				else {
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillons/3))+max);
					}
				}
			}
			else {
				if(bitSuivant == true) {
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillons/3))+min);
					}
				}
				else {
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(min);
					}
				}
			}
			copieInformationRecue.remove(0);
		}



		//Le dernier bit
		boolean avantDernierBit = copieInformationRecue.get(0);
		boolean dernierBit = copieInformationRecue.get(1);

		if(avantDernierBit == true) {
			if(dernierBit == true) {
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
				}
			}
		}
		else {
			if(dernierBit == true) {
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(coefficientDirecteur*index + moyenne);
				}
			}
			else {
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(min);
				}
			}
		}
		
		
		//deux derniers tiers
		if(dernierBit == true) {
			for(int index = nbEchantillons/3; index < 2*nbEchantillons/3; index++) {
				signalSortieInformation.add(max);
			}
			for(int index = 2*nbEchantillons/3; index < nbEchantillons; index++) {
				signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillons/3))+max);
			}
		}
		else {
			for(int index = nbEchantillons/3; index < 2*nbEchantillons/3; index++) {
				signalSortieInformation.add(min);
			}
			for(int index = 2*nbEchantillons/3; index < nbEchantillons; index++) {
				signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillons/3))+min);
			}
		}
	}
}