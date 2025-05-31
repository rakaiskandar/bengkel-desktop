/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.CustomerInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Customer;
import utils.Database;

/**
 *
 * @author HP
 */
public class CustomerService implements CustomerInterface {

    private final Database db = new Database();

    @Override
    public Customer getCustomerById(int id) {
        String sql = "SELECT id, name, phone FROM customers WHERE id = ?";
        try (ResultSet rs = db.selectQuery(sql, id)) {
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getCustomerIdAndNameById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try {
            ResultSet rs = db.selectQuery(sql);

            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getAllCustomers: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, phone) VALUES (?, ?)";
        return db.executeUpdate(sql, customer.getName(), customer.getPhone()) > 0;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, phone = ? WHERE id = ?";
        return db.executeUpdate(sql, customer.getName(), customer.getPhone(), customer.getId()) > 0;
    }

    @Override
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        return db.executeUpdate(sql, id) > 0;
    }

}
