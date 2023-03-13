package com.javacto.dao;

import com.javacto.service.User;

import java.util.ArrayList;

public interface Dao {
    /**
     * read user and course data from txt
     */
    public ArrayList<String> readFile(String filepath);
    /**
     * write user and course data into txt
     */
    public void writeFile(ArrayList<String> modifiedUserData);
    /**
     * Determine the user's occupation based on the id entered
     */
    public User getUserRole(String ID);
    /**
     * check id and password
     */
    public boolean validate (String ID, String password);
    /**
     * run user's method
     */
    public void run(User user);
    /**
     * create course require through input
     */
    public void createCourseRequirements();
    /**
     * select part time teacher
     */
    public String selectPartTimeTeacher();
    /**
     * show Course Requirements
     */
    public void showCourseRequirements();
    /**
     * show Trained Teacher's data before trained or after trained
     */
    public void showTrainedTeacher();
    /**
     * train teacher
     */
    public void getTraining(String choice);
}
