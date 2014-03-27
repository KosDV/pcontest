package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomize {
    public int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        Integer randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public List<Integer> randomFotosList(int numFotosTotal, int numFotosObtain) {

        Integer rand;
        List<Integer> photoList = new ArrayList<Integer>();

        Randomize ran = new Randomize();

        for (int i = 0; i < numFotosObtain; i++) {
            rand = ran.randInt(0, numFotosTotal - 1);
            photoList.add(rand);

            for (int j = 0; j < (photoList.size() - 1); j++) {
                if (photoList.get(j) == rand) {
                    photoList.remove(j);
                    i--;
                    break;
                }
            }
        }
        return photoList;
    }
}
