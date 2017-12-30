package genetic;

public enum Action {

    C("Cooperate"),
    D("Defect");

    private String stringValue;

    @Override
    public String toString(){
        return this.stringValue;
    }

    Action (String actionName) {
        this.stringValue = actionName;
    }

    public String getStringValue() {
        return this.stringValue;
    }
}
