package signaux;

import information.Information;

public class SignalRZ extends Signal<Float,Boolean>{

	public SignalRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}
	
	public void generer() {

		signalSortieInformation  = new Information<Float>();
		signalSortieInformation.add((max+min)/2);

		for(int index = 1; index < nbEchantillon; index++) {
			if(index >= nbEchantillon / 3 && index <= 2*nbEchantillon/3) { 
				if (signalEntree.iemeElement(0) == true) {
					signalSortieInformation.add(max);
				}
				else {
					signalSortieInformation.add(min);
				}
			}
			else {
				signalSortieInformation.add((max+min)/2);
			}
		}

		for(int bit = 1; bit<signalEntree.nbElements();bit++) {
			
			for(int index = 0; index < nbEchantillon; index++) {
				if(index >= nbEchantillon / 3 && index <= 2*nbEchantillon/3) { 
					if (signalEntree.iemeElement(bit) == true) {
						signalSortieInformation.add(max);
					}
					else {
						signalSortieInformation.add(min);
					}
				}
				else {
					signalSortieInformation.add((max+min)/2);
				}
			}
		}
	}
	
	
	public  void dechiffrer() {
		
	}
}