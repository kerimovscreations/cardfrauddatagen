import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.StringFormatType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class GoodDataGen {

    private boolean isDatasetLarge = false;

    public GoodDataGen(boolean isDatasetLarge) {
        this.isDatasetLarge = isDatasetLarge;
    }

    public void generate() {

        try {
            String dirName = Constants.folderName(this.isDatasetLarge);
            String fileName = "goods.csv";
            File goodData = new File(dirName, fileName);

            if (goodData.exists()) {
                goodData.delete();
            }

            goodData.createNewFile();

            FileWriter csvWriterGood = new FileWriter(String.format("%s/%s", dirName, fileName));

            csvWriterGood.append("goodId:ID(Goods),");
            csvWriterGood.append("NAME,");
            csvWriterGood.append("Price:int");
            csvWriterGood.append("\n");

            String fileName2 = "goodsownership.csv";
            File goodsOwnership = new File(dirName, fileName2);

            if (goodsOwnership.exists()) {
                goodsOwnership.delete();
            }

            goodsOwnership.createNewFile();

            FileWriter csvWriterOwnership = new FileWriter(String.format("%s/%s", dirName, fileName2));

            csvWriterOwnership.append(":START_ID(Goods),");
            csvWriterOwnership.append(":END_ID(Merchants),");
            csvWriterOwnership.append(":TYPE");
            csvWriterOwnership.append("\n");

            int userSize = Constants.SMALL_USER_SIZE;
            int goodSize = Constants.SMALL_GOODS_SIZE;

            if (this.isDatasetLarge) {
                userSize = Constants.LARGE_USER_SIZE;
                goodSize = Constants.LARGE_GOODS_SIZE;
            }

            int datasetSize = userSize + goodSize;

            for (int i = userSize; i < datasetSize; i++) {
                int goodId = i + 1;
                csvWriterGood.append(String.format("%d,%s,%d\n",
                        goodId,
                        getRandomName(),
                        getRandomAmount()
                ));

                csvWriterOwnership.append(String.format("%d,%d,OWNERSHIP\n",
                        goodId,
                        MerchantDataGen.getRandomMerchantId(this.isDatasetLarge)
                ));
            }

            csvWriterGood.flush();
            csvWriterGood.close();

            csvWriterOwnership.flush();
            csvWriterOwnership.close();
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

    private static String getRandomName() {
        return MockNeat.threadLocal()
                .words().nouns().format(StringFormatType.CAPITALIZED)
                .get();
    }

    public static int getRandomGoodId(boolean isDatasetLarge) {
        int goodSize = Constants.SMALL_GOODS_SIZE;
        int userSize = Constants.SMALL_USER_SIZE;

        if (isDatasetLarge) {
            goodSize = Constants.LARGE_GOODS_SIZE;
            userSize = Constants.LARGE_USER_SIZE;
        }

        return ints().range(userSize + 1, userSize + goodSize).get();
    }
}
