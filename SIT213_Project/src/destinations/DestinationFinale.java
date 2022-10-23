package destinations;

import information.Information;
import information.InformationNonConformeException;

/**
 * Simple classe definissant le point final de la transmission, qui recoit du binaire.
 */
public class DestinationFinale extends Destination <Boolean>{
	
	/**
	 * Constructeur de Destination finale
	 */
	public DestinationFinale() {
		// TODO Auto-generated constructor stub
	}
	public void recevoir(Information <Boolean> information) throws InformationNonConformeException{
		informationRecue = information;
	}
}
