import net.andreinc.mockneat.MockNeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class CycleInjectorDataGen {

    private boolean isDatasetLarge = false;

    public CycleInjectorDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        try {

            // transactions
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "cycleTransactions.csv";
            File cycleTranscations = new File(dirName, fileName);

            if (cycleTranscations.exists()) {
                cycleTranscations.delete();
            }

            cycleTranscations.createNewFile();

            FileWriter csvWriterTransaction = new FileWriter(String.format("%s/%s", dirName, fileName));

            csvWriterTransaction.append(":START_ID(Users),");
            csvWriterTransaction.append(":END_ID(Users),");
            csvWriterTransaction.append("Amount:int,");
            csvWriterTransaction.append("Timestamp:long,");
            csvWriterTransaction.append(":TYPE");
            csvWriterTransaction.append("\n");

            // connections
            String fileName2 = "cycleTransactionsConnections.csv";
            File cycleTranscationsConnections = new File(dirName, fileName2);

            if (cycleTranscationsConnections.exists()) {
                cycleTranscationsConnections.delete();
            }

            cycleTranscationsConnections.createNewFile();

            FileWriter csvWriterConnections = new FileWriter(String.format("%s/%s", dirName, fileName2));

            csvWriterConnections.append(":START_ID(Users),");
            csvWriterConnections.append(":END_ID(Users),");
            csvWriterConnections.append("Timestamp:long,");
            csvWriterConnections.append(":TYPE");
            csvWriterConnections.append("\n");

            int datasetSize = Constants.SMALL_TRANSACTION_CYCLIC;

            if (this.isDatasetLarge) {
                datasetSize = Constants.LARGE_TRANSACTION_CYCLIC;
            }

            for (int i = 0; i < datasetSize; i++) {
                int senderId = PersonDataGen.getRandomUserId(this.isDatasetLarge);
                int receivedId = PersonDataGen.getRandomUserId(this.isDatasetLarge);

                int lastMiddleMan = PersonDataGen.getRandomUserId(this.isDatasetLarge);

                csvWriterTransaction.append(String.format("%d,%d,%d,%d,TRANSACTION\n",
                        senderId,
                        lastMiddleMan,
                        getRandomAmount(),
                        Constants.getRandomTimestamp()
                ));

                int randomMiddleMenSize = ints()
                        .range(2, 5)
                        .get();

                for (int j = 0; j < randomMiddleMenSize; j++) {
                    int newMiddleMan = PersonDataGen.getRandomUserId(this.isDatasetLarge);
                    csvWriterTransaction.append(String.format("%d,%d,%d,%d,TRANSACTION\n",
                            lastMiddleMan,
                            newMiddleMan,
                            getRandomAmount(),
                            Constants.getRandomTimestamp()
                    ));

                    lastMiddleMan = newMiddleMan;
                }

                csvWriterTransaction.append(String.format("%d,%d,%d,%d,TRANSACTION\n",
                        lastMiddleMan,
                        receivedId,
                        getRandomAmount(),
                        Constants.getRandomTimestamp()
                ));

                csvWriterConnections.append(String.format("%d,%d,%d,CONNECTION\n",
                        senderId,
                        receivedId,
                        Constants.getRandomTimestamp()
                ));
            }

            csvWriterTransaction.flush();
            csvWriterTransaction.close();

            csvWriterConnections.flush();
            csvWriterConnections.close();
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
