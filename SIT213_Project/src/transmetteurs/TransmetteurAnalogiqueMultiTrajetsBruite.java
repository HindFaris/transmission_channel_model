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
	// private float alpha; 
	// private int tau; 
	private LinkedList<Float> alphas = new LinkedList<Float>(); //attenuation du second trajet entre 0 et 1
	private LinkedList<Integer> taus = new LinkedList<Integer>(); //retard du signal en nombre d'echantillons
	
	public TransmetteurAnalogiqueMultiTrajetsBruite(int nbEchantillons, float SNRParBit, Integer seed, LinkedList<Float> alphas, LinkedList<Integer> taus ) {
		super();
		this.nbEchantillons = nbEchantillons;
		this.SNRParBit = SNRParBit;
		this.seed = seed;
		this.alphas = alphas;
		this.taus = taus;
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
		//TODO : revoir la complexite (mauvaise)
				Information<Float> informationAjoutee = new Information<Float>();
				Bruit bruit = null;
				try {
					bruit = new Bruit(this.ecartType(), informationRecue.nbElements(), seed);
				} catch (Exception e) {
				}
				int tailleInformation = informationRecue.nbElements();
				int tauMax = 0;
				for (int index = 0; index < taus.size(); index++) {
					if (tauMax < Integer.valueOf(taus.get(index))) {
						tauMax= Integer.valueOf(taus.get(index));
					}
				}
				for (int index = 0; index < tailleInformation+tauMax; index++) {
					informationAjoutee.add(0.0f);
				}
				
				//trajet indirect (alpha*s(t-tau))
				for (int index = 0; index < taus.size(); index++) {
					Information<Float> information = new Information<Float>();
					int tau = taus.get(index);
					float alpha = alphas.get(index);
					
					while (tau>0){
						information.add(0.0f);
						tau--;
					}

					for (int i = 0; i < tailleInformation; i++) {
						information.add(informationRecue.iemeElement(i)*alpha);	
					}
					for (int i = 0; i < information.nbElements(); i++) {
						float var = informationAjoutee.iemeElement( i)+information.iemeElement(i);
						informationAjoutee.setIemeElement(i, var);
					}
				}
		
		//signal emis par le transmetteur
		for(int indice = 0 ; indice < tailleInformation; indice++) {
			informationEmise.add(informationRecue.iemeElement(0)+ informationAjoutee.iemeElement(0)+ bruit.iemeElement(0));
			//informationEmise.add(informationRecue.iemeElement(0)+ informationAjoutee.iemeElement(0));
			informationRecue.remove(0);
			informationAjoutee.remove(0);
			bruit.remove(0);
		}
		
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
		this.informationEmise = informationRecue;
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
	/*
	public float getAlpha() {
		return alpha;
	}

	public int getTau() {
		return tau;
	}
	*/
}
