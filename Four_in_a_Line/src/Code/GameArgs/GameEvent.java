package Code.GameArgs;

import java.io.Serializable;

abstract public class GameEvent implements Serializable {
    private String name;

    public GameEvent(String name) {
        this.name = name;
    }

    public GameEvent() {
    }

    public String getName() {
        return name;
    }
}
