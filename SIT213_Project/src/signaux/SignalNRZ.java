package signaux;

public class SignalNRZ extends Signal<Float,Boolean>{

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