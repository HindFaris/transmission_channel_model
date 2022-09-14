/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Source;

import Destination.*;
import Information.Information;
import Information.InformationNonConforme;

/**
 * 
 * Une source fixe permet de générer une information et de l'émettre
 * L'information est générée à partir d'une chaine de caractère de 0 et de 1 
 * ou est générée de manière aléatoire d'une longueur spécifiée en paramètre dans le constructeur.
 *
 * @author chauquie
 */
public class SourceFixe extends Source <Boolean>{

    private boolean infoValide = true;
    
    /**
     * Constructeur prenant une chaine de caractère, séquence de 0 et de 1
     * 
     * @param chaine
     */
    public SourceFixe(String chaine){
        super();
        
        informationGeneree = new Information<Boolean>();
        
        
        String car;
        for(int i=0 ; i < chaine.length() && infoValide ; i++)
        {
            car = chaine.substring(i, i+1);
            switch(car)
            {
                case "1":
                    informationGeneree.add(true);
                break;
                
                case "0":
                    informationGeneree.add(false);
                break;
                
                default:
                    infoValide = false;
                break; 
            }
        }
    }
    
    /**
     * Constructeur générant une séquence aléatoire de 0 et de 1 d'une longueur spécifiée en paramètre
     *
     * @param nbBits
     */
    public SourceFixe(int nbBits){
        super();
        
        informationGeneree = new Information<Boolean>();
        
        for(int i=0 ; i < nbBits ; i++)
            informationGeneree.add( (int)(Math.random()*2) == 1 ? true : false );
    }
    
    /**
     *
     * Emet la séquence générée par le constructeur
     * Renvoie InformationNonConforme si la séquence générée n'est pas valide.
     * 
     * @throw InformationNonConforme
     */
    public void emettre() throws InformationNonConforme {
        if(infoValide == false)
            throw new InformationNonConforme();
        
        informationEmise = new Information<Boolean>();
        
        // Espace pour les erreurs d'émission
        for(int i = 0 ; i < informationGeneree.nbElements() ; i++){
            informationEmise.add((Boolean)informationGeneree.iemeElement(i));
        }
        
        // parcourir les différente destinations
        for(DestinationInterface <Boolean> di : destinationsConnectees){
            // pour chaque dest, on reçoie l'information
            di.recevoir(informationEmise);
        }
        
    }
    
}
