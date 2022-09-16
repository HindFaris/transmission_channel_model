package emetteur;


import destinations.DestinationInterface;
import destinations.*;
import information.*;
import transmetteurs.*;
import signaux.*;

public class EmetteurAnalogique extends Transmetteur<Boolean, Float>{
	
	private String typeEmmeteur;
	private int nbEchantillons;
	private float min=0f;
	private float max=1f;
	
	/**
	 * constructeur de l'emetteur analogique initialise avec differents parametres
	 * @param _typeEmetteur
	 * @param _nbEchantillons
	 * @param min
	 * @param max
	 */
	public EmetteurAnalogique(String _typeEmetteur, int _nbEchantillons, float min, float max) {
		this.min = min;
		this.max = max;
		typeEmmeteur=_typeEmetteur;
		nbEchantillons=_nbEchantillons;
	}
	
	/**
	 * recevoir l'information booleenne depuis la source
	 */
	public  void recevoir(Information <Boolean> information) throws InformationNonConformeException{
		informationRecue = information;
		
	}
	/**
	 * emettre permet de generer un signal "RZ" "NRZ" ou "NRZT" selon ce qui a ete specifie 
	 * dans les arguments et ensuite d'envoyer cet information vers le recepteur
	 */
	public void emettre() throws InformationNonConformeException{
		
		if(typeEmmeteur.equalsIgnoreCase("RZ")) {
			SignalRZ signal = new SignalRZ(informationRecue, nbEchantillons, min, max);
			signal.generer();
			informationEmise = signal.getSignalSortieInformation();
		}
		else if(typeEmmeteur.equalsIgnoreCase("NRZ")) {
			SignalNRZ signal = new SignalNRZ(informationRecue, nbEchantillons, min, max);
			signal.generer();
			informationEmise = signal.getSignalSortieInformation();
		}
		else if(typeEmmeteur.equalsIgnoreCase("NRZT")) {
			SignalNRZT signal = new SignalNRZT(informationRecue, nbEchantillons, min, max);
			signal.generer();
			informationEmise = signal.getSignalSortieInformation();
		}
		else {
			throw new InformationNonConformeException("Vous devez spécifier RZ, NRZ ou NRZT en"
					+ " fonction du type de signal à émmettre");
		}
	
		for (DestinationInterface<Float> transmetteurConnectee : destinationsConnectees) {
			transmetteurConnectee.recevoir(informationEmise);
		}
		 
	}
	
	public String getTypeEmmeteur() {
		return typeEmmeteur;
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
}
