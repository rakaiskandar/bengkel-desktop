/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author musa
 */
public class Database {
    private final Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistem_bengkel", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException("Error saat menghubungkan database: " + e.getMessage(), e);
        } 
    }
    
    public CachedRowSet selectQuery(String sql, Object... params) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // set parameters
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                // populate CachedRowSet
                CachedRowSet crs = RowSetProvider
                  .newFactory()
                  .createCachedRowSet();
                crs.populate(rs);
                return crs;
            }
        } catch (SQLException e) {
            throw new RuntimeException(
              "Error saat menjalankan select query: " + e.getMessage(), e
            );
        }
    }

    public int executeUpdate(String sql, Object... params) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // set parameters
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(
              "Error saat menjalankan update query: " + e.getMessage(), e
            );
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(
              "Error saat menutup koneksi database: " + e.getMessage(), e
            );
        }
    }
}
