import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.StringFormatType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static net.andreinc.mockneat.unit.types.Ints.ints;

public class GoodDataGen {

    public static void main(String... args) {

        try {
            String fileName = "src/main/export/goods.csv";
            File goodData = new File(fileName);

            if (goodData.exists()) {
                goodData.delete();
            }

            goodData.createNewFile();

            FileWriter csvWriterGood = new FileWriter(fileName);

            csvWriterGood.append("ID,");
            csvWriterGood.append("NAME,");
            csvWriterGood.append("Price");
            csvWriterGood.append("\n");

            String fileName2 = "src/main/export/goodsownership.csv";
            File goodsOwnership = new File(fileName2);

            if (goodsOwnership.exists()) {
                goodsOwnership.delete();
            }

            goodsOwnership.createNewFile();

            FileWriter csvWriterOwnership = new FileWriter(fileName2);

            csvWriterOwnership.append("FROM,");
            csvWriterOwnership.append("TO");
            csvWriterOwnership.append("\n");

            for (int i = 0; i < 80000; i++) {
                int goodId = i + 1;
                csvWriterGood.append(String.format("%d,%s,%d\n",
                        goodId,
                        getRandomName(),
                        getRandomAmount()
                ));

                csvWriterOwnership.append(String.format("%d,%d\n",
                        goodId,
                        ints().range(1, 10001).get()
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

    private static String getRandomName() {
        return MockNeat.threadLocal()
                .words().nouns().format(StringFormatType.CAPITALIZED)
                .get();
    }
}