/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateurSignal;

import inf155.Convolution;

/**
 * Classe contenant les méthodes de codage et de décodage d'un signal RZ trapézoidal.
 * @author Clément
 */
public class SignalNRZT implements DestinationGenerateurInterface, SourceGenerateurInterface {

    private int nbEch;
    private float min;
    private float max;    
    
    public SignalNRZT(int nbEch, float min, float max)
    {
        this.nbEch=nbEch;
        this.max = max;
        this.min = min;
    }
    
    @Override
    public boolean decoder(float[] tableauDeFloats) throws BadEntrySignal {
        if (tableauDeFloats.length < 3) {
            throw new BadEntrySignal("Tableau de taille insuffisante");
        } else if (min > max) {
            throw new BadEntrySignal("Minimum supérieur au maximum");
        } else {
            //Cas ou l'on a 3 echantillons
            if (tableauDeFloats.length == 3){
                if (tableauDeFloats[1]> min + ((0.5) * (max - min))){
                    return true;
                }
                else{
                    return false;
                }
            }
            
            float[] signal1 = this.generer(true, true, true);
            float[] signal0 = this.generer(false, false, false);
            
            double[] signal1inv = new double[signal1.length];
            double[] signal0inv = new double[signal0.length];
            double[] signalT = new double[signal0.length];
            double[] signal0D = new double[signal0.length];
            double[] signal1D = new double[signal0.length];
            
            for(int i = 0 ; i<tableauDeFloats.length ; i++)
            {
                signal1inv[i] = signal1[tableauDeFloats.length-1-i];
                signal0inv[i] = signal0[tableauDeFloats.length-1-i];
                signalT[i] = tableauDeFloats[i];
                signal0D[i] = signal0[i];
                signal1D[i] = signal1[i];
            }
            
            Convolution conv0 = new Convolution(signal0inv, signalT);
            Convolution conv0T = new Convolution(signal0inv, signal0D);
            Convolution conv1 = new Convolution(signal1inv, signalT);
            Convolution conv1T = new Convolution(signal1inv, signal1D);
            
            conv0.convolve();
            conv1.convolve();
            conv0T.convolve();
            conv1T.convolve();
            
            double[] test0 = conv0.getOutput();
            double[] test1 = conv1.getOutput();
            double[] r1 = conv1T.getOutput();
            double[] r0 = conv0T.getOutput();
            
            
            double maxT = test1[0],
                    max0 = r0[0],
                    max1 = r1[0],
                    max0TT = test0[0];
            
            for(int i = 1 ; i<test1.length ; i++)
            {
                if(r0[i] > max0)
                    max0=r0[i];
                if(r1[i] > max1)
                    max1=r1[i];
                if(test1[i] > maxT)
                    maxT=test1[i];
                if(test0[i] > max0TT)
                    max0TT=test0[i];
            }

            double seuil = (max1+max0)/2;
            
            if((maxT<seuil && max+min!=0) || (max0TT>maxT && max+min==0))
                return false;
            else
                return true;
            /*
            //Détecte un 1 si au moins la moitié des échantillons du tableau sont supérieurs à la moitié de l'amplitude du signal
            //Détecte un 0 sinon.
            int cpt = 0;
            for (int i = 0; i < tableauDeFloats.length; i++) {
                if (tableauDeFloats[i] > min + ((0.5) * (max - min))) {
                    cpt++;
                }
            }
            if (cpt >= (tableauDeFloats.length / 2)) {
                return true;
            } else {
                return false;
            }*/
        }
    }

    @Override
    public float[] generer(boolean bit0, boolean bit1, boolean bit2) throws BadEntrySignal {
        if (nbEch < 3) {
            throw new BadEntrySignal("Nombre d'échantillons insuffisant");
        } else if (min > max) {
            throw new BadEntrySignal("Minimum supérieur au maximum");
        } else {

            //Création du tableau d'échantillons par bit
            float[] tabEchantillons = new float[nbEch];

            float pente = 0;
            float diffx;
            float diffy;
            //Calcul de la pente
                diffx = nbEch - 1;
                diffx = diffx / 3;
                diffy = max - min;
                pente = diffy / diffx;
            //Si le bit est à 1
            if (bit1) {
                

                //Calcul du premier et dernier tiers du signal
                
                //Si le bit précédent est à 0 et le bit suivant à 0
                if (!bit0) {
                    for (int i = 0; i <= ((nbEch - 1) / 3); i++) {
                        tabEchantillons[i] = (float) min + (i * pente);
                        tabEchantillons[nbEch - 1 - i] = max;
                    }
                }
                //Si le bit précédent est à 1 et le bit suivant à 1
                if (bit0) {
                    for (int i = 0; i <= ((nbEch - 1) / 3); i++) {
                        tabEchantillons[i] = max;
                        tabEchantillons[nbEch - 1 - i] = max;
                    }
                }
                
                
                //Remplissage du tiers du milieu pour un bit =1
                for (int i = ((nbEch - 1) / 3) + 1; i <= 2 * (nbEch - 1) / 3; i++) {
                    tabEchantillons[i] = max;
                }
            } else {
                //Si le bit est à 0
                
                //si le bit précédent est à 0 et le bit suivant à 1
                if (!bit0) {
                    for (int i = 0; i <= ((nbEch - 1) / 3); i++) {
                        tabEchantillons[i] = min;
                        tabEchantillons[nbEch - 1 - i] = min;
                    }
                }
                //Si le bit précédent est à 1 et le bit suivant à 0
                if (bit0) {
                    for (int i = 0; i <= ((nbEch - 1) / 3); i++) {
                        tabEchantillons[i] = (float) max - (i * pente);
                        tabEchantillons[nbEch - 1 - i] = min;
                    }
                }
                
                //Remplissage du tiers du milieu pour un bit =0
                for (int i = ((nbEch - 1) / 3) + 1; i <= 2 * (nbEch - 1) / 3; i++) {
                    tabEchantillons[i] = min;
                }
            }


            return tabEchantillons;
        }
    }
}
