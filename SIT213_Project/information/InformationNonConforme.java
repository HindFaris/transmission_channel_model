package Information;


 /**
 * Exception d'information 
 * @author hach
 */
public class InformationNonConforme extends Exception {
   
      private static final long serialVersionUID = 1917L;
   
       /**
     *
     */
    public InformationNonConforme() {
         super();
      }
   
   
       /**
     *
     * @param motif
     */
    public InformationNonConforme(String motif) {
         super(motif);
      }
   
   
   }