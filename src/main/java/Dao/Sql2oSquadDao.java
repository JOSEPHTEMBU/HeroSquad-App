package Dao;

import models.Hero;
import models.Squad;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSquadDao implements SquadDao {

    private final Sql2o sql2o;

    public Sql2oSquadDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Squad> getAllSquads() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM squads")
                    .executeAndFetch(Squad.class);
        }
    }

    @Override
    public void add(Squad squad) {
        String sql = "INSERT INTO squads (name, max_size, cause) VALUES (:name, :max_size, :cause)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(squad)
                    .executeUpdate()
                    .getKey();
            squad.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Squad getSquadById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM squads WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Squad.class);
        }
    }

    @Override
    public void deleteAllSquads() {
        String sql = "DELETE from squads";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
