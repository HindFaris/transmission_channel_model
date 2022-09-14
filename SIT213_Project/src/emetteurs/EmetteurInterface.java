package emetteurs;

import destinations.DestinationInterface;
import information.*;



public interface EmetteurInterface  <T> {

	public Information <T>  getInformationRecueDepuisSource();
	
	public void recevoirDepuisSource(Information <T> information) throws InformationNonConformeException;
	
    public void connecterATransmetteur (DestinationInterface <T> destination);
	
	public void emettreDepuisEmetteur() throws InformationNonConformeException;
	
    public Information <T>  getInformationEmetteurEmise();
    
    
}

