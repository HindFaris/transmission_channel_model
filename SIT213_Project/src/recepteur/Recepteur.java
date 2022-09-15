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

	public Recepteur(int _nbEchantillons, float min, float max) {
		this.min=min;
		this.max=max;
		nbEchantillons=_nbEchantillons;
		
	}

	public  void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
		try {
			dechiffrer(informationRecue);
			}
			catch(InformationNonConformeException E) {
				
			}
	}

	public  void dechiffrer(Information <Float> information) throws InformationNonConformeException{
		
		informationEmise  = new Information<Boolean>(); 
		float moyenneTemp = 0f;
		int tailleDesBooleens = information.nbElements()/nbEchantillons;

		for(int index = 0 ; index < tailleDesBooleens ; index++){
			moyenneTemp=0f;
			for (int j = 0; j < nbEchantillons; j++) {
				moyenneTemp += information.iemeElement(j+index*nbEchantillons);
			}
			System.out.println("Somme des valeurs = " + moyenneTemp);
			
			moyenneTemp = (float)(moyenneTemp/(float)nbEchantillons);
			
			System.out.println(moyenneTemp);
			
			if(moyenneTemp >= (max+min)/2) {
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
			
			
		
		}
		for (int index=0;index<informationEmise.nbElements();index++) {
			System.out.println(informationEmise.iemeElement(index));
		}

	}


	public void emettre() throws InformationNonConformeException{

		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);

				
		}
	}



}