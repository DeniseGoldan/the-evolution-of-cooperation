package game;

public enum Action {

    Cooperate("Cooperate"),
    Defect("Defect");

    private String name;

    @Override
    public String toString(){
        return this.name;
    }

    Action (String actionName) {
        this.name = actionName;
    }

    public String getName() {
        return this.name;
    }

    public Action getOppositeAction(){
        if (name.equals("Cooperate")) {
            return Action.Defect;
        }
        return Action.Cooperate;
    }
}
