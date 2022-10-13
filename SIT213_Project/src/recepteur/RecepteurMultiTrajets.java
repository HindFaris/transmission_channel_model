package recepteur;


import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;
import transmetteurs.*;

//Reception de l'information
/**
 * Classe definissant le Recepteur utilise lors de l'ajout de Multi trajets
 * @author gaelc
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
		int tauxMax = 0;
		for(int taux:taus) {
			if(taux>tauxMax) {
				tauxMax=taux;
			}
		}
		return tauxMax;
	}
	
	
	/**
	 * permet de recevoir l'information float, ensuite fait appel a la methode dechiffrer 
	 * pour la transformer en boolean
	 * @param information L'information a recevoir
	 */
	public  void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
		dechiffrer();
	}
	
	/**
	 * Analyse les donnees pour essayer d'eliminer les trajets multiples
	 * @return informationRecueCopie L'information analysee, les trajets retires
	 */
	public Float[] analyseNRZ(){

		Float[] informationRecueCopie = informationRecue.clonerDansTableau();

		int tailleTotaleMoinsTauMax = informationRecueCopie.length-tauMax();
		float elementCourant;

		for(int indice = 0; indice < tailleTotaleMoinsTauMax; indice++) {

			elementCourant = informationRecueCopie[indice];

			if(elementCourant >= (max+min)/2) {	//On est plus proche du max
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (max) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha additionne
				}
			}
			else {	//On est plus proche du min
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (min) * alphas.get(i);	//Pour tous les taux on vient ajouter le alpha additionne
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

		Float[] informationRecueCopie = informationRecue.clonerDansTableau();

		int tailleTotaleMoinsTauMax = informationRecueCopie.length-tauMax();
		float elementCourant;

		for(int indice = 0; indice < tailleTotaleMoinsTauMax; indice++) {

			elementCourant = informationRecueCopie[indice];

			if(elementCourant >= (max+min)/2) {	//On est plus proche du max
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (max) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha additionne
				}
			}
			else {	//On est plus proche du min
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (min) * alphas.get(i);	//Pour tous les taux on vient ajouter le alpha additionne
				}
			}
		}
		return informationRecueCopie;
	}
	
	/**
	 * Analyse les donnees pour essayer d'eliminer les trajets multiples
	 * @return informationRecueCopie L'information analysee, les trajets retires
	 */
	public Float[] analyseNRZT(){

		Float[] informationRecueCopie = informationRecue.clonerDansTableau();
		//float coefficientDirecteur = (max-((max+min)/2))/(nbEchantillons/3); //Ancien calcul du coefficient direteur
		int tailleTotaleMoinsTauMax = informationRecueCopie.length-tauMax();
		float elementCourant;
		int indiceCourant;
		//float moyenne = (max+min)/2;  //Ancien calcul de la moyenne simple
		
		for(int iemeInformation = 0; iemeInformation < tailleTotaleMoinsTauMax/nbEchantillons; iemeInformation++) {
			for(int indice = nbEchantillons/3; indice < 2*nbEchantillons/3; indice++) {
				indiceCourant = indice+(iemeInformation*nbEchantillons);
				elementCourant = informationRecueCopie[indiceCourant];

				if(elementCourant >= (max+min)/2) {	//On est plus proche du max
					for(int i = 0; i < taus.size(); i++) {
						informationRecueCopie[indiceCourant + taus.get(i)] -= (max) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha additionne
					}
				}
				else {	//On est plus proche du min
					for(int i = 0; i < taus.size(); i++) {
						informationRecueCopie[indiceCourant + taus.get(i)] -= (min) * alphas.get(i);	//Pour tous les taux on vient ajouter le alpha additionne
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
		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ et NRZT

		if(formeSignal.equals("RZ")) {
			moyenneLimite = (max-min)*1/6 + min;
		}

		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int nombreDeBooleens = (donnees.length-tauMax())/nbEchantillons;

		for(int index = 0 ; index < nombreDeBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += donnees[index*nbEchantillons+j];
			}

			moyenneTemp = moyenneTemp/nbEchantillons;

			if(moyenneTemp >= moyenneLimite) {
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
		}
	}
	
	/**
	 * Dechiffre les donnees en parametres pour estimer le bit emis
	 * @param donnees Les donnes analysee
	 */
	public void dechiffrerRZ(Float[] donnees) {
		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ et NRZT
		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int nombreDeBooleens = (donnees.length-tauMax())/nbEchantillons;
		int coefficient = 0;
		int borneInferieur;
		int borneSuperieur;
		
		if((nbEchantillons/3)-(float)(int)(nbEchantillons/3) == 0) {
			borneInferieur = (int)(nbEchantillons/3);
		}
		else {
			borneInferieur = (int)(nbEchantillons/3) + 1;
		}
		
		if((2*nbEchantillons/3)-(float)(int)(2*nbEchantillons/3) == 0) {
			borneSuperieur = (int)(2*nbEchantillons/3);
		}
		else {
			borneSuperieur = (int)(2*nbEchantillons/3) + 1;
		}
		
		for(int j = borneInferieur; j < borneSuperieur; j++) {
			if(j >= nbEchantillons/3 && j < 2*nbEchantillons/3){
				coefficient++;
			}
		}
			
		for(int index = 0 ; index < nombreDeBooleens ; index++){
			moyenneTemp=0f;
			
			for(int j = borneInferieur; j < borneSuperieur; j++) {
				moyenneTemp += donnees[index*nbEchantillons+(int)(j)];
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
	 * Dechiffre les donnees en parametres pour estimer le bit emis
	 * @param donnees Les donnes analysee
	 */
	public void dechiffrerNRZT(Float[] donnees) {
		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ et NRZT

		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int nombreDeBooleens = (donnees.length-tauMax())/nbEchantillons;
		
		int coefficient = 0;
		for (int j = nbEchantillons; j < 2*nbEchantillons/3; j++) {
			coefficient += 1;
		}
		
		for(int index = 0 ; index < nombreDeBooleens ; index++){
			moyenneTemp=0f;
			
			for (int j = nbEchantillons/3; j < 2*nbEchantillons/3; j++) {
				moyenneTemp += donnees[index*nbEchantillons+j];
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

	/**
	 * Envoie aux differentes destinations
	 */
	public void emettre() throws InformationNonConformeException{

		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}
	
	/**
	 * @return nbEchantillons le nombre d'echantillons de la transmission
	 */
	public int getNbEchantillons() {
		return nbEchantillons;
	}
	
	/**
	 * 
	 * @return min le Minimum
	 */
	public float getMin() {
		return min;
	}
	
	/**
	 * 
	 * @return max le Maximum
	 */
	public float getMax() {
		return max;
	}
	
	/**
	 * 
	 * @return formeSignal la forme du Signal (NRZ, NRZT ou RZ)
	 */
	public String getFormeSignal() {
		return formeSignal;
	}

}

/* Ancienne methode (ARCHIVE)
public void equivalentAlphasTaus() {

	if(taus.size() > 1) {
		LinkedList<Integer> indicesAEnlever = new LinkedList<Integer>();
		for(int i = 0; i < taus.size()-1; i++) {
			for(int j = i+1; j<taus.size(); j++) {
				if(taus.get(i) == taus.get(j) && (indicesAEnlever.contains(j)) == false) {
					indicesAEnlever.add(j);
					alphas.set(i, alphas.get(i)+alphas.get(j));
				}
			}
		}
		if(!indicesAEnlever.isEmpty()) {
			for(Integer indice : indicesAEnlever) {
				taus.remove((int) indice);
				alphas.remove((int)indice);
			}
		}
	}
}
 */