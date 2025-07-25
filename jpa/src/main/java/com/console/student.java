package com.console;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
class student {
    public student(){

    }

    @Id
    private int id;
    private String name;
    private int mark;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    @Override
    public String toString() {
        return "student [id=" + id + ", name=" + name + ", mark=" + mark + "]";
    }
    

}
