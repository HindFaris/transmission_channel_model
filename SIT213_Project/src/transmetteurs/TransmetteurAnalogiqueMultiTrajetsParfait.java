package transmetteurs;

import java.security.Identity;
import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurAnalogiqueMultiTrajetsParfait extends Transmetteur<Float, Float> {
	
	//private float alpha; 
	//private int tau; 
	private LinkedList<Float> alphas = new LinkedList<Float>(); //attenuation du second trajet entre 0 et 1
	private LinkedList<Integer> taus = new LinkedList<Integer>(); //retard du signal en nombre d'echantillons
	
	//public TransmetteurAnalogiqueMultiTrajetsParfait(int nbEchantillons, float SNRParBit, Integer seed, float alpha, int tau ) {
	public TransmetteurAnalogiqueMultiTrajetsParfait(LinkedList<Float> alpha, LinkedList<Integer> tau ) {
		super();
		this.alphas = alpha;
		this.taus = tau;
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
	
	public void emettre() {
		//TODO : revoir la complexite (mauvaise)
		Information<Float> informationAjoutee = new Information<Float>();
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
		for(int indice = 0 ; indice < (tailleInformation); indice++) {
			informationEmise.add(informationRecue.iemeElement(0)+ informationAjoutee.iemeElement(0));
			informationRecue.remove(0);
			informationAjoutee.remove(0);
		}
		
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			try {
				destinationConnectee.recevoir(informationEmise);
			} catch (InformationNonConformeException e) {
				e.printStackTrace();
			}

		}
		this.informationEmise = informationRecue;
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
	/*
	public float getAlpha() {
		return alpha;
	}

	public int getTau() {
		return tau;
	}
	*/
}
