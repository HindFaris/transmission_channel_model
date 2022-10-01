package signaux;

import java.util.LinkedList;

import information.Information;

public class SignalRZ extends Signal{

	public SignalRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}
	
	/**
	 * permet de generer le signal analogique en prenant en entree le signal logique
	 */
	public void generer() {

		signalSortieInformation  = new Information<Float>();
		LinkedList<Boolean> copieInformationRecue = new LinkedList<Boolean>();

		try {
			copieInformationRecue = signalEntree.cloneInformation();
		} catch (Exception e) {

		}

		for(int bit = 0; bit<tailleSignalEntree; bit++) {
			for(int index = 0; index < nbEchantillon/3; index++) {
				signalSortieInformation.add(min);
			}
			if(copieInformationRecue.get(0) == true) {
				for(int index = nbEchantillon/3; index < 2*nbEchantillon/3; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {
				for(int index = nbEchantillon/3; index < 2*nbEchantillon/3; index++) {
					signalSortieInformation.add(min);
				}
			}
			for(int index = 2*nbEchantillon/3; index < nbEchantillon; index++) {
				signalSortieInformation.add(min);
			}
			copieInformationRecue.remove(0);
		}
	}
}