package recepteur;


import destinations.DestinationInterface;
import destinations.*;
import information.*;
import transmetteurs.*;
import signaux.*;

//R�ception de l'information
public class Recepteur extends Transmetteur<Float, Boolean> {

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
	public Recepteur(int _nbEchantillons, float min, float max, String formSignal) {
		this.min=min;
		this.max=max;
		nbEchantillons=_nbEchantillons;
		formeSignal = formSignal;
	}

	/**
	 * permet de recevoir l'nformation float, ensuite fait appel à la méthode déchiffrer pour la transformer en boolean
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
	 * permet de déchiffrer l'information et de passer 'ue information float à une information boolean
	 * @param information
	 * @throws InformationNonConformeException
	 */

	public  void dechiffrer(Information <Float> information) throws InformationNonConformeException{

		float moyenneLimite = (max+min)/2;	//Moyenne limite pour le signal NRZ et NRZT

		if(formeSignal == "RZ") {
			moyenneLimite = (max-min)*1/6 + min;
		}

		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int tailleDesBooleens = information.nbElements()/nbEchantillons;

		for(int index = 0 ; index < tailleDesBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += information.iemeElement(j+index*nbEchantillons);
			}

			moyenneTemp = (float)(moyenneTemp/(float)nbEchantillons);
			System.out.println("Moyenne = " +moyenneTemp);

			if(moyenneTemp >= moyenneLimite) {
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
		}

	}

	/**
	 * transmet aux différentes destinations
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