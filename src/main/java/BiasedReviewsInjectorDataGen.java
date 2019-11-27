import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

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

            String fileName2 = "src/main/export/biasedgoodownership.csv";
            File ownershipFile = new File(fileName2);

            if (ownershipFile.exists()) {
                ownershipFile.delete();
            }

            ownershipFile.createNewFile();

            FileWriter csvWriter2 = new FileWriter(fileName2);

            csvWriter2.append(":START_ID(Goods),");
            csvWriter2.append(":END_ID(Merchants),");
            csvWriter2.append(":TYPE");
            csvWriter2.append("\n");

            for (int i = 0; i < 50; i++) {
                int merchantId = getRandomMerchantId();
                int randomGoodCount = ints().range(5, 10).get();

                int[] goodIds = new int[randomGoodCount];

                for (int j = 0; j < randomGoodCount; j++) {
                    int goodId = getRandomGoodId();

                    goodIds[j] = goodId;

                    csvWriter2.append(String.format("%d,%d,OWNERSHIP\n",
                            goodId,
                            merchantId
                    ));
                }

                int randomUserCount = ints().range(5, 10).get();

                for (int j = 0; j < randomUserCount; j++) {
                    int userId = getRandomUserId();

                    for (int goodId : goodIds) {
                        csvWriter.append(String.format("%d,%d,%d,%d,REVIEW_IN\n",
                                userId,
                                goodId,
                                5,
                                getRandomTimestamp()
                        ));
                    }
                }
            }

            csvWriter.flush();
            csvWriter.close();
            csvWriter2.flush();
            csvWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomUserId() {
        return ints().range(1, 200001).get();
    }

    private static int getRandomGoodId() {
        return ints().range(200000, 400001).get();
    }

    private static int getRandomMerchantId() {
        return ints().range(400000, 450001).get();
    }

    private static long getRandomTimestamp() {
        long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-06-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand.getTime();
    }
}
