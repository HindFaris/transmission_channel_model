package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import signaux.Bruit;

public class TransmetteurAnalogiqueBruite extends Transmetteur<Float, Float> {

	private int nbEchantillon;
	private float SNRParBit;
	private Integer seed = null;
	
	public TransmetteurAnalogiqueBruite(int nbEchantillon, float SNRParBit, Integer seed) {
		super();
		this.nbEchantillon = nbEchantillon;
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
	
	public float ecartType() {
		float ecartType = (float)Math.sqrt(this.puissance()*nbEchantillon/(2*Math.pow(10, SNRParBit/10)));
		return ecartType;
	}
	
	public float puissance() {
		float puissance = 0;
		for (int index = 0; index<informationRecue.nbElements(); index++) {
			puissance += Math.pow(informationRecue.iemeElement(index), 2);
		}
		puissance = (puissance/(float)(informationRecue.nbElements()));
		return puissance;
	}
	
	public void emettre() throws InformationNonConformeException{
		Bruit bruit = new Bruit(this.ecartType(), informationRecue.nbElements(), seed);
		for(int indice = 0 ; indice < informationRecue.nbElements(); indice++) {
			informationEmise.add(informationRecue.iemeElement(indice) + bruit.iemeElement(indice));
		}
		
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
		this.informationEmise = informationRecue;   	   	
	}
}
