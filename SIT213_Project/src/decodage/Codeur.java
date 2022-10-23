package decodage;
import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import transmetteurs.Transmetteur;

/**
 * La classe codeur permet de coder le signal recu de la source
 * @author 33663
 *
 */

public class Codeur extends Transmetteur<Boolean, Boolean> {
	
	/**
	 * Constructeur de codeur
	 */
	public Codeur() {
	}
	
	
	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
		informationRecue = information;
	}

	@Override
	/*
	 * On traduit l'information recue de l'Emetteur (l'Objet JAVA du moins, car le codeur fait en soit partie de l'emission)
	 * Pour un bit a 1 en entree on ajoute 101 a l'information qui sera emise
	 * Pour un bit a 0 en entree on ajoute 010 a l'information qui sera emise
	 * Enfin, on emets l'information aux destinations connectees.
	 */
	public void emettre() throws InformationNonConformeException {
		informationEmise = new Information<Boolean>();
		final int TAILLEINFORMATIONRECUE = informationRecue.nbElements();
		LinkedList<Boolean> informationRecueCopie = new LinkedList<Boolean>();
		try {
			informationRecueCopie = informationRecue.cloneInformation();//on travaille sur la copie pour eviter les problème de la complexite
		} catch (Exception e) {
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
