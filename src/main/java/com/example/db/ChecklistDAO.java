package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChecklistDAO {

    public List<String> getChecklistItems() {
        List<String> checklistItems = new ArrayList<>();

        try(Connection connection = DatabaseConnector.getConnection();
        
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT item FROM checklist")) {

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    checklistItems.add(resultSet.getString("item"));
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }

        return checklistItems;

    }
    
    public void addItemToChecklist(String item) {

        try (Connection connection = DatabaseConnector.getConnection(); 
        
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO checklist (item) VALUES (?)")) {

                preparedStatement.setString(1, item);
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
