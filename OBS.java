import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.LinkedHashSet;
import java.util.Set;



public class OBS {
    private static OBS obsSystem = null;
    private ArrayList<Topics> allTopics;
    private ArrayList<Teacher> allTeachers;
    private ArrayList<AcademicOfficer> allAcedimicOfficers;
    private ArrayList<Program> allPrograms;
    private ArrayList<PLO> allPLO;
    private ArrayList<CLO> allCLO;
    private ArrayList<Courses> allCourses;
    private ArrayList<Questions> allQuestions;
    private ArrayList<Evaluation> allEvaluations;
    private int logedInUser;
    private String path;

    //Private Constructor
    private OBS() {

        allTopics = new ArrayList<>();
        allTeachers = new ArrayList<>();
        allAcedimicOfficers = new ArrayList<>();
        allPrograms = new ArrayList<>();
        allPLO = new ArrayList<>();
        allCLO = new ArrayList<>();
        allCourses = new ArrayList<>();
        allQuestions = new ArrayList<>();
        allEvaluations = new ArrayList<>();

        this.path = "C:\\Users\\HP\\IdeaProjects\\design\\src\\Files\\";

        this.Load_OBS_System();
        this.Setting_OBS();
    }

    //Singleton constructor
    public static OBS getInstance() {
        if (obsSystem == null)
            obsSystem = new OBS();
        return obsSystem;
    }

    ///////////////////////////////Functions for loading System///////////////////////////////////////////////
    private void LoadAcademicOfficers() {
        ///////////////////////////(loading Academic Officer)/////////////////////////
        String path = this.path.concat("Officers.txt");
        System.out.println("/////////////////////////////(loading Academic Officer)/////////////////////////////////////////");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(this.ParseTab(data));
                System.out.println(attributes);
                ArrayList<Integer> programIds = null;
                if (attributes.get(3).charAt(0) != '^') {
                    programIds = new ArrayList<>(this.ParseSlash(attributes.get(3)));
                }
                this.allAcedimicOfficers.add(new AcademicOfficer(Integer.valueOf(attributes.get(0)), attributes.get(1), attributes.get(2), null, programIds));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allAcedimicOfficers.size(); i++)
            this.allAcedimicOfficers.get(i).printOfficer();
    }
    private void LoadTeachers() {
        ///////////////////////////(loading Teachers)/////////////////////////
        String path = this.path.concat("Teachers.txt");
        System.out.println("/////////////////////////////(loading Teachers)/////////////////////////////////////////");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(ParseTab(data));
                System.out.println(attributes);
                ArrayList<Integer> courseIds = null;
                ArrayList<Integer> evaluationIds = null;

                if (attributes.get(3).charAt(0) != '^')
                    courseIds = new ArrayList<>(ParseSlash(attributes.get(3)));

                if (attributes.get(4).charAt(0) != '^')
                    evaluationIds = new ArrayList<>(ParseSlash(attributes.get(4)));

                this.allTeachers.add(new Teacher(Integer.valueOf(attributes.get(0)), attributes.get(1), attributes.get(2), null, null, evaluationIds, courseIds));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allTeachers.size(); i++)
            this.allTeachers.get(i).printTeacher();
    }
    private void LoadTopics() {
        ///////////////////////////(loading topics)/////////////////////////
        System.out.println("/////////////////loading topics////////////");
        String path = this.path.concat("Topics.txt");
        //  String path=this.path.concat("topics.txt");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(ParseTab(data));
                System.out.println(attributes);
                ArrayList<Integer> idsOfCLos = null;
                if (attributes.get(2).charAt(0) != '^') {
                    idsOfCLos = new ArrayList<>(ParseSlash(attributes.get(2)));
                }
                this.allTopics.add(new Topics(Integer.valueOf(attributes.get(0)), attributes.get(1), null, idsOfCLos));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < this.allTopics.size(); i++)
            this.allTopics.get(i).PrintTopic();
    }
    private void LoadCLOS() {
        ///////////////////////////(loading CLOs)/////////////////////////
        String path = this.path.concat("CLOS.txt");
        // String path=this.path.concat("clos.txt");
        System.out.println("/////////////////////////////(loading CLOS)////////////////////");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(ParseTab(data));
                System.out.println(attributes);

                ArrayList<Integer> idsofTopics = null;
                ArrayList<Integer> idsofCourses = null;

                if (attributes.get(2).charAt(0) != '^')
                    idsofCourses = new ArrayList<>(ParseSlash(attributes.get(2)));

                if (attributes.get(3).charAt(0) != '^') {
                    idsofTopics = new ArrayList<>(ParseSlash(attributes.get(3)));
                }

                String ploId = attributes.get(4);
                this.allCLO.add(new CLO(Integer.valueOf(attributes.get(0)), attributes.get(1), null, null, idsofTopics, idsofCourses, null, Integer.valueOf(ploId)));

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allCLO.size(); i++)
            this.allCLO.get(i).printCLO();
    }
    private void LoadCourses() {
        ///////////////////////////(loading Courses)/////////////////////////
        String path = this.path.concat("Courses.txt");
        // String path=this.path.concat("courses.txt");
        System.out.println("/////////////////////////////(loading Courses)////////////////////");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(ParseTab(data));
                System.out.println(attributes);
                ArrayList<Integer> idsofPrograms = null;
                ArrayList<Integer> idsOfClos = null;
                ArrayList<Integer> teacherIds = null;
                if (attributes.get(3).charAt(0) != '^')
                    idsofPrograms = new ArrayList<>(ParseSlash(attributes.get(3)));
                if (attributes.get(4).charAt(0) != '^')
                    idsOfClos = new ArrayList<>(ParseSlash(attributes.get(4)));
                if (attributes.get(5).charAt(0) != '^')
                    teacherIds = new ArrayList<>(ParseSlash(attributes.get(5)));

                this.allCourses.add(new Courses(Integer.valueOf(attributes.get(0)), attributes.get(1), null, null, null, Integer.valueOf(attributes.get(2)), idsofPrograms, idsOfClos, teacherIds));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allCourses.size(); i++)
            this.allCourses.get(i).printCourses();
    }
    private void LoadPrograms() {
        ///////////////////////////(loading Programs)/////////////////////////
        System.out.println("/////////////////////////////(loading Programs)/////////////////////////////////////////");
        String path = this.path.concat("Programs.txt");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(ParseTab(data));
                System.out.println(attributes);

                ArrayList<Integer> idsOfCourses = null;
                ArrayList<Integer> idsOfPlos = null;
                ArrayList<Integer> idsofOfficers = null;

                if (attributes.get(2).charAt(0) != '^')
                    idsOfCourses = new ArrayList<>(ParseSlash(attributes.get(2)));
                if (attributes.get(3).charAt(0) != '^')
                    idsOfPlos = new ArrayList<>(ParseSlash(attributes.get(3)));
                if (attributes.get(4).charAt(0) != '^')
                    idsofOfficers = new ArrayList<>(ParseSlash(attributes.get(4)));

                this.allPrograms.add(new Program(Integer.valueOf(attributes.get(0)), attributes.get(1), null, null, null, idsOfCourses, idsOfPlos, idsofOfficers));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allPrograms.size(); i++)
            this.allPrograms.get(i).printProgram();
    }
    private void LoadPLOS() {
        ///////////////////////////(loading PLOS)/////////////////////////
        String path = this.path.concat("PLOS.txt");
        // String path=this.path.concat("plos.txt");
        System.out.println("/////////////////////////////(loading PlOS)/////////////////////////////////////////");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(ParseTab(data));
                ArrayList<Integer> closIds = null;
                if (attributes.get(3).charAt(0) != '^')
                    closIds = new ArrayList<>(ParseSlash(attributes.get(3)));
                System.out.println(attributes);
                this.allPLO.add(new PLO(Integer.valueOf(attributes.get(0)), attributes.get(1), null, Integer.valueOf(attributes.get(2)), null, closIds));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allPLO.size(); i++)
            this.allPLO.get(i).printPLO();
    }
    private void LoadQuestions() {
        ///////////////////////////(loading Questions )/////////////////////////
        String path = this.path.concat("Questions.txt");
        System.out.println("/////////////////////////////(loading Questions)/////////////////////////////////////////");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(this.ParseTab(data));
                System.out.println(attributes);
                ArrayList<Integer> cloIds = null;
                if (attributes.get(2).charAt(0) != '^') {
                    cloIds = new ArrayList<>(this.ParseSlash(attributes.get(2)));
                }
                this.allQuestions.add(new Questions(Integer.valueOf(attributes.get(0)), attributes.get(1), cloIds));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allQuestions.size(); i++)
            this.allQuestions.get(i).printQuestion();
    }
    private void LoadEvaluations() {
        ///////////////////////////(loading Evaluations )/////////////////////////
        String path = this.path.concat("Evaluations.txt");
        System.out.println("/////////////////////////////(loading Evaluations)/////////////////////////////////////////");
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> attributes = new ArrayList<>(this.ParseTab(data));
                System.out.println(attributes);
                ArrayList<Integer> questionIds = null;
                if (attributes.get(3).charAt(0) != '^') {
                    questionIds = new ArrayList<>(this.ParseSlash(attributes.get(3)));
                }

                if (Integer.valueOf(attributes.get(1)) == 1)
                    this.allEvaluations.add(new Assignments(Integer.valueOf(attributes.get(0)), 1, null, questionIds, Integer.valueOf(attributes.get(2))));

                else if (Integer.valueOf(attributes.get(1)) == 2)
                    this.allEvaluations.add(new Quiz(Integer.valueOf(attributes.get(0)), 2, null, questionIds, Integer.valueOf(attributes.get(2))));

                else if (Integer.valueOf(attributes.get(1)) == 3)
                    this.allEvaluations.add(new Exam(Integer.valueOf(attributes.get(0)), 3, null, questionIds, Integer.valueOf(attributes.get(2))));

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < allEvaluations.size(); i++)
            this.allEvaluations.get(i).printEvaluation();
    }
    private void Load_OBS_System() {
        this.LoadAcademicOfficers();
        this.LoadTeachers();
        this.LoadTopics();
        this.LoadCLOS();
        this.LoadCourses();
        this.LoadPrograms();
        this.LoadPLOS();
        this.LoadQuestions();
        this.LoadEvaluations();
    }

    /////////////////////////////Helper Functions for Loading System///////////////////////////////////////////
    private static ArrayList<String> ParseTab(String str) {

        ArrayList<String> list = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '\t' && i != str.length() - 1) {
                temp = temp.concat(String.valueOf(str.charAt(i)));
            } else {
                if (i == str.length() - 1)
                    temp = temp.concat(String.valueOf(str.charAt(i)));
                list.add(list.size(), temp);
                temp = "";
            }
        }

        return list;
    }
    private static ArrayList<Integer> ParseSlash(String str) {

        ArrayList<Integer> list = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '/' && i != str.length() - 1) {
                temp = temp.concat(String.valueOf(str.charAt(i)));
            } else {
                if (i == str.length() - 1)
                    temp = temp.concat(String.valueOf(str.charAt(i)));
                list.add(list.size(), Integer.valueOf(temp));
                temp = "";
            }
        }
        return list;
    }
    //////////////////////////////////Setting Pointers////////////////////////////////////////////////////////
    /////////////////////////////////setting pointers of Topics////////////////////////////////
    private void Setting_CLOS_Of_Topics() {
        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of Topics////////////////////////////////////");
        System.out.println("////////////////////////////////////setting CLOs of Topics////////////////////////////////////");
        for (int i = 0; i < this.allTopics.size(); i++) {
            ArrayList<CLO> clos = new ArrayList<>();
            ArrayList<Integer> tempcloids;
            tempcloids = this.allTopics.get(i).getClosIds();

            if (tempcloids != null) {
                for (int j = 0; j < tempcloids.size(); j++) {
                    for (int k = 0; k < this.allCLO.size(); k++) {
                        if (tempcloids.get(j) == this.allCLO.get(k).getId()) {
                            clos.add(clos.size(), this.allCLO.get(k));

                        }
                    }
                }

                this.allTopics.get(i).setClos(clos);
                System.out.print(" ///////////////////  ");
                for (int m = 0; m < clos.size(); m++) {
                    System.out.print(clos.get(m).getId() + "   ");
                }
            }
        }
    }

    /////////////////////////////////setting pointers of CLOS//////////////////////////////////
    private void Setting_Topics_Of_COS() {
        ////  setting Topics of CLOS ///////
        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of CLOS////////////////////////////////////");
        System.out.println("////////////////////////////////////setting topics of CLOS////");

        for (int i = 0; i < this.allCLO.size(); i++) {
            ArrayList<Topics> topics = new ArrayList<>();
            ArrayList<Integer> temptopicids;
            temptopicids = this.allCLO.get(i).getTopicsIds();


            if (temptopicids != null)
                for (int j = 0; j < temptopicids.size(); j++) {
                    for (int k = 0; k < this.allTopics.size(); k++) {
                        if (temptopicids.get(j) == this.allTopics.get(k).getId()) {
                            topics.add(topics.size(), this.allTopics.get(k));

                        }
                    }
                }

            this.allCLO.get(i).setTopics(topics);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < topics.size(); m++) {
                System.out.print(topics.get(m).getId() + "   ");
            }
        }
    }
    private void Setting_Courses_Of_CLOS() {
        System.out.println();
        System.out.println("////////////////////////////////////setting courses of CLOS//");
        for (int i = 0; i < this.allCLO.size(); i++) {
            ArrayList<Courses> courses = new ArrayList<>();
            ArrayList<Integer> tempCourses;
            tempCourses = this.allCLO.get(i).getCourseIds();

            if (tempCourses != null)
                for (int j = 0; j < tempCourses.size(); j++) {
                    for (int k = 0; k < this.allCourses.size(); k++) {
                        if (tempCourses.get(j) == this.allCourses.get(k).getId()) {
                            courses.add(courses.size(), this.allCourses.get(k));

                        }
                    }
                }

            this.allCLO.get(i).setCourses(courses);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < courses.size(); m++) {
                System.out.print(courses.get(m).getId() + "   ");
            }
        }

//        for(int i=0;i< this.allCLO.size();i++)
//            this.allCLO.get(i).printCLO();

    }
    private void Setting_PLO_Of_CLOS() {
        System.out.println();
        System.out.println("////////////////////////////////////setting PLO of CLOS//////");
        for (int i = 0; i < this.allCLO.size(); i++) {
            PLO plo = null;
            int tempPlosId = this.allCLO.get(i).getPloId();

            if (this.allPLO != null && !this.allPLO.isEmpty())
                for (int k = 0; k < this.allPLO.size(); k++) {
                    if (tempPlosId == this.allPLO.get(k).getId()) {
                        plo = this.allPLO.get(k);
                        break;
                    }
                }

            this.allCLO.get(i).setPlo(plo);
            System.out.print(" ///////////////////  ");
            if (plo != null)
                System.out.print(plo.getId() + "   ");

        }

//        for(int i=0;i< this.allCLO.size();i++)
//            this.allCLO.get(i).printCLO();
    }
    /////////////////////////////////setting pointers of Course////////////////////////////////
    private void Setting_CLOS_Of_Courses() {
        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of Course////////////////////////////////////");
        System.out.println("////////////////////////////////////setting CLOS of Course//");
        for (int i = 0; i < this.allCourses.size(); i++) {
            ArrayList<CLO> clos = new ArrayList<CLO>();
            ArrayList<Integer> tempclos;
            tempclos = this.allCourses.get(i).getClosIds();

            if (tempclos != null)
                for (int j = 0; j < tempclos.size(); j++) {
                    for (int k = 0; k < this.allCLO.size(); k++) {
                        if (tempclos.get(j) == this.allCLO.get(k).getId()) {
                            clos.add(clos.size(), this.allCLO.get(k));

                        }
                    }
                }

            this.allCourses.get(i).setClos(clos);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < clos.size(); m++) {
                System.out.print(clos.get(m).getId() + "   ");
            }
        }


        //        for(int i=0;i< this.allCourses.size();i++)
        //           this.allCourses.get(i).printCourses();


    }
    private void Setting_Programs_Of_Courses() {
        System.out.println();
        System.out.println("////////////////////////////////////setting Programs of Course//");
        for (int i = 0; i < this.allCourses.size(); i++) {
            ArrayList<Program> programs = new ArrayList<>();
            ArrayList<Integer> tempprograms;
            tempprograms = this.allCourses.get(i).getProgramIds();

            if (tempprograms != null)
                for (int j = 0; j < tempprograms.size(); j++) {
                    for (int k = 0; k < this.allPrograms.size(); k++) {
                        if (tempprograms.get(j) == this.allPrograms.get(k).getId()) {
                            programs.add(programs.size(), this.allPrograms.get(k));

                        }
                    }
                }

            this.allCourses.get(i).setPrograms(programs);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < programs.size(); m++) {
                System.out.print(programs.get(m).getId() + "   ");
            }
        }

        //       for(int i=0;i< this.allCourses.size();i++)
        //          this.allCourses.get(i).printCourses();
    }
    private void Setting_Teachers_Of_Courses() {
        System.out.println();
        System.out.println("////////////////////////////////////setting teachers of Course//");
        for (int i = 0; i < this.allCourses.size(); i++) {
            ArrayList<Teacher> teachers = new ArrayList<>();
            ArrayList<Integer> tempteacher;
            tempteacher = this.allCourses.get(i).getTeacherIds();

            if (tempteacher != null)
                for (int j = 0; j < tempteacher.size(); j++) {
                    for (int k = 0; k < this.allTeachers.size(); k++) {
                        if (tempteacher.get(j) == this.allTeachers.get(k).getId()) {
                            teachers.add(teachers.size(), this.allTeachers.get(k));

                        }
                    }
                }

            this.allCourses.get(i).setTeachers(teachers);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < teachers.size(); m++) {
                System.out.print(teachers.get(m).getId() + "   ");
            }


        }

        //        for(int i=0;i< this.allCourses.size();i++)
        //           this.allCourses.get(i).printCourses();
    }

    ///////////////////////////////////Settings Pointers Of Programs///////////////////////////
    private void Setting_Courses_Of_Programs() {

        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of Programs////////////////////////////////////");
        System.out.println("////////////////////////////////////setting Course of Programs//");
        for (int i = 0; i < this.allPrograms.size(); i++) {
            ArrayList<Courses> courses = new ArrayList<>();
            ArrayList<Integer> tempcourses;
            tempcourses = this.allPrograms.get(i).getCourseIds();

            if (tempcourses != null)
                for (int j = 0; j < tempcourses.size(); j++) {
                    for (int k = 0; k < this.allCourses.size(); k++) {
                        if (tempcourses.get(j) == this.allCourses.get(k).getId()) {
                            courses.add(courses.size(), this.allCourses.get(k));

                        }
                    }
                }

            this.allPrograms.get(i).setCourses(courses);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < courses.size(); m++) {
                System.out.print(courses.get(m).getId() + "   ");
            }
        }

//       for(int i=0;i<this.allPrograms.size();i++)
//          this.allPrograms.get(i).printProgram();

    }
    private void Setting_PLOS_Of_Programs() {
        System.out.println();
        System.out.println("////////////////////////////////////setting PLO of Programs//");
        for (int i = 0; i < this.allPrograms.size(); i++) {
            ArrayList<PLO> plos = new ArrayList<>();
            ArrayList<Integer> tempcourses;
            tempcourses = this.allPrograms.get(i).getPloIds();

            if (tempcourses != null)
                for (int j = 0; j < tempcourses.size(); j++) {
                    for (int k = 0; k < this.allPLO.size(); k++) {
                        if (tempcourses.get(j) == this.allPLO.get(k).getId()) {
                            plos.add(plos.size(), this.allPLO.get(k));

                        }
                    }
                }

            this.allPrograms.get(i).setPlo(plos);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < plos.size(); m++) {
                System.out.print(plos.get(m).getId() + "   ");
            }
        }

//       for(int i=0;i< this.allPrograms.size();i++)
//          this.allPrograms.get(i).printProgram();
    }
    private void Setting_Officers_Of_Programs() {
        System.out.println();
        System.out.println("////////////////////////////////////setting Officers of Programs//");
        if (this.allPrograms != null || this.allAcedimicOfficers != null)
            for (int i = 0; i < this.allPrograms.size(); i++) {
                this.allPrograms.get(i).setOfficers(this.allAcedimicOfficers);

                System.out.print(" ///////////////////  ");

                System.out.print(this.allPrograms.get(i).getOfficerIds());

            }

//       for(int i=0;i< this.allPrograms.size();i++)
//          this.allPrograms.get(i).printProgram();

    }
    //////////////////////////////////////////Setting pointers Of PLO///////////////////////////
    private void Setting_Programs_Of_PLOS() {
        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of PLO////////////////////////////////////");
        System.out.println("////////////////////////////////////setting Program of PLOs//");
        for (int i = 0; i < this.allPLO.size(); i++) {
            Program program = null;
            int tempPloid = this.allPLO.get(i).getProgramId();

            for (int j = 0; j < this.allPrograms.size(); j++) {
                if (tempPloid == this.allPrograms.get(j).getId())
                    program = this.allPrograms.get(j);
            }

            this.allPLO.get(i).setProgram(program);
            System.out.print(" ///////////////////  ");
            System.out.print(tempPloid);
        }
//               for(int i=0;i< this.allPLO.size();i++)
//          this.allPLO.get(i).printPLO();
    }
    private void Setting_Clos_Of_PLOS() {
        System.out.println();
        System.out.println("////////////////////////////////////setting CLOS OF PLOS//");
        for (int i = 0; i < this.allPLO.size(); i++) {
            ArrayList<CLO> clos = new ArrayList<>();
            ArrayList<Integer> tempclos;
            tempclos = this.allPLO.get(i).getPlosClosIds();

            if (tempclos != null)
                for (int j = 0; j < tempclos.size(); j++) {
                    for (int k = 0; k < this.allCLO.size(); k++) {
                        if (tempclos.get(j) == this.allCLO.get(k).getId()) {
                            clos.add(clos.size(), this.allCLO.get(k));


                        }
                    }
                }
            this.allPLO.get(i).setPlosClos(clos);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < clos.size(); m++) {
                System.out.print(clos.get(m).getId() + "   ");
            }
        }

//       for(int i=0;i< this.allPrograms.size();i++)
//          this.allPrograms.get(i).printProgram();
    }
    //////////////////////////////////////Setting Pointers Of Academic Ofiicers/////////////////
    private void Setting_Programs_Of_Officers() {
        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of Academic Officer////////////////////////////////////");
        System.out.println("////////////////////////////////////setting Programs of Academic Officer//");
        if (this.allAcedimicOfficers != null || this.allPrograms != null)
            for (int i = 0; i < this.allAcedimicOfficers.size(); i++) {
                this.allAcedimicOfficers.get(i).setPrograms(this.allPrograms);

                System.out.print(" ///////////////////  ");

                System.out.print(this.allAcedimicOfficers.get(i).getProgramIds());

            }

//       for(int i=0;i< this.allAcedimicOfficers.size();i++)
//          this.allAcedimicOfficers.get(i).printOfficer();
        System.out.println('\n');
    }
    /////////////////////////////////////Setting Pointers of Teachers//////////////////////////
    private void Setting_Courses_Of_Teachers() {
        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of Teachers////////////////////////////////////");
        System.out.println("////////////////////////////////////setting Courses Of Teachers//");
        for (int i = 0; i < this.allTeachers.size(); i++) {
            ArrayList<Courses> courses = new ArrayList<>();
            ArrayList<Integer> tempCourses;
            tempCourses = this.allTeachers.get(i).getCoursesId();

            if (tempCourses != null)
                for (int j = 0; j < tempCourses.size(); j++) {
                    for (int k = 0; k < this.allCourses.size(); k++) {
                        if (tempCourses.get(j) == this.allCourses.get(k).getId()) {
                            courses.add(courses.size(), this.allCourses.get(k));

                        }
                    }
                }

            this.allTeachers.get(i).setCourses(courses);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < courses.size(); m++) {
                System.out.print(courses.get(m).getId() + "   ");
            }

        }
        System.out.println('\n');
    }
    private void Setting_Evaluations_Of_Teachers() {
        System.out.println();
        System.out.println("////////////////////////////////////setting Evaluations Of Teachers//");
        for (int i = 0; i < this.allTeachers.size(); i++) {
            ArrayList<Evaluation> evaluations = new ArrayList<>();
            ArrayList<Integer> tempEvaluations;
            tempEvaluations = this.allTeachers.get(i).getEvaluationIds();

            if (tempEvaluations != null)
                for (int j = 0; j < tempEvaluations.size(); j++) {
                    for (int k = 0; k < this.allEvaluations.size(); k++) {
                        if (tempEvaluations.get(j) == this.allEvaluations.get(k).getId()) {
                            evaluations.add(evaluations.size(), this.allEvaluations.get(k));

                        }
                    }
                }

            this.allTeachers.get(i).setEvaluation(evaluations);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < evaluations.size(); m++) {
                System.out.print(evaluations.get(m).getId() + "   ");
            }

        }
        System.out.println('\n');
    }
    /////////////////////////////////////Setting Pointers of Evaluations//////////////////////////
    private void Setting_Questions_Of_Evaluations() {
        System.out.println();
        System.out.println("////////////////////////////////////setting pointers of Evaluations////////////////////////////////////");
        System.out.println("////////////////////////////////////setting Questions Of Evaluation//");
        for (int i = 0; i < this.allEvaluations.size(); i++) {
            ArrayList<Questions> questions = new ArrayList<>();
            ArrayList<Integer> tempQuestions;
            tempQuestions = this.allEvaluations.get(i).getQuestionsIds();

            if (tempQuestions != null && !tempQuestions.isEmpty())
                for (int j = 0; j < tempQuestions.size(); j++) {
                    for (int k = 0; k < this.allQuestions.size(); k++) {
                        if (tempQuestions.get(j) == this.allQuestions.get(k).getId()) {
                            questions.add(questions.size(), this.allQuestions.get(k));

                        }
                    }
                }

            this.allEvaluations.get(i).setQuestions(questions);
            System.out.print(" ///////////////////  ");
            for (int m = 0; m < questions.size(); m++) {
                System.out.print(questions.get(m).getId() + "   ");
            }

        }
        System.out.println('\n');
    }
    //////////////////////////Setting Whole OBS////////////////////////////////////////////////
    private void Setting_OBS() {
        this.Setting_CLOS_Of_Topics();
        this.Setting_Topics_Of_COS();
        this.Setting_Courses_Of_CLOS();
        this.Setting_PLO_Of_CLOS();
        this.Setting_CLOS_Of_Courses();
        this.Setting_Programs_Of_Courses();
        this.Setting_Teachers_Of_Courses();
        this.Setting_Courses_Of_Programs();
        this.Setting_PLOS_Of_Programs();
        this.Setting_Officers_Of_Programs();
        this.Setting_Programs_Of_PLOS();
        this.Setting_Clos_Of_PLOS();
        this.Setting_Programs_Of_Officers();
        this.Setting_Courses_Of_Teachers();
        this.Setting_Evaluations_Of_Teachers();
        this.Setting_Questions_Of_Evaluations();

    }

    //////////////////////////////////Functions For Saving System/////////////////////////////////////////////
    private void Save_Topics() {
        System.out.println("\n\n");
        System.out.println("////////////////////////saving files//////////////////////////////////////////////");
        System.out.println("\n\n");
        System.out.println("////////////////////////saving Topics file//////////////////////////////////////////////");
        String path = this.path.concat("Topics.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\t\tName \t\tCLOS\n");
            while (i < this.allTopics.size()) {
                String data = String.valueOf(this.allTopics.get(i).getId());
                data = data.concat('\t' + this.allTopics.get(i).getName() + '\t');
                String clos = "";
                ArrayList<Integer> popClos = this.allTopics.get(i).getClosIds();

                if (popClos != null && !popClos.isEmpty()) {
                    for (int j = 0; j < popClos.size(); j++) {
                        if (j != popClos.size() - 1)
                            clos = clos.concat(String.valueOf(popClos.get(j)) + '/');
                        else {
                            clos = clos.concat(String.valueOf(popClos.get(j)));
                        }
                    }
                } else clos = clos.concat("^");

                data = data.concat(clos + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    private void Save_Clos() {

        System.out.println("\n\n");
        System.out.println("////////////////////////saving CLOS file//////////////////////////////////////////////");
        String path = this.path.concat("CLOS.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\t\t\t\t Objective\t\t\tCoures  Topics  PLOS\n");
            while (i < this.allCLO.size()) {
                String data = String.valueOf(this.allCLO.get(i).getId());
                data = data.concat('\t' + this.allCLO.get(i).getObjective() + '\t');
                String courses = "";
                String topics = "";
                ArrayList<Integer> popCourses = this.allCLO.get(i).getCourseIds();
                ArrayList<Integer> popTopics = this.allCLO.get(i).getTopicsIds();
                if (popTopics != null && !popCourses.isEmpty()) {
                    for (int j = 0; j < popCourses.size(); j++) {
                        if (j != popCourses.size() - 1)
                            courses = courses.concat(String.valueOf(popCourses.get(j)) + '/');
                        else {
                            courses = courses.concat(String.valueOf(popCourses.get(j)));
                        }
                    }
                } else courses = courses.concat("^");

                if (popTopics != null && !popTopics.isEmpty()) {
                    for (int j = 0; j < popTopics.size(); j++) {
                        if (j != popTopics.size() - 1)
                            topics = topics.concat(String.valueOf(popTopics.get(j)) + '/');
                        else {
                            topics = topics.concat(String.valueOf(popTopics.get(j)));
                        }
                    }
                } else topics = topics.concat("^");


                data = data.concat(courses + '\t' + topics + '\t' + this.allCLO.get(i).getPloId() + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
    private void Save_Courses() {
        System.out.println("\n\n");
        System.out.println("////////////////////////saving Courses file//////////////////////////////////////////////");
        String path = this.path.concat("Courses.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\t\tName\t\t\tCH\tProgss\tCLOSs\tTeachss\n");
            while (i < this.allCourses.size()) {
                String data = String.valueOf(this.allCourses.get(i).getId());
                data = data.concat('\t' + this.allCourses.get(i).getName() + '\t');
                data = data.concat(String.valueOf(this.allCourses.get(i).getCreditHours()) + '\t');
                String programs = "";
                String clos = "";
                String teachers = "";
                ArrayList<Integer> popprograms = this.allCourses.get(i).getProgramIds();
                ArrayList<Integer> popclos = this.allCourses.get(i).getCloIds();
                ArrayList<Integer> popteachser = this.allCourses.get(i).getTeacherIds();
                if (popprograms != null && !popprograms.isEmpty()) {
                    for (int j = 0; j < popprograms.size(); j++) {
                        if (j != popprograms.size() - 1)
                            programs = programs.concat(String.valueOf(popprograms.get(j)) + '/');
                        else {
                            programs = programs.concat(String.valueOf(popprograms.get(j)));
                        }
                    }
                } else programs = programs.concat("^");

                if (popclos != null && !popclos.isEmpty()) {
                    for (int j = 0; j < popclos.size(); j++) {
                        if (j != popclos.size() - 1)
                            clos = clos.concat(String.valueOf(popclos.get(j)) + '/');
                        else {
                            clos = clos.concat(String.valueOf(popclos.get(j)));
                        }
                    }
                } else clos = clos.concat("^");

                if (popteachser != null && !popteachser.isEmpty()) {
                    for (int j = 0; j < popteachser.size(); j++) {
                        if (j != popteachser.size() - 1)
                            teachers = teachers.concat(String.valueOf(popteachser.get(j)) + '/');
                        else {
                            teachers = teachers.concat(String.valueOf(popteachser.get(j)));
                        }
                    }
                } else teachers = teachers.concat("^");

                data = data.concat(programs + '\t' + clos + '\t' + teachers + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    private void Save_Programs() {
        System.out.println("\n\n");
        System.out.println("////////////////////////saving Program file//////////////////////////////////////////////");
        String path = this.path.concat("Programs.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\t\tName\t\tCours\tPLOS\tOfficers\n");
            while (i < this.allPrograms.size()) {
                String data = String.valueOf(this.allCourses.get(i).getId());
                data = data.concat('\t' + this.allPrograms.get(i).getName() + '\t');
                String courses = "";
                String plos = "";
                String officers = "";
                ArrayList<Integer> popcourses = this.allPrograms.get(i).getCourseIds();
                ArrayList<Integer> popplos = this.allPrograms.get(i).getPloIds();
                ArrayList<Integer> popofficers = this.allPrograms.get(i).getOfficerIds();
                if (popcourses != null && !popcourses.isEmpty())
                    for (int j = 0; j < popcourses.size(); j++) {
                        if (j != popcourses.size() - 1)
                            courses = courses.concat(String.valueOf(popcourses.get(j)) + '/');
                        else {
                            courses = courses.concat(String.valueOf(popcourses.get(j)));
                        }
                    }
                else courses = courses.concat("^");

                if (popplos != null && !popplos.isEmpty())
                    for (int j = 0; j < popplos.size(); j++) {
                        if (j != popplos.size() - 1)
                            plos = plos.concat(String.valueOf(popplos.get(j)) + '/');
                        else {
                            plos = plos.concat(String.valueOf(popplos.get(j)));
                        }
                    }
                else plos = plos.concat("^");

                if (popofficers != null && !popofficers.isEmpty())
                    for (int j = 0; j < popofficers.size(); j++) {
                        if (j != popofficers.size() - 1)
                            officers = officers.concat(String.valueOf(popofficers.get(j)) + '/');
                        else {
                            officers = officers.concat(String.valueOf(popofficers.get(j)));
                        }
                    }
                else officers = officers.concat("^");

                data = data.concat(courses + '\t' + plos + '\t' + officers + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    private void Save_PLOS() {

        System.out.println("\n\n");
        System.out.println("////////////////////////saving PLO file//////////////////////////////////////////////");
        String path = this.path.concat("plos.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\t\t\t\t\tObjective\t\t\t  Prog\t\tCLOS\n");
            while (i < this.allPLO.size()) {
                String data = String.valueOf(this.allCourses.get(i).getId());
                data = data.concat('\t' + this.allPLO.get(i).getObjective() + '\t');
                data = data.concat(String.valueOf(this.allPLO.get(i).getProgramId()) + '\t');
                ArrayList<Integer> plosClosIds = new ArrayList<>(this.allPLO.get(i).getPlosClosIds());
                String plosClosId = "";
                if (plosClosIds != null && !plosClosIds.isEmpty())
                    for (int j = 0; j < plosClosIds.size(); j++) {
                        if (j != plosClosIds.size() - 1)
                            plosClosId = plosClosId.concat(String.valueOf(plosClosIds.get(j)) + '/');
                        else {
                            plosClosId = plosClosId.concat(String.valueOf(plosClosIds.get(j)));
                        }
                    }
                else plosClosId = plosClosId.concat("^");
                data = data.concat(plosClosId + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void Save_Officers() {
        System.out.println("\n\n");
        System.out.println("////////////////////////saving Officer file//////////////////////////////////////////////");
        String path = this.path.concat("Officers.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID	     Name 		Password	Programs\n");
            while (i < this.allAcedimicOfficers.size()) {
                String data = String.valueOf(this.allAcedimicOfficers.get(i).getId());
                data = data.concat('\t' + this.allAcedimicOfficers.get(i).getName() + '\t');
                data = data.concat(this.allAcedimicOfficers.get(i).getPassword() + '\t');
                String programs = "";
                ArrayList<Integer> popprograms = this.allAcedimicOfficers.get(i).getProgramIds();

                if (popprograms != null && !popprograms.isEmpty())
                    for (int j = 0; j < popprograms.size(); j++) {
                        if (j != popprograms.size() - 1)
                            programs = programs.concat(String.valueOf(popprograms.get(j)) + '/');
                        else {
                            programs = programs.concat(String.valueOf(popprograms.get(j)));
                        }
                    }
                else programs = programs.concat("^");
                data = data.concat(programs + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    private void Save_Teacher() {
        System.out.println("\n\n");
        System.out.println("////////////////////////saving Teacher file//////////////////////////////////////////////");
        String path = this.path.concat("Teachers.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\t Name\t\tPassword\tCourss\tEvalss\n");
            while (i < this.allTeachers.size()) {
                String data = String.valueOf(this.allTeachers.get(i).getId());
                data = data.concat('\t' + this.allTeachers.get(i).getName() + '\t');
                data = data.concat(this.allTeachers.get(i).getPassword() + '\t');
                String courses = "";
                String evaluations = "";
                ArrayList<Integer> popcourses = this.allTeachers.get(i).getCoursesId();
                ArrayList<Integer> popevaluations = this.allTeachers.get(i).getEvaluationIds();

                if (popcourses != null && !popcourses.isEmpty())
                    for (int j = 0; j < popcourses.size(); j++) {
                        if (j != popcourses.size() - 1)
                            courses = courses.concat(String.valueOf(popcourses.get(j)) + '/');
                        else {
                            courses = courses.concat(String.valueOf(popcourses.get(j)));
                        }
                    }
                else courses = courses.concat("^");


                if (popevaluations != null && !popevaluations.isEmpty())
                    for (int j = 0; j < popevaluations.size(); j++) {
                        if (j != popevaluations.size() - 1)
                            evaluations = evaluations.concat(String.valueOf(popevaluations.get(j)) + '/');
                        else {
                            evaluations = evaluations.concat(String.valueOf(popevaluations.get(j)));
                        }
                    }
                else evaluations = evaluations.concat("^");


                data = data.concat(courses + '\t' + evaluations + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void Save_Questions() {
        System.out.println("\n\n");
        System.out.println("////////////////////////saving Questions file//////////////////////////////////////////////");
        String path = this.path.concat("Questions.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\t\t\t\tStatement\t\t\tCLOSs\n");
            while (i < this.allQuestions.size()) {
                String data = String.valueOf(this.allQuestions.get(i).getId());
                data = data.concat('\t' + this.allQuestions.get(i).getStatement() + '\t');

                String clos = "";
                ArrayList<Integer> popclos = this.allQuestions.get(i).getClos();

                if (popclos != null && !popclos.isEmpty())
                    for (int j = 0; j < popclos.size(); j++) {
                        if (j != popclos.size() - 1)
                            clos = clos.concat(String.valueOf(popclos.get(j)) + '/');
                        else {
                            clos = clos.concat(String.valueOf(popclos.get(j)));
                        }
                    }
                else clos = clos.concat("^");


                data = data.concat(clos + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void Save_Evaluations() {
        System.out.println("\n\n");
        System.out.println("////////////////////////saving Evaluation file//////////////////////////////////////////////");
        String path = this.path.concat("Evaluations.txt");
        try {
            FileWriter myWriter = new FileWriter(path);
            int i = 0;
            myWriter.write("ID\tType\tcourse\tQuess\n");
            while (i < this.allEvaluations.size()) {
                String data = String.valueOf(this.allEvaluations.get(i).getId());
                data = data.concat('\t' + String.valueOf(this.allEvaluations.get(i).getType()) + '\t');
                data = data.concat(String.valueOf(this.allEvaluations.get(i).getCoursesId()) + '\t');
                String questions = "";
                ArrayList<Integer> popQuestions = this.allEvaluations.get(i).getQuestionsIds();

                if (popQuestions != null && !popQuestions.isEmpty())
                    for (int j = 0; j < popQuestions.size(); j++) {
                        if (j != popQuestions.size() - 1)
                            questions = questions.concat(String.valueOf(popQuestions.get(j)) + '/');
                        else {
                            questions = questions.concat(String.valueOf(popQuestions.get(j)));
                        }
                    }
                else questions = questions.concat("^");

                data = data.concat(questions + '\n');
                System.out.print(data);
                myWriter.write(data);
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void Save_OBS_System() {
        this.Save_Topics();
        this.Save_Officers();
        this.Save_Clos();
        this.Save_Courses();
        this.Save_PLOS();
        this.Save_Programs();
        this.Save_Teacher();
        this.Save_Evaluations();
        this.Save_Questions();
    }


    /////////////////////////////////////////Functionalities of ODS///////////////////////////////////////////
    /////////////////////////////////////////////////////(1)Login/////////////////////////
    public int Login() {
        System.out.println("**************************<<(Welcome To OBES)>>**************************");
        System.out.println();
        System.out.println("-----------------------------<(Login Panel)>-----------------------------");
        boolean login = false;
        AcademicOfficer logedInOfficer = null;
        Teacher logedInTeacher = null;

        Scanner scan = new Scanner(System.in);

        while (login == false) {
            System.out.print("Enter Login ID:   ");
            int ID = scan.nextInt();
            scan.nextLine();
            System.out.print("Enter password:   ");
            String pass = scan.nextLine();

            ////checking user in academic officers
            for (int i = 0; i < this.allAcedimicOfficers.size(); i++) {
                if (allAcedimicOfficers.get(i).getId() == ID && allAcedimicOfficers.get(i).getPassword().equals(pass)) {
                    logedInOfficer = allAcedimicOfficers.get(i);
                    this.logedInUser = allAcedimicOfficers.get(i).getId();
                    login = true;
                }
            }
            if (login == false) ///if user not found in officers checking in users
            {
                for (int i = 0; i < this.allTeachers.size(); i++) {
                    if (allTeachers.get(i).getId() == ID && allTeachers.get(i).getPassword().equals(pass)) {
                        logedInTeacher = allTeachers.get(i);
                        this.logedInUser = allTeachers.get(i).getId();
                        login = true;
                    }
                }
            }
            if (login == false) {
                System.out.println("Invalid Credentials :(..............Please Recheck LoginID or Password.....!!!");
                System.out.println();
            }
        }

        Wait("Logging In");

        if (logedInOfficer != null) {
            System.out.println();
            System.out.println("****************************************************************************");
            System.out.println("*    Logged In Successfully :) (Welcome)...............................    *");
            System.out.println("****************************************************************************");
            System.out.println();
            System.out.println("<<>><<>><<>>>><<>><<(Hello Officer " + logedInOfficer.getName()+"))>><<>><<>>>><<>><<>>");
            System.out.println();
        }
        else {
            System.out.println("****************************************************************************");
            System.out.println("*    Logged In Successfully :) (Welcome)...............................    *");
            System.out.println("****************************************************************************");
            System.out.println();
            System.out.println("<<>><<>><<>>>><<>><<(Hello Teacher " + logedInTeacher.getName()+"))>><<>><<>>>><<>><<>>");
            System.out.println();
        }
        return this.logedInUser;

    }
    //helper functions for log In
    public AcademicOfficer RetrieverLogedInOfficer(int Id) {
        for (int i = 0; i < this.allAcedimicOfficers.size(); i++) {
            if (allAcedimicOfficers.get(i).getId() == Id) {
                return allAcedimicOfficers.get(i);
            }
        }
        return null;
    }
    public Teacher RetrieverLogedInTeacher(int Id) {
        for (int i = 0; i < this.allTeachers.size(); i++) {
            if (allTeachers.get(i).getId() == Id) {
                return allTeachers.get(i);
            }
        }
        return null;
    }
    /////////////////////////////////////////////////////(2)Managing CLOs///////////////
    public void AddCLO(AcademicOfficer ao) {

        ///////////////addingCLO////////////////////
        System.out.println(".....................(Adding CLO).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select PLO ..................................");
        for (int i = 0; i < this.allPLO.size(); i++) {
            System.out.println((i + 1) + ". " + "ID: " + this.allPLO.get(i).getId() + "  " + this.allPLO.get(i).getObjective());
        }
        System.out.print("Enter PLO No : ");
        int selected_PloId = scan.nextInt() - 1;
        if (selected_PloId > this.allPLO.size() - 1 || selected_PloId < 0) {
            System.out.println("Sorry you Enter Incorrect Option.........");
            this.Wait("Returning");
            System.out.println(".....................CLO Not Created :(................");
            return;
        }
        PLO selected_Plo = this.allPLO.get(selected_PloId);

        ////////////making id for clo///////////////
        int cloid = 1;
        if (this.allCLO != null && !this.allCLO.isEmpty()) {
            ArrayList<Integer> cloIds = new ArrayList<>();
            for (int i = 0; i < this.allCLO.size(); i++) {
                cloIds.add(this.allCLO.get(i).getId());
            }
            cloIds.sort(Collections.reverseOrder());
            cloid = cloIds.get(0) + 1;
        }

        CLO clo = ao.addCLO(selected_Plo, cloid);
        if (clo != null) {
            this.Wait("Saving");
            this.allCLO.add(this.allCLO.size(), clo);
            this.Save_Clos();
            this.Save_Topics();
            this.Save_Courses();
            this.Save_PLOS();
            System.out.println(".....................CLO created Successfully :)................");
        } else {
            System.out.println(".....................CLO Not Created :(................");
            return;
        }

    }
    public void deleteClo(AcademicOfficer ao) {

        ///////////////Deleting CLO////////////////////
        System.out.println(".....................(Deleting CLO).....................");
        Scanner scan = new Scanner(System.in);

        if (this.allPLO != null && !this.allPLO.isEmpty()) {
            System.out.println("Select PLO .............");
            for (int i = 0; i < this.allPLO.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.allPLO.get(i).getId() + "  " + this.allPLO.get(i).getObjective());
            }
            System.out.print("Enter PLO No : ");
            int selected_PloId = scan.nextInt() - 1;
            if (selected_PloId > this.allPLO.size() - 1 || selected_PloId < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................CLO Not Deleted :(................");
                return;
            }
            PLO selected_Plo = this.allPLO.get(selected_PloId);
            CLO clo = ao.DeleteCLO(selected_Plo);
            if (clo != null) {
                this.Wait("Deleting CLo");
                for (int i = 0; i < allCLO.size(); i++) {
                    if (allCLO.get(i) == clo)
                        allCLO.remove(i);
                }
                this.Wait("Saving");
                this.Save_Clos();
                this.Save_PLOS();
                this.Save_Courses();
                this.Save_Topics();

                System.out.println(".................CLO Deleted Successfully :) ...............)");
            } else System.out.println(".................CLO NOt Deleted  :( ...............)");
        } else {
            System.out.println("Sorry NO PLOS to Show.Please Add Plo first and assign CLO to it");
            System.out.println(".................CLO NOt Deleted  :( ...............)");
        }
    }
    public void updateCLO(AcademicOfficer ao) {
        ///////////////Update CLO////////////////////
        System.out.println(".....................(Updating CLO).....................");
        Scanner scan = new Scanner(System.in);
        if (this.allPLO != null && !this.allPLO.isEmpty()) {
            System.out.println("Select PLO .............");
            for (int i = 0; i < this.allPLO.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.allPLO.get(i).getId() + "  " + this.allPLO.get(i).getObjective());
            }
            System.out.print("Enter PLO No : ");
            int selected_PloId = scan.nextInt() - 1;
            if (selected_PloId > this.allPLO.size() - 1 || selected_PloId < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................CLO Not Updated :(................");
                return;
            }
            PLO selected_Plo = this.allPLO.get(selected_PloId);
            if (ao.updateCLO(selected_Plo) == 0) {
                this.Wait("Saving");
                this.Save_Clos();
                this.Save_Topics();
                this.Save_Courses();
                this.Save_PLOS();

            } else {
                System.out.println(".....................CLO Not Updated :(................");
            }
        } else {
            System.out.println("Sorry NO Plo to Show. Please first create a PLO and assign CLO to that PLO");
            System.out.println(".....................CLO Not Updated :(................");
        }
    }
    /////////////////////////////////////////////////////(3)Managing Courses///////////////
    public void AddCourse(AcademicOfficer ao) {


        ////////////making id for course///////////////
        int course_id = 1;
        if (this.allCourses != null && !this.allCourses.isEmpty()) {
            ArrayList<Integer> course_ids = new ArrayList<>();
            for (int i = 0; i < this.allCourses.size(); i++) {
                course_ids.add(this.allCourses.get(i).getId());
            }
            course_ids.sort(Collections.reverseOrder());
            course_id = course_ids.get(0) + 1;
        }

        Courses course = ao.addCourse(course_id);
        if (course != null) {
            this.Wait("Saving");
            this.allCourses.add(this.allCourses.size(), course);
            this.Save_Clos();
            this.Save_Teacher();
            this.Save_Courses();
            this.Save_Programs();
            System.out.println(".....................Course created Successfully :)................");
        } else {
            System.out.println(".....................Course Not Created :(................");
            return;
        }

    }
    public void deleteCourse(AcademicOfficer ao) {

        Courses course = ao.DeleteCourse();
        if (course != null) {
            this.Wait("Deleting Course");
            this.allCourses.remove(course);
            this.Wait("Saving");
            this.Save_Courses();
            this.Save_Clos();
            this.Save_Programs();
            this.Save_Teacher();

            System.out.println(".................Course Deleted Successfully :) ...............)");
        } else System.out.println(".................Course NOt Deleted  :( ...............)");

    }
    public void updateCourse(AcademicOfficer ao) {
        ///////////////Update CLO////////////////////
        System.out.println(".....................(Updating Course).....................");

        if (ao.updateCourse() == 0) {
            this.Wait("Saving");
            this.Save_Clos();
            this.Save_Courses();
            this.Save_Programs();
            this.Save_Teacher();
            System.out.println("..................... Course Updated Successfully :).....................");

        } else {
            System.out.println(".....................CLO Not Updated :(................");
        }
    }
    /////////////////////////////////////////////////////(4)Managing Evaluations///////////////
    public void AddEvaluation(Teacher t) {

        Evaluation ev = null;
        ev = t.addEvaluation();
        if (ev != null) {
            this.Wait("Saving");
            this.getAllEvaluations().add(ev);
            this.Save_Teacher();
            this.Save_Evaluations();
            this.Save_Questions();
            System.out.println(".....................Evaluation Created Successfully :)................");
        }
    }
    public void deleteEvaluation(Teacher t) {

        Evaluation ev = null;
        ev = t.deleteEvaluation();
        if (ev != null) {
            this.Wait("Saving");
            this.getAllEvaluations().remove(ev);
            this.Save_Teacher();
            this.Save_Evaluations();
            System.out.println(".....................Evaluation Deleted Successfully :)................");
        }
    }
    public void updateEvaluation(Teacher t) {

        if (t.updateEvaluation() == 0) {
            this.Wait("Saving");
            this.Save_Teacher();
            this.Save_Evaluations();
            this.Save_Questions();
            System.out.println(".....................Evaluation Updated Successfully :)................");
        }
    }
    ///////////////////////////////////////////////////(4)Managing Questions///////////////
    public void AddQuestion(Teacher t) {

        Questions qu = null;
        qu = t.AddQuestion();
        if (qu != null) {
            this.Wait("Saving");
            this.getAllQuestions().add(qu);
            this.Save_Questions();
            this.Save_Evaluations();
            System.out.println(".....................Question Created Successfully :)................");
        }
    }
    public void updateQuestion(Teacher t) {

        if (t.UpdateQuestion() == 0) {
            this.Wait("Saving");
            this.Save_Questions();
            System.out.println(".....................Evaluation Updated Successfully :)................");
        }
    }
    public void DeleteQuestion(Teacher t) {

        Questions qu = null;
        qu = t.deleteQuestion();
        if (qu != null) {
            this.Wait("Saving");
            this.getAllQuestions().remove(qu);
            this.Save_Questions();
            this.Save_Evaluations();
            System.out.println(".....................Question Deleted Successfully :)................");
        }
    }
    ///////////////////////////////////////////////////(5)Check CLO Tested///////////////
    public void CheckCLOTested(Teacher t) {
        t.CheckCloTested();
    }
    public void CheckAllCLOTested(Teacher t) {
     t.CheckAllClosTested();
    }


    ///////////////////////////////////////////////////Functions For Displaying Lists///////////////////////
    private void DisplayPrograms(){
        if(this.allPrograms!=null)
        for(int i=0;i< this.allPrograms.size();i++)
          this.allPrograms.get(i).printProgram();

    }
    private void DisplayPLOS(){
        if(this.allPLO!=null)
        for(int i=0;i< this.allPLO.size();i++)
           this.allPLO.get(i).printPLO();

    }
    private void DisplayCLOS(){
        if(this.allCLO!=null)
        for(int i=0;i< this.allCLO.size();i++)
        this.allCLO.get(i).printCLO();
    }
    private void DisplayCourse(){
        if(this.allCourses!=null)
            for(int i=0;i< this.allCourses.size();i++)
                this.allCourses.get(i).printCourses();
    }
    private void DisplayTopics(){
        if(this.allTopics!=null)
            for(int i=0;i< this.allTopics.size();i++)
                this.allTopics.get(i).PrintTopic();
    }
    private void DisplayTeacher(){
        if(this.allTeachers!=null)
            for(int i=0;i< this.allTeachers.size();i++)
                this.allTeachers.get(i).printTeacher();
    }
    private void DisplayAcademicOfficer(){
        if(this.allAcedimicOfficers!=null)
            for(int i=0;i< this.allAcedimicOfficers.size();i++)
                this.allAcedimicOfficers.get(i).printOfficer();
    }
    private void DisplayEvaluations(){
        if(this.allEvaluations!=null)
            for(int i=0;i< this.allEvaluations.size();i++)
                this.allEvaluations.get(i).printEvaluation();
    }
    private void DisplayQuestions(){
        if(this.allQuestions!=null)
            for(int i=0;i< this.allQuestions.size();i++)
                this.allQuestions.get(i).printQuestion();
    }
    private void Display_Current_System(){
        System.out.println(".................................Welcome Boss.................................");
        Scanner scan=new Scanner(System.in);
        int op= -1;
        while(op!=0){

            System.out.println("............................Displaying  System  Condition..................: ");
            System.out.println("1. Print All Academic Officers");
            System.out.println("2. Print All Teachers");
            System.out.println("3. Print All Programs");
            System.out.println("4. Print All Course");
            System.out.println("5. Print All PLOS");
            System.out.println("6. Print All CLOS");
            System.out.println("7. Print All Topics");
            System.out.println("8. Print All Evaluations");
            System.out.println("9. Print All Questions");

            System.out.print("Enter Option : ");
            op=scan.nextInt();
            System.out.println();
            System.out.println("************************************************");

            if(op==1)
                this.DisplayAcademicOfficer();
            else if(op==2)
                this.DisplayTeacher();
            else if(op==3)
                this.DisplayPrograms();
            else if(op==4)
                this.DisplayCourse();
            else if(op==5)
                this.DisplayPLOS();
            else if(op==6)
                this.DisplayCLOS();
            else if(op==7)
                this.DisplayTopics();
            else if(op==8)
                this.DisplayEvaluations();
            else if(op==9)
                this.DisplayQuestions();
            System.out.println("************************************************");

        }

    }
    public void Special_Officer(){
        Scanner scan =new Scanner(System.in);
        String pass="";

        while(!pass.equals("111")) {
            System.out.print("Enter Password : ");
            pass = scan.nextLine();
            if (pass.equals("111"))
                this.Display_Current_System();
            else System.out.print("Incorrect Password");
        }
    }


    //////////////////////////////////////////////// Menus///////////
    ////////////////////////////////////print Officer Menu
    public void PrintOfficerMenu() {
        System.out.println();
        System.out.println("..............................(Main Menu).....................................");
        System.out.println("0.Logout");
        System.out.println("1.Manage Courses");
        System.out.println("2.Manage CLOS");
        System.out.println("3.Special Functionalities");
        System.out.println("..........................................................................");
    }
    public void Print_ManageCLOS_Menu(){

        System.out.println();
        System.out.println("0.Return to Main Menu");
        System.out.println("1.Add CLO");
        System.out.println("2.Update CLO");
        System.out.println("3.Delete CLO");
        System.out.println();
    }
    public void Print_ManageCOURSE_Menu() {
        System.out.println();
        System.out.println("0.Return to Main Menu");
        System.out.println("1.Add Course");
        System.out.println("2.Update Course");
        System.out.println("3.Delete Course");
        System.out.println();
    }
    ////////////////////////////////////////////////print Teacher Menu
    public void PrintTeacherMenu(){
        System.out.println();
        System.out.println("..............................(Menu).....................................");
        System.out.println("0.Logout");
        System.out.println("1.Manage Questions");
        System.out.println("2.Manage Evaluations");
        System.out.println("3.Check CLO Tested");
        System.out.println("4.Check All Clos Tested");
        System.out.println("5.Special Functionalities");
        System.out.println("..........................................................................");
    }
    public void Print_ManageQuestions_Menu(){
        System.out.println();
        System.out.println("0. Return to Main Menu");
        System.out.println("1. Add Question");
        System.out.println("2. Update Question");
        System.out.println("3. Delete Question");
        System.out.println();
    }
    public void Print_ManageEvaluations_Menu(){
        System.out.println();
        System.out.println("0.Return to Main Menu");
        System.out.println("1.Add Evaluation");
        System.out.println("2.Update Evaluation");
        System.out.println("3.Delete Evaluation");
        System.out.println();
    }
    ////////////////////////////////////////////////////Closing System
    public void printLogoutMenu() {
        System.out.println();
        System.out.println("****************************************************************************");
        System.out.println("*  Logged Out Successfully :) (Good Bye).................................  *");
        System.out.println("****************************************************************************");
        System.out.println();
        System.out.println("Select Option....................");
        System.out.println("0. Close System");
        System.out.println("1. Continue with an other account");
        System.out.print("Enter Option : ");
    }
    private void Wait(String str) {

        try{
            int i=0;
            System.out.print(str);
            while(i<3)
            {
                System.out.print(".");
                Thread.sleep(800);
                i++;
            }
            System.out.println();
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }


    /////////////////////////////getter setters//////////////////
    public ArrayList<Topics> getAllTopics() {
        return allTopics;
    }
    public ArrayList<Teacher> getAllTeachers() {
        return allTeachers;
    }
    public ArrayList<PLO> getAllPLO() {
        return allPLO;
    }
    public ArrayList<CLO> getAllCLO() {
        return allCLO;
    }
    public ArrayList<Courses> getAllCourses() {
        return allCourses;
    }
    public ArrayList<Questions> getAllQuestions() {
        return allQuestions;
    }
    public ArrayList<Evaluation> getAllEvaluations() {
        return allEvaluations;
    }
    public String getPath() {
        return path;
    }
}
