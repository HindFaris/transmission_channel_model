/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transmetteur;

import Destination.DestinationInterface;
import Information.Information;
import Information.InformationNonConforme;

/**
 * Transmet sans erreur une information composée de booleans
 * 
 * @author chauquie
 */
public class TransmetteurParfait extends Transmetteur<Boolean, Boolean>{
            
    /**
     * Recoie une information composée de booleans
     */
    public void recevoir(Information information) throws InformationNonConforme {
        informationRecue = new Information<Boolean>();
        
        for(int i = 0 ; i < information.nbElements() ; i++){
            informationRecue.add((Boolean)information.iemeElement(i));
        }
        this.emettre();
    }

    /**
     * Emet une information
     */
    public void emettre() throws InformationNonConforme {
        informationEmise = new Information<Boolean>();
        // Dans un premier cas, informtion recue = information émise ;
        // Ce ne sera plus le cas dansl es prochaines itérations du programme, d'où la séparation des boucles
        for(int i = 0 ; i < informationRecue.nbElements() ; i++){
            informationEmise.add((Boolean)informationRecue.iemeElement(i));
        }
        
        // parcourir les différente destinations
        for(DestinationInterface <Boolean> di : destinationsConnectees){
            // pour chaque dest, on reçoie l'information
            di.recevoir(informationEmise);
        }
    }
    
}
