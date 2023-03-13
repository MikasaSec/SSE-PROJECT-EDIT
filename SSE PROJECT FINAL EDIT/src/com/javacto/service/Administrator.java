package com.javacto.service;
import com.javacto.action.UserAction;
import com.javacto.dao.Dao;
import com.javacto.dao.DaoImpl;
import java.util.Arrays;
import java.util.Scanner;

public class Administrator extends User {
    public void run(){
        Dao courseRequirements=new DaoImpl();
        courseRequirements.showCourseRequirements();
        courseRequirements.selectPartTimeTeacher();

    }

}

