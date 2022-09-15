package signaux;

import java.awt.desktop.PrintFilesEvent;
import java.util.BitSet;

import information.Information;

public class SignalNRZ extends Signal<Float,Boolean>{

	public SignalNRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}

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

		for(int bit = 1; bit<signalEntree.nbElements();bit++) {

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


