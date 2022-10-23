package recepteur;


import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;
import transmetteurs.*;


/**
 * Classe definissant le Recepteur sans Multi trajets
 * @author gaelc
 *
 */
public class Recepteur extends Transmetteur<Float, Boolean>{

	private int nbEchantillons;
	private float min=0f;
	private float max=1f;
	private String formeSignal;

	/**
	 * permet d'initialiser le Recepteur
	 * @param nbEchantillons le nombre d'echantillons
	 * @param min le minimum du signal
	 * @param max le maximum du signal
	 * @param formSignal la forme du signal (NRZ, NRZT ou RZ)
	 */
	public Recepteur(int nbEchantillons, float min, float max, String formSignal) {
		this.min=min;
		this.max=max;
		this.nbEchantillons=nbEchantillons;
		this.formeSignal = formSignal;
	}
	
	@Override
	public  void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
		dechiffrer();
	}

	/**
	 * Permet de dechiffrer l'information en comparant au seuil et de passer une information float a une information boolean
	 * On estime la valeur envoyee a l'origine en faisant une moyenne
	 * @throws InformationNonConformeException L'exception est levee si l'information n'est pas conforme
	 */
	public  void dechiffrer() throws InformationNonConformeException{
		LinkedList<Float> information = null; 
		try {
			information = informationRecue.cloneInformation();
		} catch (Exception e) {
			System.out.println("Err : impossible de cloner informationRecue dans recepteur");
		}
		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ et NRZT

		if(formeSignal == "RZ") {
			moyenneLimite = (max-min)*1/6 + min;
		}

		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int tailleDesBooleens = information.size()/nbEchantillons;
					
		for(int index = 0 ; index < tailleDesBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += information.get(0);
				information.remove(0);
			}
			
			//calcule la moyenne du bit
			moyenneTemp = moyenneTemp/nbEchantillons;
			
			//compare avec la moyenne limite pour decider de la valeur du bit
			if(moyenneTemp >= moyenneLimite) {
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
		}
	}

	@Override
	public void emettre() throws InformationNonConformeException{

		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}
	
	/**
	 * Un getter qui retourne le nombre d'echantillons
	 * @return nbEchantillons le nombre d'echantillons
	 */
	public int getNbEchantillons() {
		return nbEchantillons;
	}

	/**
	 * Un getter qui retourne le minimum et le maximum du signal
	 * @return min Le minimum du signal
	 */
	public float getMin() {
		return min;
	}

	/**
	 * Un getter qui retourne le maximum du signal
	 * @return max Le maximum du signal
	 */
	public float getMax() {
		return max;
	}

	/**
	 * Un getter qui retourne la forme du signal
	 * @return formeSignal la forme du signal (NRZ, NRZT ou RZ)
	 */
	public String getFormeSignal() {
		return formeSignal;
	}

}