package emetteur;


import destinations.DestinationInterface;
import information.*;
import transmetteurs.*;
import signaux.*;

/**
 * Emetteur utilise lorsque l'on souhaite envoye des informations analogiques.
 * @author gaelc
 */
public class EmetteurAnalogique extends Transmetteur<Boolean, Float>{
	
	private String typeEmetteur;
	private int nbEchantillons;
	private float min=0f;
	private float max=1f;

	/**
	 * constructeur de l'emetteur analogique initialise avec differents parametres
	 * @param typeEmetteur Le type d'emetteur choisi
 	 * @param nbEchantillons Le nombre d'echantillons
	 * @param min La valeur minimale
	 * @param max La valeur maximale
	 */
	public EmetteurAnalogique(String typeEmetteur, int nbEchantillons, float min, float max) {
		this.min = min;
		this.max = max;
		this.typeEmetteur=typeEmetteur;
		this.nbEchantillons=nbEchantillons;
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
		
		if(typeEmetteur.equalsIgnoreCase("RZ")) {
			
			SignalRZ signal = new SignalRZ(informationRecue, nbEchantillons, min, max);
			informationEmise = signal.getSignalSortieInformation();
		}
		else if(typeEmetteur.equalsIgnoreCase("NRZ")) {
			SignalNRZ signal = new SignalNRZ(informationRecue, nbEchantillons, min, max);
			informationEmise = signal.getSignalSortieInformation();
		}
		else if(typeEmetteur.equalsIgnoreCase("NRZT")) {
			SignalNRZT signal = new SignalNRZT(informationRecue, nbEchantillons, min, max);
			informationEmise = signal.getSignalSortieInformation();
		}
		else {
			throw new InformationNonConformeException("Vous devez specifier RZ, NRZ ou NRZT en"
					+ " fonction du type de signal à émmettre");
		}
	
		for (DestinationInterface<Float> transmetteurConnectee : destinationsConnectees) {
			transmetteurConnectee.recevoir(informationEmise);
		}		 
	}
	
	public String getTypeEmetteur() {
		return typeEmetteur;
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
