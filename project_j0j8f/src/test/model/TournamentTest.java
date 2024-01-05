package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest extends JsonTest {

    private Tournament tournament;
    private CompetitorList divA;
    private CompetitorList divB;

    @BeforeEach
    void runBefore() {
        tournament = new Tournament("Tournament");
        divA = new CompetitorList();
        divB = new CompetitorList();
        divA.addCompetitor(new Competitor("joe"));
        divA.addCompetitor(new Competitor("jim"));
        divA.addCompetitor(new Competitor("john"));
        divA.addCompetitor(new Competitor("dan"));
        divA.addCompetitor(new Competitor("ethan"));
        divA.addCompetitor(new Competitor("francis"));
        divA.addCompetitor(new Competitor("gary"));
        divA.addCompetitor(new Competitor("harry"));
        divB.addCompetitor(new Competitor("john"));
        divB.addCompetitor(new Competitor("dan"));
        divB.addCompetitor(new Competitor("ethan"));
        divB.addCompetitor(new Competitor("francis"));
        divB.addCompetitor(new Competitor("gary"));
        divB.completeCompetitorList();
        divB.matchmakeCompetitorList();
    }

    @Test
    void testConstructor() {
        Tournament test = new Tournament("Test");
        assertEquals(0, test.getCompetitorLists().size());
        assertEquals("Test", test.getName());

    }

    @Test
    void testAddingDivision() {
        tournament.addCompetitorList(divA);
        assertEquals(1, tournament.getCompetitorLists().size());
    }

    @Test
    void testRemovingDivision() {
        tournament = new Tournament("A");
        tournament.addCompetitorList(divA);
        tournament.addCompetitorList(divB);
        assertEquals(2, tournament.getCompetitorLists().size());
        tournament.removeCompetitorList();
        assertEquals(1, tournament.getCompetitorLists().size());
        assertEquals(8, tournament.getCompetitorLists().get(0).getCompetitorList().size());
        CompetitorList competitorList = tournament.getCompetitorLists().get(0);
        assertEquals("joe", competitorList.getCompetitorList().get(0).getName());
    }

    @Test
    void testAddingMultipleDivisions() {
        tournament.addCompetitorList(divA);
        tournament.addCompetitorList(divB);
        tournament.addCompetitorList(divA);
        assertEquals(3, tournament.getCompetitorLists().size());
    }

    @Test
    void testAddingAndRemovingMultipleDivisions() {
        tournament.addCompetitorList(divA);
        tournament.addCompetitorList(divA);
        tournament.removeCompetitorList();
        assertEquals(1, tournament.getCompetitorLists().size());
        assertEquals(divA, tournament.getCompetitorLists().get(0));
        tournament.addCompetitorList(divB);
        tournament.addCompetitorList(divA);
        tournament.addCompetitorList(divB);
        assertEquals(4, tournament.getCompetitorLists().size());
        tournament.removeCompetitorList();
        tournament.removeCompetitorList();
        assertEquals(2, tournament.getCompetitorLists().size());
        assertEquals(divA, tournament.getCompetitorLists().get(0));
        assertEquals(divB, tournament.getCompetitorLists().get(1));

    }

    @Test
    void testGettingDivisionAndChanging() {
        tournament.addCompetitorList(divA);
        tournament.getCompetitorLists().get(0).removeCompetitor();
        tournament.getCompetitorLists().get(0).addCompetitor(new Competitor("tom"));
        Competitor tom = tournament.getCompetitorLists().get(0).getCompetitorList().get(7);
        assertEquals("tom", tom.getName());
    }

    @Test
    void testToJson() {
        try {
            Tournament tournament = new Tournament("Tournament");
            tournament.addCompetitorList(divA);
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralTournament.json");
            writer.openTournament();
            writer.writeTournament(tournament);
            writer.closeTournament();

            JsonReader reader = new JsonReader("./data/testReaderGeneralTournament.json");
            tournament = reader.read();
            assertEquals("Tournament", tournament.getName());
            ArrayList<CompetitorList> competitorList = tournament.getCompetitorLists();
            assertEquals(1, tournament.getCompetitorLists().size());
            checkDivision(tournament.getCompetitorLists(), competitorList, 0);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
