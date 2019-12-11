import net.andreinc.mockneat.MockNeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class GoodReviewsDataGen {

    private boolean isDatasetLarge = false;

    public GoodReviewsDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "goodreviews.csv";
            File reviewsFile = new File(dirName, fileName);

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

            int datasetSize = Constants.SMALL_REVIEWS;

            if (this.isDatasetLarge) {
                datasetSize = Constants.LARGE_REVIEWS;
            }

            for (int i = 0; i < datasetSize; i++) {
                csvWriter.append(String.format("%d,%d,%d,%d,REVIEW_IN\n",
                        PersonDataGen.getRandomUserId(this.isDatasetLarge),
                        GoodDataGen.getRandomGoodId(this.isDatasetLarge),
                        getRandomRating(),
                        Constants.getRandomTimestamp()
                ));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomRating() {
        return MockNeat.threadLocal().probabilites(Integer.class)
                .add(0.7, ints().range(4, 6))
                .add(0.3, ints().range(1, 4))
                .get();
    }
}
