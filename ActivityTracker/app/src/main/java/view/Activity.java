package view;

// ViewModel.Activity.java
public class Activity {
    private int id;
    private String name;
    private String date;
    private int duration; // duration in minutes
    private String type; // e.g., "Bike Ride", "Gym"

    public Activity(int id, String name, String date, int duration, String type) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    // Getters and Setters
}
