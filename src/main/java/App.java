import Dao.Sql2oHeroDao;
import Dao.Sql2oSquadDao;
import models.Hero;
import models.Squad;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        Connection conn;

//        ---h2 db---
//        String connectionString = "jdbc:h2:~/HeroesSquad-App.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");

//        ---Local Database---
        String connectionString = "jdbc:postgresql://localhost:5432/heroapp"; // local db connection string
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "Access"); // local db sql2o instance

//        ---heroku Database---
//        String connectionString = "jdbc:postgresql://ec2-23-22-156-110.compute-1.amazonaws.com/dca403p0rj7rd1"; // heroku db connection string
//        Sql2o sql2o = new Sql2o(connectionString, "pawsdbhpnhsqno", "c3d09ad0e163678f9c64ceddef7f58e08083fdb434c10925ae9b9bcef355ff3d"); // heroku db sql2o instance
//

        conn = sql2o.open();

        Sql2oHeroDao heroDAO = new Sql2oHeroDao(sql2o);
        Sql2oSquadDao squadDAO = new Sql2oSquadDao(sql2o);
        Map<String, Object> model = new HashMap<>();


        get("/", (req, res) -> {
            model.put("heroes", heroDAO.getAllHeroes());
            model.put("squads", squadDAO.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/addhero", (req, res) -> {
            model.put("squads", squadDAO.getAllSquads());
            model.put("heroes", heroDAO.getAllHeroes());
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/addhero", (req, res) -> {
            String name = req.queryParams("name");
            String power = req.queryParams("power");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            int squadId = Integer.parseInt(req.queryParams("squad"));
            int squadSize = squadDAO.getSquadById(squadId).getMax_size();
            int numberOfHeroesInSquad = squadDAO.getSquadHeroesById(squadId).size();
            if(numberOfHeroesInSquad < squadSize){
                heroDAO.add(new Hero(name, power, weakness, age, squadId));
                model.put("squads", squadDAO.getAllSquads());
                model.put("heroes", squadDAO.getSquadHeroesById(squadId));
                return new ModelAndView(model, "index.hbs");
            }else{
                return new ModelAndView(model, "squad-full.hbs");
            }

        }, new HandlebarsTemplateEngine());

        get("/heroes/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Hero foundHero = heroDAO.getHeroById(id);
            model.put("squads", squadDAO.getAllSquads());
            model.put("hero", foundHero);
            return new ModelAndView(model, "hero-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deleteallheroes", (req, res) -> {
            heroDAO.deleteAllHeroes();
            model.put("heroes", heroDAO.getAllHeroes());
            model.put("squads", squadDAO.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        System.out.println(squadDAO.getAllSquads());

        get("/addsquad", (req, res) -> {
            model.put("squads", squadDAO.getAllSquads());
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/addsquad", (req, res) -> {
            String name = req.queryParams("name");
            String cause = req.queryParams("cause");
            int maxSize = Integer.parseInt(req.queryParams("size"));
            Squad newSquad = new Squad(name, maxSize, cause);
            squadDAO.add(newSquad);
            model.put("squads", squadDAO.getAllSquads());
            model.put("heroes", heroDAO.getAllHeroes());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Squad foundSquad = squadDAO.getSquadById(id);
            model.put("squads", squadDAO.getAllSquads());
            model.put("heroes", squadDAO.getSquadHeroesById(id));
            model.put("squad", foundSquad);
            return new ModelAndView(model, "squad-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deleteallsquads", (req, res) -> {
            squadDAO.deleteAllSquads();
            heroDAO.deleteAllHeroes();
            model.put("squads", squadDAO.getAllSquads());
            model.put("heroes", heroDAO.getAllHeroes());
            return new ModelAndView(model, "squads.hbs");
        }, new HandlebarsTemplateEngine());

        get("/edithero/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("editHero", true);
            model.put("hero", heroDAO.getHeroById(id));
            model.put("squads", squadDAO.getAllSquads());
            System.out.println(squadDAO.getAllSquads());
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deletehero/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            heroDAO.deleteHeroById(id);
            model.put("heroes", heroDAO.getAllHeroes());
            model.put("squads", squadDAO.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edithero/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            String name = req.queryParams("name");
            String power = req.queryParams("power");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            int squadId = Integer.parseInt(req.queryParams("squad"));
            heroDAO.updateHero(id, name, power, weakness, age, squadId);
            model.put("squads", squadDAO.getAllSquads());
            model.put("squad", squadDAO.getSquadById(heroDAO.getHeroById(id).getSquadId()));
            model.put("hero", heroDAO.getHeroById(id));
            return new ModelAndView(model, "hero-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/addherotosquad/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("addHeroToSquad",true);
            model.put("squads", squadDAO.getAllSquads());
            model.put("squad", squadDAO.getSquadById(id));
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/addherotosquad/:id", (req, res) -> {
            int squadId = Integer.parseInt(req.params("id"));
            String name = req.queryParams("name");
            String power = req.queryParams("power");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            int squadSize = squadDAO.getSquadById(squadId).getMax_size();
            int numberOfHeroesInSquad = squadDAO.getSquadHeroesById(squadId).size();
            if(numberOfHeroesInSquad < squadSize){
                heroDAO.add(new Hero(name, power, weakness, age, squadId));
                model.put("squads", squadDAO.getAllSquads());
                model.put("squad", squadDAO.getSquadById(squadId));
                model.put("heroes", squadDAO.getSquadHeroesById(squadId));
                return new ModelAndView(model, "squad-detail.hbs");
            }else{
                model.put("addHeroToSquad", true);
                return new ModelAndView(model, "squad-full.hbs");
            }
        }, new HandlebarsTemplateEngine());

        get("/editsquad/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("editSquad", true);
            model.put("squad", squadDAO.getSquadById(id));
            model.put("squads", squadDAO.getAllSquads());
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/editsquad/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            String name = req.queryParams("name");
            String cause = req.queryParams("cause");
            int size = Integer.parseInt(req.queryParams("size"));
            squadDAO.updateSquad(id, name, cause, size);
            model.put("squads", squadDAO.getAllSquads());
            model.put("squad", squadDAO.getSquadById(id));
            model.put("heroes", squadDAO.getSquadHeroesById(id));
            return new ModelAndView(model, "squad-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deletesquad/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            squadDAO.deleteSquadById(id);
            squadDAO.deleteHeroesInSquad(id);
            model.put("heroes", heroDAO.getAllHeroes());
            model.put("squads", squadDAO.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }
}