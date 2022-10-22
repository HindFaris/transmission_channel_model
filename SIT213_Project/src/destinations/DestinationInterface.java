package destinations;

import information.*;


/** 
 * Interface d'un composant ayant le comportement d'une destination
 * d'informations dont les elements sont de type T
 * @author prou
 */
public  interface DestinationInterface <T>  {   
   
    /**
     * pour obtenir la derniere information recue par une destination.
     * @return une information   
     */  
    public Information <T>  getInformationRecue(); 
   	 
    /**
     * La methode pour recevoir une information de la source a qui l'on est connectee
     * @param information  l'information a recevoir
     * @throws InformationNonConformeException si l'information n'est pas conforme au moment de l'envoi
     */
    public void recevoir(Information <T> information) throws InformationNonConformeException;
   
}
