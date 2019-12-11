import net.andreinc.mockneat.MockNeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class TransactionDataGen {

    private boolean isDatasetLarge = false;

    public TransactionDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "transactions.csv";
            File file = new File(dirName, fileName);

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            FileWriter csvWriter = new FileWriter(String.format("%s/%s", dirName, fileName));

            //:START_ID,:END_ID,Amount,Timestamp
            csvWriter.append(":START_ID(Users),");
            csvWriter.append(":END_ID(Users),");
            csvWriter.append("Amount:int,");
            csvWriter.append("Timestamp:long,");
            csvWriter.append(":TYPE");
            csvWriter.append("\n");

            int datasetSize = Constants.SMALL_TRANSACTION;

            if (this.isDatasetLarge) {
                datasetSize = Constants.LARGE_TRANSACTION;
            }

            for (int i = 0; i < datasetSize; i++) {
                csvWriter.append(String.format("%d,%d,%d,%d,TRANSACTION\n",
                        PersonDataGen.getRandomUserId(this.isDatasetLarge),
                        PersonDataGen.getRandomUserId(this.isDatasetLarge),
                        getRandomAmount(),
                        Constants.getRandomTimestamp()
                ));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomAmount() {
        return MockNeat.threadLocal().probabilites(Integer.class)
                .add(0.5, ints().range(0, 100))
                .add(0.3, ints().range(100, 300))
                .add(0.2, ints().range(300, 800))
                .get();
    }
}
