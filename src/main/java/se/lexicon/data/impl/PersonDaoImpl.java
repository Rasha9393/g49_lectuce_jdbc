package se.lexicon.data.impl;

import se.lexicon.connecationDB.DBConnectionManager;
import se.lexicon.data.PersonDao;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    @Override
    public Person create(Person person) {
        String insertPersonQuery = "INSERT INTO person (FirstName, lastName) VALUES (?, ?)";
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertPersonQuery, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());


            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Insert operation for Person table successfully.");
            } else {
                System.out.println("Insert operation for person table failed.");
            }
            try (
                    ResultSet generatedKey = preparedStatement.getGeneratedKeys()
            ) {
                if (generatedKey.next()) {
                    person.setId(generatedKey.getInt(1));
                } else {
                    System.out.println("Insert operation for person table failed.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return person;
    }

    @Override
    public Person findById(int id) {
        Person personById = null;
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                        int Id = resultSet.getInt(1);
                        String firstName = resultSet.getString(2);
                        String lastName = resultSet.getString(3);

                    personById = new Person(Id, firstName, lastName);

                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findById() with id: " + id + " " + e.getMessage());
        }
        return personById;
    }

    @Override
    public List<Person> findAll() {
        List<Person> personFindAllList = new ArrayList<>();
        try (
                Connection connection = DBConnectionManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM person")
        ) {
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                personFindAllList.add(new Person(Id, firstName, lastName));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findAll() " + e.getMessage());
        }
        return personFindAllList;

    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> personByNameList = new ArrayList<>();
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE name = ?")
        ) {
            preparedStatement.setString(1, name);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {

                    int Id = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);

                    personByNameList.add(new Person(Id, firstName, lastName));

                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findByName() with name: " + name + " " + e.getMessage());
        }
        return personByNameList;
    }

    @Override
    public Person update(Person person) {
        String updatepersonQuery = "UPDATE person SET  firstName = ?,lastName= ?, creator=?, WHERE ID = ?";
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updatepersonQuery)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            preparedStatement.setInt(3, person.getId());

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
        return person;
    }

    @Override
    public boolean deleteById(Person person) {
        int  rowsDeleted = 0;
        String deletePersonQuery= "DELETE FROM person WHERE ID = ?";
        try (
                Connection connection = DBConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deletePersonQuery)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, person.getId());

            rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted);

            if (rowsDeleted>0 ) {
                System.out.println("Delete operation for person table successfully.");
                return false;
            } else {
                connection.rollback();
                System.out.println("Delete operation for person table failed.");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return rowsDeleted >0;
    }
}
