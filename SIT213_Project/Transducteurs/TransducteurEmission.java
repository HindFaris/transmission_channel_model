/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transducteurs;

import Destination.DestinationInterface;
import Information.Information;
import Information.InformationNonConforme;
import Transmetteur.Transmetteur;

/**
 *
 * @author manfred
 */
public class TransducteurEmission extends Transmetteur<Boolean, Boolean> {

    @Override
    public void recevoir(Information information) throws InformationNonConforme {
        this.informationRecue = new Information<>();
        this.informationEmise = new Information<>();
        
        if(information == null)
            throw new InformationNonConforme("Erreur : la chaine en entree du transducteur d'emission est null.");
        
        for(int i=0 ; i < information.nbElements() ; i++){
            if(information.iemeElement(i) == null){
                throw new InformationNonConforme("Erreur : Une partie de la"
                        + " chaine en entree du transducteur d'emission est null.");
            }else{
                this.informationRecue.add((boolean)information.iemeElement(i));
            }
        }
        
        this.emettre();
    }

    @Override
    public void emettre() throws InformationNonConforme {
        
        for(int i=0 ; i<this.informationRecue.nbElements() ; i++){
            if(this.informationRecue.iemeElement(i) == true){
                this.informationEmise.add(true);
                this.informationEmise.add(false);
                this.informationEmise.add(true);
            }else if(this.informationRecue.iemeElement(i) == false){
                this.informationEmise.add(false);
                this.informationEmise.add(true);
                this.informationEmise.add(false);
            }
        }
        
        // Pour chaque destination connectée
        for(DestinationInterface<Boolean> d: destinationsConnectees){
            d.recevoir(informationEmise);
        }
    }
    
}
