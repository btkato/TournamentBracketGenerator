package persistence;

import model.Competitor;
import model.CompetitorList;
import model.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// The following was code was modified from the JsonWriterTest class in the JsonSerializationDemo project:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java
public class JsonWriterTest extends JsonTest {

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
    void testWriterInvalidTournamentFile() {
        try {
            Tournament tournament = new Tournament("Invalid");
            JsonWriter writer = new JsonWriter("./data/my\0invalid:filename.json");
            writer.openTournament();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTournament() {
        try {
            Tournament tournament = new Tournament("New Tournament");
            JsonWriter write = new JsonWriter("./data/testWriterEmptyTournament.json");
            write.openTournament();
            write.writeTournament(tournament);
            write.closeTournament();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTournament.json");
            tournament = reader.read();
            assertEquals("New Tournament", tournament.getName());
            assertEquals(0, tournament.getCompetitorLists().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTournament() {
        try {
            Tournament tournament = new Tournament("Tournament");
            tournament.addCompetitorList(comp);
            tournament.addCompetitorList(compTwo);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTournament.json");
            writer.openTournament();
            writer.writeTournament(tournament);
            writer.closeTournament();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTournament.json");
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
