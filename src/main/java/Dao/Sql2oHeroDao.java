package Dao;

import models.Hero;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oHeroDao implements HeroDao{

    private final Sql2o sql2o;

    public Sql2oHeroDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }




    @Override
    public List<Hero> getAllHeroes() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM heroes")
                    .executeAndFetch(Hero.class);
        }
    }

    @Override
    public void add(Hero hero) {
        String sql = "INSERT INTO heroes (name, power, weakness, age, squadId) VALUES (:name, :power, :weakness, :age, :squadId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .addParameter("name", hero.getName())
                    .addParameter("power", hero.getPower())
                    .addParameter("weakness", hero.getWeakness())
                    .addParameter("age", hero.getAge())
                    .addParameter("squadId", hero.getSquadId())
                    .executeUpdate()
                    .getKey();
            hero.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Hero getHeroById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM heroes WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Hero.class);
        }
    }


    @Override
    public void deleteAllHeroes() {
        String sql = "DELETE from heroes";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteHeroById(int id) {
        String sql = "DELETE FROM heroes WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void updateHero(int id, String name, String power, String weakness, int age, int squadId) {
        String sql = "UPDATE heroes SET name = :name, age = :age, power = :power, weakness = :weakness, squadId = :squadId WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("power", power)
                    .addParameter("weakness", weakness)
                    .addParameter("age", age)
                    .addParameter("squadId", squadId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


}
