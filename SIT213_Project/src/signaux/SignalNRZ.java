package signaux;

import information.Information;

public class SignalNRZ extends Signal<Float,Boolean>{

	public SignalNRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}
	
	public void generer() {
		for(Boolean bit : signalEntree) {
			if (bit == true) {
				for(int index = 0; index < nbEchantillon; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = 0; index < nbEchantillon; index++) {
					signalSortieInformation.add(min);
				}
			}
		}
	}
}