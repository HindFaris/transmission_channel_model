package transmetteurs;

import java.util.LinkedList;

import javax.management.ConstructorParameters;

import destinations.DestinationInterface;
import information.*;
import signaux.Bruit;

public class TransmetteurAnalogiqueMultiTrajets extends Transmetteur<Float, Float> {

	private int nbEchantillons;
	private float SNRParBit;
	private Integer seed = null;
	private LinkedList<Float> alphas = new LinkedList<Float>(); //attenuation du second trajet entre 0 et 1
	private LinkedList<Integer> taus = new LinkedList<Integer>(); //retard du signal en nombre d'echantillons
	private boolean bruitActif;

	public TransmetteurAnalogiqueMultiTrajets(int nbEchantillons, float SNRParBit, Integer seed, LinkedList<Float> alphas, LinkedList<Integer> taus, boolean bruitActif) {
		super();
		this.nbEchantillons = nbEchantillons;
		this.SNRParBit = SNRParBit;
		this.seed = seed;
		this.alphas = alphas;
		this.taus = taus;
		this.bruitActif = bruitActif;
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
		int tauMax = taus.get(0);

		for (int index = 1; index < taus.size(); index++) {	//Definit tauMax : le plus grand tau
			if (tauMax < Integer.valueOf(taus.get(index))) {
				tauMax= Integer.valueOf(taus.get(index));
			}
		}

		for(int index = 0; index < tauMax; index++) {	//On ajoute taumax '0' à la fin du message recue pour qu'il fasse la bonne taille
			informationRecue.add(0.0f);
		}
		for(int index = 0; index < taus.get(index); index++) {	//cette information correspond a un signal fille
			informationAjoutee.add(0.0f);
		}
		//trajet indirect (alpha*s(t-tau))
		Information<Float> information ;
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
			LinkedList<Float> informationAjouteeCopie = new LinkedList<Float>();
			try {
				informationAjouteeCopie = informationAjoutee.cloneInformation();
				for (int i = 0; i < tailleInformation; i++) {
					informationAjouteeCopie.add(0.0f);
				}
			} catch (Exception e1) {
				System.out.println("ERR : Impossible de cloner l'informationRecue dans Transmetteur MultiTrajet Parfait");
			}
			int tailleDeInformation = information.nbElements();
			for (int i = 0; i < tailleDeInformation; i++) {
				float var = informationAjouteeCopie.get(0)+information.iemeElement(0);
				information.remove(0);
				informationAjouteeCopie.remove(0);
				informationAjoutee.add(var);
			} 


		}

		//signal emis par le transmetteur	
		if(bruitActif) {
			
			Bruit bruit = null;
			try {
				bruit = new Bruit(this.ecartType(), tailleInformation+tauMax, seed);
			} catch (Exception e) {
				System.out.println("Err : Impossible de creer le bruit dans Transmetteur MultiTrajet Bruite");
			}
			
			for(int indice = 0 ; indice < (tailleInformation+tauMax); indice++) {
				informationEmise.add(informationRecue.iemeElement(0)+ informationAjoutee.iemeElement(0)+ bruit.iemeElement(0) );
				informationRecue.remove(0);
				informationAjoutee.remove(0);
				bruit.remove(0);
			}
		}
		else {
			for(int indice = 0 ; indice < (tailleInformation+tauMax); indice++) {
				informationEmise.add(informationRecue.iemeElement(0)+ informationAjoutee.iemeElement(0));
				informationRecue.remove(0);
				informationAjoutee.remove(0);
			}
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

	public float ecartType() throws Exception{
		float ecartType = (float)Math.sqrt(this.puissance()*nbEchantillons/(2*Math.pow(10, SNRParBit/10)));
		return ecartType;
	}

	public LinkedList<Float> getAlphas() {
		return alphas;
	}

	public LinkedList<Integer> getTaus() {
		return taus;

	}
}
