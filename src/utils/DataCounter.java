/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class DataCounter {
    private final Database db;

    public DataCounter() {
        db = new Database();
    }

    private int countFromTable(String tableName) {
        String sql = "SELECT COUNT(*) AS total FROM " + tableName;
        try (CachedRowSet crs = db.selectQuery(sql)) {
            if (crs.next()) {
                return crs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menghitung data dari tabel " + tableName + ": " + e.getMessage());
        }
        return 0;
    }

    public int countCustomers() {
        return countFromTable("customers");
    }

    public int countSpareparts() {
        return countFromTable("spare_parts");
    }

    public int countVehicles() {
        return countFromTable("vehicles");
    }

    public void close() {
        db.closeConnection();
    }
}
