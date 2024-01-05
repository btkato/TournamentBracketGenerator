package persistence;

import model.Competitor;
import model.Tournament;
import model.CompetitorList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// The following was code was modified from the JsonReaderTest class in the JsonSerializationDemo project:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java
public class JsonReaderTest extends JsonTest {

    private CompetitorList comp;
    private CompetitorList compTwo;

    @BeforeEach
    void runBefore() {
        comp = new CompetitorList();
        compTwo = new CompetitorList();
        comp.addCompetitor(new Competitor("ann"));
        comp.addCompetitor(new Competitor("john"));
        comp.addCompetitor(new Competitor("jim"));
        comp.addCompetitor(new Competitor("foo"));
        comp.addCompetitor(new Competitor("bar"));
        comp.addCompetitor(new Competitor("greg"));
        comp.addCompetitor(new Competitor("gary"));
        comp.addCompetitor(new Competitor("jeff"));
        compTwo.addCompetitor(new Competitor("jeff"));
        compTwo.addCompetitor(new Competitor("gary"));
        compTwo.addCompetitor(new Competitor("greg"));
        compTwo.addCompetitor(new Competitor("bar"));
        compTwo.addCompetitor(new Competitor("foo"));
        compTwo.addCompetitor(new Competitor("jim"));
        compTwo.addCompetitor(new Competitor("john"));
        compTwo.addCompetitor(new Competitor("ann"));
    }

    @Test
    void testReadingNonExistentTournament() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Tournament tournament = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyTournament() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTournament.json");
        try {
            Tournament tournament = reader.read();
            assertEquals("Tournament", tournament.getName());
            assertEquals(0, tournament.getCompetitorLists().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testGeneralTournament() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTournament.json");
        try {
            Tournament tournament = new Tournament("Tournament");
            tournament.addCompetitorList(comp);
            tournament.addCompetitorList(compTwo);
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralTournament.json");
            writer.openTournament();
            writer.writeTournament(tournament);
            writer.closeTournament();

            tournament = reader.read();
            assertEquals("Tournament", tournament.getName());
            ArrayList<CompetitorList> competitorList = tournament.getCompetitorLists();
            assertEquals(2, tournament.getCompetitorLists().size());
            checkDivision(tournament.getCompetitorLists(), competitorList, 0);
            checkDivision(tournament.getCompetitorLists(), competitorList, 1);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testJsonWriter() {
        try {
            Tournament tournament = new Tournament("Tournament");
            tournament.addCompetitorList(comp);
            tournament.addCompetitorList(compTwo);
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralTournament.json");
            writer.openTournament();
            writer.writeTournament(tournament);
            writer.closeTournament();

            JsonReader reader = new JsonReader("./data/testReaderGeneralTournament.json");
            tournament = reader.read();
            assertEquals("Tournament", tournament.getName());
            ArrayList<CompetitorList> competitorList = tournament.getCompetitorLists();
            assertEquals(2, tournament.getCompetitorLists().size());
            checkDivision(tournament.getCompetitorLists(), competitorList, 0);
            checkDivision(tournament.getCompetitorLists(), competitorList, 1);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
        }
}

