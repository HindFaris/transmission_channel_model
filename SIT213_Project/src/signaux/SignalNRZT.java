package signaux;

import javax.swing.text.AbstractDocument.LeafElement;

import information.Information;

public class SignalNRZT extends Signal<Float,Boolean>{

	public SignalNRZT(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}

	public void generer() {
		
		//Le premier bit
		float coefficientDirecteur = (max-((max+min)/2))/(nbEchantillon/3);
		
		signalSortieInformation  = new Information<Float>();
		signalSortieInformation.add((max+min)/2);
		

		for(int index = 1; index < nbEchantillon/3; index++) {

			if (signalEntree.iemeElement(0) == true) {
				signalSortieInformation.add(coefficientDirecteur*index + (max + min)/2);
			}
			else {
				signalSortieInformation.add(-coefficientDirecteur*index + (max + min)/2);
			}
		}

		for(int index = (int)(nbEchantillon/3); index < 2*nbEchantillon/3; index++) {

			if (signalEntree.iemeElement(0) == true) {
				signalSortieInformation.add(max);
			}
			else {
				signalSortieInformation.add(min);
			}
		}

		for(int index = (int)(2*nbEchantillon/3); index < nbEchantillon; index++) {

			if (signalEntree.iemeElement(0) == true && signalEntree.iemeElement(1) == true) {
				signalSortieInformation.add(max);
			}
			else if(signalEntree.iemeElement(0) == true && signalEntree.iemeElement(1) == false) {
				signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillon/3))+max);
			}
			else if(signalEntree.iemeElement(0) == false && signalEntree.iemeElement(1) == false) {
				signalSortieInformation.add(min);
			}
			else if(signalEntree.iemeElement(0) == false && signalEntree.iemeElement(1) == true) {
				signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillon/3))+min);
			}
		}

		
		//Tous les bits du milieu
		for(int bit = 1; bit<signalEntree.nbElements()-1;bit++) {

			for(int index = 0; index < nbEchantillon/3; index++) {
				if (signalEntree.iemeElement(bit-1) == true && signalEntree.iemeElement(bit) == true) {
					signalSortieInformation.add(max);
				}
				else if(signalEntree.iemeElement(bit-1) == true && signalEntree.iemeElement(bit) == false) {
					signalSortieInformation.add(-coefficientDirecteur*index + (max + min)/2);
				}
				else if(signalEntree.iemeElement(bit-1) == false && signalEntree.iemeElement(bit) == false) {
					signalSortieInformation.add(min);
				}
				else if(signalEntree.iemeElement(bit-1) == false && signalEntree.iemeElement(bit) == true) {
					signalSortieInformation.add(coefficientDirecteur*index + (max + min)/2);
				}
			}

			for(int index = (int)(nbEchantillon/3); index < 2*nbEchantillon/3; index++) {
				if (signalEntree.iemeElement(bit) == true) {
					signalSortieInformation.add(max);
				}
				else {
					signalSortieInformation.add(min);
				}
			}

			for(int index = (int)(2*nbEchantillon/3); index < nbEchantillon; index++) {

				if (signalEntree.iemeElement(bit) == true && signalEntree.iemeElement(bit+1) == true) {
					signalSortieInformation.add(max);
				}
				else if(signalEntree.iemeElement(bit) == true && signalEntree.iemeElement(bit+1) == false) {
					signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillon/3))+max);
				}
				else if(signalEntree.iemeElement(bit) == false && signalEntree.iemeElement(bit+1) == false) {
					signalSortieInformation.add(min);
				}
				else if(signalEntree.iemeElement(bit) == false && signalEntree.iemeElement(bit+1) == true) {
					signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillon/3))+min);
				}
			}
		}


		//Le dernier bit
		
		
		
		for(int index = 0; index < nbEchantillon/3; index++) {
			if (signalEntree.iemeElement(signalEntree.nbElements()-2) == true && signalEntree.iemeElement(signalEntree.nbElements()-1) == true) {
				signalSortieInformation.add(max);
			}
			else if (signalEntree.iemeElement(signalEntree.nbElements()-2) == true && signalEntree.iemeElement(signalEntree.nbElements()-1) == false){
				signalSortieInformation.add(-coefficientDirecteur*index + (max + min)/2);
			}
			else if (signalEntree.iemeElement(signalEntree.nbElements()-2) == false && signalEntree.iemeElement(signalEntree.nbElements()-1) == false){
				signalSortieInformation.add(min);
			}
			else if (signalEntree.iemeElement(signalEntree.nbElements()-2) == false && signalEntree.iemeElement(signalEntree.nbElements()-1) == true){
				signalSortieInformation.add(coefficientDirecteur*index + (max + min)/2);
			}
		}

		for(int index = (int)(nbEchantillon/3); index < 2*nbEchantillon/3; index++) {

			if (signalEntree.iemeElement(signalEntree.nbElements()-1) == true) {
				signalSortieInformation.add(max);
			}
			else {
				signalSortieInformation.add(min);
			}
		}

		for(int index = (int)(2*nbEchantillon/3); index <= nbEchantillon; index++) {

			if (signalEntree.iemeElement(signalEntree.nbElements()-1) == true) {
				signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillon/3))+max);
			}
			else if(signalEntree.iemeElement(signalEntree.nbElements()-1) == false) {
				signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillon/3))+min);
			}
		}
	}
}