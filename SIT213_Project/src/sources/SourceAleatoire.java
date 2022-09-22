package sources;

import java.util.Random;

import information.Information;


public class SourceAleatoire extends Source<Boolean>{

	/**
	 * Cree le message correspondant au mot aleatoire
	 * @param nbBitsMess
	 * 			la longueur en nombre de bit du message
	 * @param seed
	 * 			la seed permet de rejouer une simulation si elle est differente de 0
	 */
	public SourceAleatoire (int nbBitsMess, int seed) {
		informationGeneree = new Information<Boolean>();
		if (seed == 0) {
			Random randomZeroOrOne = new Random();
			for (int index=0; index < nbBitsMess; index++) {
				informationGeneree.add(randomZeroOrOne.nextBoolean());
			}
		}
		
		else {
			Random randomZeroOrOne = new Random(seed);
			for (int index=0; index < nbBitsMess; index++) {
				informationGeneree.add(randomZeroOrOne.nextBoolean());
			}
		}		
	}
}




