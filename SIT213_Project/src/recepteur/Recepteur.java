package recepteur;


import destinations.DestinationInterface;
import destinations.*;
import information.*;
import transmetteurs.*;
import signaux.*;


public class Recepteur extends Transmetteur<Float, Boolean> {

	private int nbEchantillons;
	private float min=0f;
	private float max=1f;

	/**
	 * permet d'initialiser le Recepteur
	 * @param _nbEchantillons
	 * @param min
	 * @param max
	 */
	public Recepteur(int _nbEchantillons, float min, float max) {
		this.min=min;
		this.max=max;
		nbEchantillons=_nbEchantillons;
		
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
		
		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int tailleDesBooleens = information.nbElements()/nbEchantillons;

		for(int index = 0 ; index < tailleDesBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += information.iemeElement(j+index*nbEchantillons);
			}
			
			moyenneTemp = (float)(moyenneTemp/(float)nbEchantillons);
			
			if(moyenneTemp >= (max+min)/2) {
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



}