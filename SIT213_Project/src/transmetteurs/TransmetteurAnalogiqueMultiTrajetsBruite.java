package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import signaux.Bruit;

public class TransmetteurAnalogiqueMultiTrajetsBruite extends Transmetteur<Float, Float> {
	
	private int nbEchantillons;
	private float SNRParBit;
	private Integer seed = null;
	private float alpha; //attenuation du second trajet entre 0 et 1
	private int tau; //retard du signal en nombre d'echantillons
	
	public TransmetteurAnalogiqueMultiTrajetsBruite(int nbEchantillons, float SNRParBit, Integer seed, float alpha, int tau ) {
		super();
		this.nbEchantillons = nbEchantillons;
		this.SNRParBit = SNRParBit;
		this.seed = seed;
		this.alpha = alpha;
		this.tau = tau;
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
	
	public void emettre() throws InformationNonConformeException {
		Information<Float> informationAjoutee = new Information<Float>();
		
		Bruit bruit = null;
		try {
			bruit = new Bruit(this.ecartType(), informationRecue.nbElements()+tau, seed);
		} catch (Exception e) {
		}
		int tailleInformation = informationRecue.nbElements();
		//trajet indirect (s(t) avec retard)
		int t = this.tau;
		while (t>0){
			informationAjoutee.add(0.0f);
			informationRecue.add(0.0f);
			t--;
		}
		for (int i = 0; i < tailleInformation; i++) {
			//TODO : revoir la complexite (mauvaise)
			informationAjoutee.add(informationRecue.iemeElement(0)*alpha);	
		}
		
		//signal emis par le transmetteur
		for(int indice = 0 ; indice < tailleInformation; indice++) {
			informationEmise.add(informationRecue.iemeElement(0)+ informationAjoutee.iemeElement(0));
			informationRecue.remove(0);
			informationAjoutee.remove(0);
			
			//TODO : ajouter le bruit (2eme partie)
			//informationEmise.add(informationRecue.iemeElement(0) + informationAjoutee.iemeElement(0) + bruit.iemeElement(0));
			//bruit.remove(0);
		}
		System.out.println(informationEmise);
		
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
		this.informationEmise = informationRecue; //utile ? -> informationRecue vide (null)
	}
	
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
	
	public int getNbEchantillons() {
		return nbEchantillons;
	}

	public float getSNRParBit() {
		return SNRParBit;
	}

	public Integer getSeed() {
		return seed;
	}
}
