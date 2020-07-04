/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.model;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.sire.interestcalculator.domain.InterestRate;
import com.sire.interestcalculator.domain.InterestRateString;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                + "    rate real NOT NULL\n"
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
     * select all rows in the rates table
     *
     * @return
     */
    public ArrayList<InterestRateString> selectAllString() {
        String sql = "SELECT * FROM rates ORDER BY rateDate";
        ArrayList<InterestRateString> rates = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                InterestRateString actualRateString = new InterestRateString(rs.getInt("id"), rs.getString("rateDate"), String.valueOf(rs.getDouble("rate")));
                rates.add(actualRateString);
                // System.out.println(rs.getInt("id") + "\t"
                //       + rs.getString("companyName") + "\t"
                //     + rs.getString("phoneNumber"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rates;
    }

    /**
     * select all rows in the rates table
     *
     * @return
     */
    public ArrayList<InterestRate> selectInterestPeriod(String dueDate, String paymentDate) {
        String sql = "select rateDate,rate from rates where (rateDate = (SELECT max(rateDate) FROM rates where rateDate <= ?) OR (rateDate > ? AND rateDate < ?)) ORDER BY rateDate ASC";
        ArrayList<InterestRate> rates = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ) {
            pstmt.setString(1, dueDate);
            pstmt.setString(2, dueDate);
            pstmt.setString(3, paymentDate);
            ResultSet rs = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate rateDate = LocalDate.parse(rs.getString("rateDate"), formatter);
                InterestRate actualRate = new InterestRate(rateDate, rs.getDouble("rate"));
                System.out.println(actualRate.getRateDate() + ", " + actualRate.getRate());
                rates.add(actualRate);
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rates;
    }

    /**
     * Insert a new row into the rates table
     *
     * @param partner
     */
    public void addRate(InterestRateString rate) {
        String sql = "INSERT INTO rates(rateDate, rate) VALUES(?,?)";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, rate.getRateDate());
                pstmt.setDouble(2, Double.parseDouble(rate.getRate()));
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat felvitelekor!");
        }
    }

    /**
     * Update field in the rates table
     *
     * @param rate
     */
    public void updateRate(InterestRateString rate) {
        String sql = "UPDATE rates SET rateDate = ?, rate = ? WHERE id = ?";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, rate.getRateDate());
                pstmt.setDouble(2, Double.parseDouble(rate.getRate()));
                pstmt.setInt(3, rate.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat módosításakor!");
        }
    }

    /**
     * Update field in the rates table
     *
     * @param rate
     */
    public void removeRate(InterestRateString rate) {
        String sql = "DELETE FROM rates WHERE id = ?";
        if (conn != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, rate.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Hiba történt az adat törlésekor!");
        }
    }
    
}
