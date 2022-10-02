package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;

public class TransmetteurAnalogiqueMultiTrajetsParfait extends Transmetteur<Float, Float> {

	private LinkedList<Float> alphas = new LinkedList<Float>(); //attenuation du second trajet entre 0 et 1
	private LinkedList<Integer> taus = new LinkedList<Integer>(); //retard du signal en nombre d'echantillons

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
		Information<Float> informationAjoutee = new Information<Float>();
		int tailleInformation = informationRecue.nbElements();
		int tauMax = 0;
		
		for (int index = 0; index < taus.size(); index++) {
			if (tauMax < Integer.valueOf(taus.get(index))) {
				tauMax= Integer.valueOf(taus.get(index));
			}
		}
		
		for (int index = 0; index < tailleInformation; index++) {
			informationAjoutee.add(0.0f);
		}
		for(int index = 0; index < tauMax; index++) {
			informationRecue.add(0.0f);
			informationAjoutee.add(0.0f);
		}

		//trajet indirect (alpha*s(t-tau))
		Information<Float> information;
		LinkedList<Float> infoRecue = null;
		
		for (int index = 0; index < taus.size(); index++) {
			information = new Information<Float>();
			
			try {
				infoRecue = informationRecue.cloneInformation();
			} catch (Exception e1) {
				System.out.println("ERR : Impossible de cloner l'informationRecue dans Transmetteur MultiTrajet Parfait");
			}
			
			int tau = taus.get(index);
			float alpha = alphas.get(index);

			while (tau>0){
				information.add(0.0f);
				tau--;
			}
			
			for (int i = 0; i < tailleInformation; i++) {
				information.add(infoRecue.get(0)*alpha);
				infoRecue.remove(0);
			}
			
			for (int i = 0; i < information.nbElements(); i++) {
				float var = informationAjoutee.iemeElement(0)+information.iemeElement(0);
				information.remove(0);
				informationAjoutee.remove(0);
				informationAjoutee.add(var);
			}
		}

		//signal emis par le transmetteur		
		for(int indice = 0 ; indice < (tailleInformation+tauMax); indice++) {
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
}
