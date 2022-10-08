package transmetteurs;

import java.util.Arrays;
import java.util.LinkedList;


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

	@SuppressWarnings("null")
	public void emettre() {

		int tauxMax = 0;
		//recupere le plus grand taux
		for(int taux:taus) {
			if(taux>tauxMax) {
				tauxMax=taux;
			}
		}

		Float[] informationRecueCopie = informationRecue.clonerDansTableau(tauxMax);	//copie de l'info recue avec taux '0' a la fin
		int tailleInfoRecue = informationRecueCopie.length;
		Float[] tableauEmis = new Float[tailleInfoRecue];

		for(int indice = 0; indice < tailleInfoRecue; indice ++) {
			tableauEmis[indice] = informationRecueCopie[indice];
		}

		if(!bruitActif) {
			for(int indice = 0; indice < tailleInfoRecue - tauxMax; indice++) {
				for(int i = 0; i < taus.size(); i++) {
						tableauEmis[indice+taus.get(i)] += informationRecueCopie[indice] * alphas.get(i);
				}
			}
		}
		else {
			Bruit bruit = null;
			try {
				bruit = new Bruit(this.ecartType(), tailleInfoRecue, seed);
			} catch (Exception e) {
				System.out.println("Err : Impossible de creer le bruit dans Transmetteur MultiTrajet Bruite");
			}
			Float[] informationBruit = bruit.getSignalSortieInformation().clonerDansTableau();

			for(int indice = 0; indice < tailleInfoRecue - tauxMax; indice++) {
				tableauEmis[indice] += informationBruit[indice];
				for(int i = 0; i < taus.size(); i++) {
					tableauEmis[indice+taus.get(i)] += informationRecueCopie[indice] * alphas.get(i);
				}
			}

			for(int indice = tailleInfoRecue - tauxMax; indice < tailleInfoRecue; indice++) {
				tableauEmis[indice] += informationBruit[indice];
			}
		}

		informationEmise = (Information<Float>) new Information((Float[]) tableauEmis);

		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			try {
				destinationConnectee.recevoir(informationEmise);
			} catch (InformationNonConformeException e) {
				// TODO Auto-generated catch block
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
