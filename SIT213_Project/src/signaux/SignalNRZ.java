package signaux;

import java.util.LinkedList;

import information.Information;

public class SignalNRZ extends Signal{

	public SignalNRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}

	/**
	 * permet de generer le signal analogique en prenant en entree le signal logique
	 */
	public void generer() {

		signalSortieInformation  = new Information<Float>();
		signalSortieInformation.add((max+min)/2);
		LinkedList<Boolean> copieInformationRecue = new LinkedList<Boolean>();

		try {
			copieInformationRecue = signalEntree.cloneInformation();
		} catch (Exception e) {

		}
		for(int index = 1; index < nbEchantillon; index++) {
			if (copieInformationRecue.get(0) == true) {
				signalSortieInformation.add(max);
			}
			else {
				signalSortieInformation.add(min);
			}
		}
		copieInformationRecue.remove(0);

		for(int bit = 1; bit<tailleSignalEntree;bit++) {

			if (copieInformationRecue.get(0) == true) {
				for(int index = 0; index < nbEchantillon; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = 0; index < nbEchantillon; index++) {
					signalSortieInformation.add(min);
				}
			}
			copieInformationRecue.remove(0);
		}


	}
}


