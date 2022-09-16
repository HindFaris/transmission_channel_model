package signaux;

import information.Information;

public class SignalRZ extends Signal<Float,Boolean>{

	public SignalRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}
	
	/**
	 * permet de générer le signal analogique en prenant en entrée le signal logique
	 */
	public void generer() {

		signalSortieInformation  = new Information<Float>();

		for(int bit = 0; bit<signalEntree.nbElements(); bit++) {
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
					signalSortieInformation.add(min);
				}
			}
		}
	}
}