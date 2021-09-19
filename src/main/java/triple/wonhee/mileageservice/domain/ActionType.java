package triple.wonhee.mileageservice.domain;

public enum ActionType {
    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");

    final private String name;

    ActionType(String name) {
        this.name = name;
    }
}
