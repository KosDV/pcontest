package util;

import java.util.List;

public class maine {

    public static void main(String[] args) {

        Randomize rand = new Randomize();
        
        List<Integer> photoList = rand.randomFotosList(100,25);

        System.out.println("\n\n VALORES LISTA \n\n");
        for (int i = 0; i < photoList.size(); i++) {
            System.out.println(photoList.get(i));
        }
    }

}
