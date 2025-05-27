/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.SparePartInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.SparePart;
import utils.Database;

/**
 *
 * @author HP
 */
public class SparePartService implements SparePartInterface {

    private final Database db = new Database();

    @Override
    public SparePart getSparePartById(int id) {
        String sql = "SELECT * FROM spare_parts WHERE id = ?";
        try {
            ResultSet rs = db.selectQuery(sql, id);
            if (rs.next()) {
                return new SparePart(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<SparePart> getAllSpareParts() {
        List<SparePart> list = new ArrayList<SparePart>();
        String sql = "SELECT * FROM spare_parts";
        try (ResultSet rs = db.selectQuery(sql)) {
            while (rs.next()) {
                list.add(new SparePart(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getAll: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean addSparePart(SparePart part) {
        String sql = "INSERT INTO spare_parts (name, price) VALUES (?, ?)";
        return db.executeUpdate(sql, part.getName(), part.getPrice()) > 0;
    }

    @Override
    public boolean updateSparePart(SparePart part) {
        String sql = "UPDATE spare_parts SET name = ?, price = ? WHERE id = ?";
        return db.executeUpdate(sql, part.getName(), part.getPrice(), part.getId()) > 0;
    }

    @Override
    public boolean deleteSparePart(int id) {
        String sql = "DELETE FROM spare_parts WHERE id = ?";
        return db.executeUpdate(sql, id) > 0;
    }

}
