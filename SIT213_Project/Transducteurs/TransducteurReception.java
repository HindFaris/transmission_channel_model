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
public class TransducteurReception extends Transmetteur<Boolean, Boolean>{
    
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
        
        boolean bit1 ;
        boolean bit2 ;
        boolean bit3 ;
        
        boolean[] info = new boolean[3] ;
        boolean[] pattern1 = {true, false, true} ;
        boolean[] pattern0 = {false, true, false} ;
        
        for(int i=0 ; i<this.informationRecue.nbElements()-2 ; i=i+3){
            
            bit1 = this.informationRecue.iemeElement(i);
            bit2 = this.informationRecue.iemeElement(i+1);
            bit3 = this.informationRecue.iemeElement(i+2);
            
            
            if(bit1 == false && bit2 == true && bit3 == false){ // cas 010 > 0
                this.informationEmise.add(false);
            }else if(bit1 == true && bit2 == false && bit3 == true){ // cas 101 > 1
                this.informationEmise.add(true);
            }else{
                info[0] = bit1 ;
                info[1] = bit2 ;
                info[2] = bit3 ;
                
                int resCompareP0 = compareInformation3Bits(info, pattern0);
                int resCompareP1 = compareInformation3Bits(info, pattern1);
                
                if(resCompareP0 > resCompareP1){
                    this.informationEmise.add(false);
                }else if(resCompareP1 > resCompareP0){
                    this.informationEmise.add(true);
                }else{
                    this.informationEmise.add(false);
                }
            }
        }
        
        // Pour chaque destination connectée
        for(DestinationInterface<Boolean> d: destinationsConnectees){
            d.recevoir(informationEmise);
        }
    }
    
    private int compareInformation3Bits(boolean[] info, boolean[] pattern){
        int nbSimilitudes = 0 ;
        int taille = info.length ;
        for(int i=0 ; i < taille ; i++){
            if(info[i] == pattern[i]){
                nbSimilitudes++ ;
            }
        }
        return nbSimilitudes ;
    }
}