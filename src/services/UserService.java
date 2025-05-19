/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import utils.Hash;
import utils.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

/**
 *
 * @author HP
 */
public class UserService {
    private final Database db;

    public UserService() {
        db = new Database();
        createDefaultAdmin();
    }
    
    private void createDefaultAdmin() {
        String checkQuery = "SELECT * FROM users WHERE username = ?";
        try {
            ResultSet rs = db.selectQuery(checkQuery, "admin");
            if (!rs.next()) {
                String insertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
                String hashed = Hash.HashPassword("ADMIN1#3");
                db.executeUpdate(insertQuery, "admin", hashed, "admin");
                System.out.println("Admin user created with username: admin and password: admin123");
            }
        } catch (SQLException e) {
            System.out.println("Error saat cek/admin insert: " + e.getMessage());
        }
    }
    
    public User login(String username, String password) {
        String query = "SELECT username, password FROM users WHERE username = ? AND password = ?";
        
        try {
            ResultSet rs = db.selectQuery(query, username);
            
            if (rs.next()) {
                String storedHash = rs.getString("password");
                String inputHash = Hash.HashPassword(password);

                if (storedHash.equals(inputHash)) {
                    return new User(username, storedHash);
                }
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        
        return null;
    }
}
