package OutilsTest;


import Destination.Destination;
import Information.Information;
import Information.InformationNonConforme;

/**
 *
 * @author juliengambier
 */
/**
 * Classe de test permettant de recuperer une chaine de bit sous la forme d'une
 * String
 */
public class DestinationBouchon extends Destination {

	/**
	 * Stocke l'information dans l'information recue
	 */
	@Override
	public void recevoir(Information information) throws InformationNonConforme {
		// TODO Auto-generated method stub
		this.informationRecue = information;
	}

}
