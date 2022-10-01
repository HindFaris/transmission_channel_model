package recepteur;


import destinations.DestinationInterface;
import information.*;
import transmetteurs.*;

//R�ception de l'information
public class RecepteurMultiTrajets extends Transmetteur<Float, Boolean> {

	private int nbEchantillons;
	private float min=0f;
	private float max=1f;
	private String formeSignal;
	private float alpha;
	private int tau;

	/**
	 * permet d'initialiser le Recepteur
	 * @param _nbEchantillons
	 * @param min
	 * @param max
	 * @param formeSignal
	 * @param alpha
	 * @param tau
	 */
	public RecepteurMultiTrajets(int nbEchantillons, float min, float max, String formSignal, float alpha, int tau) {
		this.min = min;
		this.max = max;
		this.nbEchantillons = nbEchantillons;
		this.formeSignal = formSignal;
		this.alpha = alpha;
		this.tau = tau;
	}
	
	/**
	 * permet de recevoir l'nformation float, ensuite fait appel a la methode dechiffrer 
	 * pour la transformer en boolean
	 * @param information
	 */
	public  void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
		try {
			dechiffrer(informationRecue);
		}
		catch(InformationNonConformeException E) {

		}
	}

	/**
	 * permet de dechiffrer l'information et de passer une information float a une information boolean
	 * @param information
	 * @throws InformationNonConformeException
	 */

	public  void dechiffrer(Information <Float> informationRecue) throws InformationNonConformeException{

		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ et NRZT

		if(formeSignal == "RZ") {
			moyenneLimite = (max-min)*1/6 + min;
		}

		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int tailleDesBooleens = informationRecue.nbElements()/nbEchantillons;
				
		for(int index = 0 ; index < tailleDesBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += informationRecue.iemeElement(0);
				informationRecue.remove(0);
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
	
	public float getAlpha() {
		return alpha;
	}

	public int getTau() {
		return tau;
	}


}