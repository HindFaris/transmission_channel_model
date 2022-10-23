package recepteur;


import java.util.LinkedList;
import java.util.function.ObjDoubleConsumer;

import javax.swing.CellEditor;

import destinations.DestinationInterface;
import information.*;
import signaux.Signal;
import transmetteurs.*;

//Reception de l'information
/**
 * Classe definissant le Recepteur utilise lors de l'ajout de Multi trajets
 * @author jerom
 *
 */
public class RecepteurMultiTrajets extends Transmetteur<Float, Boolean>{

	private int nbEchantillons;
	private float min=0f;
	private float max=1f;
	private String formeSignal;
	private LinkedList<Integer> taus;
	private LinkedList<Float> alphas;

	/**
	 * permet d'initialiser le Recepteur
	 * @param nbEchantillons le nombre d'echantillons
	 * @param min le minimum du signal
	 * @param max le maximum du signal
	 * @param formSignal la forme du signal (NRZ, NRZT ou RZ)
	 * @param alphas les alphas (attenuation) des multi trajets
	 * @param taus les taus (retards) des multi trajets
	 */
	public RecepteurMultiTrajets(int nbEchantillons, float min, float max, String formSignal, LinkedList<Integer> taus, LinkedList<Float> alphas) {
		this.min=min;
		this.max=max;
		this.nbEchantillons=nbEchantillons;
		this.formeSignal = formSignal;
		this.taus = taus;
		this.alphas = alphas;

	}
	
	/**
	 * Methode qui renvoie le tau maximum de la liste des taus
	 * @return tauMax le tau maximal
	 */
	public int tauMax() {
		int tauxMax = 0;	//Le plus petit taux possible est de 0. Tous les taux sont au moins superieurs ou egal a lui
		for(int taux:taus) {
			if(taux>tauxMax) {
				tauxMax=taux;
			}
		}
		return tauxMax;
	}
	
	
	@Override
	public  void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
		dechiffrer();
	}
	
	/**
	 * Analyse le signal pour essayer d'eliminer les trajets multiples
	 * @return informationRecueCopie L'information analysee, les trajets retires
	 */
	public Float[] analyseNRZ(){

		Float[] informationRecueCopie = informationRecue.clonerDansTableau();	//On en fait une copie dans un tableau car l'acces en memoire y est plus direct

		int tailleTotaleMoinsTauMax = informationRecueCopie.length-tauMax();	//Correspond a la taille du tableau sans les multi trajets
		float elementCourant;

		for(int indice = 0; indice < tailleTotaleMoinsTauMax; indice++) {	//Pour la longueur du signal sans multi trajets

			elementCourant = informationRecueCopie[indice];

			if(elementCourant >= (max+min)/2) {	//On est plus proche du max
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (max) * alphas.get(i);	//Pour tous les taux on vient enlever alpha*max une fois decale de tau
				}
			}
			else {	//On est plus proche du min
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (min) * alphas.get(i);	//Pour tous les taux on vient enlever alpha*min une fois decale de tau
				}
			}
		}

		return informationRecueCopie;
	}
	
	/**
	 * Analyse les donnees pour essayer d'eliminer les trajets multiples
	 * @return informationRecueCopie L'information analysee, les trajets retires
	 */
	public Float[] analyseRZ(){

		Float[] informationRecueCopie = informationRecue.clonerDansTableau();	//On en fait une copie dans un tableau car l'acces en memoire y est plus direct

		int tailleTotaleMoinsTauMax = informationRecueCopie.length-tauMax();	//Correspond a la taille du tableau sans les multi trajets
		float elementCourant;

		for(int indice = 0; indice < tailleTotaleMoinsTauMax; indice++) {	//Pour la longueur du signal sans multi trajets

			elementCourant = informationRecueCopie[indice];

			if(elementCourant >= (max+min)/2) {	//On est plus proche du max
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (max) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha*max une fois decale de tau
				}
			}
			else {	//On est plus proche du min
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (min) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha*min une fois decale de tau
				}
			}
		}
		return informationRecueCopie;
	}
	
	/**
	 * Analyse les donnees pour essayer d'eliminer les trajets multiples
	 * @return informationRecueCopie L'information analysee, les trajets retires
	 */
	public Float[] analyseNRZT(){	//Pas tres performant, necessiterait d'etre revu.
		//Cela demanderait beacoup de disjonction de cas a l'image de la classe SignalNRZT dans le package Signal

		Float[] informationRecueCopie = informationRecue.clonerDansTableau();	//On en fait une copie dans un tableau car l'acces en memoire y est plus direct
		int tailleTotaleMoinsTauMax = informationRecueCopie.length-tauMax();
		float elementCourant;
		int indiceCourant;
		
		for(int iemeInformation = 0; iemeInformation < tailleTotaleMoinsTauMax/nbEchantillons; iemeInformation++) {//Pour le nombre de bits a la source
			for(int indice = nbEchantillons/3; indice < 2*nbEchantillons/3; indice++){	//On ne regarde que ce qu'il y a au milieu de l'intervalle (dans la partie "stable" de NRZT)
				//Cette methode est tres efficace si aucun tau est inferieur ou egal a nbEch/3. Sinon elle est relativement mauvaise.
				indiceCourant = indice+(iemeInformation*nbEchantillons);
				elementCourant = informationRecueCopie[indiceCourant];

				if(elementCourant >= (max+min)/2) {	//On est plus proche du max
					for(int i = 0; i < taus.size(); i++) {
						informationRecueCopie[indiceCourant + taus.get(i)] -= (max) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha*max une fois decale de tau
					}
				}
				else {	//On est plus proche du min
					for(int i = 0; i < taus.size(); i++) {
						informationRecueCopie[indiceCourant + taus.get(i)] -= (min) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha*min une fois decale de tau
					}
				}
			}
		}

		return informationRecueCopie;
	}
	
	/**
	 * Dechiffre les donnees en parametres pour estimer le bit emis
	 * @param donnees	Les donnes analysee
	 */
	public void dechiffrerNRZ(Float[] donnees) {
		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ

		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int nombreDeBooleens = (donnees.length-tauMax())/nbEchantillons;	//La taille du signal a emettre

		for(int index = 0 ; index < nombreDeBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += donnees[index*nbEchantillons+j];	//moyenne temporelle sur la duree d'un bit
			}

			moyenneTemp = moyenneTemp/nbEchantillons;

			if(moyenneTemp >= moyenneLimite) {	//Si la moyenne est au-dessus du seuil, on ajoute un true (etat haut)
				informationEmise.add(true);
			}
			else {	//Sinon on ajoute un false (etat bas)
				informationEmise.add(false);
			}
		}
	}
	
	/**
	 * Dechiffre les donnees en parametres pour estimer le bit emis
	 * @param donnees Les donnes analysee
	 */
	public void dechiffrerRZ(Float[] donnees) {
		float moyenneLimite = (max+min)/2;	//Valeur du seuil pour le signal RZ
		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int nombreDeBooleens = (donnees.length-tauMax())/nbEchantillons;	//La taille du signal a emettre
		int coefficient = 0;
		int borneInferieur;
		int borneSuperieur;
		
		//definit la borne inferieure a partir de laquelle le signal RZ change potentiellement d'etat au sein d'un meme bit
		if((nbEchantillons/3)-(float)(int)(nbEchantillons/3) == 0) {
			borneInferieur = (int)(nbEchantillons/3);	
		}
		else {
			borneInferieur = (int)(nbEchantillons/3) + 1;
		}
		
		//definit la borne superieure a partir de laquelle le signal RZ change potentiellement d'etat au sein d'un meme bit
		if((2*nbEchantillons/3)-(float)(int)(2*nbEchantillons/3) == 0) {
			borneSuperieur = (int)(2*nbEchantillons/3);
		}
		else {
			borneSuperieur = (int)(2*nbEchantillons/3) + 1;
		}
		
		//Compte le nombre d'element qu'il y a dans un bit afin de realiser une moyenne par la suite
		for(int j = borneInferieur; j < borneSuperieur; j++) {
			if(j >= nbEchantillons/3 && j < 2*nbEchantillons/3){
				coefficient++;
			}
		}
			
		for(int index = 0 ; index < nombreDeBooleens ; index++){
			moyenneTemp=0f;
			
			for(int j = borneInferieur; j < borneSuperieur; j++) {
				moyenneTemp += donnees[index*nbEchantillons+(int)(j)];	//Calcule la moyenne le temps d'un tiers de bit
			}

			moyenneTemp = moyenneTemp/coefficient;

			if(moyenneTemp >= moyenneLimite) {	//si la moyenne est superieure au seuil, on ajoute true
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);	//sinon on ajoute false
			}
		}
	}
	
	/**
	 * Dechiffre les donnees en parametres pour estimer le bit emis
	 * @param donnees Les donnes analysee
	 */
	public void dechiffrerNRZT(Float[] donnees) {
		float moyenneLimite = (max+min)/2;	//Valeur de seuil pour le signal NRZT

		informationEmise  = new Information<Boolean>();
		float moyenneTemp = 0f;
		int nombreDeBooleens = (donnees.length-tauMax())/nbEchantillons;	//La taille du signal a emettre
		
		int coefficient = 0;
		//Compte le nombre d'element qu'il y a dans un bit afin de realiser une moyenne par la suite
		for (int j = nbEchantillons; j < 2*nbEchantillons/3; j++) {
			coefficient += 1;
		}
		
		for(int index = 0 ; index < nombreDeBooleens ; index++){
			moyenneTemp=0f;
			
			for (int j = nbEchantillons/3; j < 2*nbEchantillons/3; j++) {
				moyenneTemp += donnees[index*nbEchantillons+j];	//La moyenne temporaire le temps d'un tiers de bit
			}

			moyenneTemp = moyenneTemp/coefficient;

			if(moyenneTemp >= moyenneLimite) {
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
		}
	}

	/**
	 * permet de dechiffrer l'information et de passer une information float a une information boolean
	 * @throws InformationNonConformeException L'exception est levee si l'information n'est pas conforme
	 */
	public  void dechiffrer() throws InformationNonConformeException{
		Float[] donnees = new Float[informationRecue.nbElements()];
		if(formeSignal.equals("NRZ")) {
			donnees = this.analyseNRZ();
			dechiffrerNRZ(donnees);
		}
		else if(formeSignal.equals("RZ")) {
			donnees = this.analyseRZ();
			dechiffrerRZ(donnees);
		}
		else {
			donnees = this.analyseNRZT();
			dechiffrerNRZT(donnees);
		}
	}

	@Override
	public void emettre() throws InformationNonConformeException{

		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}
	
	/**
	 * Un getter retournant le nombre d'echantillons
	 * @return nbEchantillons le nombre d'echantillons de la transmission
	 */
	public int getNbEchantillons() {
		return nbEchantillons;
	}
	
	/**
	 * Un getter retournant le minimum
	 * @return min le Minimum
	 */
	public float getMin() {
		return min;
	}
	
	/**
	 * un getter retournant le minimum
	 * @return max le Maximum
	 */
	public float getMax() {
		return max;
	}
	
	/**
	 * un getter retournant la forme du signal
	 * @return formeSignal la forme du Signal (NRZ, NRZT ou RZ)
	 */
	public String getFormeSignal() {
		return formeSignal;
	}

}