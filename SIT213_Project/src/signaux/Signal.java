package signaux;


import information.Information;

public abstract class Signal<R,E> {
	
	protected Information<E> signalEntree;
	
	protected Information<R> signalSortieInformation;
	
	protected int nbEchantillon;
	
	public Signal() {
		signalEntree = new Information<E>();
	}
	
	public Signal(Information<E> informationRecue, int nbEchantillons) {
		signalEntree = informationRecue;
	}
	
	public abstract void generer();
	
	public Information<R> signalSortieInformation(){
		return signalSortieInformation;
	}
}