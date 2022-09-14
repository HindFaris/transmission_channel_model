package signaux;

import information.Information;

public class SignalNRZT extends Signal<Float,Boolean>{
	
	public SignalNRZT(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}
	
	public void generer() {
		
	}
	
	
	public  void dechiffrer() {
		
	}
}