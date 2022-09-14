package signaux;

public class SignalNRZ extends Signal<Float,Boolean>{

	public void generer() {
		for(Boolean bit : signalEntree) {
			if (bit == true) {
				signalSortieInformation.add((float)5);
			}
			else {
				signalSortieInformation.add((float)-5);
			}
		}
	}
}