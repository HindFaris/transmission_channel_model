package signaux;

import information.Information;

public class SignalNRZ extends Signal{

	public SignalNRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		super(informationRecue, nbEchantillons, min, max, SNRParBit, bruitActif);
	}

	/**
	 * permet de generer le signal analogique en prenant en entree le signal logique
	 */
	public void generer() {
		
		signalSortieInformation  = new Information<Float>();
		signalSortieInformation.add((max+min)/2);
		
		for(int index = 1; index < nbEchantillon; index++) {
			if (signalEntree.iemeElement(0) == true) {
				signalSortieInformation.add(max);
			}
			else {
				signalSortieInformation.add(min);
			}
		}

		for(int bit = 1; bit<tailleSignalEntree;bit++) {

			if (signalEntree.iemeElement(bit) == true) {
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


