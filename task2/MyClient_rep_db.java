package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyClient_rep_db  {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

    public List<Client> readAll_clients() throws Exception {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM clients ORDER BY clientId";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Client c = new Client(
                        rs.getString("organizationName"),
                        rs.getString("typeProperty"),
                        rs.getString("address"),
                        rs.getString("telephone"),
                        rs.getString("contactPerson")
                );
                c.setClientId(rs.getInt("clientId"));
                list.add(c);
            }
        }
        return list;
    }

    public Client getById_client(int id) throws Exception {
        String sql = "SELECT * FROM clients WHERE clientId = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client c = new Client(
                        rs.getString("organizationName"),
                        rs.getString("typeProperty"),
                        rs.getString("address"),
                        rs.getString("telephone"),
                        rs.getString("contactPerson")
                );
                c.setClientId(id);
                return c;
            }
            return null;
        }
    }

    public List<String> get_k_n_short_list_clients(int k, int n) throws Exception {
        List<String> list = new ArrayList<>();
        String sql = "SELECT clientId, organizationName, contactPerson, telephone FROM clients " +
                "ORDER BY clientId LIMIT ? OFFSET ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, n);
            stmt.setInt(2, k * n);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ClientShort cs = new ClientShort(
                        rs.getString("organizationName"),
                        rs.getString("contactPerson"),
                        rs.getString("telephone")
                );
                cs.setClientId(rs.getInt("clientId"));
                list.add(cs.toStringShort());
            }
        }
        return list;
    }

    public void add_client(Client client) throws Exception {
        String sql = "INSERT INTO clients (organizationName, typeProperty, address, telephone, contactPerson) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getOrganizationName());
            stmt.setString(2, client.getTypeProperty());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getTelephone());
            stmt.setString(5, client.getContactPerson());
            stmt.executeUpdate();
        }
    }

    public boolean delete_client(int id) throws Exception {
        String sql = "DELETE FROM clients WHERE clientId = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean replace_client(int id, Client updatedClient) throws Exception {
        String sql = "UPDATE clients SET organizationName=?, typeProperty=?, address=?, telephone=?, contactPerson=? WHERE clientId=?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedClient.getOrganizationName());
            stmt.setString(2, updatedClient.getTypeProperty());
            stmt.setString(3, updatedClient.getAddress());
            stmt.setString(4, updatedClient.getTelephone());
            stmt.setString(5, updatedClient.getContactPerson());
            stmt.setInt(6, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public int get_count_clients() throws Exception {
        String sql = "SELECT COUNT(*) FROM clients";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
