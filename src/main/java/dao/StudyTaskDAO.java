package dao;

import db.DBConnection;
import model.StudyTask;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudyTaskDAO {

    public void save(StudyTask task) {
        String sql = "INSERT INTO study_tasks (goal_id, task_date, description, required_commit, status, topic_id, session_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, task.getGoalId());
            stmt.setDate(2, Date.valueOf(task.getTaskDate()));
            stmt.setString(3, task.getDescription());
            stmt.setBoolean(4, task.isRequiredCommit());
            stmt.setString(5, task.getStatus());
            stmt.setInt(6, task.getTopicId());
            stmt.setString(7, task.getSessionType());

            int affectedRows = stmt.executeUpdate();
            System.out.println("StudyTaskDAO.save: affectedRows = " + affectedRows);

            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    task.setId(rs.getInt(1));
                    System.out.println("Generated task ID: " + task.getId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error in StudyTaskDAO.save: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveAll(List<StudyTask> tasks) {
        System.out.println("Saving " + tasks.size() + " tasks to database");
        for (StudyTask task : tasks) {
            save(task);
        }
        System.out.println("All tasks saved successfully");
    }

    // ✅ PLAN-SPECIFIC METHOD - KEEP
    public List<StudyTask> findByGoalId(int goalId) {
        List<StudyTask> tasks = new ArrayList<>();
        String sql = "SELECT * FROM study_tasks WHERE goal_id = ? ORDER BY task_date ASC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, goalId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(mapResultSetToStudyTask(rs));
            }

            System.out.println("Found " + tasks.size() + " tasks for goal ID: " + goalId);
        } catch (SQLException e) {
            System.err.println("Database error in StudyTaskDAO.findByGoalId: " + e.getMessage());
            e.printStackTrace();
        }

        return tasks;
    }

    // ✅ PLAN-SPECIFIC METHOD - KEEP
    public List<StudyTask> findTodayTasksByPlan(int planId) {
        List<StudyTask> tasks = new ArrayList<>();
        String sql = "SELECT * FROM study_tasks WHERE goal_id = ? AND task_date = CURRENT_DATE ORDER BY id";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, planId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(mapResultSetToStudyTask(rs));
            }

            System.out.println("Found " + tasks.size() + " tasks for today for plan ID: " + planId);
        } catch (SQLException e) {
            System.err.println("Database error in StudyTaskDAO.findTodayTasksByPlan: " + e.getMessage());
            e.printStackTrace();
        }

        return tasks;
    }

    // ✅ PLAN-SPECIFIC METHOD - KEEP
    public int getTotalTaskCountByPlan(int planId) {
        String sql = "SELECT COUNT(*) FROM study_tasks WHERE goal_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, planId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Database error in StudyTaskDAO.getTotalTaskCountByPlan: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ PLAN-SPECIFIC METHOD - KEEP
    public int getCompletedTaskCountByPlan(int planId) {
        String sql = "SELECT COUNT(*) FROM study_tasks WHERE goal_id = ? AND status = 'COMPLETED'";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, planId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Database error in StudyTaskDAO.getCompletedTaskCountByPlan: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ KEEP - Used for deleting tasks
    public void deleteByGoalId(int goalId) {
        String sql = "DELETE FROM study_tasks WHERE goal_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, goalId);
            int affectedRows = stmt.executeUpdate();
            System.out.println("Deleted " + affectedRows + " tasks for goal ID: " + goalId);
        } catch (SQLException e) {
            System.err.println("Database error in StudyTaskDAO.deleteByGoalId: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ KEEP - Used for toggling task completion
    public void updateStatus(int taskId, String status) {
        String sql = "UPDATE study_tasks SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, taskId);

            int affectedRows = stmt.executeUpdate();
            System.out.println("✅ Task " + taskId + " status updated to: " + status + " (rows: " + affectedRows + ")");

            // Verify the update
            if (affectedRows > 0) {
                String checkSql = "SELECT status FROM study_tasks WHERE id = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, taskId);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("   Verified - DB status: " + rs.getString("status"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Database error in StudyTaskDAO.updateStatus: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ❌ REMOVED - Global methods that combined data across plans
    // public List<StudyTask> findTodayTasks(int userId) - REMOVED
    // public List<StudyTask> findAllTasksByUser(int userId) - REMOVED
    // public int getTotalTaskCount(int userId) - REMOVED
    // public int getCompletedTaskCount(int userId) - REMOVED
    // public List<StudyTask> findTasksByDate(int userId, LocalDate date) - REMOVED

    private StudyTask mapResultSetToStudyTask(ResultSet rs) throws SQLException {
        StudyTask task = new StudyTask();
        task.setId(rs.getInt("id"));
        task.setGoalId(rs.getInt("goal_id"));
        task.setTaskDate(rs.getDate("task_date").toLocalDate());
        task.setDescription(rs.getString("description"));
        task.setRequiredCommit(rs.getBoolean("required_commit"));
        task.setStatus(rs.getString("status"));
        task.setTopicId(rs.getInt("topic_id"));
        task.setSessionType(rs.getString("session_type"));
        return task;
    }
}