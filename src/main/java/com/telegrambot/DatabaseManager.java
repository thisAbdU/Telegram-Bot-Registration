package com.telegrambot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:C:/Users/user/Documents/MyABDUFiles/simpletelegrambot/src/main/java/com/telegrambot/botdata.db";

    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(
                 "CREATE TABLE IF NOT EXISTS user_data (" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "name TEXT," +
                 "email TEXT," +
                 "phone TEXT," +
                 "gender TEXT" +
                 ")"
             )) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUserData(UserFormData userData) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO user_data (name, email, phone, gender) VALUES (?, ?, ?, ?)"
             )) {
            stmt.setString(1, userData.getName());
            stmt.setString(2, userData.getEmail());
            stmt.setString(3, (String) userData.getPhone());
            stmt.setString(4, (String) userData.getGender());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

