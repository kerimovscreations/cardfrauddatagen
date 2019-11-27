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
            File merchantData = new File(fileName);

            if (merchantData.exists()) {
                merchantData.delete();
            }

            merchantData.createNewFile();

            FileWriter csvWriter = new FileWriter(fileName);

            csvWriter.append("merchantId:ID(Merchants),");
            csvWriter.append("Name,");
            csvWriter.append("Email,");
            csvWriter.append("Domain,");
            csvWriter.append("VATNumber");
            csvWriter.append("\n");

            for (int i = 400000; i < 450000; i++) {
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
