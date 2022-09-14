package signaux;


import information.Information;

public abstract class Signal<R,E> {
	
	protected Information<E> signalEntree;
	protected Information<R> signalSortieInformation;
	protected int nbEchantillon = 30;
	protected float min=0f;
	protected float max=1f;
	
	public Signal() {
		signalEntree = new Information<E>();
	}
	
	public Signal(Information<E> informationRecue, int nbEchantillons, float min, float max) {
		signalEntree = informationRecue;
	}
	
	public abstract void generer();
	
	public Information<R> signalSortieInformation(){
		return signalSortieInformation;
	}
}