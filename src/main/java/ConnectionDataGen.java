import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConnectionDataGen {

    private boolean isDatasetLarge = false;

    public ConnectionDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "connections.csv";
            File userData = new File(dirName, fileName);

            if (userData.exists()) {
                userData.delete();
            }

            userData.createNewFile();

            FileWriter csvWriter = new FileWriter(String.format("%s/%s", dirName, fileName));

            csvWriter.append(":START_ID(Users),");
            csvWriter.append(":END_ID(Users),");
            csvWriter.append("Timestamp,");
            csvWriter.append(":TYPE");
            csvWriter.append("\n");

            int datasetSize = Constants.SMALL_CONNECTIONS;

            if (this.isDatasetLarge) {
                datasetSize = Constants.LARGE_CONNECTIONS;
            }

            for (int i = 0; i < datasetSize; i++) {
                csvWriter.append(String.format("%d,%d,%d,CONNECTED_TO\n",
                        PersonDataGen.getRandomUserId(this.isDatasetLarge),
                        PersonDataGen.getRandomUserId(this.isDatasetLarge),
                        Constants.getRandomTimestamp()
                ));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
