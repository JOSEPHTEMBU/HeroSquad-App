package Dao;

import models.Squad;

import java.util.List;

public interface SquadDao {

    List<Squad> getAllSquads();

    void add(Squad squad);

    Squad getSquadById(int id);

    void deleteAllSquads();
}
