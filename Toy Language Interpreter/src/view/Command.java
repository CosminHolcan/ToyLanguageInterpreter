package view;

public abstract class Command {

    private String key;
    private String description;

    public Command(String key, String description)
    {
        this.key = key;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public abstract void execute();
}
