package core;

public enum Param {
    KEY,
    LOCATION,
    RADIUS,
    LANGUAGE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
