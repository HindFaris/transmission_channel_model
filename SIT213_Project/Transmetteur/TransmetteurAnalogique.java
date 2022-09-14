/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transmetteur;

import Destination.DestinationInterface;
import Information.Information;
import Information.InformationNonConforme;

/**
 *
 * @author manfred
 */
public class TransmetteurAnalogique extends Transmetteur<Float, Float>{
            
    /**
     * Recoie une information
     */
    public void recevoir(Information information) throws InformationNonConforme {
        informationRecue = new Information<Float>();
        
        for(int i = 0 ; i < information.nbElements() ; i++){
            informationRecue.add((Float)information.iemeElement(i));
        }
        this.emettre();
    }

    /**
     * Emet une information sans la modifier
     */
    public void emettre() throws InformationNonConforme {
        informationEmise = new Information<Float>();
        // Dans un premier cas, informtion recue = information émise ;
        // Ce ne sera plus le cas dansl es prochaines itérations du programme, d'où la séparation des boucles
        for(int i = 0 ; i < informationRecue.nbElements() ; i++){
            informationEmise.add((Float)informationRecue.iemeElement(i));
        }
        
        // parcourir les différente destinations
        for(DestinationInterface <Float> di : destinationsConnectees){
            // pour chaque dest, on reçoie l'information
            di.recevoir(informationEmise);
        }
    }
    
}
