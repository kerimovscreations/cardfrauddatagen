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

            if (reviewsFile.exists()) {
                reviewsFile.delete();
            }

            reviewsFile.createNewFile();

            FileWriter csvWriter = new FileWriter(fileName);

            csvWriter.append(":START_ID(Users),");
            csvWriter.append(":END_ID(Goods),");
            csvWriter.append("Rating:int,");
            csvWriter.append("Timestamp:long,");
            csvWriter.append(":TYPE");
            csvWriter.append("\n");

            HashMap<Integer, int[]> productsMap = new HashMap<Integer, int[]>();

            productsMap.put(97, new int[]{99700, 86088, 83840, 83812, 73626, 70122,
                    67797, 44057, 26921, 11561});
            productsMap.put(139, new int[]{85361, 69842, 57316, 55767,
                    46351, 41829, 37573, 28405, 23352, 15570, 12343, 11033});
            productsMap.put(142, new int[]{96292, 90098, 83894, 81213, 74757, 71178,
                    45215, 44429, 28628, 17184, 9844, 4993});
            productsMap.put(533, new int[]{98728, 95878, 88130, 44225, 37857,
                    37410, 37164, 36789, 13472, 7436});
            productsMap.put(694, new int[]{99314, 90618, 71887, 69696, 65826, 36289,
                    35820, 14339, 12595, 8927});
            productsMap.put(1041, new int[]{99466, 96425, 94310, 90180, 59423, 56134,
                    54895, 48424, 46353, 46293, 42302, 39321, 32348, 30712, 23212, 19942});
            productsMap.put(1209, new int[]{98102, 95917, 82918, 81425, 78819, 61777,
                    59874, 58611, 50162, 47571, 44995, 37178, 26866, 21528, 15940, 6962, 1429});
            productsMap.put(1271, new int[]{90981, 90964, 90036, 88231, 87518, 81135,
                    72338, 67478, 40183, 29061, 18696, 12695});
            productsMap.put(1687, new int[]{98204, 84305, 80620, 79084, 58250,
                    34964, 25942, 19114, 4147});
            productsMap.put(1856, new int[]{94617, 90230, 83109, 79636, 76518, 68152,
                    34043, 33396, 23427});

            Iterator mapIterator = productsMap.keySet().iterator();

            while (mapIterator.hasNext()) {
                int[] products = productsMap.get(mapIterator.next());
                int userId = getRandomUserId();
                int randomReviewCount = ints().range(5, products.length).get();

                for (int i = 0; i < randomReviewCount; i++) {
                    csvWriter.append(String.format("%d,%d,%d,%d,REVIEW_IN\n",
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
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand.getTime();
    }
}
