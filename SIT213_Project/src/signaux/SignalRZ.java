package signaux;

import java.util.LinkedList;
import java.util.function.ObjDoubleConsumer;

import information.Information;

/**
 * Classe pour generer le signal RZ
 * @author 33663
 *
 */
public class SignalRZ extends Signal{

	/**
	 * permet d'initialiser le signal RZ
	 * @param informationRecue l'info recue
	 * @param nbEchantillons le nb d'echantillons
	 * @param min l'amplitude minimale
	 * @param max l'amplitude maximale
	 */
	public SignalRZ(Information<Boolean> informationRecue, int nbEchantillons, float min, float max) {
		super(informationRecue, nbEchantillons, min, max);
	}
	
	/**
	 * permet de generer le signal analogique en prenant en entree le signal logique
	 */
	public void generer() {

		signalSortieInformation  = new Information<Float>();
		LinkedList<Boolean> copieInformationRecue = new LinkedList<Boolean>();

		try {
			copieInformationRecue = signalEntree.cloneInformation();	//On copie l'information pour ne pas modifier l'information principale
		} catch (Exception e) {

		}

		for(int bit = 0; bit<tailleSignalEntree; bit++) {
			for(float indice = 0; indice < nbEchantillons; indice++) {
				if(indice >= nbEchantillons/3 && indice < 2*nbEchantillons/3 && copieInformationRecue.get(0)) {	//Si le bit est dans le tiers du milieu et que le bit est a l'etat haut, on ajoute un max
					signalSortieInformation.add(max);
				}
				else {	//Sinon on ajoute un min
					signalSortieInformation.add(min);
				}
			}
			copieInformationRecue.remove(0);	//On passe a au bit suivant
		}
	}
}