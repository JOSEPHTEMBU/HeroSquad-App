package Dao;

import models.Hero;

import java.util.List;

public interface HeroDao {

    List<Hero> getAllHeroes();

    void add(Hero hero);

    Hero getHeroById(int id);

    void deleteAllHeroes();

}
