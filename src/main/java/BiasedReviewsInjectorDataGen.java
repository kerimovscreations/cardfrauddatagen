import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class BiasedReviewsInjectorDataGen {

    private boolean isDatasetLarge = false;

    public BiasedReviewsInjectorDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "biasedgoodreviews.csv";
            File reviewsFile = new File(dirName, fileName);

            if (reviewsFile.exists()) {
                reviewsFile.delete();
            }

            reviewsFile.createNewFile();

            FileWriter csvWriter = new FileWriter(String.format("%s/%s", dirName, fileName));

            csvWriter.append(":START_ID(Users),");
            csvWriter.append(":END_ID(Goods),");
            csvWriter.append("Rating,");
            csvWriter.append("Timestamp,");
            csvWriter.append(":TYPE");
            csvWriter.append("\n");

            String fileName2 = "biasedgoodownership.csv";
            File ownershipFile = new File(dirName, fileName2);

            if (ownershipFile.exists()) {
                ownershipFile.delete();
            }

            ownershipFile.createNewFile();

            FileWriter csvWriter2 = new FileWriter(String.format("%s/%s", dirName, fileName2));

            csvWriter2.append(":START_ID(Goods),");
            csvWriter2.append(":END_ID(Merchants),");
            csvWriter2.append(":TYPE");
            csvWriter2.append("\n");

            int datasetSize = Constants.SMALL_OWNERSHIP_BIASED;

            if (this.isDatasetLarge) {
                datasetSize = Constants.LARGE_OWNERSHIP_BIASED;
            }

            for (int i = 0; i < datasetSize; i++) {
                int merchantId = MerchantDataGen.getRandomMerchantId(this.isDatasetLarge);
                int randomGoodCount = ints().range(5, 10).get();

                int[] goodIds = new int[randomGoodCount];

                for (int j = 0; j < randomGoodCount; j++) {
                    int goodId = GoodDataGen.getRandomGoodId(this.isDatasetLarge);

                    goodIds[j] = goodId;

                    csvWriter2.append(String.format("%d,%d,OWNERSHIP\n",
                            goodId,
                            merchantId
                    ));
                }

                int randomUserCount = ints().range(5, 10).get();

                for (int j = 0; j < randomUserCount; j++) {
                    int userId = PersonDataGen.getRandomUserId(this.isDatasetLarge);

                    for (int goodId : goodIds) {
                        csvWriter.append(String.format("%d,%d,%d,%d,REVIEW_IN\n",
                                userId,
                                goodId,
                                5,
                                Constants.getRandomTimestamp()
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
}
