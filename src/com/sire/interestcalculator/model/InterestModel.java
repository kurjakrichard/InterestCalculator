/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.model;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.sire.interestcalculator.domain.InterestRate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//</editor-fold>

/**
 *
 * @author balza
 */
public class InterestModel {

//<editor-fold defaultstate="collapsed" desc="Class variables">
    private final String FILENAME = "interest.db";
    private final String URL = "jdbc:sqlite:" + FILENAME;
    private Connection conn = null;
//</editor-fold>

    public InterestModel() {
        connect(URL);
        createNewTableIfNotExist();
    }

    /**
     * Create connection to the database
     */
    private void connect(String url) {

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("Hiba történt a kapcsolat létrehozásakor!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public final void createNewTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS rates (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    rateDate text NOT NULL,\n"
                + "    rate text NOT NULL\n"
                + ");";
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adatbázis tábla létrehozásakor!");
        }
    }

    /**
     * select all rows in the partners table
     */
    public ArrayList<InterestRate> selectAll() {
        String sql = "SELECT * FROM partners";
        ArrayList<InterestRate> partners = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                InterestRate actualRate = new InterestRate(rs.getInt("id"), rs.getString("rateDate"), rs.getString("rate"));
                partners.add(actualRate);
                // System.out.println(rs.getInt("id") + "\t"
                //       + rs.getString("companyName") + "\t"
                //     + rs.getString("phoneNumber"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return partners;
    }

    /**
     * Insert a new row into the warehouses table
     *
     * @param partner
     */
    public void addRate(InterestRate rate) {
        String sql = "INSERT INTO partners(companyName,phoneNumber) VALUES(?,?)";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, rate.getRateDate());
                pstmt.setString(2, rate.getRate());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat felvitelekor!");
        }
    }

    public void updateRate(InterestRate rate) {
        String sql = "UPDATE partners SET companyName = ?, phoneNumber = ? WHERE id = ?";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, rate.getRateDate());
                pstmt.setString(2, rate.getRate());
                pstmt.setInt(3, rate.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat felvitelekor!");
        }
    }

}
