package signaux;


import information.Information;

public abstract class Signal<R,E> {
	
	protected Information<E> signalEntree;
	protected Information<R> signalSortieInformation;
	protected int nbEchantillon = 30;
	protected float min;
	protected float max;
	
	public Signal() {
		signalEntree = new Information<E>();
	}
	
	public Signal (Information<E> informationRecue, int nbEchantillons, float min, float max) {
		this.min =min;
		this.max=max;
		this.nbEchantillon = nbEchantillons;
		signalEntree = informationRecue;
	}
	
	public abstract void generer();
	
	
	public Information<R> getSignalSortieInformation(){
		return signalSortieInformation;
	}
}