package sources;

import java.util.Random;
import information.Information;

public class SourceAleatoire extends Source{


	public SourceAleatoire (int nbBitsMess) {


		Random randomZeroOrOne = new Random();
		int randomInt;

		informationGeneree = new Information<Boolean>();

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

}



