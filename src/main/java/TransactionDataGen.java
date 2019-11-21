import net.andreinc.mockneat.MockNeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class TransactionDataGen {

    public static void main(String... args) {

        try {
            String fileName = "src/main/export/transactions.csv";
            File userData = new File(fileName);

            if(userData.exists()) {
                userData.delete();
            }

            userData.createNewFile();

            FileWriter csvWriter = new FileWriter(fileName);

            //:START_ID,:END_ID,Amount,Timestamp
            csvWriter.append(":START_ID(Users),");
            csvWriter.append(":END_ID(Users),");
            csvWriter.append("Amount:int,");
            csvWriter.append("Timestamp:long,");
            csvWriter.append(":TYPE");
            csvWriter.append("\n");

            for (int i = 0; i < 100000; i++) {
                csvWriter.append(String.format("%d,%d,%d,%d,TRANSACTION\n",
                        getRandomId(),
                        getRandomId(),
                        getRandomAmount(),
                        getRandomTimestamp()
                ));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomId() {
        return ints().range(1, 100001).get();
    }

    private static int getRandomAmount() {
        return MockNeat.threadLocal().probabilites(Integer.class)
                .add(0.5, ints().range(0, 100))
                .add(0.3, ints().range(100, 300))
                .add(0.2, ints().range(300, 800))
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
