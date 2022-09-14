package emetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import transmetteurs.*;

public class Emetteur_NRZ extends Emetteur<Boolean,Float>{
	
	public void recevoirDepuisSource(Information <Float> information) throws InformationNonConformeException{

		informationRecue = information;
	}

	/**
	 * émet l'information construite par le transmetteur
	 */
	public void emettreDepuisEmetteur() throws InformationNonConformeException{

		for (TransmetteurInterface <Float> destinationConnectee : TransmetteursConnectees) {
			TransmetteursConnectees.recevoirDepuisEmetteur(informationRecue);
		}
		informationEmise = informationRecue;   	
	}

	/*
	 * public Information <Integer> CoderMessageAEnvoyer(){
	 */
		
		
	}

