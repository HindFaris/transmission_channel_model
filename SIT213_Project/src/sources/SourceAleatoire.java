package sources;

import java.util.Random;

public class SourceAleatoire {


	private int nbBitsMess;
	private String mssg;

	public SourceAleatoire (int nbBitsMess) {

		this.nbBitsMess = nbBitsMess;
		Random randomZeroOrOne = new Random();
		int randomInt;
		String messageString = "";

		for (int index=0; index < nbBitsMess; index++) {
			randomInt = randomZeroOrOne.nextInt(0,2);
			messageString += randomInt;
			
			this.mssg = messageString;
			
		}

	}
	    

}

