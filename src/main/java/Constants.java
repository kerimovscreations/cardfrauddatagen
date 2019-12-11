import java.sql.Timestamp;

public class Constants {

    // SMALL

    public static int SMALL_USER_SIZE = 20000;
    public static int SMALL_MERCHANT_SIZE = 10000;
    public static int SMALL_GOODS_SIZE = 20000;

    public static int SMALL_TRANSACTION = 100000;
    public static int SMALL_TRANSACTION_SMURFING = 100;
    public static int SMALL_TRANSACTION_CYCLIC = 100;
    public static int SMALL_CONNECTIONS = 10000;
    public static int SMALL_REVIEWS = 10000;
    public static int SMALL_OWNERSHIP_BIASED = 20;

    // LARGE

    public static int LARGE_USER_SIZE = 200000;
    public static int LARGE_MERCHANT_SIZE = 100000;
    public static int LARGE_GOODS_SIZE = 200000;

    public static int LARGE_TRANSACTION = 1000000;
    public static int LARGE_TRANSACTION_SMURFING = 200;
    public static int LARGE_TRANSACTION_CYCLIC = 200;
    public static int LARGE_CONNECTIONS = 100000;
    public static int LARGE_REVIEWS = 20000;
    public static int LARGE_OWNERSHIP_BIASED = 50;

    public static long getRandomTimestamp() {
        long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-06-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
        return rand.getTime();
    }

    public static String folderName(boolean isLargeDataset) {
        if (isLargeDataset)
            return "export/large";
        else
            return "export/small";
    }
}
