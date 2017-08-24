package com.libertymutual.goforcode.todolist.models;

public class ToDoItem {

    private int id;
    private String text;
    private boolean isComplete;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public boolean isComplete() {
        return isComplete;
    }
    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean getIsIncomplete() {
        return !isComplete;
    }

    @Override
    public String toString() {
        return text + "<" + id + "> (complete: " + isComplete + ")";
    }

}