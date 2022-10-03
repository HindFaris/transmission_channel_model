package decodage;
import information.Information;
import information.InformationNonConformeException;
import transmetteurs.Transmetteur;

public class codeur extends Transmetteur<Boolean, Boolean> {

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
		informationRecue = information;
	}

	@Override
	public void emettre() throws InformationNonConformeException {
		final int TAILLEINFORMATIONRECUE = informationRecue.nbElements();
		for(int indice = 0; indice < TAILLEINFORMATIONRECUE; indice++) {
			if(informationRecue.iemeElement(0)) {
				informationEmise.add(true,false,true);
			}
			else {
				informationEmise.add(false,true,false);

			}
			informationRecue.remove(0);
		}
	}

}
