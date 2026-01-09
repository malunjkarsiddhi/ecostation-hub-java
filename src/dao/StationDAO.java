package dao;

import model.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    public void addStation(Station station) {
        String sql = "INSERT INTO stations " +
                "(code, name, location, status, energy_kwh, revenue) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, station.getCode());
            ps.setString(2, station.getName());
            ps.setString(3, station.getLocation());
            ps.setString(4, station.getStatus());
            ps.setInt(5, station.getEnergyKwh());
            ps.setDouble(6, station.getRevenue());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Station> getAllStations() {
        List<Station> list = new ArrayList<>();
        String sql = "SELECT id, code, name, location, status, energy_kwh, revenue FROM stations";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Station s = new Station(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("status"),
                        rs.getInt("energy_kwh"),
                        rs.getDouble("revenue")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalStations() {
        String sql = "SELECT COUNT(*) AS c FROM stations";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("c");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalEnergy() {
        String sql = "SELECT COALESCE(SUM(energy_kwh), 0) AS s FROM stations";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("s");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "SELECT COALESCE(SUM(revenue), 0) AS s FROM stations";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("s");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // ---------- extra for Stations CRUD ----------

    public void updateStation(Station station) {
        String sql = "UPDATE stations SET " +
                "code = ?, name = ?, location = ?, status = ?, " +
                "energy_kwh = ?, revenue = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, station.getCode());
            ps.setString(2, station.getName());
            ps.setString(3, station.getLocation());
            ps.setString(4, station.getStatus());
            ps.setInt(5, station.getEnergyKwh());
            ps.setDouble(6, station.getRevenue());
            ps.setInt(7, station.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStationById(int id) {
        String sql = "DELETE FROM stations WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- extra for Analytics ----------

    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) AS c FROM stations WHERE status = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("c");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getAverageRevenue() {
        String sql = "SELECT COALESCE(AVG(revenue), 0) AS a FROM stations";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("a");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public Station getTopRevenueStation() {
        String sql = "SELECT id, code, name, location, status, energy_kwh, revenue " +
                     "FROM stations ORDER BY revenue DESC LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return new Station(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("status"),
                        rs.getInt("energy_kwh"),
                        rs.getDouble("revenue")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
