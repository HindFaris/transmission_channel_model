import GenerateurBruit.BruitGaussien;
import GenerateurSignal.BadEntrySignal;
import Information.Information;
import Vues.VueCourbe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe dont le but est de verifier le caractere gaussien d'un bruit
 * @author hach
 */
public class visualisateurBruit {
    public static void main(String[] args) throws BadEntrySignal {
        int nb_echan = 100000;
        int pas = 30;
        
        Information<Float> info =new Information<>();
        for(int i=0 ; i< nb_echan ; i++)
            info.add(0.0f);
        
        BruitGaussien bruit = new BruitGaussien();
        bruit.setPuissanceBruit(100);
        Information<Float> infoBruit = bruit.deformerInformation(info);
        
        float min=infoBruit.iemeElement(0), max=infoBruit.iemeElement(0);
        for(int i=0 ; i<infoBruit.nbElements() ; i++)
        {
            if(infoBruit.iemeElement(i)>max)
                max=infoBruit.iemeElement(i);
            if(infoBruit.iemeElement(i)<min)
                min=infoBruit.iemeElement(i);
        }
        
        max=(int)Math.floor(max)+1;
        min=(int)Math.floor(min);
        
        max=max>min ? max : Math.abs(min);
                
        float deciles[] = new float[pas];
        
        
        for(int i=0 ; i<infoBruit.nbElements() ; i++)
        {
            int interm = (int)Math.floor((infoBruit.iemeElement(i)+max)/(max/(pas/2)));
            deciles[interm]++;
        }
        
        VueCourbe v = new VueCourbe(deciles, "Le bruit blanc gaussien");
    }
}
