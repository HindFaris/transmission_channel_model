package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurParfait extends Transmetteur<Boolean, Boolean>{

	@Override
	public void recevoir(Information <Boolean> information) throws InformationNonConformeException{

		informationRecue = information;
	}

	@Override
	public void emettre() throws InformationNonConformeException{

		for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationRecue);
		}
		informationEmise = informationRecue;   	
	}

}