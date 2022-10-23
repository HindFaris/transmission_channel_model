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
	
	@Override
	public  void recevoir(Information <Boolean> information) throws InformationNonConformeException{
		informationRecue = information;
		
	}

	@Override
	public void emettre() throws InformationNonConformeException{
		
		if(typeEmetteur.equalsIgnoreCase("RZ")) {
			//si le RZ est remonte par le simulateur, on initialise un signal RZ
			SignalRZ signal = new SignalRZ(informationRecue, nbEchantillons, min, max);
			//le signal est stocke dans information emise
			informationEmise = signal.getSignalSortieInformation();
		}
		else if(typeEmetteur.equalsIgnoreCase("NRZ")) {
			//si le NRZ est remonte par le simulateur, on initialise un signal NRZ
			SignalNRZ signal = new SignalNRZ(informationRecue, nbEchantillons, min, max);
			//le signal est stocke dans information emise
			informationEmise = signal.getSignalSortieInformation();
		}
		else if(typeEmetteur.equalsIgnoreCase("NRZT")) {
			//si le NRZT est remonte par le simulateur, on initialise un signal NRZT
			SignalNRZT signal = new SignalNRZT(informationRecue, nbEchantillons, min, max);
			//le signal est stocke dans information emise
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
	
	/**
	 * Un getter qui retourne le type de forme
	 * @return le type d'emetteur RZ, NRZ ou NRZT
	 */
	public String getTypeEmetteur() {
		return typeEmetteur;
	}
	/**
	 * Un getter qui retourne le nombre d'echantillons
	 * @return le nombre d'echantillons
	 */
	public int getNbEchantillons() {
		return nbEchantillons;
	}
	/**
	 * Un getter qui retourne l'amplitude minimale du signal
	 * @return l'amplitude minimale
	 */
	public float getMin() {
		return min;
	}
	/**
	 * Un getter qui retourne l'amplitude maximale du signal
	 * @return l'amplitude maximale
	 */
	public float getMax() {
		return max;
	}
}
