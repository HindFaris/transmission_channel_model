package transmetteurs;

import destinations.DestinationInterface;
import information.*;


public interface TransmetteurInterface <T>{
	
	public Information <T>  getInformationRecueDepuisEmetteur();
	
	public void recevoir(Information <T> information) throws InformationNonConformeException;
	
	public void connecter(DestinationInterface <T> destination);
	
	public void emettre() throws InformationNonConformeException;
	
    public Information <T>  getInformationEmise();

}
