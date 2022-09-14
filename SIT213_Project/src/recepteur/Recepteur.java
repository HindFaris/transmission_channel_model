package recepteur;


import transmetteurs.TransmetteurInterface;

import java.util.LinkedList;

import destinations.*;
import information.Information;
import information.InformationNonConformeException;

public abstract class Recepteur <R,E> implements TransmetteurInterface <E>, DestinationInterface <R> {

	
	 /** 
     * la liste des composants destination connectés en sortie du transmetteur 
     */
    protected LinkedList <DestinationInterface <E>> DestinationsConnectees;
   
    /** 
     * l'information reçue en entrée du transmetteur 
     */
    protected Information <R>  informationRecue;
		
    /** 
     * l'information émise en sortie du transmetteur
     */		
    protected Information <E>  informationEmise;
   
    /** 
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Transmetteur
     */
    public Recepteur() {
    DestinationsConnectees = new LinkedList <DestinationInterface <E>> ();
	informationRecue = null;
	informationEmise = null;
    }
   	
    /**
     * retourne la dernière information reçue en entrée du
     * transmetteur
     * @return une information   
     */
	public Information <R>  getInformationTransmetteurRecue() {
		return this.informationRecue;
	}

	/**
	 * retourne la dernière information émise en sortie du
	 * transmetteur
	 * @return une information   
	 */
	public Information <E>  getInformationTransmetteurEmise() {
		return this.informationEmise;
	}
	
    /**
     * connecte une destination à la sortie du transmetteur
     * @param destination  la destination à connecter
     */
    public void connecter (DestinationInterface <E> destination) {
    	DestinationsConnectees.add(destination); 
    }

    /**
     * déconnecte une destination de la la sortie du transmetteur
     * @param destination  la destination à déconnecter
     */
    public void deconnecter (DestinationInterface <E> destination) {
    	DestinationsConnectees.remove(destination); 
    }
   	    
    /**
     * reçoit une information.  Cette méthode, en fin d'exécution,
     * appelle la méthode émettre.
     * @param information  l'information  reçue
     */
    public abstract void recevoirDepuisTransmetteur(Information <R> information) throws InformationNonConformeException;

	/**
	 * émet l'information construite par l'emetteur
	 */
	public abstract void emettreDepuisRecepteur() throws InformationNonConformeException;   

}