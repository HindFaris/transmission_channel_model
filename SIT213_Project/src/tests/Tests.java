package tests;


/**
 * Tests helps to convey information about performed tests
 * @author Cudennec Gael
 *
 */
public class Tests {
	
	private int nbTests;  // Total number of tests that were run
	private int nbErrors; // Total number of errors that were detected while running these tests
	
	/**
	 * 
	 * @param nbTests Total number of tests that were run.
	 * @param nbErrors Total number of errors that were detected while running these tests. 
	 * @throws NotTestsException if nbTests and/or nbErrors are not complying with the following constraints :	nbTests and nbErrors should be positive, nbTests should be greater or equal to nbErrors
	 */
	public Tests (int nbTests, int nbErrors) throws Exception {
		if ((nbTests<0) || (nbErrors <0) || (nbTests < nbErrors)) throw new Exception("The test values are not correct");
		this.nbTests = nbTests;
		this.nbErrors = nbErrors;
	}
	
	/**
	 * Get a String representation of a Test
	 */
	public String toString (){
		String s = "[ Nb of performed tests : " + nbTests + " / nb of detected error(s) : "+ nbErrors + " ]";
		return s;
	}
	
	/**
	 * Add to the current Tests the results contained in an other Tests 
	 * @param other another Tests 
	 */
	public void add (Tests other){
		this.nbTests += other.nbTests;
		this.nbErrors += other.nbErrors;
	}
	
	/**
	 * Two Tests are equals if all their attributes are equals.
	 */
	public boolean equals (Object o){
		if (o instanceof Tests){
			Tests tr = (Tests) o;
			return ((this.nbTests == tr.nbTests) && (this.nbErrors == tr.nbErrors));
		}
		return false;
	}

	

	public static Tests TestReport(){
		
		int nbTests = 0; // total number of performed tests 
		int nbErrors = 0; // total number of failed tests
		//On peut faire un truc qui compte les erreurs aussi
		
		System.out.println("Beginning of a new Test Report...  ");
		try {
			
			System.out.println("Testing each class individually before testing the whole process : \n");
			
			System.out.println("Testing SourceFixe \n");
			nbTests++;
			String Binary = new String("0111000111");
			SourceFixeTest T0 = new SourceFixeTest(Binary);
			if(true) { //Ici on mets les conditions du ou des tests que l'on veut mener, tu peux mettre des assertEquals aussi.
				nbErrors++;
				System.out.println("Err : XXXX");
			}
			
			System.out.println("Testing SourceAleatoire \n");
			nbTests++;
			int nBitMess = 23;
			int seed = 10;
			SourceAleatoireTest T1 = new SourceAleatoireTest(nBitMess,seed);
			if(true) {
				nbErrors++;
				System.out.println("Err : XXXX");
			}
	
			System.out.println("Testing Emetteur \n");
			nbTests++;
			System.out.println("Testing with a NRZ Signal \n");
			String typeEmetteur = "NRZ";
			int nbEchantillons = 10000;
			float min = -5;
			float max = 5;
			float SNRParBit = 0;
			boolean bruitActif = true;
			EmetteurTest T2_0 = new EmetteurTest(typeEmetteur, nbEchantillons,min,max, SNRParBit, bruitActif);
			if(true) {
				nbErrors++;
				System.out.println("Err : XXXX");
			}
			
			System.out.println("Testing with a NRZT Signal \n");
			typeEmetteur = "NRZT";
			EmetteurTest T2_1 = new EmetteurTest(typeEmetteur, nbEchantillons,min,max,SNRParBit, bruitActif);
			if(true) {
				nbErrors++;
				System.out.println("Err : XXXX");
			}
			
			System.out.println("Testing with a RZ Signal \n");
			typeEmetteur = "RZ";
			EmetteurTest T2_2 = new EmetteurTest(typeEmetteur, nbEchantillons,min,max,SNRParBit, bruitActif);
			if(true) {
				nbErrors++;
				System.out.println("Err : XXXX");
			}
			
			System.out.println("Testing DestinationFinale \n");
			nbTests++;
			DestinationFinaleTest T3 = new DestinationFinaleTest(Binary);
			if(true) {
				nbErrors++;
				System.out.println("Err : XXXX");
			}
	
		}
		
		
		
		
		catch (Exception e) {
			System.out.println("Err : Unexpected exception : " + e);
			e.printStackTrace();
			System.exit(1);
		}
		// Print a summary of the tests and return test results
		try{
			Tests tr = new Tests(nbTests, nbErrors);	
			System.out.println("InitTest : " + tr);
			return tr;	
		}
		catch (Exception e){ //This shouldn't happen
			System.out.println("Unexpected error in Tests code - Can't return valuable test results");
			return null;
			}
	}
	
	/**
	 * Launches test()
	 * @param args not used
	 */
	public static void main(String[] args) {
		TestReport();
	}

}

