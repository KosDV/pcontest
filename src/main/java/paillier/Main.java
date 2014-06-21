package paillier;

public class Main {

    public static void main(String[] args) {
	try {
	    Paillier paillier = new Paillier(1024);
	    paillier.printValues();

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
