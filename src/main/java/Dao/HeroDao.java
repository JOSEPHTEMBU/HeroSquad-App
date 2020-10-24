package Dao;

import models.Hero;

import java.util.List;

public interface HeroDao {

    List<Hero> getAllHeroes();

    void add(Hero hero);

    Hero getHeroById(int id);

    void deleteAllHeroes();

    void deleteHeroById(int id);

    void updateHero(int id, String name, String power, String weakness, int age, int squadId);

}
