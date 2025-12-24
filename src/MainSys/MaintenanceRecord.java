package MainSys;

public class MaintenanceRecord {
    private String id;
    private String date;
    private String description;

    public MaintenanceRecord(String id, String date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public String getId() { return id; }
    public String getDate() { return date; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "MaintenanceRecord{id='" + id + "', date='" + date + "', description='" + description + "'}";
    }
}
