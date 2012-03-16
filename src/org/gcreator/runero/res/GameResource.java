package org.gcreator.runero.res;

public class GameResource {

    String name;
    int id;
    
    public GameResource(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public GameResource(String name) {
        this(name, -1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
