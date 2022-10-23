package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Le transmetteur analogique parfait
 * @author jerom
 *
 */
public class TransmetteurAnalogiqueParfait extends Transmetteur<Float, Float> {

	@Override
	public void recevoir(Information <Float> information) throws InformationNonConformeException{
		informationRecue = information;
	}

	@Override
	public void emettre() throws InformationNonConformeException{
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationRecue);
		}
		this.informationEmise = informationRecue;   	   	
	}
}
