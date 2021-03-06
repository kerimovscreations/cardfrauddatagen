import net.andreinc.mockneat.MockNeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class SmurfingInjectorDataGen {

    private boolean isDatasetLarge = false;

    public SmurfingInjectorDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "smurfingTransactions.csv";
            File userData = new File(dirName, fileName);

            if (userData.exists()) {
                userData.delete();
            }

            userData.createNewFile();

            FileWriter csvWriter = new FileWriter(String.format("%s/%s", dirName, fileName));

            csvWriter.append(":START_ID(Users),");
            csvWriter.append(":END_ID(Users),");
            csvWriter.append("Amount,");
            csvWriter.append("Timestamp,");
            csvWriter.append(":TYPE");
            csvWriter.append("\n");

            int datasetSize = Constants.SMALL_TRANSACTION_SMURFING;

            if (this.isDatasetLarge) {
                datasetSize = Constants.LARGE_TRANSACTION_SMURFING;
            }

            for (int i = 0; i < datasetSize; i++) {
                int senderId = PersonDataGen.getRandomUserId(this.isDatasetLarge);
                int receivedId = PersonDataGen.getRandomUserId(this.isDatasetLarge);

                int randomMiddleMenSize = ints()
                        .range(3, 10)
                        .get();

                for (int j = 0; j < randomMiddleMenSize; j++) {
                    int middleManId = PersonDataGen.getRandomUserId(this.isDatasetLarge);
                    csvWriter.append(String.format("%d,%d,%d,%d,SENT_TO\n",
                            senderId,
                            middleManId,
                            getRandomAmount(),
                            Constants.getRandomTimestamp()
                    ));

                    csvWriter.append(String.format("%d,%d,%d,%d,SENT_TO\n",
                            middleManId,
                            receivedId,
                            getRandomAmount(),
                            Constants.getRandomTimestamp()
                    ));
                }
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomAmount() {
        return MockNeat.threadLocal().probabilites(Integer.class)
                .add(0.2, ints().range(0, 100))
                .add(0.3, ints().range(100, 300))
                .add(0.5, ints().range(300, 1000))
                .get();
    }
}
