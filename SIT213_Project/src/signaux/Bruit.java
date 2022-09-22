package signaux;

import information.Information;

import java.util.Random;

public class Bruit {
	
	protected Information<Float> signalSortieInformation;
	private float ecartType;
	private int tailleBruit;
	
	public Float iemeElement(int i) {
		return signalSortieInformation.iemeElement(i);
	}

	public Information<Float> getSignalSortieInformation() {
		return signalSortieInformation;
	}
	
	public float getEcartType() {
		return ecartType;
	}

	public int getTailleBruit() {
		return tailleBruit;
	}

	public Bruit(float ecartType, int tailleBruit) {
		this.ecartType = ecartType;
		this.tailleBruit = tailleBruit;
		signalSortieInformation = new Information<Float>();
		this.generer();
		
	}
	
	public void generer(){
		Random random = new Random();
		float rdm = 0;
		for(int index = 0; index < tailleBruit; index++) {
			rdm=(float)random.nextGaussian()*ecartType;
			signalSortieInformation.add(rdm);
		}
	}
}
