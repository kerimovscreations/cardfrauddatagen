import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.company.Company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class MerchantDataGen {

    private boolean isDatasetLarge = false;

    public MerchantDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate(String... args) {

        Fairy fairy = Fairy.create();
        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "merchants.csv";
            File merchantData = new File(dirName, fileName);

            if (merchantData.exists()) {
                merchantData.delete();
            }

            merchantData.createNewFile();

            FileWriter csvWriter = new FileWriter(String.format("%s/%s", dirName, fileName));

            csvWriter.append("merchantId:ID(Merchants),");
            csvWriter.append("Name,");
            csvWriter.append("Email,");
            csvWriter.append("Domain,");
            csvWriter.append("VATNumber");
            csvWriter.append("\n");

            int userSize = Constants.SMALL_USER_SIZE;
            int goodSize = Constants.SMALL_GOODS_SIZE;
            int merchantSize = Constants.SMALL_MERCHANT_SIZE;

            if (this.isDatasetLarge) {
                userSize = Constants.LARGE_USER_SIZE;
                goodSize = Constants.LARGE_GOODS_SIZE;
                merchantSize = Constants.LARGE_MERCHANT_SIZE;
            }

            for (int i = userSize + goodSize; i < userSize + goodSize + merchantSize; i++) {
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

    public static int getRandomMerchantId(boolean isDatasetLarge) {
        int userSize = Constants.SMALL_USER_SIZE;
        int goodSize = Constants.SMALL_GOODS_SIZE;
        int merchantSize = Constants.SMALL_MERCHANT_SIZE;

        if (isDatasetLarge) {
            userSize = Constants.LARGE_USER_SIZE;
            goodSize = Constants.LARGE_GOODS_SIZE;
            merchantSize = Constants.LARGE_MERCHANT_SIZE;
        }

        return ints().range(userSize + goodSize + 1, userSize + goodSize + merchantSize).get();
    }
}
