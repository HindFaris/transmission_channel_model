package recepteur;

import destinations.DestinationInterface;
import information.*;


public interface RecepteurInterface <T>{

	public Information <T>  getInformationRecueDepuisTransmetteur();
	
	public void recevoirDepuisTransmetteur(Information <T> information) throws InformationNonConformeException;
	
	public void connecterADestination(DestinationInterface <T> destination);
	
	public void emettreDepuisRecepteur() throws InformationNonConformeException;
	
    public Information <T>  getInformationRecepteurEmise();

}
