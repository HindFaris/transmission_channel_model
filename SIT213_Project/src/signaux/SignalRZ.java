package signaux;

import information.Information;

public class SignalRZ extends Signal{

	public SignalRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		super(informationRecue, nbEchantillons, min, max, SNRParBit, bruitActif);
	}
	
	/**
	 * permet de générer le signal analogique en prenant en entrée le signal logique
	 */
	public void generer() {

		signalSortieInformation  = new Information<Float>();

		for(int bit = 0; bit<tailleSignalEntree; bit++) {
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