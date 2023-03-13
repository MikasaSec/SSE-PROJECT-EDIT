package com.javacto.service;
import com.javacto.dao.Dao;
import com.javacto.dao.DaoImpl;



public class PartTimeTeacher extends User{
    public void run() {
        Dao courseRequirements=new DaoImpl();
        courseRequirements.showTrainedTeacher();
    }
}
