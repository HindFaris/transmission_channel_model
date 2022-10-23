package sources;

import java.util.Random;
import information.Information;

/**
 * La source generant une trame de bit de maniere aleatoire
 * @author jerom
 *
 */
public class SourceAleatoire extends Source<Boolean>{

	/**
	 * Cree le message correspondant au mot aleatoire
	 * @param nbBitsMess
	 * 			la longueur en nombre de bit du message
	 * @param seed
	 * 			la seed permet de rejouer une simulation si elle est differente de 0
	 */
	public SourceAleatoire (int nbBitsMess, Integer seed) {
		informationGeneree = new Information<Boolean>();
		if (seed == null) {
			Random randomZeroOrOne = new Random();
			for (int index=0; index < nbBitsMess; index++) {
				informationGeneree.add(randomZeroOrOne.nextBoolean());
			}
		}
		
		else {//si une seed est rentree par l'utilusateur on l'utilise pour generer l'objet Random
			Random randomZeroOrOne = new Random(seed);
			for (int index=0; index < nbBitsMess; index++) {
				informationGeneree.add(randomZeroOrOne.nextBoolean());
			}
		}	
	}
}




