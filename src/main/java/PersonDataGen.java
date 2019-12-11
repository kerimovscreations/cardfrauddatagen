import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class PersonDataGen {

    private boolean isDatasetLarge = false;

    public PersonDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        Fairy fairy = Fairy.create();
        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "users.csv";
            File userData = new File(dirName, fileName);

            if (userData.exists()) {
                userData.delete();
            }

            userData.createNewFile();

            FileWriter csvWriter = new FileWriter(fileName);

            csvWriter.append("userId:ID(Users),");
            csvWriter.append("Name,");
            csvWriter.append("Email,");
            csvWriter.append("Age:int,");
            csvWriter.append("Sex");
            csvWriter.append("\n");

            int datasetSize = Constants.SMALL_USER_SIZE;

            if (this.isDatasetLarge) {
                datasetSize = Constants.LARGE_USER_SIZE;
            }

            for (int i = 0; i < datasetSize; i++) {
                Person person = fairy.person();
                csvWriter.append(String.format("%d,%s %s,%s,%s,%s\n",
                        i + 1,
                        person.getFirstName(),
                        person.getLastName(),
                        person.getEmail(),
                        person.getAge(),
                        person.getSex()
                ));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getRandomUserId(boolean isDatasetLarge) {
        int userSize = Constants.SMALL_USER_SIZE;

        if (isDatasetLarge) {
            userSize = Constants.LARGE_USER_SIZE;
        }

        return ints().range(1, userSize).get();
    }
}
