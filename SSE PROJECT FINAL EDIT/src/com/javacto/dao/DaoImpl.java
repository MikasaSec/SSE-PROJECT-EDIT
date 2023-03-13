package com.javacto.dao;

import com.javacto.action.UserAction;
import com.javacto.service.*;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DaoImpl implements Dao{


    static String PartTimeTeacherID;//Ensure that personal information is displayed directly when the teacher logs in successfully, without having to assign it twice

    public ArrayList<String> readFile(String filepath) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    public void writeFile(ArrayList<String> modifiedUserData) {
        try {
            FileWriter writer = new FileWriter("src/com/javacto/users.txt");
            BufferedWriter buffer = new BufferedWriter(writer);

            for (String data : modifiedUserData) {
                buffer.write(data);
                buffer.newLine();
            }

            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserRole(String ID) {

        User user = null;

        for (String contents : UserAction.userData) {
            String[] content = contents.split(" ");
            if (content[0].equals(ID)) {
                try {
                    int roleCode = Integer.parseInt(content[content.length-1]);
                    switch (roleCode) {
                        case 1:
                            user = new CourseDirector();
                            break;
                        case 2:
                            user = new Administrator();
                            break;
                        case 3:
                            user = new PartTimeTeacher();
                            break;
                        default:
                            System.out.println("Error, we cannot find your role in the system");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid role code: " + content[content.length-1]);
                }
                break;
            }
        }

        return user;
    }

    public boolean validate (String ID, String password){
        Boolean IDExists = false;
        Boolean successLogin = false;

        for (int i = 0; i < UserAction.userData.size(); i++) {
            String[] content = UserAction.userData.get(i).split(" ");
            if(content[0].equals(ID)){
                IDExists = true;
                if(content[1].equals(password)){
                    successLogin = true;
                    PartTimeTeacherID = ID;
                    System.out.println(ID);
                } else {
                    System.out.println("Sorry, your ID or password is incorrect!");
                }
            }

        }
        if(!IDExists){
            System.out.println("Sorry, the input ID doesn't exist!");
        }

        return successLogin;
    }

    public void run(User user) {
        if (user instanceof CourseDirector) {
            System.out.println("Welcome, CourseDirector");
            CourseDirector courseDirector = (CourseDirector) user;
            courseDirector.run();
        } else if (user instanceof Administrator) {
            System.out.println("Welcome, Administrator");
            Administrator administrator = (Administrator) user;
            administrator.run();
        } else if (user instanceof PartTimeTeacher) {
            System.out.println("Welcome, PartTimeTeacher");
            PartTimeTeacher partTimeTeacher = (PartTimeTeacher) user;
            partTimeTeacher.run();

        }
    }

    public void createCourseRequirements() {
        CourseRequirements courseRequirements = CourseRequirements.getcourseRequirements();

        Scanner a = new Scanner(System.in);

        System.out.println("Input your course requirements:");
        System.out.println("Input course Name:");

        String courseName = a.nextLine().replaceAll(" ", "");
        courseRequirements.setCourseName(courseName);

        System.out.println("Input start time dd/mm/yyyy");
        Scanner b = new Scanner(System.in);
        String start_time = b.nextLine().replaceAll(" ", "");
        courseRequirements.setStart_time(start_time);

        System.out.println("Input course duration dd/mm");
        Scanner c = new Scanner(System.in);
        String duration = c.nextLine().replaceAll(" ", "");
        courseRequirements.setDuration(duration);

        System.out.println("Input required subject");
        Scanner d = new Scanner(System.in);
        String subject= d.nextLine().replaceAll(" ", "");
        courseRequirements.setSubject(subject);

        System.out.println("Input required teacher's teaching level 1~5");
        Scanner f = new Scanner(System.in);
        String teachinglevel= f.nextLine().replaceAll(" ", "");
        courseRequirements.setTeachinglevel(teachinglevel);

        System.out.println("Input required teacher's student affinity 1~5");
        Scanner g = new Scanner(System.in);
        String studentaffinity= g.nextLine().replaceAll(" ", "");
        courseRequirements.setStudentaffinity(studentaffinity);

        try {
            FileWriter writer = new FileWriter("src/com/javacto/courses.txt", true);
            writer.write("\n");
            writer.write(courseRequirements.getCourseName() + " ");
            writer.write(courseRequirements.getStart_time() + " ");
            writer.write(courseRequirements.getDuration() + " ");
            writer.write(courseRequirements.getSubject() + " ");
            writer.write(courseRequirements.getTeachinglevel() + " ");
            writer.write(courseRequirements.getStudentaffinity());
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException ex) {
            System.out.println("An error occurred while writing to local file.");
            ex.printStackTrace();
        }

    }

    public void showCourseRequirements() {

        course:
        while (true) {
            Scanner input = new Scanner(System.in);
            Boolean courseExists = false;

            //input ID
            System.out.println("Please type in the course name");
            String courseTitle = input.nextLine().replaceAll(" ", "");

            for (int i = 0; i < UserAction.courseData.size(); i++) {
                String[] courseContent = UserAction.courseData.get(i).split(" ");
                if (courseContent[0].equals(courseTitle)) {
                    courseExists = true;
                    //course startTime Duration Subject Level Affinity
                    System.out.println("courseName:" + courseContent[0]
                            + ", start_time:" + courseContent[1]
                            + ", duration:" + courseContent[2]
                            + ", subject:" + courseContent[3]
                            + ", level:" + courseContent[4]
                            + ", affinity:" + courseContent[5]
                            + '\n');
                    break course;
                }
            }
            if (!courseExists) {
                System.out.println("The course data is not recorded in the system, please try again!");
            }
        }
    }

    public String selectPartTimeTeacher() {
        System.out.println("ID    Name Subject Level Affinity");

        //show avaliable teachers for selection
        for (int i = 0; i < UserAction.userData.size(); i++) {
            String[] info = UserAction.userData.get(i).split(" ");
            if (info[info.length - 1].equals(String.valueOf(3))) {
                String userInfo = UserAction.userData.get(i);
                String[] userInfos = userInfo.split(" ");
                userInfos[1] = "->";
                userInfos[userInfos.length - 1] = "*";
                System.out.println(Arrays.toString(userInfos));
            }
        }

        Scanner scanner = new Scanner(System.in);
        String choice;

        Choice:
        while (true) {
            System.out.println("Select and Input ID of suitable teacher for this course");
            choice = scanner.nextLine();

            Boolean validChoice = false;
            for (int i = 0; i < UserAction.userData.size(); i++) {
                String[] info = UserAction.userData.get(i).split(" ");
                if (info[info.length - 1].equals(String.valueOf(3))) {
                    if (choice.equals(info[0])) {
                        validChoice = true;
                    }
                }
            }
            if (validChoice) {
                System.out.println("Your choice has been saved! Do you want to train this teacher?" +
                        "\n"+"Answer yes or no:");
                String booleanTrain = scanner.nextLine();
                if(booleanTrain.toLowerCase().equals("yes")){
                    getTraining(choice);
                    break Choice;
                }else{
                    System.out.println("The teacher is ready for lecturing");
                    break Choice;
                }

            } else {
                System.out.println("Invalid Choice, try again!");
            }

        }

        return choice;

    }

    public void showTrainedTeacher() {
            for (int i = 0; i < UserAction.userData.size(); i++) {
                String[] courseContent = UserAction.userData.get(i).split(" ");
                if (courseContent[0].equals(PartTimeTeacherID)) {
                    System.out.println("ID:" + courseContent[0]
                            + ", password:" + courseContent[1]
                            + ", Name:" + courseContent[2]
                            + ", subject:" + courseContent[3]
                            + ", level:" + courseContent[4]
                            + ", Affinity:" + courseContent[5]
                            + ", roleCode:" + courseContent[6]
                            + '\n');
                }
            }
    }

    public void getTraining(String choice) {

        ArrayList<String> modifiedUserData = UserAction.userData;

        for (int i = 0; i < UserAction.userData.size(); i++) {

            String[] info = UserAction.userData.get(i).split(" ");

            if (choice.equals(info[0])) {
                info[4] = String.valueOf(Integer.parseInt(info[4]) + 1);
                String modifiedLine = String.join(" ",info);
                System.out.println(modifiedLine);
                modifiedUserData.set(i,modifiedLine);
            } else {
                modifiedUserData.set(i,UserAction.userData.get(i));
            }

        }
        new DaoImpl().writeFile(modifiedUserData);
        System.out.println("The teacher has been trained");
    }
}

