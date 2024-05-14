package se.lexicon.data.impl;

import se.lexicon.connecationDB.DBConnectionManager;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoItemDaoImpl implements TodoItemDao {
    @Override
    public TodoItem create(TodoItem todoItem) {

        String inserttodoItQuery = "INSERT INTO person(FirstName, CountryCode, District, Population) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(inserttodoItQuery, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todoItem.getDeadline()));
            preparedStatement.setBoolean(4, todoItem.isDone());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Insert operation for todoItem table successfully.");
            } else {
                connection.rollback();
                System.out.println("Insert operation for todoItem table failed.");
            }
            try (
                    ResultSet generatedKey = preparedStatement.getGeneratedKeys()
            ) {
                if (generatedKey.next()) {
                    todoItem.setTodo_id(generatedKey.getInt(1));
                } else {
                    connection.rollback();
                    System.out.println("Insert operation for city table failed.");
                }
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return todoItem;
    }

    @Override
    public List<TodoItem> findAll() {

        List<TodoItem> todoFindAllList = new ArrayList<>();
        try (
                Connection connection = DBConnectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM todo_item")
        ) {
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                LocalDate deadline = resultSet.getDate(4).toLocalDate();
                boolean done = resultSet.getBoolean(5);
                int assignee = resultSet.getInt(6);

                todoFindAllList.add(new TodoItem(Id, title, description, deadline, done, assignee));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findAll() " + e.getMessage());
        }
        return todoFindAllList;
    }

    @Override
    public TodoItem findById(int id) {
        TodoItem todoById = null;
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo_item WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    int Id = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    LocalDate deadline = resultSet.getDate(4).toLocalDate();
                    boolean done = resultSet.getBoolean(5);
                    int assignee = resultSet.getInt(6);


                    todoById = new TodoItem(Id, title, description, deadline, done, assignee);

                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findById() with id: " + id + " " + e.getMessage());
        }
        return todoById;
    }

    @Override
    public List<TodoItem> findBDoneStatus(boolean done) {


        TodoItem foundItem = null;
        List<TodoItem> foundItems = new ArrayList<>();
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo_item WHERE done = ?")
        ) {
            preparedStatement.setBoolean(1, done);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    int Id = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    LocalDate deadline = resultSet.getDate(4).toLocalDate();
                    boolean statusDone = resultSet.getBoolean(5);
                    int assignee = resultSet.getInt(6);


                    foundItem = new TodoItem(Id, title, description, deadline, statusDone, assignee);

                    foundItems.add(foundItem);

                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findBydone() with done: " + done + " " + e.getMessage());
        }
        return foundItems;
    }

    @Override
    public List<TodoItem> FindByAssignee(int id) {
        TodoItem foundItem = null;
       // List<TodoItem> foundItems = new ArrayList<>();

        List<TodoItem> todoByassignee = new ArrayList<>();

        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo_item WHERE Assignee_id = ?")
        ) {
            preparedStatement.setInt(1, id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    int Id = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    LocalDate deadline = resultSet.getDate(4).toLocalDate();
                    boolean done = resultSet.getBoolean(5);
                    int assignee = resultSet.getInt(6);


                    //  todoByassignee = new Person(id, title,description,deadline,done ,assignee_Id);
                    todoByassignee.add( new TodoItem(Id, title, description, deadline, done, assignee));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findByassignee with id: " + id + " " + e.getMessage());
        }
        return todoByassignee;
    }

    @Override
    public List<TodoItem> FindByAssignee(Person person) {
        List<TodoItem> todoByassignee = new ArrayList<>();

        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo_item  WHERE Assignee_id = ?")
        ) {
            preparedStatement.setInt(1, person.getId());
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    int Id = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    LocalDate deadline = resultSet.getDate(4).toLocalDate();
                    boolean done = resultSet.getBoolean(5);
                    int assignee = resultSet.getInt(6);


                    //  todoByassignee = new Person(id, title,description,deadline,done ,assignee_Id);
                    todoByassignee.add( new TodoItem(Id, title, description, deadline, done, assignee));

                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findByassignee with person: " + person + " " + e.getMessage());
        }
        return todoByassignee;
    }

    @Override
    public List<TodoItem> FindByUnassigned() {

        List<TodoItem> todoByUnassignee = new ArrayList<>();

        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo_item WHERE  assignee_id is null")
        ) {
            preparedStatement.setInt(1,assignee_id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (!resultSet.next()) {
                    int Id = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    LocalDate deadline = resultSet.getDate(4).toLocalDate();
                    boolean done = resultSet.getBoolean(5);
                    int assignee = resultSet.getInt(6);


                    //  todoByassignee = new Person(id, title,description,deadline,done ,assignee_Id);
                    todoByUnassignee.add(new TodoItem(Id, title, description, deadline, done, assignee));

                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findByUnassignee with id: " + assignee_id + " " + e.getMessage());
        }
        return todoByUnassignee;
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        String updatetodoQuery = "UPDATE todoitem  SET  title= ?,description= ?deadline=? ,done =?,assignee_id=?, WHERE ID = ?";
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updatetodoQuery)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todoItem.getDeadline()));;
            preparedStatement.setBoolean(4, todoItem.isDone());
            preparedStatement.setInt(5, todoItem.getAssigneeId());


            preparedStatement.setInt(6, todoItem.getId());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Update operation for person table successfully.");
            } else {
                connection.rollback();
                System.out.println("Update operation for person table failed.");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return todoItem;
    }

    @Override
    public boolean deleteById(TodoItem todoItem) {
        String deletetodoQuery = "DELETE FROM todo WHERE ID = ?";
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deletetodoQuery)
        ) {
            preparedStatement.setInt(1, todoItem.getId());

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return false;
    }
}
