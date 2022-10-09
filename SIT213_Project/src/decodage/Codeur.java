package decodage;
import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import transmetteurs.Transmetteur;

public class Codeur extends Transmetteur<Boolean, Boolean> {

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
		informationRecue = information;
	}

	@Override
	public void emettre() throws InformationNonConformeException {
		informationEmise = new Information<Boolean>();
		final int TAILLEINFORMATIONRECUE = informationRecue.nbElements();
		LinkedList<Boolean> informationRecueCopie = new LinkedList<Boolean>();
		try {
			informationRecueCopie = informationRecue.cloneInformation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int indice = 0; indice < TAILLEINFORMATIONRECUE; indice++) {
			if(informationRecueCopie.get(0)) {
				informationEmise.add(true,false,true);
			}
			else {
				informationEmise.add(false,true,false);

			}
			informationRecueCopie.remove(0);
		}
		
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
		
	}

}
