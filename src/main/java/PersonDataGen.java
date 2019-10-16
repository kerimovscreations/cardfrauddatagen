import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PersonDataGen {

    public static void main(String... args) {
        Fairy fairy = Fairy.create();
        try {
            String fileName = "users.csv";
            File userData = new File(fileName);

            if (userData.exists()) {
                userData.delete();
            }

            userData.createNewFile();

            FileWriter csvWriter = new FileWriter(fileName);

            csvWriter.append("ID");
            csvWriter.append(",");
            csvWriter.append("Name");
            csvWriter.append(",");
            csvWriter.append("Email");
            csvWriter.append(",");
            csvWriter.append("Age");
            csvWriter.append(",");
            csvWriter.append("Sex");
            csvWriter.append("\n");

            for (int i = 0; i < 20000; i++) {
                Person person = fairy.person();
                csvWriter.append(String.format("%d,%s %s,%s,%s,%s",
                        i + 1,
                        person.getFirstName(),
                        person.getLastName(),
                        person.getEmail(),
                        person.getAge(),
                        person.getSex()
                ));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
