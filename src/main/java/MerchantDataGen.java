import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.company.Company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MerchantDataGen {

    public static void main(String... args) {
        Fairy fairy = Fairy.create();
        try {
            String fileName = "src/main/export/merchants.csv";
            File merchandData = new File(fileName);

            if (merchandData.exists()) {
                merchandData.delete();
            }

            merchandData.createNewFile();

            FileWriter csvWriter = new FileWriter(fileName);

            csvWriter.append("ID,");
            csvWriter.append("Name,");
            csvWriter.append("Email,");
            csvWriter.append("Domain,");
            csvWriter.append("VATNumber");
            csvWriter.append("\n");

            for (int i = 0; i < 10000; i++) {
                Company company = fairy.company();
                csvWriter.append(String.format("%d,%s,%s,%s,%s\n",
                        i + 1,
                        company.getName(),
                        company.getEmail(),
                        company.getDomain(),
                        company.getVatIdentificationNumber()
                ));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}