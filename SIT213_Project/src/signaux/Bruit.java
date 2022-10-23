package signaux;

import information.Information;

import java.util.Random;

public class Bruit {

	protected Information<Float> signalSortieInformation;
	private float ecartType;
	private int tailleBruit;
	private Integer seed;

	/**
	 * retourne le i eme element du bruit
	 * @param i indice de l'element recherche
	 * @return la valeur du i eme element
	 */
	public Float iemeElement(int i) {
		return signalSortieInformation.iemeElement(i);
	}

	/**
	 * Un getter retournant le bruit
	 * @return le contenu du bruit
	 */
	public Information<Float> getSignalSortieInformation() {
		return signalSortieInformation;
	}

	/**
	 * un getter donnant l'ecart type du bruit suivant une loi gaussienne
	 * @return l'ecart type
	 */
	public float getEcartType() {
		return ecartType;
	}

	/**
	 * un getter retournant le nombre d'elements contenus dans l'information bruitee
	 * @return la taille du message bruite
	 */
	public int getTailleBruit() {
		return tailleBruit;
	}

	/**
	 * Le constructeur du bruit blanc gaussien (il genere tout seul le signal)
	 * 
	 * @param ecartType l'ecart type de la gaussienne
	 * @param tailleBruit la longueur du bruit
	 * @param seed La seed utilisee pour generer le bruit
	 */

	public Bruit(float ecartType, int tailleBruit, Integer seed) {
		this.ecartType = ecartType;
		this.tailleBruit = tailleBruit;
		signalSortieInformation = new Information<Float>();
		this.seed = seed;
		this.generer();
	}

	/**
	 * genere le bruit blanc gaussien
	 */
	public void generer(){
		if(seed == null) {	//Si la seed du bruit n'est pas active alors on ne passe rien en argument a l'objet Random
			Random random = new Random();
			float rdm = 0;
			for(int index = 0; index < tailleBruit; index++) {
				//la fonction nextGaussian suit une loi centree reduite. Le bruit est centre.
				//Cependant, pour ne pas qu'il soit reduit, on le multiplie par l'ecart-type souhaite
				rdm=(float)random.nextGaussian()*ecartType;
				signalSortieInformation.add(rdm);
			}

		}
		else {	//Si la seed du bruit est active alors on la passe en argument a l'objet Random afin d'avoir le même bruit
			Random random = new Random(seed);
			float rdm = 0;
			for(int index = 0; index < tailleBruit; index++) {
				//la fonction nextGaussian suit une loi centree reduite. Le bruit est centre.
				//Cependant, pour ne pas qu'il soit reduit, on le multiplie par l'ecart-type souhaite
				rdm=(float)random.nextGaussian()*ecartType;
				signalSortieInformation.add(rdm);
			}
		}

	}

	/**
	 * Un getter donnant la seed du bruit
	 * @return la seed
	 */
	public Integer getSeed() {
		return seed;
	}

	/**
	 * Enleve le i eme element du bruit
	 * @param index : le ieme element
	 */
	public void remove(int index) {
		signalSortieInformation.remove(index);
	}
}
