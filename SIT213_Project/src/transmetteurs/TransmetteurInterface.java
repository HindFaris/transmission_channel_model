package transmetteurs;

import destinations.DestinationInterface;
import information.*;

/**
 * L'interface des transmetteurs
 * @author jerom
 * 
 * @param <T> un type
 */
public interface TransmetteurInterface <T>{
	
	/**
	 * Un getter donnant l'information recue depuis l'emtteur
	 * @return l'information recue depuis l'emetteur
	 */
	public Information <T>  getInformationRecueDepuisEmetteur();
	
	/**
	 * Recoit l'information
	 * @param information l'information recue
	 * @throws InformationNonConformeException l'exception potentiellement levee
	 */
	public void recevoir(Information <T> information) throws InformationNonConformeException;
	
	/**
	 * Permet de connecter les destinations
	 * @param destination la liste des destinations connectees au transmetteur
	 */
	public void connecter(DestinationInterface <T> destination);
	
	/**
	 * emmet l'information aux objets connectes
	 * @throws InformationNonConformeException l'information potentiellement levee
	 */
	public void emettre() throws InformationNonConformeException;
	
	/**
	 * un getter retournant l'information emise
	 * @return l'information emise
	 */
    public Information <T>  getInformationEmise();

}
