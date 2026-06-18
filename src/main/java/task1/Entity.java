package task1;

public abstract class Entity {

    private int age;
    private String name;
    private boolean isActive;

    public Entity(int age, String name, boolean isActive) {
        this.age = age;
        this.name = name;
        this.isActive = isActive;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }


}
