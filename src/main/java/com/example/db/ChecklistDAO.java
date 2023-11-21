package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChecklistDAO {

    public List<ChecklistItem> getChecklistItems() {
        List<ChecklistItem> checklistItems = new ArrayList<>();

        try(Connection connection = DatabaseConnector.getConnection();
        
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM checklist")) {

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ChecklistItem item = new ChecklistItem(
                        resultSet.getInt("id"),
                        resultSet.getInt("number"),
                        resultSet.getString("task"),
                        resultSet.getString("department"),
                        resultSet.getString("person"),
                        resultSet.getDate("planned_date").toLocalDate(),
                        resultSet.getDate("completed_date").toLocalDate(),
                        resultSet.getString("signature")
                    );
                    checklistItems.add(item);
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }

        return checklistItems;

    }
    
    public void addItemToChecklist(ChecklistItem item) {

        try (Connection connection = DatabaseConnector.getConnection(); 
        
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO checklist (number, task, department, person, planned_date, completed_date, signature) VALUES (?,?,?,?,?,?,?,?)")) {

                preparedStatement.setInt(1, item.getNumber());
                preparedStatement.setString(2, item.getTask());
                preparedStatement.setString(3, item.getDepartment());
                preparedStatement.setString(4, item.getPerson());
                preparedStatement.setDate(5, java.sql.Date.valueOf(item.getPlannedDate()));
                preparedStatement.setDate(6, java.sql.Date.valueOf(item.getCompletedDate()));
                preparedStatement.setString(7, item.getSignature());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
