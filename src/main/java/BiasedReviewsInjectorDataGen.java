import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class BiasedReviewsInjectorDataGen {

    public static void main(String... args) {

        try {
            String fileName = "src/main/export/biasedgoodreviews.csv";
            File reviewsFile = new File(fileName);

            if(reviewsFile.exists()) {
                reviewsFile.delete();
            }

            reviewsFile.createNewFile();

            FileWriter csvWriter = new FileWriter(fileName);

            csvWriter.append("FROM,");
            csvWriter.append("TO,");
            csvWriter.append("Rating,");
            csvWriter.append("Timestamp");
            csvWriter.append("\n");

            HashMap<Integer, int[]> productsMap= new HashMap<Integer, int[]>();

            productsMap.put(9217, new int[]{78681, 70026, 70397,
                    67218, 67212, 52610, 25215, 15638, 15359, 10872, 6023, 1677});

            productsMap.put(9219, new int[]{62906, 56141, 50627, 47360, 43412, 28203,
                    23887, 21515, 17048, 8553, 768});

            productsMap.put(9220, new int[]{69169, 65530, 55382, 48122, 40519, 38390,
                    34765, 34524, 24218, 5226});

            productsMap.put(9227, new int[]{77018, 58852, 56901, 48583, 44640, 42804,
                    30726, 22821, 22779, 16492, 37});

            productsMap.put(9231, new int[]{77432, 71335, 70340, 62713, 57593, 41844,
                    38690, 31993, 29880, 17615, 11487, 5104, 5032, 3849});


            Iterator mapIterator = productsMap.keySet().iterator();

            while (mapIterator.hasNext()) {
                int[] products = productsMap.get(mapIterator.next());
                int userId = getRandomUserId();
                int randomReviewCount = ints().range(5, products.length).get();

                for (int i = 0; i < randomReviewCount; i++) {
                    csvWriter.append(String.format("%d,%d,%d,%d\n",
                            userId,
                            products[i],
                            5,
                            getRandomTimestamp()
                    ));
                }
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomUserId() {
        return ints().range(1, 20001).get();
    }

    private static long getRandomTimestamp() {
        long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-06-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
        return rand.getTime();
    }
}
