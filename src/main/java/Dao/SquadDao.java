package Dao;

import models.Hero;
import models.Squad;

import java.util.List;

public interface SquadDao {

    List<Squad> getAllSquads();

    void add(Squad squad);

    Squad getSquadById(int id);

    void deleteAllSquads();

    List<Hero> getSquadHeroesById(int id);

    void deleteSquadById(int id);

    void deleteHeroesInSquad(int id);

    void updateSquad(int id, String name, String cause, int size);


}
