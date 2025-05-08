package model;

public class Game {
    private int id;
    private String name;
    private String relDate;

    public Game(int id, String name, String relDate) {
        this.id = id;
        this.name = name;
        this.relDate = relDate;
    }

    public Game() {
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

    public String getRelDate() {
        return relDate;
    }

    public void setRelDate(String relDate) {
        this.relDate = relDate;
    }
}
