package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import signaux.Bruit;

public class TransmetteurAnalogiqueBruite extends Transmetteur<Float, Float> {
	
	private float SNRParBit;
	private Integer seed = null;
	private int nbEchantillons;
	
	
	/**
	 * retourne le nombre d'echantillons
	 */
	public int getNbEchantillons() {
		return nbEchantillons;
	}

	/**
	 * retourne le SNRparBit
	 */
	public float getSNRParBit() {
		return SNRParBit;
	}
	/**
	 * retourne la seed si elle existe
	 */
	public Integer getSeed() {
		return seed;
	}

	
	/**
	 * initialise le transmetteur analogique bruite avec le nbEchantillons, la seed et le SNR
	 * @param nbEchantillons
	 * @param SNRParBit
	 * @param seed
	 */
	public TransmetteurAnalogiqueBruite(int nbEchantillons, float SNRParBit, Integer seed) {
		super();
		this.nbEchantillons = nbEchantillons;
		this.SNRParBit = SNRParBit;
		this.seed = seed;
		informationEmise = new Information<Float>();
	}
	
	@Override
	public void recevoir(Information <Float> information) throws InformationNonConformeException{

		informationRecue = information;
	}

	/**
	 * calculer l'ecartype en faisant appel a puissance 
	 * @return ecartType
	 * @throws Exception
	 */
	
	public float ecartType() throws Exception{
		float ecartType = (float)Math.sqrt(this.puissance()*nbEchantillons/(2*Math.pow(10, SNRParBit/10)));
		return ecartType;
	}
	
	/**
	 * calcul de la puissance du signal emis
	 * @return puissance du signal
	 */
	public float puissance(){
		float puissance = 0;
		LinkedList<Float> copieInformationRecue = new LinkedList<Float>();
		
		try {
			copieInformationRecue = informationRecue.cloneInformation();//travaille sur la copie pour eviter les problemes de complexite
		} catch (Exception e) {

		}
		for (int index = 0; index<informationRecue.nbElements(); index++) {
			puissance += Math.pow(copieInformationRecue.get(0), 2);
			copieInformationRecue.remove(0);
		}
		puissance = (puissance/(float)(informationRecue.nbElements()));
		return puissance;
	}
	
	@Override
	public void emettre() throws InformationNonConformeException {
		
		Bruit bruit = null;
		try {
			bruit = new Bruit(this.ecartType(), informationRecue.nbElements(), seed);
		} catch (Exception e) {
			System.out.println("Probleme survenu dans la creation du bruit");
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
