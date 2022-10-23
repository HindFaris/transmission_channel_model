package signaux;

import information.Information;

public abstract class Signal {
	
	protected Information<Boolean> signalEntree;
	protected Information<Float> signalSortieInformation;
	protected int nbEchantillons;
	protected float min;	//la valeur du signal pour l'etat 0
	protected float max;	//la valeur du signal pour l'etat 1
	protected int tailleSignalEntree;	
	
	/**
	 * Un getter retournant le signal d'entree
	 * @return le signal en entree
	 */
	public Information<Boolean> getSignalEntree() {
		return signalEntree;
	}

	/**
	 * Un getter retournant le nombre d'echantillons
	 * @return le nombre d'echantillons
	 */
	public int getNbEchantillons() {
		return nbEchantillons;
	}
	
	/**
	 * Un getter retournant la valeur du signal pour l'etat 0
	 * @return la valeur du signal pour l'etat 0
	 */
	public float getMin() {
		return min;
	}

	/**
	 * Un getter retournant la valeur du signal pour l'etat 1
	 * @return la valeur du signal pour l'etat 1
	 */
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
	 * Permet d'initialiser le signal avec des parametres : l'information recue, le nombred'echantillon, la valeur
	 * du signal a l'etat 0 et 1
	 * @param informationRecue L'information recue
	 * @param nbEchantillons Le nombre d'echantillons
	 * @param min La valeur minimum du signal
	 * @param max La valeur maximum du signal
	 */
	public Signal (Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		this.min =min;
		this.max=max;
		this.nbEchantillons = nbEchantillons;
		signalEntree = informationRecue;
		tailleSignalEntree = signalEntree.nbElements();
		this.generer();		//genere le signal voulu
	}
	
	/**
	 * Genere le code analogique a partir du code logique
	 */
	public abstract void generer();

	/**
	 * Un getter donnant la taille du signal en entree
	 * @return la taille du signal d'entree
	 */
	public int getTailleSignalEntree() {
		return tailleSignalEntree;
	}

	/**
	 * Un getter retournant le signal
	 * @return le signal
	 */
	public Information<Float> getSignalSortieInformation(){
		return signalSortieInformation;
	}
}