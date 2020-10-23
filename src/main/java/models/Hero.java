package models;

public class Hero {
    private int id;
    private String name;
    private String power;
    private String weakness;
    private int age;
    private int squadId;

    public static void clearAll() {
    }

    public int getSquadId() {
        return squadId;
    }

    public void setSquadId(int squadId) {
        this.squadId = squadId;
    }

    public Hero(String name, String power, String weakness, int age, int squadId) {
        this.name = name;
        this.power = power;
        this.weakness = weakness;
        this.age = age;
        this.squadId = squadId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
