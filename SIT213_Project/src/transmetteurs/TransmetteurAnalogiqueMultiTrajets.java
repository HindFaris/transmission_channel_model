package transmetteurs;

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
		Information<Float>[] infosFilles;
		
		//initialise la taille du tableau des informations filles selon la presence du bruit
		if(bruitActif) {
			infosFilles = new Information[taus.size()+1];
		}
		else {
			infosFilles = new Information[taus.size()];
		}
		
		for(int indice = 0; indice<taus.size(); indice++) {
			infosFilles[indice] = new Information<Float>();
		}
		
		for(int i=0; i<taus.size();i++) {
			LinkedList<Float> informationRecueCopie = null;
			try {
				informationRecueCopie = informationRecue.cloneInformation();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Rajout de 0 au debut
			for(int index=0;index<taus.get(i);index++) {
				//Rajout de 0 au début 
				infosFilles[i].add(0.0f);	
			}
			//Rajout de l'information attenuee
			for(int index=0;index<informationRecue.nbElements();index++) {
				infosFilles[i].add(alphas.get(i)*informationRecueCopie.get(0));
				informationRecueCopie.remove(0);
			}
			//Rajout de 0 pour faire la taille du taux le plus grand
			if(tauxMax != taus.get(i)) {
				for(int index=0; index<tauxMax-taus.get(i);index++) {
					infosFilles[i].add(0.0f);
				}
			}
		}

		for(int index=0; index<tauxMax; index++) {
			informationRecue.add(0.0f);
		}

		if(bruitActif) {
			Bruit bruit = null;
			try {
				bruit = new Bruit(this.ecartType(), informationRecue.nbElements(), seed);
			} catch (Exception e) {
				System.out.println("Err : Impossible de creer le bruit dans Transmetteur MultiTrajet Bruite");
			}
			infosFilles[taus.size()] = bruit.getSignalSortieInformation();
		}
		
		informationEmise = informationRecue.addInformations(infosFilles);
		
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
