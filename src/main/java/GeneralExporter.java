public class GeneralExporter {

    public static void main(String ...args) {

        boolean isDatasetLarge = false;

        if (args[0].equals("large")) {
            isDatasetLarge = true;
        }

        new PersonDataGen(isDatasetLarge).generate();
        new MerchantDataGen(isDatasetLarge).generate();
        new GoodDataGen(isDatasetLarge).generate();

        new BiasedReviewsInjectorDataGen(isDatasetLarge).generate();
        new ConnectionDataGen(isDatasetLarge).generate();
        new CycleInjectorDataGen(isDatasetLarge).generate();
        new GoodReviewsDataGen(isDatasetLarge).generate();
        new SmurfingInjectorDataGen(isDatasetLarge).generate();
        new TransactionDataGen(isDatasetLarge).generate();
    }
}
