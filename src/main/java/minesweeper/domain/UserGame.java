package minesweeper.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usergame")
public class UserGame implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "stage")
    private String stage;

    @Column(name = "difficulty")
    private String difficulty;

    @Lob
    @Column(name = "cells")
    private String cells;

    public UserGame() {
    }

    public long getId() {
        return id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCells() {
        return cells;
    }

    public void setCells(String cells) {
        this.cells = cells;
    }

    public void setId(long id) {
        this.id = id;
    }
}