import net.andreinc.mockneat.MockNeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class CycleInjectorDataGen {

    public static void main(String... args) {

        try {

            // transactions

            String fileName = "src/main/export/cycleTransactions.csv";
            File cycleTranscations = new File(fileName);

            if (cycleTranscations.exists()) {
                cycleTranscations.delete();
            }

            cycleTranscations.createNewFile();

            FileWriter csvWriterTransaction = new FileWriter(fileName);

            csvWriterTransaction.append("FROM,");
            csvWriterTransaction.append("TO,");
            csvWriterTransaction.append("Amount,");
            csvWriterTransaction.append("Timestamp");
            csvWriterTransaction.append("\n");

            // connections
            String fileName2 = "src/main/export/cycleTransactionsConnections.csv";
            File cycleTranscationsConnections = new File(fileName2);

            if (cycleTranscationsConnections.exists()) {
                cycleTranscationsConnections.delete();
            }

            cycleTranscationsConnections.createNewFile();

            FileWriter csvWriterConnections = new FileWriter(fileName2);

            csvWriterConnections.append("RIGHT_ID,");
            csvWriterConnections.append("LEFT_ID,");
            csvWriterConnections.append("Timestamp");
            csvWriterConnections.append("\n");

            for (int i = 0; i < 20; i++) {
                int senderId = getRandomId();
                int receivedId = getRandomId();

                int lastMiddleMan = getRandomId();

                csvWriterTransaction.append(String.format("%d,%d,%d,%d\n",
                        senderId,
                        lastMiddleMan,
                        getRandomAmount(),
                        getRandomTimestamp()
                ));

                int randomMiddleMenSize = ints()
                        .range(2, 5)
                        .get();

                for (int j = 0; j < randomMiddleMenSize; j++) {
                    int newMiddleMan = getRandomId();
                    csvWriterTransaction.append(String.format("%d,%d,%d,%d\n",
                            lastMiddleMan,
                            newMiddleMan,
                            getRandomAmount(),
                            getRandomTimestamp()
                    ));

                    lastMiddleMan = newMiddleMan;
                }

                csvWriterTransaction.append(String.format("%d,%d,%d,%d\n",
                        lastMiddleMan,
                        receivedId,
                        getRandomAmount(),
                        getRandomTimestamp()
                ));

                csvWriterConnections.append(String.format("%d,%d,%d\n",
                        senderId,
                        receivedId,
                        getRandomTimestamp()
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

    private static int getRandomId() {
        return ints().range(1, 20001).get();
    }

    private static int getRandomAmount() {
        return MockNeat.threadLocal().probabilites(Integer.class)
                .add(0.5, ints().range(0, 100))
                .add(0.3, ints().range(100, 300))
                .add(0.2, ints().range(300, 500))
                .get();
    }

    private static long getRandomTimestamp() {
        long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-06-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand.getTime();
    }
}
