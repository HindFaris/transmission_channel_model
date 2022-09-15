package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurAnalogiqueParfait extends Transmetteur<Float, Float> {

	public void recevoir(Information <Float> information) throws InformationNonConformeException{

		informationRecue = information;
	}

	public void emettre() throws InformationNonConformeException{

		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationRecue);
		}
		this.informationEmise = informationRecue;   	   	
	}
}
