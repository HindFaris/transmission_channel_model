package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import signaux.Bruit;
/**
 * Le transmetteur analogique bruite
 * @author jerom
 *
 */
public class TransmetteurAnalogiqueBruite extends Transmetteur<Float, Float> {
	
	private float SNRParBit;
	private Integer seed = null;
	private int nbEchantillons;
	
	
	/**
	 * Un getter donnant le nombre d'echantillons
	 * @return nbEchantillons le nombre d'echantillons
	 */
	public int getNbEchantillons() {
		return nbEchantillons;
	}

	/**
	 * Un getter retournant la valeur du rapport signal a bruit par bit en dB
	 * @return SNRParBit le SNRparBit
	 */
	public float getSNRParBit() {
		return SNRParBit;
	}
	/**
	 * Un getter pour avoir l'identifiant de la seed si elle est présente
	 * @return seed l'identifiant de la seed ou null s'il n'y en a pas
	 */
	public Integer getSeed() {
		return seed;
	}

	
	/**
	 * Initialise le transmetteur analogique bruite avec le nbEchantillons, la seed et le SNRpb
	 * @param nbEchantillons le nombre d'echantillons
	 * @param SNRParBit le SNRpb
	 * @param seed la seed
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
	 * @return ecartType l'ecart-type
	 * @throws Exception l'exception potentiellement levee
	 */
	
	public float ecartType() throws Exception{
		float ecartType = (float)Math.sqrt(this.puissance()*nbEchantillons/(2*Math.pow(10, SNRParBit/10)));
		return ecartType;
	}
	
	/**
	 * calcul de la puissance du signal emis
	 * @return puissance la puissance du signal
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
