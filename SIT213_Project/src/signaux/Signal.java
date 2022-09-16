package signaux;


import information.Information;

public abstract class Signal<R,E> {
	
	protected Information<E> signalEntree;
	protected Information<R> signalSortieInformation;
	protected int nbEchantillon = 30;
	protected float min;
	public Information<E> getSignalEntree() {
		return signalEntree;
	}

	public void setSignalEntree(Information<E> signalEntree) {
		this.signalEntree = signalEntree;
	}

	public int getNbEchantillon() {
		return nbEchantillon;
	}

	public void setNbEchantillon(int nbEchantillon) {
		this.nbEchantillon = nbEchantillon;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public void setSignalSortieInformation(Information<R> signalSortieInformation) {
		this.signalSortieInformation = signalSortieInformation;
	}


	protected float max;
	
	/**
	 * constructeur du signal
	 */
	public Signal() {
		signalEntree = new Information<E>();
	}
	
	/**
	 * permet d'initialiser le signal avec des paramètres : informationRecue, nb echantillons, max, min
	 * @param informationRecue
	 * @param nbEchantillons
	 * @param min
	 * @param max
	 */
	public Signal (Information<E> informationRecue, int nbEchantillons, float min, float max) {
		this.min =min;
		this.max=max;
		this.nbEchantillon = nbEchantillons;
		signalEntree = informationRecue;
	}
	
	/**
	 * genere le code analogique à partir du code logique
	 */
	public abstract void generer();
	
	
	public Information<R> getSignalSortieInformation(){
		return signalSortieInformation;
	}
}