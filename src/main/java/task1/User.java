package task1;

public class User extends Entity{
    public User(int age, String name, boolean isActive) {
        super(age, name, isActive);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
