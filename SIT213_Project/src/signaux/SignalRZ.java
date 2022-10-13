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
			for(float indice = 0; indice < nbEchantillons; indice++) {
				if(indice >= nbEchantillons/3 && indice < 2*nbEchantillons/3 && copieInformationRecue.get(0)) {
					signalSortieInformation.add(max);
				}
				else {
					signalSortieInformation.add(min);
				}
			}
			copieInformationRecue.remove(0);
		}
	}
}