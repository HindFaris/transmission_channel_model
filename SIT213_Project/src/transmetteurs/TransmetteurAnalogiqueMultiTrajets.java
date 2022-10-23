package transmetteurs;

//import java.util.Arrays;
import java.util.LinkedList;


import destinations.DestinationInterface;
import information.*;
import signaux.Bruit;

/**
 * Le transmetteur analogique multi-trajets
 * @author jerom
 *
 */
public class TransmetteurAnalogiqueMultiTrajets extends Transmetteur<Float, Float> {

	private int nbEchantillons;
	private float SNRParBit;
	private Integer seed = null;
	private LinkedList<Float> alphas = new LinkedList<Float>(); //attenuation du second trajet entre 0 et 1
	private LinkedList<Integer> taus = new LinkedList<Integer>(); //retard du signal en nombre d'echantillons
	private boolean bruitActif;
	
	/**
	 * initialise le transmetteur analogique multitrajet
	 * @param nbEchantillons le nombre d'echantillons
	 * @param SNRParBit le SNRParBit
	 * @param seed la seed
	 * @param alphas les decalages
	 * @param taus les attenuations
	 * @param bruitActif l'etat du bruit
	 */
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

	@Override
	public void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
	}

	@Override
	public void emettre() {

		int tauxMax = 0;
		//recupere le plus grand taux
		for(int taux:taus) {
			if(taux>tauxMax) {
				tauxMax=taux;
			}
		}

		Float[] informationRecueCopie = informationRecue.clonerDansTableau(tauxMax);//copie de l'info recue avec taux '0' a la fin dans un tableau
		//pour eviter des problemes de complexite
		int tailleInfoRecue = informationRecueCopie.length;
		Float[] tableauEmis = new Float[tailleInfoRecue];

		for(int indice = 0; indice < tailleInfoRecue; indice ++) {
			tableauEmis[indice] = informationRecueCopie[indice];
		}

		if(!bruitActif) {//si bruit n'est pas actif
			for(int indice = 0; indice < tailleInfoRecue - tauxMax; indice++) {//boucle sur la taille de l'info recue
				for(int i = 0; i < taus.size(); i++) {//boucle sur tous les retards
						tableauEmis[indice+taus.get(i)] += informationRecueCopie[indice] * alphas.get(i);
						// remet l'infomation retardee et attenuee dans le tableau tableauEmis
				}
			}
		}
		else {//si bruit actif, il est pris en compte dans les informations retardees et l'information principale
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
					// remet l'infomation retardee et attenuee dans le tableau tableauEmis
				}
			}

			for(int indice = tailleInfoRecue - tauxMax; indice < tailleInfoRecue; indice++) {
				tableauEmis[indice] += informationBruit[indice];
				//rajoute le tableau des valeurs du bruit au tableau de l'information
			}
		}
		//remet a nouveau le tableau dans une liste Information
		informationEmise = (Information<Float>) new Information<Float>((Float[]) tableauEmis);
		
		//envoie l'information a la destination
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

	/**
	 * calcul la puissance du signal emis
	 * @return puissance du signal
	 */
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
	 * Un getter pour la liste des attenuations
	 * @return alphas l'attenuation
	 */
	public LinkedList<Float> getAlphas() {
		return alphas;
	}

	/**
	 * un getter pour la liste de taus
	 * @return taus le retard
	 */
	public LinkedList<Integer> getTaus() {
		return taus;

	}
}
