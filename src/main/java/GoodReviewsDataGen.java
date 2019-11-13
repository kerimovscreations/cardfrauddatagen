import net.andreinc.mockneat.MockNeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class GoodReviewsDataGen {

    public static void main(String... args) {

        try {
            String fileName = "src/main/export/goodreviews.csv";
            File reviewsFile = new File(fileName);

            if(reviewsFile.exists()) {
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

            for (int i = 0; i < 80000; i++) {
                csvWriter.append(String.format("%d,%d,%d,%d,REVIEW_IN\n",
                        getRandomUserId(),
                        getRandomGoodId(),
                        getRandomRating(),
                        getRandomTimestamp()
                ));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomUserId() {
        return ints().range(1, 100001).get();
    }

    private static int getRandomGoodId() {
        return ints().range(1, 100001).get();
    }

    private static int getRandomRating() {
        return MockNeat.threadLocal().probabilites(Integer.class)
                .add(0.7, ints().range(4, 6))
                .add(0.3, ints().range(1, 4))
                .get();
    }

    private static long getRandomTimestamp() {
        long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-06-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
        return rand.getTime();
    }
}
