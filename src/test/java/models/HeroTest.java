package models;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HeroTest extends TestCase {

    @Test
    public void lookForCorrectInstantiationOfHeroClass_true() throws Exception{
        Hero hero = setUpNewhero();
        assertTrue(hero instanceof Hero);
    }

    private Hero setUpNewhero() {
        return setUpNewhero();
    }

}