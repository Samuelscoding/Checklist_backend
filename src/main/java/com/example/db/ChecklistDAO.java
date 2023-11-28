package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
                        resultSet.getFloat("number"),
                        resultSet.getString("task"),
                        resultSet.getString("department"),
                        resultSet.getString("person"),
                        resultSet.getDate("planned_date").toLocalDate(),
                        resultSet.getDate("completed_date").toLocalDate(),
                        resultSet.getString("signature"),
                        resultSet.getString("colorClass_pv"),
                        resultSet.getString("colorClass_rv")
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
        
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO checklist (number, task, department, person, planned_date, completed_date, signature, colorClass_pv, colorClass_rv) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setFloat(1, item.getNumber());
                preparedStatement.setString(2, item.getTask());
                preparedStatement.setString(3, item.getDepartment());
                preparedStatement.setString(4, item.getPerson());

                if(item.getPlannedDate() != null) {
                    preparedStatement.setDate(5, java.sql.Date.valueOf(item.getPlannedDate()));
                } else {
                    preparedStatement.setNull(5, Types.DATE);
                }

                if(item.getCompletedDate() != null) {
                    preparedStatement.setDate(6, java.sql.Date.valueOf(item.getCompletedDate()));
                } else {
                    preparedStatement.setNull(6, Types.DATE);
                }

                if(item.getSignature() != null && !item.getSignature().isEmpty()) {
                    preparedStatement.setString(7, item.getSignature());
                } else {
                    preparedStatement.setNull(7, Types.VARCHAR);
                }

                preparedStatement.setString(8, item.getColorClass_pv());
                preparedStatement.setString(9, item.getColorClass_rv());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {

                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generateId = generatedKeys.getInt(1);
                        item.setId(generateId);

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void deleteItemFromChecklist(int taskId) {

        try (Connection connection = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM checklist WHERE id = ?")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
