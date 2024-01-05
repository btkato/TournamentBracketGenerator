package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompetitorTest extends JsonTest {

    private Competitor joe;
    private CompetitorList comp;

    @BeforeEach
    void runBefore() {
        joe = new Competitor("Joe");
        comp = new CompetitorList();
        comp.addCompetitor(new Competitor("ann"));
        comp.addCompetitor(new Competitor("john"));
        comp.addCompetitor(new Competitor("jim"));
        comp.addCompetitor(new Competitor("foo"));
        comp.addCompetitor(new Competitor("bar"));
        comp.addCompetitor(new Competitor("greg"));
        comp.addCompetitor(new Competitor("gary"));
        comp.addCompetitor(new Competitor("jeff"));
    }

    @Test
    void testConstructor() {
        assertEquals("Joe", joe.getName());
    }

    @Test
    void testToJson() {
        try {
            Tournament tournament = new Tournament("Tournament");
            tournament.addCompetitorList(comp);
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


    @Test
    void testCompetitorList() {
        comp.removeCompetitor();
        assertEquals(7, comp.getCompetitorListSize());
        comp.removeCompetitor();
        comp.removeCompetitor();
        comp.removeCompetitor();
        comp.removeCompetitor();
        comp.removeCompetitor();
        comp.removeCompetitor();
        comp.completeCompetitorList();
        assertEquals(8, comp.getCompetitorList().size());
        comp.matchmakeCompetitorList();
        assertEquals("ann", comp.getCompetitorList().get(0).getName());

    }

    @Test
    void testTournament() {
        Tournament tournament = new Tournament("Tournament");
        tournament.addCompetitorList(comp);
        tournament.addCompetitorList(comp);
        tournament.removeCompetitorList();
        String ann = tournament.getCompetitorLists().get(0).getCompetitorList().get(0).getName();
        assertEquals("ann", ann);
    }
}

