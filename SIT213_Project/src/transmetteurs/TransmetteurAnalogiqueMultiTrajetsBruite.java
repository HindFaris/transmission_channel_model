package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import signaux.Bruit;

public class TransmetteurAnalogiqueMultiTrajetsBruite extends Transmetteur<Float, Float> {

	private int nbEchantillons;
	public int getNbEchantillons() {
		return nbEchantillons;
	}

	public float getSNRParBit() {
		return SNRParBit;
	}

	public Integer getSeed() {
		return seed;
	}

	private float SNRParBit;
	private Integer seed = null;
	
	public TransmetteurAnalogiqueMultiTrajetsBruite(int nbEchantillons, float SNRParBit, Integer seed) {
		super();
		this.nbEchantillons = nbEchantillons;
		this.SNRParBit = SNRParBit;
		this.seed = seed;
		informationEmise = new Information<Float>();
	}
	
	/**
	 * recevoir l'information float
	 */
	public void recevoir(Information <Float> information) throws InformationNonConformeException{

		informationRecue = information;
	}

	/**
	 * emettre l'information float a toutes les destinations connectees
	 */
	
	public float ecartType() throws Exception{
		float ecartType = (float)Math.sqrt(this.puissance()*nbEchantillons/(2*Math.pow(10, SNRParBit/10)));
		return ecartType;
	}
	
	public float puissance(){
		float puissance = 0;
		LinkedList<Float> copieInformationRecue = new LinkedList<Float>();
		
		try {
			copieInformationRecue = informationRecue.cloneInformation();
		} catch (Exception e) {

		}
		for (int index = 0; index<informationRecue.nbElements(); index++) {
			puissance += Math.pow(copieInformationRecue.get(0), 2);
			copieInformationRecue.remove(0);
		}
		puissance = (puissance/(float)(informationRecue.nbElements()));
		return puissance;
	}
	
	public void emettre() throws InformationNonConformeException {
		
		Bruit bruit = null;
		try {
			bruit = new Bruit(this.ecartType(), informationRecue.nbElements(), seed);
		} catch (Exception e) {
		}
		int tailleInformation = informationRecue.nbElements();
		for(int indice = 0 ; indice < tailleInformation; indice++) {
			informationEmise.add(informationRecue.iemeElement(0) + bruit.iemeElement(0));
			informationRecue.remove(0);
			bruit.remove(0);
		}
		
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
		this.informationEmise = informationRecue; 
	}
}
