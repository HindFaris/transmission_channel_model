package sources;

import java.util.Random;

import destinations.DestinationFinale;
import information.Information;
import simulateur.ArgumentsException;
import transmetteurs.TransmetteurParfait;
import visualisations.SondeLogique;

public class SourceAleatoire extends Source{

	/**
	 * Créé le message correspondant au mot aléatoire
	 * @param nbBitsMess
	 * 			la longueur en nombre de bit du message
	 * @param seed
	 * 			la seed permet de rejouer une simulation si elle est différente de 0
	 */
	public SourceAleatoire (int nbBitsMess, int seed) {

		int randomInt;
		if (seed == 0) {
			informationGeneree = new Information<Boolean>();

			Random randomZeroOrOne = new Random();
			for (int index=0; index < nbBitsMess; index++) {
				randomInt = randomZeroOrOne.nextInt(0,2);

				if(randomInt == 1) {
					informationGeneree.add(true);;
				}

				else {
					informationGeneree.add(false);
				}
			}
		}
		
		else {
			informationGeneree = new Information<Boolean>();

			Random randomZeroOrOne = new Random(seed);
			for (int index=0; index < nbBitsMess; index++) {

				randomInt = randomZeroOrOne.nextInt(0,2);

				if(randomInt == 1) {
					informationGeneree.add(true);;
				}

				else {
					informationGeneree.add(false);
				}
				
			}
			String texte = informationGeneree.toString();
		}		
	}

	
}




