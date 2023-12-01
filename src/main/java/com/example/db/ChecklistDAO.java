package com.example.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
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
                        resultSet.getString("task"),
                        resultSet.getString("department"),
                        resultSet.getString("person"),
                        getDateOrNull(resultSet, "planned_date"),
                        getDateOrNull(resultSet, "completed_date"),
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
        
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO checklist (task, department, person, planned_date, completed_date, signature, colorClass_pv, colorClass_rv) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                
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

    public void updateItemInChecklist(ChecklistItem upatedItem) {

        try(Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE checklist SET task=?, department=?, person=?, planned_date=?, completed_date=?, signature=? WHERE id=?")) {

                    preparedStatement.setString(1, upatedItem.getTask());
                    preparedStatement.setString(2, upatedItem.getDepartment());
                    preparedStatement.setString(3, upatedItem.getPerson());

                    if(upatedItem.getPlannedDate() != null) {
                        preparedStatement.setDate(4, java.sql.Date.valueOf(upatedItem.getPlannedDate()));
                    } else {
                        preparedStatement.setNull(4, Types.DATE);
                    }

                    if(upatedItem.getCompletedDate() != null) {
                        preparedStatement.setDate(5, java.sql.Date.valueOf(upatedItem.getCompletedDate()));
                    } else {
                        preparedStatement.setNull(5, Types.DATE);
                    }

                    if(upatedItem.getSignature() != null && !upatedItem.getSignature().isEmpty()) {
                        preparedStatement.setString(6, upatedItem.getSignature());
                    } else {
                        preparedStatement.setNull(6, Types.VARCHAR);
                    }

                    preparedStatement.setInt(7, upatedItem.getId());

                    preparedStatement.executeUpdate();

            } catch(SQLException e) {

                e.printStackTrace();
            }
    }

    private LocalDate getDateOrNull(ResultSet resultSet, String columnName) throws SQLException {

        Date date = resultSet.getDate(columnName);
        return (date != null) ? date.toLocalDate() : null;
    }
}
