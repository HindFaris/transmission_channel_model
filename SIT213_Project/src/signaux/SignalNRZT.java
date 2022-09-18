package signaux;

import information.Information;

public class SignalNRZT extends Signal{

	public SignalNRZT(Information<Boolean> informationRecue, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		super(informationRecue, nbEchantillons, min, max, SNRParBit, bruitActif);
	}

	public void generer() {

		float coefficientDirecteur = (max-((max+min)/2))/(nbEchantillon/3);
		float moyenne = (max+min)/2;
		signalSortieInformation  = new Information<Float>();


		//Le premier bit

		//Les deux premiers tiers des echantillons
		boolean premierBit = signalEntree.iemeElement(0);
		boolean deuxiemeBit = signalEntree.iemeElement(1);
		signalSortieInformation.add(moyenne);

		if(premierBit == true){
			for(int index = 1; index < nbEchantillon/3; index++) {
				signalSortieInformation.add(coefficientDirecteur*index + moyenne);
			}
			for(int index = nbEchantillon/3; index < 2*nbEchantillon/3; index++) {
				signalSortieInformation.add(max);
			}
		}
		else {
			for(int index = 1; index < nbEchantillon/3; index++) {
				signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
			}
			for(int index = nbEchantillon/3; index < 2*nbEchantillon/3; index++) {
				signalSortieInformation.add(min);
			}
		}
		
		//Le troisieme tiers des echantillons
		if (premierBit == true) {
			if(deuxiemeBit == true) {
				for(int index = 2*nbEchantillon/3; index < nbEchantillon; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = 2*nbEchantillon/3; index < nbEchantillon; index++) {
					signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillon/3))+max);
				}
			}
		}
		else {
			if (deuxiemeBit == true) {
				for(int index = 2*nbEchantillon/3; index < nbEchantillon; index++) {
					signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillon/3))+min);
				}
			}
			else {
				for(int index = 2*nbEchantillon/3; index < nbEchantillon; index++) {
					signalSortieInformation.add(min);
				}
			}
		}	


		//Tous les bits du milieu (hors premier et dernier bit)
		for(int bit = 1; bit<tailleSignalEntree-1;bit++) {

			Boolean bitPrecedent = signalEntree.iemeElement(bit-1);
			Boolean bitActuel = signalEntree.iemeElement(bit);
			Boolean bitSuivant = signalEntree.iemeElement(bit+1);

			//Traitement du premier tiers des echantillons pour un bit donne
			if(bitPrecedent == true) {
				if(bitActuel == true) {
					for(int index = 0; index < nbEchantillon/3; index++) {
						signalSortieInformation.add(max);
					}
				}
				else {
					for(int index = 0; index < nbEchantillon/3; index++) {
						signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
					}
				}
			}
			else {
				if(bitActuel == true) {
					for(int index = 0; index < nbEchantillon/3; index++) {
						signalSortieInformation.add(coefficientDirecteur*index + moyenne);
					}
				}
				else {
					for(int index = 0; index < nbEchantillon/3; index++) {
						signalSortieInformation.add(min);
					}
				}
			}
			

			//Traitement du deuxieme tiers des echantillons pour un bit donne
			if (signalEntree.iemeElement(bit) == true) {
				for(int index = (int)(nbEchantillon/3); index < 2*nbEchantillon/3; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = (int)(nbEchantillon/3); index < 2*nbEchantillon/3; index++) {
					signalSortieInformation.add(min);
				}
			}


			//Traitement du troisieme tiers des echantillons pour un bit donne
			if(bitActuel == true) {
				if(bitSuivant == true) {
					for(int index = (int)(2*nbEchantillon/3); index < nbEchantillon; index++) {
						signalSortieInformation.add(max);
					}
				}
				else {
					for(int index = (int)(2*nbEchantillon/3); index < nbEchantillon; index++) {
						signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillon/3))+max);
					}
				}
			}
			else {
				if(bitSuivant == true) {
					for(int index = (int)(2*nbEchantillon/3); index < nbEchantillon; index++) {
						signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillon/3))+min);
					}
				}
				else {
					for(int index = (int)(2*nbEchantillon/3); index < nbEchantillon; index++) {
						signalSortieInformation.add(min);
					}
				}
			}
		}



		//Le dernier bit

		boolean avantDernierBit = signalEntree.iemeElement(tailleSignalEntree-2);
		boolean dernierBit = signalEntree.iemeElement(tailleSignalEntree-1);

		if(avantDernierBit == true) {
			if(dernierBit == true) {
				for(int index = 0; index < nbEchantillon/3; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = 0; index < nbEchantillon/3; index++) {
					signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
				}
			}
		}
		else {
			if(dernierBit == true) {
				for(int index = 0; index < nbEchantillon/3; index++) {
					signalSortieInformation.add(coefficientDirecteur*index + moyenne);
				}
			}
			else {
				for(int index = 0; index < nbEchantillon/3; index++) {
					signalSortieInformation.add(min);
				}
			}
		}
		
		
		//deux derniers tiers
		if(dernierBit == true) {
			for(int index = nbEchantillon/3; index < 2*nbEchantillon/3; index++) {
				signalSortieInformation.add(max);
			}
			for(int index = 2*nbEchantillon/3; index <= nbEchantillon; index++) {
				signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillon/3))+max);
			}
		}
		else {
			for(int index = nbEchantillon/3; index < 2*nbEchantillon/3; index++) {
				signalSortieInformation.add(min);
			}
			for(int index = 2*nbEchantillon/3; index <= nbEchantillon; index++) {
				signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillon/3))+min);
			}
		}
	}
}