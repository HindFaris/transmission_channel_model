package recepteur;


import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;
import transmetteurs.*;

//Reception de l'information
public class RecepteurMultiTrajets extends Transmetteur<Float, Boolean>{

	private int nbEchantillons;
	private float min=0f;
	private float max=1f;
	private String formeSignal;
	private LinkedList<Integer> taus;
	private LinkedList<Float> alphas;

	/**
	 * permet d'initialiser le Recepteur
	 * @param _nbEchantillons
	 * @param min
	 * @param max
	 * @param formeSignal
	 */
	public RecepteurMultiTrajets(int _nbEchantillons, float min, float max, String formSignal, LinkedList<Integer> taus, LinkedList<Float> alphas) {
		this.min=min;
		this.max=max;
		nbEchantillons=_nbEchantillons;
		formeSignal = formSignal;
		this.taus = taus;
		this.alphas = alphas;

	}
	
	public int tauMax() {
		int tauxMax = 0;
		for(int taux:taus) {
			if(taux>tauxMax) {
				tauxMax=taux;
			}
		}
		return tauxMax;
	}
	/*
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
	/**
	 * permet de recevoir l'information float, ensuite fait appel a la methode dechiffrer 
	 * pour la transformer en boolean
	 * @param information
	 */
	public  void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
		dechiffrer();
	}

	public Float[] nrzAnalyse(){
		
		Float[] informationRecueCopie = informationRecue.clonerDansTableau();

		int tailleTotaleMoinsTauMax = informationRecueCopie.length-tauMax();
		float elementCourant;
		
		for(int indice = 0; indice < tailleTotaleMoinsTauMax; indice++) {
			
			elementCourant = informationRecueCopie[indice];
			
			if(elementCourant >= (max+min)/2) {	//On est plus proche du max
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] -= (max-min) * alphas.get(i);	//Pour tous les taux on vient enlever le alpha additionne
				}
			}
			else {	//On est plus proche du min
				for(int i = 0; i < taus.size(); i++) {
					informationRecueCopie[indice + taus.get(i)] += (max-min) * alphas.get(i);	//Pour tous les taux on vient ajouter le alpha additionne
				}
			}
		}

		return informationRecueCopie;
	}


	/**
	 * permet de dechiffrer l'information et de passer une information float a une information boolean
	 * @param information
	 * @throws InformationNonConformeException
	 */
	public  void dechiffrer() throws InformationNonConformeException{
		Float[] donnees = new Float[informationRecue.nbElements()];
		if(formeSignal.equals("NRZ")) {
			donnees = this.nrzAnalyse();
		}

		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ et NRZT

		if(formeSignal == "RZ") {
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
	 * transmet aux differentes destinations
	 */

	public void emettre() throws InformationNonConformeException{

		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}

	public int getNbEchantillons() {
		return nbEchantillons;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	public String getFormeSignal() {
		return formeSignal;
	}

}