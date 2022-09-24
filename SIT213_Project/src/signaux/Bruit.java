package signaux;

import information.Information;

import java.util.Random;

public class Bruit {
	
	protected Information<Float> signalSortieInformation;
	private float ecartType;
	private int tailleBruit;
	private Integer seed;
	
	/**
	 * retourne le i eme element du bruit
	 * @param i indice de l'element recherche
	 * @return la valeur du i eme element
	 */
	public Float iemeElement(int i) {
		return signalSortieInformation.iemeElement(i);
	}

	/**
	 * un getter donnant le bruit
	 * @return le bruit
	 */
	public Information<Float> getSignalSortieInformation() {
		return signalSortieInformation;
	}
	
	/**
	 * un getter donnant l'ecart type de la gaussienne
	 * @return l'ecart type
	 */
	public float getEcartType() {
		return ecartType;
	}

	/**
	 * un getter retournant le nombre d'elements contenus dans l'information bruitee
	 * @return la taille du message bruite
	 */
	public int getTailleBruit() {
		return tailleBruit;
	}

	/**
	 * Le constructeur du bruit blanc gaussien (il genere tout seul le signal)
	 * 
	 * @param ecartType l'ecart type de la gaussienne
	 * @param tailleBruit la longueur du bruit
	 */
	public Bruit(float ecartType, int tailleBruit) {
		this.ecartType = ecartType;
		this.tailleBruit = tailleBruit;
		signalSortieInformation = new Information<Float>();
		this.generer();
	}
	
	public Bruit(float ecartType, int tailleBruit, Integer seed) {
		this.ecartType = ecartType;
		this.tailleBruit = tailleBruit;
		signalSortieInformation = new Information<Float>();
		this.seed = seed;
		this.generer();
	}
	
	/**
	 * genere le bruit blanc gaussien
	 */
	public void generer(){
		if(seed == null) {
			Random random = new Random();
			float rdm = 0;
			for(int index = 0; index < tailleBruit; index++) {
				rdm=(float)random.nextGaussian()*ecartType;
				signalSortieInformation.add(rdm);
			}
		}
		else {
			Random random = new Random(seed);
			float rdm = 0;
			for(int index = 0; index < tailleBruit; index++) {
				rdm=(float)random.nextGaussian()*ecartType;
				signalSortieInformation.add(rdm);
			}
		}
		
	}
}
