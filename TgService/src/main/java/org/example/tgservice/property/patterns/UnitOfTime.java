package org.example.tgservice.property.patterns;

public enum UnitOfTime {

    HOURS(0),
    DAYS(1),
    MONTHS(2),
    MINUTES(3);

    UnitOfTime(int on) {
        this.on = on;
    }

    private int on;

    public int getOn() {
        return on;
    }
}
