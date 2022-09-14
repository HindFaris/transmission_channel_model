/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Destination;

import Information.Information;
import Information.InformationNonConforme;

/**
 *
 * Destination parfaite, recoit de l'information binaire (composée de booleans) sans erreur.
 * 
 * @author chauquie
 */
public class DestinationFinale extends Destination<Boolean> {

    /**
     *
     * Recoit une information et la stock dans information recue.
     * 
     * @throws InformationNonConforme 
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConforme {
        informationRecue = new Information<Boolean>();
        
        for(int i = 0 ; i < information.nbElements() ; i++){
            informationRecue.add((Boolean)information.iemeElement(i));
        }
    }
    
}
