package signaux;

import information.Information;

import java.util.Random;

public class Bruit {
	
	protected Information<Float> signalSortieInformation;
	private float ecartType;
	private int tailleSignalEntree;
	private int nbEchantillons;
	
	public Float iemeElement(int i) {
		return signalSortieInformation.iemeElement(i);
	}

	public Information<Float> getSignalSortieInformation() {
		return signalSortieInformation;
	}
	
	public float getEcartType() {
		return ecartType;
	}

	public int getTailleSignalEntree() {
		return tailleSignalEntree;
	}

	public int getNbEchantillons() {
		return nbEchantillons;
	}

	public Bruit(float ecartType, int tailleSignalEntree, int nbEchantillons) {
		this.ecartType = ecartType;
		this.tailleSignalEntree = tailleSignalEntree;
		this.nbEchantillons = nbEchantillons;
		signalSortieInformation = new Information<Float>();
		this.generer();
		
	}
	
	public void generer(){
		Random random = new Random();
		for(int index = 0; index < tailleSignalEntree*nbEchantillons; index++) {
			signalSortieInformation.add((float)random.nextGaussian()*ecartType);
		}
	}
}
