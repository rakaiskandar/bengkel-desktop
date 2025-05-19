package services;

import utils.Hash;
import utils.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserService {
    private final Database db;

    public UserService() {
        db = new Database();
        createDefaultAdmin();
    }

    private void createDefaultAdmin() {
        final String defaultUsername = "admin";
        final String defaultPassword = "ADMIN1#3";

        // Hanya 1 placeholder → username
        String checkQuery
                = "SELECT username, password FROM users WHERE username = ?";
        try ( ResultSet rs = db.selectQuery(checkQuery, defaultUsername)) {
            if (!rs.next()) {
                // Hanya 2 placeholder → username & password
                String insertQuery
                        = "INSERT INTO users (username, password) VALUES (?, ?)";
                String hashed = Hash.HashPassword(defaultPassword);

                db.executeUpdate(insertQuery, defaultUsername, hashed);
                System.out.println(
                        "Admin user created — username: "
                        + defaultUsername
                        + ", password: "
                        + defaultPassword
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat cek/insert admin: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        // Hanya 1 placeholder → username
        String query
                = "SELECT password FROM users WHERE username = ?";

        try ( ResultSet rs = db.selectQuery(query, username)) {
            if (rs.next()) {
                String storedHash = rs.getString("password");
                String inputHash = Hash.HashPassword(password);

                if (storedHash.equals(inputHash)) {
                    // Login sukses
                    return new User(username, storedHash);
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }

        // Login gagal
        return null;
    }
}
