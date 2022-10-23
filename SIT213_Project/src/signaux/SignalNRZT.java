package signaux;

import java.util.LinkedList;
import java.util.function.ObjDoubleConsumer;

import information.Information;

/**
 * Classe pour generer le signal NRZT
 * @author 33663
 *
 */
public class SignalNRZT extends Signal{

	/**
	 * Le constructeur faisant appel au constructeur de la classe mere : Signal
	 * @param informationRecue l'information recue
	 * @param nbEchantillons le nombre d'echantillons
	 * @param min minimum
	 * @param max maximum
	 */
	public SignalNRZT(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}

	@Override
	public void generer() {

		float coefficientDirecteur = (max-((max+min)/2))/(nbEchantillons/3); //modelise le coefficient directeur de la pente
		float moyenne = (max+min)/2;	//le milieu entre max et min
		signalSortieInformation  = new Information<Float>();
		LinkedList<Boolean> copieInformationRecue = new LinkedList<Boolean>();

		try {
			copieInformationRecue = signalEntree.cloneInformation();	//Copie l'information afin de ne pas modifier l'information primaire
		} catch (Exception e) {
			System.out.println("Le clone de l'information s'est mal passe");
		}


		//Le premier bit a traite a part car il n'a pas de bit anterieur

		
		//Les deux premiers tiers des echantillons
		boolean premierBit = copieInformationRecue.get(0);
		boolean deuxiemeBit = copieInformationRecue.get(1);
		signalSortieInformation.add(moyenne);	//la premiere valeur correspond toujours au milieu entre min et max

		if(premierBit == true){	//Le premier bit est a l'etat haut
			for(int index = 1; index < (nbEchantillons)/3; index++) {	//Pour le premier tiers d'echantillons on ajoute les bits correspondant a la pente
				signalSortieInformation.add(coefficientDirecteur*index + moyenne);
			}
			for(int index = (nbEchantillons)/3; index < 2*(nbEchantillons)/3; index++) {	//Pour le deuxieme tiers d'echantillons on ajoute les bits a l'etat haut
				signalSortieInformation.add(max);
			}
		}
		else {	//Le premier bit est a l'etat bas
			for(int index = 1; index < (nbEchantillons)/3; index++) {	//Pour le premier tiers d'echantillons on ajoute les bits correspondant a la pente
				signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
			}
			for(int index = (nbEchantillons)/3; index < 2*(nbEchantillons)/3; index++) {	//Pour le deuxieme tiers d'echantillons on ajoute les bits a l'etat bas
				signalSortieInformation.add(min);
			}
		}
		
		//Le troisieme tiers des echantillons
		if (premierBit == true) {	
			if(deuxiemeBit == true) {	//On reste a l'etat haut, on ajoute des max
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {	//On passe de l'etat haut a l'etat bas, on a une pente
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillons/3))+max);
				}
			}
		}
		else {
			if (deuxiemeBit == true) {	//On passe de l'eta bas a l'etat haut, on a une pente
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillons/3))+min);
				}
			}
			else {	//On reste a l'etat bas, on ajoute des min
				for(int index = 2*(nbEchantillons)/3; index < nbEchantillons; index++) {
					signalSortieInformation.add(min);
				}
			}
		}	

		//Tous les bits du milieu (hors premier et dernier bit)
		for(int bit = 1; bit<tailleSignalEntree-1;bit++) {
			//On actualise a chaque fois l'etat du bits precedent, actuel et suivant
			Boolean bitPrecedent = copieInformationRecue.get(0);
			Boolean bitActuel = copieInformationRecue.get(1);
			Boolean bitSuivant = copieInformationRecue.get(2);

			//Traitement du premier tiers des echantillons pour un bit donne
			if(bitPrecedent == true) {
				if(bitActuel == true) {	//On reste a l'etat haut, on ajoute des max
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(max);
					}
				}
				else {	//On passe de l'eat haut a l'etat bas, on ajoute la pente
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
					}
				}
			}
			else {
				if(bitActuel == true) {	//On passe de l'etat bas a l'etat haut, on ajoute la pente
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(coefficientDirecteur*index + moyenne);
					}
				}
				else {	//On reste au minimum, on ajoute que des min
					for(int index = 0; index < nbEchantillons/3; index++) {
						signalSortieInformation.add(min);
					}
				}
			}
			

			//Traitement du deuxieme tiers des echantillons pour un bit donne
			if (bitActuel == true) {	//On ajoute des max
				for(int index = (int)(nbEchantillons/3); index < 2*nbEchantillons/3; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {	//on ajoute des min
				for(int index = (int)(nbEchantillons/3); index < 2*nbEchantillons/3; index++) {
					signalSortieInformation.add(min);
				}
			}


			//Traitement du troisieme tiers des echantillons pour un bit donne
			if(bitActuel == true) {
				if(bitSuivant == true) {	//le bit suicant est au max egalement. On ajoute des max
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(max);
					}
				}
				else {	//On passe du max au min, on modelise la pente
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillons/3))+max);
					}
				}
			}
			else {
				if(bitSuivant == true) {	//On passe du min au max, on modelise la pente
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillons/3))+min);
					}
				}
				else {	//On reste au min, on ajoute des min
					for(int index = (int)(2*nbEchantillons/3); index < nbEchantillons; index++) {
						signalSortieInformation.add(min);
					}
				}
			}
			copieInformationRecue.remove(0);	//On passe au bit suivant
		}



		//Le dernier bit a traite a part car il n'a pas de bit suivant
		
		
		//Le premier tiers des informations
		boolean avantDernierBit = copieInformationRecue.get(0);
		boolean dernierBit = copieInformationRecue.get(1);

		if(avantDernierBit == true) {
			if(dernierBit == true) {	//On reste a l'etat haut, on ajoute des max
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {	//On passe de l'etat haut a l'etat bas, on modelise la pente
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(-coefficientDirecteur*index + moyenne);
				}
			}
		}
		else {
			if(dernierBit == true) {	//On passe de l'etat bas a l'etat haut, on modelise la pente
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(coefficientDirecteur*index + moyenne);
				}
			}
			else {	//On reste a l'etat bas, on ajoute des mins
				for(int index = 0; index < nbEchantillons/3; index++) {
					signalSortieInformation.add(min);
				}
			}
		}
		
		
		//deux derniers tiers
		if(dernierBit == true) {	//On ajoute des max puis la pente pour revenir au milieu de max et min
			for(int index = nbEchantillons/3; index < 2*nbEchantillons/3; index++) {
				signalSortieInformation.add(max);
			}
			for(int index = 2*nbEchantillons/3; index < nbEchantillons; index++) {
				signalSortieInformation.add(-coefficientDirecteur*(index-(2*nbEchantillons/3))+max);
			}
		}
		else {	//On ajoute des min puis la pente pour revenir au milieu de max et min
			for(int index = nbEchantillons/3; index < 2*nbEchantillons/3; index++) {
				signalSortieInformation.add(min);
			}
			for(int index = 2*nbEchantillons/3; index < nbEchantillons; index++) {
				signalSortieInformation.add(coefficientDirecteur*(index-(2*nbEchantillons/3))+min);
			}
		}
	}
}