package signaux;

import java.util.LinkedList;

import information.Information;

/**
 * classe permettant de gener le signal NRZ
 * @author 33663
 *
 */
public class SignalNRZ extends Signal{

	/**
	 * Le constructeur faisant appel au constructeur de la classe mere : Signal
	 * @param informationRecue information recu
	 * @param nbEchantillons le nombre d'echantillons
	 * @param min l'amplitude minimale
	 * @param max l'amplitude maximale
	 */
	public SignalNRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}

	@Override
	public void generer() {

		signalSortieInformation  = new Information<Float>();
		signalSortieInformation.add((max+min)/2);	//La premiere valeur est quoi qu'il arrive entre le milieu de min et max
		LinkedList<Boolean> copieInformationRecue = new LinkedList<Boolean>();

		try {
			copieInformationRecue = signalEntree.cloneInformation();	//On prend une copie de l'information afin de ne pas changer sa valeur par la suite
		} catch (Exception e) {

		}
		for(int index = 1; index < nbEchantillons; index++) {	//On boucle de 1 jusqu'au nombre d'echantillons - 1 puisqu'on a traite le premier bit a part
			if (copieInformationRecue.get(0) == true) {	//Si la premiere valeur est a l'etat haut alors on ajoute le max nb echantillons fois
				signalSortieInformation.add(max);
			}
			else {
				signalSortieInformation.add(min);	//Si la premiere valeur est a l'etat bas alors on ajoute le min nb echantillons fois
			}
		}
		copieInformationRecue.remove(0);	//On a traite le premier booleen, on peut desormais l'enlever/

		for(int bit = 1; bit<tailleSignalEntree;bit++) {	//Pour tous les autres booleens restants

			if (copieInformationRecue.get(0) == true) {	//Si l'on a un 1, on ajoute le max nb echantillons fois
				for(int index = 0; index < nbEchantillons; index++) {
					signalSortieInformation.add(max);
				}
			}
			else {	//Si l'on a un 0, on ajoute le min nb echantillons fois
				for(int index = 0; index < nbEchantillons; index++) {
					signalSortieInformation.add(min);
				}
			}
			copieInformationRecue.remove(0);	//On enleve le booleen que l'on vient de traiter
		}


	}
}


