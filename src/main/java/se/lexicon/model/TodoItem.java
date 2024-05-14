package se.lexicon.model;

import java.time.LocalDate;
import java.util.Arrays;

public class TodoItem {
    private int todo_id;
    private  String title ;
    private String description ;
    private LocalDate deadline;
    private boolean done;
    private int assignee_Id;

    public TodoItem(int todo_id, String title, String description, LocalDate deadline, boolean done, int assignee_Id) {
        this.todo_id= todo_id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.assignee_Id = assignee_Id;
    }

    public TodoItem(String title, String description, LocalDate deadline, boolean done) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
    }

    public int getId() {
        return todo_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public int getAssigneeId() {
        return assignee_Id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setAssignee_Id(int assignee_Id) {
        this.assignee_Id = assignee_Id;
    }


    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + todo_id+
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", assigneeId=" + assignee_Id +
                '}';
    }
}
