package decodage;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import transmetteurs.Transmetteur;

public class Decodeur extends Transmetteur<Boolean, Boolean>{

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
		informationRecue = information;
	}

	@Override
	public void emettre() throws InformationNonConformeException {
		informationEmise = new Information<Boolean>();
		final int TAILLEINFORMATIONAEMETTRE = informationRecue.nbElements()/3;
		boolean informationelement1;
		boolean informationelement2;
		boolean informationelement3;
		for(int indice = 0; indice < TAILLEINFORMATIONAEMETTRE; indice++) {
			informationelement1 = informationRecue.iemeElement(0);
			informationelement2 = informationRecue.iemeElement(1);
			informationelement3 = informationRecue.iemeElement(2);
			if(informationelement1 && informationelement2 && informationelement3) {
				informationEmise.add(true);
			}
			else if(informationelement1 && !informationelement2 && informationelement3){
				informationEmise.add(true);
			}
			else if(informationelement1 && !informationelement2 && !informationelement3){
				informationEmise.add(true);
			}
			else if(!informationelement1 && !informationelement2 && informationelement3){
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
			informationRecue.remove(0);
			informationRecue.remove(0);
			informationRecue.remove(0);
		}
		
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
		
	}
	
}
