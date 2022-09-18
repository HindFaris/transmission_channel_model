package signaux;

import information.Information;

public abstract class Signal {
	
	protected Information<Boolean> signalEntree;
	protected Information<Float> signalSortieInformation;
	protected int nbEchantillon;
	protected float min;
	protected float SNRParBit;
	protected int tailleSignalEntree;
	protected boolean bruitActif;
	
	public Information<Boolean> getSignalEntree() {
		return signalEntree;
	}

	public void setSignalEntree(Information<Boolean> signalEntree) {
		this.signalEntree = signalEntree;
	}

	public int getNbEchantillon() {
		return nbEchantillon;
	}

	public void setNbEchantillon(int nbEchantillon) {
		this.nbEchantillon = nbEchantillon;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public void setSignalSortieInformation(Information<Float> signalSortieInformation) {
		this.signalSortieInformation = signalSortieInformation;
	}


	protected float max;
	
	/**
	 * constructeur du signal
	 */
	public Signal() {
		signalEntree = new Information<Boolean>();
		
	}
	
	/**
	 * permet d'initialiser le signal avec des parametres : informationRecue, nb echantillons, max, min
	 * @param informationRecue
	 * @param nbEchantillons
	 * @param min
	 * @param max
	 */
	public Signal (Information<Boolean> informationRecue, int nbEchantillons, float min, float max, float SNRParBit, boolean bruitActif) {
		this.min =min;
		this.max=max;
		this.nbEchantillon = nbEchantillons;
		signalEntree = informationRecue;
		tailleSignalEntree = signalEntree.nbElements();
		this.SNRParBit = SNRParBit;
		this.bruitActif = bruitActif;
		this.generer();
		if (bruitActif == true) {
			Bruit bruit = new Bruit(this.ecartType(), tailleSignalEntree, nbEchantillon);
			for (int index = 0; index < tailleSignalEntree*nbEchantillon; index ++) {
				signalSortieInformation.setIemeElement(index, signalSortieInformation.iemeElement(index)+bruit.iemeElement(index));
			}
		}
		
	}
	
	/**
	 * genere le code analogique a  partir du code logique
	 */
	public abstract void generer();
	
	public float puissance() {
		float puissance = 0;
		for (int index = 0; index<tailleSignalEntree*nbEchantillon; index++) {
			puissance += Math.pow(signalSortieInformation.iemeElement(index), 2);
		}
		puissance = (puissance/(float)(tailleSignalEntree*nbEchantillon));
		return puissance;
	}
	
	public float getSNRParBit() {
		return SNRParBit;
	}

	public void setSNRParBit(float sNRParBit) {
		SNRParBit = sNRParBit;
	}

	public int getTailleSignalEntree() {
		return tailleSignalEntree;
	}

	public void setTailleSignalEntree(int tailleSignalEntree) {
		this.tailleSignalEntree = tailleSignalEntree;
	}

	public boolean isBruitActif() {
		return bruitActif;
	}

	public void setBruitActif(boolean bruitActif) {
		this.bruitActif = bruitActif;
	}

	public float ecartType() {
		if(tailleSignalEntree*nbEchantillon == signalSortieInformation.nbElements()) {
			System.out.println("Bien joué !");
		}
		float ecartType = (float)Math.sqrt(this.puissance()*nbEchantillon/(2*Math.pow(10, SNRParBit/10)));
		return ecartType;
	}
	
	public Information<Float> getSignalSortieInformation(){
		return signalSortieInformation;
	}
}