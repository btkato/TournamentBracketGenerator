package persistence;

import model.CompetitorList;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// The following was code was modified from the JsonTest class in the JsonSerializationDemo project:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonTest.java
public class JsonTest {
    protected void checkDivision(ArrayList<CompetitorList> competitorList, ArrayList<CompetitorList> fromJson, int i) {
        assertEquals(competitorList.get(i), fromJson.get(i));
    }
}
