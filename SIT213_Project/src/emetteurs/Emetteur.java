package emetteurs;

import sources.*;
import information.*;
import transmetteurs.*;
import java.util.*;

public abstract class Emetteur <T> implements  EmetteurInterface<T>  {

	/** 
	 * la liste des composants destination connectés en sortie de l'émetteur 
	 */
	protected LinkedList <TransmetteurInterface <TransmetteurInterface<T>>> TransmetteursConnectees;

	/** 
	 * l'information reçue en entrée de l'emetteur 
	 */
	protected Information <SourceInterface<T>>  informationRecue;

	/** 
	 * l'information émise en sortie de l'emetteur
	 */		
	protected Information <TransmetteurInterface<T>>  informationEmise;

	/** 
	 * un constructeur factorisant les initialisations communes aux
	 * réalisations de la classe abstraite Transmetteur
	 */
	public Emetteur(){
		TransmetteursConnectees = new LinkedList <TransmetteurInterface<T>> ();
		informationRecue = null;
		informationEmise = null;
	}

	/**
	 * retourne la dernière information reçue en entrée du
	 * transmetteur
	 * @return une information   
	 */
	public Information <R>  getInformationRecue() {
		return this.informationRecue;
	}

	/**
	 * retourne la dernière information émise en sortie du
	 * transmetteur
	 * @return une information   
	 */
	public Information <E>  getInformationEmise() {
		return this.informationEmise;
	}

	/**
	 * connecte une destination à la sortie du transmetteur
	 * @param destination  la destination à connecter
	 */
	public void connecter (TransmetteurInterface <E> transmetteur) {
		TransmetteursConnectees.add(transmetteur); 
	}

	
	//****secondaire *****
	/**
	 * déconnecte une destination de la la sortie de l'emetteur
	 * @param destination  la destination à déconnecter
	 */
	public void deconnecter (TransmetteurInterface <E> transmetteur) {
		TransmetteursConnectees.remove(transmetteur); 
	}
	//****secondaire *****
	
	
	/**
	 * reçoit une information.  Cette méthode, en fin d'exécution,
	 * appelle la méthode émettre.
	 * @param information  l'information  reçue
	 */
	public  abstract void recevoir(Information <R> information) throws InformationNonConformeException;

	/**
	 * émet l'information construite par l'emetteur
	 */
	public  abstract void emettre() throws InformationNonConformeException; 
	
	
	public  abstract Information <Integer> CoderMessageAEnvoyer();
	
}

