import org.desu.answermap.AnswerMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Chapter10Tests {

    // Create AnswerMap for use in all tests
    AnswerMap SB2AnswerMap = new AnswerMap();
    String Answer1 = SB2AnswerMap.map.get("Answer1");
    String Answer2 = SB2AnswerMap.map.get("Answer2");
    String Answer3 = SB2AnswerMap.map.get("Answer3");

    // Part 1
    @Test
    void test1() {
        String fileName = "DataFiles/ThirdPartyDataCSVFiles/TopSongs5000Edited.csv";
        GenericDataReaderIntoMap ourGenericDataReaderIntoMap =
                new GenericDataReaderIntoMap(fileName);
        String myValue = ourGenericDataReaderIntoMap.keyValueLookup("Elton John");
        Assertions.assertEquals(Answer1, myValue);
    }

    @Test
    void test2() {
        String fileName = "DataFiles/ThirdPartyDataCSVFiles/TopSongs5000Edited.csv";
        GenericDataReaderIntoMap ourGenericDataReaderIntoMap =
                new GenericDataReaderIntoMap(fileName);
        String myValue = ourGenericDataReaderIntoMap.keyValueLookup("Bon Jovi");
        Assertions.assertEquals(Answer2, myValue);
    }

    @Test
    void test3() {
        String fileName = "DataFiles/ThirdPartyDataCSVFiles/TopSongs5000Edited.csv";
        GenericDataReaderIntoMap ourGenericDataReaderIntoMap =
                new GenericDataReaderIntoMap(fileName);
        String myKey = "Bon Jovy";
        //String myValue = ourGenericDataReaderIntoMap.keyValueLookup(myKey);
        //if (myValue == null) {
            String myValue = ourGenericDataReaderIntoMap.keyValueNearby(myKey);
        //}
        //  System.out.println(myValue);
        Assertions.assertEquals(Answer3, myValue);
    }


}