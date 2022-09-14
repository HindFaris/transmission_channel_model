/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateurSignal;

import inf155.Convolution;

/**
 * Classe contenant les méthodes de codage et de décodage d'un signal RZ.
 * @author Clément
 */
public class SignalRZ implements DestinationGenerateurInterface, SourceGenerateurInterface {

    private int nbEch;
    private float min;
    private float max;    
    
    public SignalRZ(int nbEch, float min, float max)
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
            float esp = (nbEch - 1);
            esp = esp / 2;
            float et = nbEch * 0.13f;
            float diff = max - min;
            //Calcule la valeur maximale de la Gaussienne pour normalisation en amplitude
            float maxGauss = calculGaussienne(esp, et, esp);
            if (bit1) {
                for (int i = 0; i < nbEch; i++) {
                    tabEchantillons[i] = calculGaussienne(esp, et, i);
                    tabEchantillons[i] = tabEchantillons[i] * diff;
                    tabEchantillons[i] = tabEchantillons[i] / maxGauss;
                    tabEchantillons[i] = min + tabEchantillons[i];
                }
            }
            if (!bit1) {
                for (int i = 0; i < nbEch; i++) {
                    tabEchantillons[i] = min;
                }
            }


            return tabEchantillons;
        }


    }

    private float calculGaussienne(float esp, float et, float x) {
        float nb = 0;
        nb = (x - esp);
        nb = nb * nb;
        nb = nb * (-1);
        nb = (nb / (2 * et * et));
        nb = (float) Math.exp(nb);
        nb = (float) (nb / (et * Math.sqrt(2 * Math.PI)));
        return nb;
    }
}
