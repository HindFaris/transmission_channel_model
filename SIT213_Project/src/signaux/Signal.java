package signaux;

import information.Information;

public abstract class Signal {
	
	protected Information<Boolean> signalEntree;
	protected Information<Float> signalSortieInformation;
	protected int nbEchantillons;
	protected float min;
	protected float max;
	protected int tailleSignalEntree;
	
	public Information<Boolean> getSignalEntree() {
		return signalEntree;
	}

	public int getNbEchantillons() {
		return nbEchantillons;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	/**
	 * constructeur du signal
	 */
	public Signal() {
		signalEntree = new Information<Boolean>();
		
	}
	
	/**
	 * permet d'initialiser le signal avec des parametres : informationRecue, nb echantillons, max, min
	 * @param informationRecue
	 * @param nbEchantillons
	 * @param min
	 * @param max
	 */
	public Signal (Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		this.min =min;
		this.max=max;
		this.nbEchantillons = nbEchantillons;
		signalEntree = informationRecue;
		tailleSignalEntree = signalEntree.nbElements();
		this.generer();		
	}
	
	/**
	 * genere le code analogique a  partir du code logique
	 */
	public abstract void generer();

	public int getTailleSignalEntree() {
		return tailleSignalEntree;
	}

	public Information<Float> getSignalSortieInformation(){
		return signalSortieInformation;
	}
}