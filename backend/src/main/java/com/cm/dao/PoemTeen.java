package com.cm.dao;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "poem_teen")
public class PoemTeen {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "verse1")
    private String verse1;

    @Column(name = "verse2")
    private String verse2;

    @Column(name = "verse3")
    private String verse3;

    @Column(name = "verse4")
    private String verse4;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVerse1() {
        return verse1;
    }

    public void setVerse1(String verse1) {
        this.verse1 = verse1;
    }

    public String getVerse2() {
        return verse2;
    }

    public void setVerse2(String verse2) {
        this.verse2 = verse2;
    }

    public String getVerse3() {
        return verse3;
    }

    public void setVerse3(String verse3) {
        this.verse3 = verse3;
    }

    public String getVerse4() {
        return verse4;
    }

    public void setVerse4(String verse4) {
        this.verse4 = verse4;
    }
}
