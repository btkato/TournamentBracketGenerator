package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompetitorListTest extends JsonTest {

    private CompetitorList nothingList;
    private CompetitorList threeList;
    private CompetitorList fullTournament;
    private Competitor tom;
    private Competitor joe;
    private Competitor wes;
    private Competitor john;
    private Competitor jim;
    private Competitor alan;
    private Competitor jeff;
    private Competitor alex;

    @BeforeEach
    void runBefore() {
        nothingList = new CompetitorList();
        threeList = new CompetitorList();
        fullTournament = new CompetitorList();
        joe = new Competitor("Joe");
        tom = new Competitor("Tom");
        wes = new Competitor("Wes");
        john = new Competitor("John");
        jim = new Competitor("Jim");
        alan = new Competitor("alan");
        jeff = new Competitor("jeff");
        alex = new Competitor("alex");
        threeList.addCompetitor(joe);
        threeList.addCompetitor(tom);
        threeList.addCompetitor(wes);
        fullTournament.addCompetitor(joe);
        fullTournament.addCompetitor(tom);
        fullTournament.addCompetitor(wes);
        fullTournament.addCompetitor(john);
        fullTournament.addCompetitor(jim);
        fullTournament.addCompetitor(alan);
        fullTournament.addCompetitor(jeff);
        fullTournament.addCompetitor(alex);
    }

    @Test
    void testAddingOneCompetitor() {
        nothingList.addCompetitor(joe);
        Competitor joeComp = nothingList.getCompetitorList().get(0);
        assertEquals(1, nothingList.getCompetitorListSize());
        assertEquals("Joe", joeComp.getName());
    }

    @Test
    void testAddingMultipleCompetitors() {
        nothingList.addCompetitor(tom);
        nothingList.addCompetitor(wes);
        nothingList.addCompetitor(joe);
        Competitor tomComp = nothingList.getCompetitorList().get(0);
        Competitor wesComp = nothingList.getCompetitorList().get(1);
        Competitor joeComp = nothingList.getCompetitorList().get(2);
        assertEquals(3, nothingList.getCompetitorListSize());
        assertEquals("Tom", tomComp.getName());
        assertEquals("Wes", wesComp.getName());
        assertEquals("Joe", joeComp.getName());

    }

    @Test
    void testRemovingCompetitor() {
        threeList.removeCompetitor();
        assertEquals(2, threeList.getCompetitorListSize());
        assertFalse(threeList.getCompetitorList().contains(wes));
    }

    @Test
    void testRemovingMultipleCompetitors() {
        threeList.removeCompetitor();
        threeList.removeCompetitor();
        assertEquals(1, threeList.getCompetitorListSize());
        assertFalse(threeList.getCompetitorList().contains(tom));
        assertFalse(threeList.getCompetitorList().contains(wes));
    }

    @Test
    void testAddAndRemoveCompetitor() {
        nothingList.addCompetitor(tom);
        assertTrue(nothingList.getCompetitorList().contains(tom));
        nothingList.removeCompetitor();
        assertFalse(nothingList.getCompetitorList().contains(tom));
    }

    @Test
    void testAddMultipleAndRemoveOne() {
        nothingList.addCompetitor(tom);
        nothingList.addCompetitor(wes);
        nothingList.removeCompetitor();
        assertTrue(nothingList.getCompetitorList().contains(tom));
        assertFalse(nothingList.getCompetitorList().contains(wes));
        nothingList.addCompetitor(joe);
        assertEquals(2, nothingList.getCompetitorListSize());

    }

    @Test
    void testAddMultipleAndRemoveMultiple() {
        nothingList.addCompetitor(tom);
        nothingList.addCompetitor(wes);
        nothingList.removeCompetitor();
        nothingList.removeCompetitor();
        assertFalse(nothingList.getCompetitorList().contains(tom));
        assertFalse(nothingList.getCompetitorList().contains(wes));
        nothingList.addCompetitor(joe);
        assertEquals(1, nothingList.getCompetitorListSize());
    }

    @Test
    void testCompletingBracket() {
        fullTournament.removeCompetitor();
        fullTournament.removeCompetitor();
        fullTournament.removeCompetitor();
        fullTournament.completeCompetitorList();
        assertEquals(8, fullTournament.getCompetitorListSize());
        assertEquals(joe, fullTournament.getCompetitorList().get(0));
        assertEquals(tom, fullTournament.getCompetitorList().get(1));
        assertEquals(wes, fullTournament.getCompetitorList().get(2));
        assertEquals(john, fullTournament.getCompetitorList().get(3));
        assertEquals(jim, fullTournament.getCompetitorList().get(4));

    }

    @Test
    void testCompletingBracketUpperBound() {
        fullTournament.removeCompetitor();
        fullTournament.completeCompetitorList();
        assertEquals(8, fullTournament.getCompetitorListSize());
        assertEquals(joe, fullTournament.getCompetitorList().get(0));
        assertEquals(tom, fullTournament.getCompetitorList().get(1));
        assertEquals(wes, fullTournament.getCompetitorList().get(2));
        assertEquals(john, fullTournament.getCompetitorList().get(3));
        assertEquals(jim, fullTournament.getCompetitorList().get(4));
        assertEquals(alan, fullTournament.getCompetitorList().get(5));
        assertEquals(jeff, fullTournament.getCompetitorList().get(6));

    }

    @Test
    void testCompletingBracketMidBound() {
        fullTournament.removeCompetitor();
        fullTournament.removeCompetitor();
        fullTournament.completeCompetitorList();
        assertEquals(8, fullTournament.getCompetitorListSize());
        assertEquals(joe, fullTournament.getCompetitorList().get(0));
        assertEquals(tom, fullTournament.getCompetitorList().get(1));
        assertEquals(wes, fullTournament.getCompetitorList().get(2));
        assertEquals(john, fullTournament.getCompetitorList().get(3));
        assertEquals(jim, fullTournament.getCompetitorList().get(4));
        assertEquals(alan, fullTournament.getCompetitorList().get(5));
    }

    @Test
    void testMatchMaking() {
        fullTournament.matchmakeCompetitorList();
        assertEquals(8, fullTournament.getCompetitorListSize());
        assertEquals(joe, fullTournament.getCompetitorList().get(0));
        assertEquals(jim, fullTournament.getCompetitorList().get(1));
        assertEquals(tom, fullTournament.getCompetitorList().get(2));
        assertEquals(alan, fullTournament.getCompetitorList().get(3));
        assertEquals(wes, fullTournament.getCompetitorList().get(4));
        assertEquals(jeff, fullTournament.getCompetitorList().get(5));
        assertEquals(john, fullTournament.getCompetitorList().get(6));
        assertEquals(alex, fullTournament.getCompetitorList().get(7));
    }


    @Test
    void testToJson() {
        try {
            Tournament tournament = new Tournament("Tournament");
            tournament.addCompetitorList(fullTournament);
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
    void testRemovedCompetitorLists() {
        Tournament tournament = new Tournament("Tournament");
        tournament.addCompetitorList(fullTournament);
        assertEquals(8, tournament.getCompetitorLists().get(0).getCompetitorListSize());
        tournament.removeCompetitorList();
        assertEquals(0, tournament.getCompetitorLists().size());
    }

}
