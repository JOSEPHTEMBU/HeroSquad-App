


package models;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

import junit.framework.TestCase;
import org.junit.Test;

public class SquadTest  {


    @Test
    public void squadinstatiatesCorrectly(){
        Squad newSquad = new Squad ("marvel",12,"fighting");
        assertEquals("marvel",  newSquad.getName() );
    }
    @Test
    public void getNewSquad(){
        Squad newSquad = new Squad ("marvel",12,"fighting");
        assertEquals("marvel",  newSquad.getName() );
    }
    @Test
    public void getNewMaxsize(){
        Squad newSquad = new Squad ("marvel",12,"fighting");
        assertEquals("marvel",  newSquad.getName() );
    }
    @Test
    public void getNewCause(){
        Squad newSquad = new Squad ("marvel",12,"fighting");
        assertEquals("marvel",  newSquad.getName() );
    }
    @Test
    public void getNewage(){
        Squad newSquad = new Squad ("marvel",12,"fighting");
        assertEquals("marvel",  newSquad.getName() );
    }
    @Test
    public void testSquad_getsCause_true() throws Exception {
        Squad testSquad = new Squad("marvel",12,"fighting aliens");
        assertEquals("fighting aliens", testSquad.getCause());
    }
}
