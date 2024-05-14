package se.lexicon.data.impl;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.util.List;

public interface TodoItemDao {

    TodoItem create(TodoItem todoItem);
    List<TodoItem> findAll();
    TodoItem findById(int id);


    List<TodoItem> findBDoneStatus (boolean done);
    List<TodoItem> FindByAssignee(int id);
    List<TodoItem> FindByAssignee(Person person);
    List<TodoItem> FindByUnassigned();




    TodoItem update(TodoItem todoItem);
    boolean deleteById(TodoItem todoItem);
}
