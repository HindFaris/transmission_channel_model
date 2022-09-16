package signaux;


import information.Information;

public abstract class Signal<R,E> {
	
	protected Information<E> signalEntree;
	protected Information<R> signalSortieInformation;
	protected int nbEchantillon = 30;
	protected float min;
	protected float max;
	
	/**
	 * constructeur du signal
	 */
	public Signal() {
		signalEntree = new Information<E>();
	}
	
	/**
	 * permet d'initialiser le signal avec des parametres : informationRecue, nb echantillons, max, min
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
	 * genere le code analogique a  partir du code logique
	 */
	public abstract void generer();
	
	
	public Information<R> getSignalSortieInformation(){
		return signalSortieInformation;
	}
}