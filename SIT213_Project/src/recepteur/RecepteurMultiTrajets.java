package recepteur;


import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;
import transmetteurs.*;

//Reception de l'information
public class RecepteurMultiTrajets extends Transmetteur<Float, Boolean> {

	private int nbEchantillons;
	private float min=0f;
	private float max=1f;
	private String formeSignal;

	/**
	 * permet d'initialiser le Recepteur
	 * @param _nbEchantillons
	 * @param min
	 * @param max
	 * @param formeSignal
	 */
	public RecepteurMultiTrajets(int nbEchantillons, float min, float max, String formSignal) {
		this.min = min;
		this.max = max;
		this.nbEchantillons = nbEchantillons;
		this.formeSignal = formSignal;
	}
	
	/**
	 * permet de recevoir l'nformation float, ensuite fait appel a la methode dechiffrer 
	 * pour la transformer en boolean
	 * @param information
	 */
	public  void recevoir(Information <Float> information){
		informationRecue = information;
		dechiffrer(informationRecue);
	}

	/**
	 * permet de dechiffrer l'information et de passer une information float a une information boolean
	 * @param information
	 * @throws Exception 
	 */

	public  void dechiffrer(Information <Float> informationRecue){

		float moyenneLimite;	//Moyenne limite pour le signal NRZ et NRZT
		if(formeSignal == "RZ") moyenneLimite = (max-min)*1/6 + min;
		else moyenneLimite = (max+min)/2;

		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int tailleDesBooleens = informationRecue.nbElements()/nbEchantillons;
		
		LinkedList<Float> infoRecue = null; 
		try {
			infoRecue = informationRecue.cloneInformation();
		} catch (Exception e) {
			System.out.println("Clone info recept multi trajet");
		}		
		for(int index = 0 ; index < tailleDesBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += infoRecue.get(0);
				infoRecue.remove(0);
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
	 * emet aux differentes destinations
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