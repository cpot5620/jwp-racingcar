package racingcar.domain;

public class Car {
    private static final int START_POSITION = 0;
    private final Name name;
    private final Position position;

    public Car(String name) {
        this(name, START_POSITION);
    }

    public Car(String name, int position) {
        this.name = new Name(name);
        this.position = new Position(position);
    }

    public void move() {
        position.forward();
    }

    public boolean isSamePosition(int position) {
        return this.position.getValue() == position;
    }

    public int getMovedLength() {
        return position.getValue();
    }

    public String getName() {
        return name.getName();
    }
}
