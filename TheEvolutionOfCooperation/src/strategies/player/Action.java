package strategies.player;

public enum Action {

    Cooperate(0),
    Defect(1);

    private int binaryValue;

    @Override
    public String toString(){
        if (this.binaryValue == 0) {
            return "Cooperate";
        } else {
            return "Defect";
        }
    }

    Action (int binaryValue) {
        this.binaryValue = binaryValue;
    }

    public int getBinaryValue() {
        return this.binaryValue;
    }

    public Action getOppositeAction(){
        if (this.binaryValue == 0) {
            return Action.Defect;
        }
        return Action.Cooperate;
    }

}
