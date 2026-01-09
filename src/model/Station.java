package model;

public class Station {

    private int id;
    private String code;
    private String name;
    private String location;
    private String status;
    private int energyKwh;
    private double revenue;

    public Station(int id, String code, String name, String location,
                   String status, int energyKwh, double revenue) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.location = location;
        this.status = status;
        this.energyKwh = energyKwh;
        this.revenue = revenue;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public int getEnergyKwh() {
        return energyKwh;
    }

    public double getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", energyKwh=" + energyKwh +
                ", revenue=" + revenue +
                '}';
    }
}
