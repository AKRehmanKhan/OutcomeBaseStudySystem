
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class AcademicOfficer extends User{
    private ArrayList<Program> programs=new ArrayList<Program>();
    private ArrayList<Integer> programIds=new ArrayList<Integer>();

    ///////////Constructor////////////
    public AcademicOfficer(int id, String name, String password,ArrayList<Program> programs,ArrayList<Integer> programIds) {
        super(id,name,password);
        this.programs = programs;
        this.programIds=programIds;
    }


    /////////////print officer//////////
    public void printOfficer() {
        System.out.println("ID: "+this.getId());
        System.out.println("Name: "+this.getName());
        System.out.println("Password: "+this.getPassword());

        if(programs!=null) {
            for (int i = 0; i < programs.size(); i++)
                System.out.print("programs " + programs.get(i).getId() + "  ");
            System.out.print("      |||        ");
        }

        if(programIds!=null) {
            for (int i = 0; i < programIds.size(); i++)
                System.out.print("programIds " + programIds.get(i) + "  ");
        }
        System.out.println("\n");

    }

    /////////////////////////functionalities///////////////////
    public CLO addCLO(PLO sPLO,int cloid) {

        Scanner scan = new Scanner(System.in);  //declaring input variable

        Program sProgram=sPLO.getProgram();   //get program of selected PLO


        ArrayList<Courses> selected_Courses = new ArrayList<>();
        ArrayList<Integer> selected_Courses_Ids = new ArrayList<>();
        if(sProgram.getCourses()!=null && !sProgram.getCourses().isEmpty()) {
            System.out.println();
            System.out.println("(Optional) Select Courses...........Press 0 for finish selecting course");
            this.PrintCourses(sProgram.getCourses());   //printing courses of selected program
            System.out.println("Enter Courses (can choose more than 1) : ");
            ArrayList<Integer> selectedCourses = new ArrayList<>();
            int op = -1;
            while (op != 0) {
                op = scan.nextInt();
                if (op != 0)
                    selectedCourses.add(selectedCourses.size(), op - 1);
            }
            if (!selectedCourses.isEmpty()) {
                // System.out.println(selectedCourses.toString());
                for (int i = 0; i < selectedCourses.size(); i++) {
                    if (selectedCourses.get(i) < 0 || selectedCourses.get(i) > sProgram.getCourses().size() - 1) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        return null;
                    }
                    selected_Courses.add(selected_Courses.size(), sProgram.getCourses().get(selectedCourses.get(i)));
                    selected_Courses_Ids.add(selected_Courses_Ids.size(), sProgram.getCourses().get(selectedCourses.get(i)).getId());
                }
                //System.out.println(selected_Courses_Ids.toString());
            }
        }
        else{
            System.out.println("Sorry no Courses to Display.");
            this.Wait("Skipping Course Selection");
        }

        ArrayList<CLO> allCLO=sPLO.getPlosClos();
        ArrayList<Topics> selected_Topics = new ArrayList<>();
        ArrayList<Integer> selected_Topics_Ids = new ArrayList<>();
        if(allCLO!=null && !allCLO.isEmpty()) {
            ArrayList<Topics> allTopics = null;
            ArrayList<Topics> uniqueTopics =null;

            allTopics=this.getAllTopics(allCLO);  //getting topics of selected plos clos
            if(allTopics!=null && !allTopics.isEmpty())
                uniqueTopics = this.getUniqueTopics(this.getTopicsIds(allTopics), allTopics);

            if (uniqueTopics != null && !uniqueTopics.isEmpty()) {
                System.out.println();
                System.out.println("(Optional) Select Topics Which will cover your CLO...........Press 0 to exit selecting topics2");
                this.PrintTopics(uniqueTopics);    //printing topics of selected course clos

                System.out.println("Enter Topics IDs : ");
                ArrayList<Integer> selectedTopics = new ArrayList<>();
                int op = -1;
                while (op != 0) {
                    op = scan.nextInt();
                    if (op != 0)
                        selectedTopics.add(selectedTopics.size(), op - 1);
                }
                if (!selectedTopics.isEmpty()) {


                    for (int i = 0; i < selectedTopics.size(); i++) {
                        if (selectedTopics.get(i) < 0 || selectedTopics.get(i) > uniqueTopics.size() - 1) {
                            System.out.println("Sorry you Enter Incorrect Option.........");
                            this.Wait("Returning");
                            return null;
                        }
                        selected_Topics.add(selected_Topics.size(), uniqueTopics.get(selectedTopics.get(i)));
                        selected_Topics_Ids.add(selected_Topics_Ids.size(), uniqueTopics.get(selectedTopics.get(i)).getId());
                    }
                    //System.out.println(selected_Topics_Ids.toString());
                }
            } else {
                System.out.println("Sorry NO Topics to Display.");
                this.Wait("Skipping Topics Selection");
            }
        }

        System.out.print("Enter Objective or description of your CLO : ");
        scan.nextLine();////extra scan topscrp new line reading in string
        String obj= scan.nextLine();

        if(this.SearchObjective(obj,OBS.getInstance().getAllCLO())==false) {
            this.Wait("Creating CLO");
            CLO clonew = new CLO(cloid, obj, selected_Topics, selected_Courses, selected_Topics_Ids, selected_Courses_Ids, sPLO, sPLO.getId());

            this.Wait("Updating Related Classes ");
            //adding clo to plo's list of clo
            sPLO.assignCLO(clonew);

            //adding clo to courses lists of clo
            if (selected_Courses != null && !selected_Courses.isEmpty())
                for (int i = 0; i < selected_Courses.size(); i++) {
                    selected_Courses.get(i).assignCLO(clonew);
                }

            //adding clo to topic lists of clo
            if (selected_Topics != null && !selected_Topics.isEmpty())
                for (int i = 0; i < selected_Topics.size(); i++) {
                    selected_Topics.get(i).assignCLO(clonew);
                }

            return clonew;
        }
        else {
            System.out.println("Sorry Such CLO Already exists");
            this.Wait("Returning");
            return null;
        }
    }
    public CLO DeleteCLO(PLO sPLO) {

        Scanner scan =new Scanner(System.in);
        ArrayList<CLO> allClO=sPLO.getPlosClos();
        if(allClO!=null&& !allClO.isEmpty() ) {
            System.out.println("Select CLO to Delete................. ");
            this.PrintCLOS(allClO);
            System.out.print("Enter CLO NO : ");
            int op = scan.nextInt() - 1;
            if (op < 0 || op >= allClO.size()) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                return null;
            }

            CLO selectedCLO = allClO.get(op);


            for (int i = 0; i < sPLO.getPlosClos().size(); i++) {
                if (sPLO.getPlosClos().get(i) == selectedCLO) {
                    sPLO.getPlosClos().remove(i);
                    sPLO.getPlosClosIds().remove(i);
                }
            }


            if(selectedCLO.getTopics()!=null && !selectedCLO.getTopics().isEmpty())
            for (int i = 0; i < selectedCLO.getTopics().size(); i++) {
                for (int j = 0; j < selectedCLO.getTopics().get(i).getClos().size(); j++) {
                    if (selectedCLO.getTopics().get(i).getClos().get(j) == selectedCLO) {
                        selectedCLO.getTopics().get(i).getClos().remove(j);
                        selectedCLO.getTopics().get(i).getClosIds().remove(j);
                    }
                }
            }

            if(selectedCLO.getCourses()!=null && !selectedCLO.getCourses().isEmpty())
            for (int i = 0; i < selectedCLO.getCourses().size(); i++) {
                for (int j = 0; j < selectedCLO.getCourses().get(i).getClos().size(); j++) {
                    if (selectedCLO.getCourses().get(i).getClos().get(j) == selectedCLO) {
                        selectedCLO.getCourses().get(i).getClos().remove(j);
                        selectedCLO.getCourses().get(i).getClosIds().remove(j);
                    }
                }
            }

            return selectedCLO;
        }
        else{
            System.out.println("Sorry NO CLOs to delete. Either add New CLO or Assign CLo to PLO you selected");
            return null;
        }



    }
    public int updateCLO(PLO sPLO){
        ArrayList<Topics> allTopics=OBS.getInstance().getAllTopics();
        Scanner scan =new Scanner(System.in);
        ArrayList<CLO> allClO=sPLO.getPlosClos();
        if(allClO!=null&& !allClO.isEmpty() ) {
            System.out.println();
            System.out.println("Select CLO to Update................. ");
            this.PrintCLOS(allClO);
            System.out.print("Enter CLO NO : ");
            int op = scan.nextInt() - 1;
            if (op < 0 || op >= allClO.size()) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                return -1;
            }

            CLO selectedCLO = allClO.get(op);

            System.out.println();
            System.out.println("1. Update CLO Objective");
            System.out.println("2. Update CLO Topics");
            System.out.println("3. Update CLO's PLO");
            System.out.print("Enter what do you want to update?:  ");
            int op1 = scan.nextInt();
            if (op1 < 0 || op1 > 3) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                return -1;
            }

            // updating clo objective
            else if (op1 == 1) {

                scan.nextLine();
                System.out.print("Enter New Objective : ");
                String newName = scan.nextLine();
                selectedCLO.setObjective(newName);
            }

            //updating clo topics
            else if (op1 == 2) {
                System.out.println();
                System.out.println("1. Remove Topic from CLO ");
                System.out.println("2. Add Topic to CLO ");
                System.out.print("Enter what do you want to do with CLO's topics?:  ");
                int op2 = scan.nextInt();

                if (op2 < 0 || op2 > 2) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    return -1;
                }

                //remove topic
                else if (op2 == 1) {
                    if (selectedCLO.getTopics() != null && !selectedCLO.getTopics().isEmpty() && selectedCLO.getTopics().size()>1) {
                        ArrayList<Topics> filterTopics=this.FilterTopics(selectedCLO);
                        if(filterTopics!=null && !filterTopics.isEmpty()) {
                            this.PrintTopics(filterTopics);
                            System.out.println("Enter Topic which you want to remove : ");
                            int op3 = scan.nextInt() - 1;
                            if (op3 < 0 || op3 >= filterTopics.size()) {
                                System.out.println("Sorry you Enter Incorrect Option.........");
                                this.Wait("Returning");
                                return -1;
                            }
                            else {

                                Topics topic = filterTopics.get(op3);

                                topic.getClos().remove(selectedCLO);

                                for (int i = 0; i < topic.getClosIds().size(); i++) {
                                    if (selectedCLO.getId() == topic.getClosIds().get(i))
                                        topic.getClosIds().remove(i);

                                }

                                selectedCLO.getTopics().remove(topic);
                                for (int i = 0; i < selectedCLO.getTopicsIds().size(); i++) {
                                    if (topic.getId() == selectedCLO.getTopicsIds().get(i))
                                        selectedCLO.getTopicsIds().remove(i);

                                }
                            }
                        }
                        else{
                            System.out.println("Sorry no removable topic exist is this course .........");
                            this.Wait("Returning");
                            return -1;
                        }
                    } else {
                        System.out.println("Sorry selected Clo don't have any removable Topic .........");
                        System.out.println("Selected CLO has single Topic. Thus removing that Topic will make CLO without Topic .........");
                        this.Wait("Returning");
                        return -1;
                    }
                }
                //add topic
                else if (op2 == 2) {


                    ArrayList<CLO> allCLO=sPLO.getPlosClos();
                    if(allCLO!=null && !allCLO.isEmpty()) {
                        ArrayList<Topics> uniqueTopics = null;

                        allTopics = this.getAllTopics(allCLO);  //getting topics of selected plos clos
                        if (allTopics != null && !allTopics.isEmpty())
                            uniqueTopics = this.getUniqueTopics(this.getTopicsIds(allTopics), allTopics);

                        ArrayList<Topics> topics=null;
                        if(selectedCLO.getTopics()!=null && !selectedCLO.getTopics().isEmpty())
                        topics = this.filterTopics(selectedCLO.getTopics(), uniqueTopics);
                        else topics=uniqueTopics;

                        if (topics != null && !topics.isEmpty()) {

                            System.out.println();
                            System.out.println("Assigning Topic To CLO.......................");
                            this.PrintTopics(topics);
                            System.out.print("Enter Topic to assign: ");
                            int op4 = scan.nextInt() - 1;
                            if (op4 < 0 || op4 >= topics.size()) {
                                System.out.println("Sorry you Enter Incorrect Option.........");
                                this.Wait("Returning");
                                return -1;
                            } else {
                                this.Wait("Updating");
                                topics.get(op4).assignCLO(selectedCLO);
                                selectedCLO.assignTopic(topics.get(op4));
                            }

                        } else {
                            System.out.println("Sorry No Topics to add.........");
                            this.Wait("Returning");
                            return -1;
                        }
                    }
                    else {
                        System.out.println("Sorry No Topics to add.........");
                        this.Wait("Returning");
                        return -1;
                    }


                }

            }
            //updating clo plo
            else if(op1==3){
                ArrayList<PLO> flteredPLO=this.FilteredPLO(selectedCLO.getPlo(),OBS.getInstance().getAllPLO());
                if(flteredPLO!=null && !flteredPLO.isEmpty()) {
                    System.out.println("Selecting PLO.........");
                    this.PrintPLos(flteredPLO);
                    System.out.println("Enter new PLO NO. : ");
                    int op3 = scan.nextInt() - 1;
                    if (op3 < 0 || op3 >= flteredPLO.size()) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        return -1;
                    }

                    PLO nsPLO =flteredPLO.get(op3);

                    selectedCLO.getPlo().getPlosClos().remove(selectedCLO);
                    selectedCLO.getPlo().getPlosClosIds().remove(selectedCLO.getPlo().getPlosClosIds().indexOf(selectedCLO.getId()));

                    nsPLO.getPlosClos().add(selectedCLO);
                    nsPLO.getPlosClosIds().add(selectedCLO.getId());

                    selectedCLO.setPlo(nsPLO);
                    selectedCLO.setPloId(nsPLO.getId());


                }
                else {
                    System.out.println("Sorry No PLos to update add PLO to System.........");
                    this.Wait("Returning");
                    return -1;

                }
            }
            return 0;
        }else {
            System.out.println("Sorry No CLO to update. Either add New CLO or Assign CLO to plo you selected");
            return -1;
        }

    }
    public Courses addCourse(int course_id){
        ArrayList<Teacher> allTeachers= OBS.getInstance().getAllTeachers();
        System.out.println(".....................(Adding Course).....................");
        ArrayList<Program>selected_Programs=new ArrayList<>();
        ArrayList<Integer>selected_Programs_Ids=new ArrayList<>();
        Scanner scan= new Scanner(System.in);


        System.out.println("Select programs...........Press 0 for finish selecting Programs");
        ArrayList<Program> allPrograms = this.getPrograms();
        if(allPrograms!=null && !allPrograms.isEmpty()){
        for(int i=0;i< allPrograms.size();i++) {
            System.out.println((i+1)+". "+"ID: "+allPrograms.get(i).getId()+"  "+allPrograms.get(i).getName());
             }  //printing programs
            System.out.print("Enter Program No. (can choose more than 1) : ");
            ArrayList<Integer> selectdPrograms= new ArrayList<>();
            int op=-1;
            while(op!=0) {
                op=scan.nextInt();
                if(op!=0)
                    selectdPrograms.add(selectdPrograms.size(),op-1);
            }
            if(selectdPrograms.isEmpty())  {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                return null;
            }

            // System.out.println(selectedCourses.toString());
            for(int i=0;i< selectdPrograms.size();i++) {
                if(selectdPrograms.get(i)<0 ||selectdPrograms.get(i)>allPrograms.size()-1){
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    return null;
                }
                selected_Programs.add(selected_Programs.size(),allPrograms.get(selectdPrograms.get(i)));
                selected_Programs_Ids.add(selected_Programs_Ids.size(),allPrograms.get(selectdPrograms.get(i)).getId());
            }
        }
        else  {
            System.out.println("No Programs to show please Create Program first");
            this.Wait("Returning");
            return null;
        }

        System.out.println("(Optional) Select Teachers...........Press 0 for finish selecting Teachers");
        ArrayList<Teacher> selected_Teacher = new ArrayList<>();
        ArrayList<Integer> selected_Teachers_Ids = new ArrayList<>();
        if(allTeachers!=null && !allTeachers.isEmpty()) {
           for (int i = 0; i < allTeachers.size(); i++) {
               System.out.println((i + 1) + ". " + "ID: " + allTeachers.get(i).getId() + "  " + allTeachers.get(i).getName());
           }  //printing courses of selected program

           System.out.print("Enter Teacher No (can choose more than 1) : ");
           ArrayList<Integer> selectdTeacher = new ArrayList<>();
           int op1 = -1;
           while (op1 != 0) {
               op1 = scan.nextInt();
               if (op1 != 0)
                   selectdTeacher.add(selectdTeacher.size(), op1 - 1);
           }
           if (!selectdTeacher.isEmpty()) {

               // System.out.println(selectedCourses.toString());
               for (int i = 0; i < selectdTeacher.size(); i++) {
                   if (selectdTeacher.get(i) < 0 || selectdTeacher.get(i) > allTeachers.size() - 1) {
                       System.out.println("Sorry you Enter Incorrect Option.........");
                       this.Wait("Returning");
                       return null;
                   }
                   selected_Teacher.add(selected_Teacher.size(), allTeachers.get(selectdTeacher.get(i)));
                   selected_Teachers_Ids.add(selected_Teachers_Ids.size(), allTeachers.get(selectdTeacher.get(i)).getId());
               }
           }
       }
        else{
           System.out.println("No Teachers in the System.");
           this.Wait("Skipping Teachers Selection");
       }

        System.out.println("(Optional) Select CLOS...........Press 0 for finish selecting CLOS");
        ArrayList<CLO>selected_Clos=new ArrayList<>();
        ArrayList<Integer>selected_Clos_Ids=new ArrayList<>();

        ArrayList<PLO> allPLOS= null;
        ArrayList<CLO> clos=null;
        ArrayList<Integer> cloIds=null;
        ArrayList<CLO> allClos=null;

        allPLOS= this.getPLOSofSelectedPrograms(selected_Programs);
        if(allPLOS!=null)
            clos = this.getCLOs(allPLOS);
        if(clos!=null)
           cloIds = this.getCLOSIds(clos);
        if(cloIds!=null)
            allClos = this.getUniqueCLOs(cloIds, clos);

        if(allClos!=null && !allClos.isEmpty()) {
            for (int i = 0; i < allClos.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + allClos.get(i).getId() + "  " + allClos.get(i).getObjective());
            }  //printing clos of selected program

            System.out.print("Enter CLO No (can choose more than 1) : ");
            ArrayList<Integer> selectedCLO= new ArrayList<>();
            int op3=-1;
            while(op3!=0) {
                op3=scan.nextInt();
                if(op3!=0)
                    selectedCLO.add(selectedCLO.size(),op3-1);
            }
            if(!selectedCLO.isEmpty()) {

                // System.out.println(selectedCourses.toString());
                for (int i = 0; i < selectedCLO.size(); i++) {
                    if (selectedCLO.get(i) < 0 || selectedCLO.get(i) > allClos.size() - 1) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        return null;
                    }
                    selected_Clos.add(selected_Clos.size(), allClos.get(selectedCLO.get(i)));
                    selected_Clos_Ids.add(selected_Clos_Ids.size(), allClos.get(selectedCLO.get(i)).getId());
                }
            }
        }
        else{
            System.out.println("No CLOs to assign.");
            System.out.println("Skipping Clos selection");
        }

        System.out.println("Select Course Name.........................................");
        System.out.print("Enter Course Name : ");
        scan.nextLine();
        String CourseName = scan.nextLine();

        System.out.println();
        System.out.println("Select Course Credit Hours.........................................");
        int ch=4;
        while(ch>3) {
            System.out.print("Enter Course Credit Hours : ");
            ch = scan.nextInt();
            if(ch>3)
                System.out.println("Sorry you Enter credit hour > 3. Please enter again");
        }

        if(this.SearchCourse(CourseName,OBS.getInstance().getAllCourses())==false) {

            this.Wait("Creating Course");

            Courses course = null;
            course = new Courses(course_id, CourseName, selected_Programs, selected_Clos, selected_Teacher, ch, selected_Programs_Ids, selected_Clos_Ids, selected_Teachers_Ids);

            if (selected_Programs != null && !selected_Programs.isEmpty())
                for (int i = 0; i < selected_Programs.size(); i++) {
                    selected_Programs.get(i).assignCourse(course);
                }

            if (selected_Teacher != null && !selected_Teacher.isEmpty())
                for (int i = 0; i < selected_Teacher.size(); i++) {
                    selected_Teacher.get(i).assignCourse(course);
                }

            if (selected_Clos != null && !selected_Clos.isEmpty())
                for (int i = 0; i < selected_Clos.size(); i++) {
                    selected_Clos.get(i).assignCourse(course);
                }

            return course;
        }
        else{
            System.out.println("Sorry Such Course Already exists");
            this.Wait("Returning");
            return null;
        }
    }
    public Courses DeleteCourse(){
        if(this.programs!=null && !this.programs.isEmpty()){
            Scanner scan = new Scanner(System.in);
             System.out.println("..........................(Deleting Course)...............................");
             System.out.println("Select Program.........................");
             this.PrintPrograms();
             System.out.print("Enter Program : ");
             int op=scan.nextInt()-1;
             if(op<0 || op>this.programs.size()-1){
                 System.out.println("Sorry you Enter Incorrect Option.........");
                 this.Wait("Returning");
                 return null;
              }
             Program sProg=this.programs.get(op);

             if(sProg.getCourses()!=null && !sProg.getCourses().isEmpty()) {
                 System.out.println();
                 System.out.println("Select Course............................");
                 this.PrintCourses(sProg.getCourses());
                 System.out.print("Enter Course : ");
                 op=scan.nextInt()-1;
                 if(op<0 || op>sProg.getCourses().size()-1){
                     System.out.println("Sorry you Enter Incorrect Option.........");
                     this.Wait("Returning");
                     return null;
                 }
                Courses sCourse=sProg.getCourses().get(op);

                 if(sCourse.getPrograms()!=null && !sCourse.getPrograms().isEmpty())
                     for(int i=0; i<sCourse.getPrograms().size();i++) {
                         sCourse.getPrograms().get(i).getCourses().remove(sCourse);
                         sCourse.getPrograms().get(i).getCourseIds().remove( sCourse.getPrograms().get(i).getCourseIds().indexOf(sCourse.getId()));
                     }

                 if(sCourse.getTeachers()!=null && !sCourse.getTeachers().isEmpty())
                 for(int i=0; i<sCourse.getTeachers().size();i++) {
                     sCourse.getTeachers().get(i).getCourses().remove(sCourse);
                     sCourse.getTeachers().get(i).getCoursesId().remove( sCourse.getTeachers().get(i).getCoursesId().indexOf(sCourse.getId()));
                 }

                 if(sCourse.getClos()!=null && !sCourse.getClos().isEmpty())
                     for(int i=0; i<sCourse.getClos().size();i++) {
                         sCourse.getClos().get(i).getCourses().remove(sCourse);
                         sCourse.getClos().get(i).getCourseId().remove(sCourse.getClos().get(i).getCourseId().indexOf(sCourse.getId()));
                     }


                 return sCourse;

             }
             else{
                 System.out.println("Sorry NO Courses to show in this Program. Add courses first.........");
                 this.Wait("Returning");
                 return null;
             }

        }
        else {
            System.out.println("Sorry NO Programs to show ,Please Add Program first.........");
            this.Wait("Returning");
            return null;
             }
    }
    public int updateCourse(){
        ArrayList<Teacher> allTeachers= OBS.getInstance().getAllTeachers();
        ArrayList<CLO> allClos=OBS.getInstance().getAllCLO();
        if(this.programs!=null && !this.programs.isEmpty()) {
            Scanner scan = new Scanner(System.in);
                System.out.println("Select Program...........................");
            this.PrintPrograms();
            System.out.print("Enter Program : ");
            int op = scan.nextInt() - 1;
            if (op < 0 || op > this.programs.size() - 1) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                return -1;
            }
            Program sProg = this.programs.get(op);

            if (sProg.getCourses() != null && !sProg.getCourses().isEmpty()) {
                System.out.println();
                System.out.println("Select Course to update...................");
                this.PrintCourses(sProg.getCourses());
                System.out.print("Enter Course : ");
                op = scan.nextInt() - 1;
                if (op < 0 || op > sProg.getCourses().size() - 1) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    return -1;
                }
                Courses sCourse = sProg.getCourses().get(op);

                System.out.println();
                System.out.println("Selecting Option.............................");
                System.out.println("1. Update Course Name");
                System.out.println("2. Update Course Programs");
                System.out.println("3. Update Course Teachers");
                System.out.println("4. Update Course CLO");
                System.out.print("Enter what do you want to update?:  ");
                int op1 = scan.nextInt();
                if (op1 < 0 || op1 > 4) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    return -1;
                }

                //updating name
                else if(op1==1) {
                    scan.nextLine();
                    System.out.print("Enter New Name : ");
                    String newName = scan.nextLine();
                    sCourse.setName(newName);
                }

                //updating program of course
                else if(op1==2){
                    System.out.println();
                    System.out.println("Selecting Option...................................");
                    System.out.println("1. Remove Program");
                    System.out.println("2. Assign Program");
                    System.out.print("Enter what do you want to do with Course Programs?:  ");
                    int op2 = scan.nextInt();

                    if (op2 < 0 || op2 > 2) {
                        System.out.println("Sorry you Enter Incorrect Option...............");
                        this.Wait("Returning");
                        return -1;
                    }
                    //removing program
                    else if(op2==1){
                        if( sCourse.getPrograms()!=null && !sCourse.getPrograms().isEmpty() && sCourse.getPrograms().size()>1) {
                            System.out.println();
                            System.out.println("Select Program To remove.............");
                            //filtering programs which have more than 1 course
                            ArrayList<Program> FProg = this.FilterPrograms(sCourse);
                            if (FProg != null && !FProg.isEmpty()) {
                                this.PrintPrograms(FProg);
                                System.out.print("Enter Program No to remove : ");
                                int op3 = scan.nextInt() - 1;
                                if (op3 < 0 || op3 > FProg.size() - 1) {
                                    System.out.println("Sorry you Enter Incorrect Option.........");
                                    this.Wait("Returning");
                                    return -1;
                                }

                                this.Wait("Removing");
                                Program rProg = FProg.get(op3);

                                rProg.getCourses().remove(sCourse);
                                rProg.getCourseIds().remove(sProg.getCourseIds().indexOf(sCourse.getId()));

                                sCourse.getPrograms().remove(rProg);
                                sCourse.getProgramIds().remove(sCourse.getProgramIds().indexOf(rProg.getId()));

                            } else {
                                System.out.println("...........................................................................................");
                                System.out.println("Sorry No removable Programs exists in this Course. Please Add Program to this course first!");
                                this.Wait("Returning");
                                return -1;
                            }
                        }
                        else {
                            this.Wait("Fetching Programs");
                            System.out.println();
                            System.out.println("................................................................................................................");
                            System.out.println("Sorry No removable Programs exists in this Course");
                            System.out.println("Or either Only single Program is offering this course and removing that Program will make Course without Program");
                            System.out.println("................................................................................................................");
                            System.out.println();
                            this.Wait("Returning");
                            return -1;
                        }

                    }

                    //assign program
                    else if(op2==2){
                        System.out.println();
                        System.out.println("Select Program TO Assign.....................");
                        ArrayList<Program> FProg=null;
                        if(sCourse.getPrograms()!=null && !sCourse.getPrograms().isEmpty())
                        FProg=this.getProgramsExceptgivenProg(sCourse);
                        else FProg=this.programs;

                        if(FProg!=null && !FProg.isEmpty()) {
                            this.PrintPrograms(FProg);
                            System.out.println("Enter Program No. to Assign : ");
                            int op3 = scan.nextInt() - 1;
                            if (op3 < 0 || op3 > FProg.size() - 1) {
                                System.out.println("Sorry you Enter Incorrect Option.........");
                                this.Wait("Returning");
                                return -1;
                            }
                            this.Wait("Assigning Program");
                            Program aProg=FProg.get(op3);

                            aProg.getCourses().add(sCourse);
                            aProg.getCourseIds().add(sCourse.getId());

                            sCourse.getPrograms().add(aProg);
                            sCourse.getProgramIds().add(aProg.getId());


                        }
                        else{
                            System.out.println();
                            System.out.println("................................................................................................................");
                            System.out.println("Sorry No assignable Programs exists. Please add Program First");
                            System.out.println("................................................................................................................");
                            System.out.println();
                            this.Wait("Returning");
                            return  -1;
                        }
                    }
                }

                //updating teachers of course
                else if(op1==3) {
                    System.out.println();
                    System.out.println("1. Remove Teacher");
                    System.out.println("2. Assign Teacher");
                    System.out.print("Enter what do you want to do with Course Teachers?:  ");
                    int op2 = scan.nextInt();

                    if (op2 < 0 || op2 > 2) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        return -1;
                    }
                    //removing teacher
                    else if (op2 == 1) {
                        if (sCourse.getTeachers()!=null && !sCourse.getTeachers().isEmpty() && sCourse.getTeachers().size() > 1) {
                            System.out.println();
                            System.out.println("Select Teacher To Remove.............");
                            //Filtering teachers which are teaching more than 1 courses
                            ArrayList<Teacher> FTeach = this.FilterTeachers(sCourse);
                            if (FTeach != null && !FTeach.isEmpty()) {
                                this.PrintTeachers(FTeach);
                                System.out.print("Enter Teacher No. to remove : ");
                                int op3 = scan.nextInt() - 1;
                                if (op3 < 0 || op3 > FTeach.size() - 1) {
                                    System.out.println("Sorry you Enter Incorrect Option.........");
                                    this.Wait("Returning");
                                    return -1;
                                }

                                this.Wait("Removing");
                                Teacher rTeach = FTeach.get(op3);

                                rTeach.getCourses().remove(sCourse);
                                rTeach.getCoursesId().remove(rTeach.getCoursesId().indexOf(sCourse.getId()));

                                sCourse.getTeachers().remove(rTeach);
                                sCourse.getTeacherIds().remove(sCourse.getTeacherIds().indexOf(rTeach.getId()));

                            } else {
                                System.out.println();
                                System.out.println("................................................................................................................");
                                System.out.println("Sorry No removable Teacher exists in this Course");
                                System.out.println("................................................................................................................");
                                 System.out.println();
                                this.Wait("Returning");
                                return -1;
                            }
                        }
                        else {
                            this.Wait("Fetching Teachers");
                            System.out.println();
                            System.out.println("................................................................................................................");
                            System.out.println("Sorry No removable Teacher exists in this Course. Please add Teacher to this course first!");
                            System.out.println("Or either Only single teacher is teaching this course and removing that Teacher will make Course without Teacher");
                            System.out.println("................................................................................................................");
                            System.out.println();
                            this.Wait("Returning");
                            return -1;
                        }

                    }

                    //assign teacher
                    else if (op2 == 2) {
                        System.out.println();
                        System.out.println("Select Teacher TO Assign.....................");

                        //filtering teachers except already exists
                        ArrayList<Teacher>    FTeacher=null;
                        if(sCourse.getTeachers()!=null && !sCourse.getTeachers().isEmpty())
                            FTeacher = this.getTeachersExceptgivenTeachers(sCourse, allTeachers);
                        else FTeacher=allTeachers;

                        if (FTeacher != null && !FTeacher.isEmpty()) {
                            this.PrintTeachers(FTeacher);
                            System.out.print("Enter Teacher No. to Assign : ");
                            int op3 = scan.nextInt() - 1;
                            if (op3 < 0 || op3 > FTeacher.size() - 1) {
                                System.out.println("Sorry you Enter Incorrect Option.........");
                                this.Wait("Returning");
                                return -1;
                            }

                            this.Wait("Assigning Teacher");
                            Teacher aTeacher = FTeacher.get(op3);

                            aTeacher.getCourses().add(sCourse);
                            aTeacher.getCoursesId().add(sCourse.getId());

                            sCourse.getTeachers().add(aTeacher);
                            sCourse.getTeacherIds().add(aTeacher.getId());


                        } else {
                            System.out.println("Sorry No assignable Teacher exists in this Course.");
                            this.Wait("Returning");
                            return -1;
                        }
                    }

                }

                //updating clos of Course
                else if(op1==4){
                    System.out.println();
                    System.out.println("1. Remove CLO");
                    System.out.println("2. Assign CLO");
                    System.out.print("Enter what do you want to do with Course CLOS?:  ");
                    int op2 = scan.nextInt();

                    if (op2 < 0 || op2 > 2) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        return -1;
                    }
                    //removing clo
                    else if (op2 == 1) {
                        if (sCourse.getClos()!=null && !sCourse.getClos().isEmpty() && sCourse.getClos().size() > 1) {
                            System.out.println();
                            System.out.println("Select CLO To Remove.............");
                            //Filtering clos which are in more than 1 courses
                            ArrayList<CLO> FCLOs = this.FilterCLOS(sCourse);
                            if (FCLOs != null && !FCLOs.isEmpty()) {
                                this.PrintCLOS(FCLOs);
                                System.out.print("Enter Teacher No. to remove : ");
                                int op3 = scan.nextInt() - 1;
                                if (op3 < 0 || op3 > FCLOs.size() - 1) {
                                    System.out.println("Sorry you Enter Incorrect Option.........");
                                    this.Wait("Returning");
                                    return -1;
                                }

                                this.Wait("Removing");
                                CLO rCLO = FCLOs.get(op3);

                                rCLO.getCourses().remove(sCourse);
                                rCLO.getCourseIds().remove(rCLO.getCourseIds().indexOf(sCourse.getId()));

                                sCourse.getClos().remove(rCLO);
                                sCourse.getClosIds().remove(sCourse.getClosIds().indexOf(rCLO.getId()));

                            } else {
                                 System.out.println();
                                System.out.println("............................................");
                                System.out.println("Sorry No removable CLO exists in this Course");
                                System.out.println("............................................");
                                System.out.println();
                                this.Wait("Returning");
                                return -1;
                            }
                        }
                        else {
                            this.Wait("Fetching CLOS");
                            System.out.println();
                            System.out.println("..............................................................................................");
                            System.out.println("Sorry No removable CLOS exists in this Course. Please add CLOS to this course first!");
                            System.out.println("OR Either Only single CLO is in this course and removing that CLO will make Course without CLO");
                            System.out.println("..............................................................................................");
                            System.out.println();
                            this.Wait("Returning");
                            return -1;
                        }

                    }

                    //assign clo
                    else if (op2 == 2) {
                        System.out.println();
                        System.out.println("Select CLO TO Assign.....................");
                        //filtering clos except already exists
                        ArrayList<CLO> FClos=null;
                        if(sCourse.getClos()!=null && !sCourse.getClos().isEmpty())
                                FClos= this.getCLOSExceptgivenClo(sCourse, allClos);
                        else FClos=allClos;

                        if (FClos != null && !FClos.isEmpty()) {
                            this.PrintCLOS(FClos);
                            System.out.print("Enter CLO No. to Assign : ");
                            int op3 = scan.nextInt() - 1;
                            if (op3 < 0 || op3 > FClos.size() - 1) {
                                System.out.println("Sorry you Enter Incorrect Option.........");
                                this.Wait("Returning");
                                return -1;
                            }

                            CLO aClo = FClos.get(op3);

                            aClo.getCourses().add(sCourse);
                            aClo.getCourseIds().add(sCourse.getId());

                            sCourse.getClos().add(aClo);
                            sCourse.getClosIds().add(aClo.getId());


                        } else {
                            System.out.println();
                            System.out.println("..............................................");
                            System.out.println("Sorry No assignable CLO exists in this Course.");
                            System.out.println("..............................................");
                            System.out.println();
                            this.Wait("Returning");
                            return -1;
                        }
                    }

                }

            } else{

                System.out.println();
                System.out.println("........................................................................");
                    System.out.println("Sorry NO Courses to show in this Program. Add courses first.........");
                System.out.println("........................................................................");
                System.out.println();
                    this.Wait("Returning");
                    return -1;
                }

        } else {

            System.out.println();
            System.out.println("................................................................................................................");
                System.out.println("Sorry NO Programs to show ,Please Add Program first.........");
            System.out.println("................................................................................................................");
            System.out.println();
                this.Wait("Returning");
                return -1;
            }
        return 0;
    }

    //////////////////////////////////////////////////////helper functions////////////////////////////////////////
    private void Wait(String str)  {
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
    private ArrayList<Topics> getAllTopics(ArrayList<CLO> allCLOS) {

        ArrayList<Topics> allTopics=null;
        if(allCLOS!=null && !allCLOS.isEmpty()) {
            allTopics=new ArrayList<>();
            for (int i = 0; i < allCLOS.size(); i++) {
                ArrayList<Topics> tempTopics = allCLOS.get(i).getTopics();
                if(tempTopics!=null && !tempTopics.isEmpty())
                for (int j = 0; j < tempTopics.size(); j++) {
                    allTopics.add(tempTopics.get(j));
                }
            }
        }
        return allTopics;
    }
    private ArrayList<Topics> getUniqueTopics(ArrayList<Integer> allTopicsIds,ArrayList<Topics> allTopics) {

        ArrayList<Topics> uniqueTopics=new ArrayList<>();
        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(allTopicsIds);
        ArrayList<Integer> uniqueTopicIds=new ArrayList<>();
        uniqueTopicIds.addAll(set);

       // System.out.println(uniqueTopicIds.toString());
       // System.out.println(allTopicsIds.toString());
        for(int i=0;i<uniqueTopicIds.size();i++) {
            for(int j=0; j<allTopicsIds.size();j++) {
                if(uniqueTopicIds.get(i)==allTopics.get(j).getId())
                {
                    uniqueTopics.add(allTopics.get(j));
                    break;
                }
            }
        }

       // System.out.println(uniqueTopics.toString());
        return uniqueTopics;
    }
    private ArrayList<Integer> getTopicsIds(ArrayList<Topics> topics) {
        ArrayList<Integer> topicsIds =null;
        if(topics!=null && !topics.isEmpty()) {
            topicsIds=new ArrayList<>();
            for (int i = 0; i < topics.size(); i++) {
                topicsIds.add(topics.get(i).getId());
            }
        }
        return topicsIds;
    }
    private ArrayList<PLO> getPLOSofSelectedPrograms(ArrayList<Program> programs) {
        ArrayList<PLO> plos=null;
        if(programs!=null && !programs.isEmpty()){
            plos =new ArrayList<>();
            for(int i=0; i<programs.size();i++){
                if(programs.get(i).getPlo()!=null && !programs.get(i).getPlo().isEmpty())
                for(int j=0; j<programs.get(i).getPlo().size();j++){
                    plos.add(programs.get(i).getPlo().get(j));
                }
            }
        }


        return plos;
    }
    private ArrayList<CLO> getCLOs(ArrayList<PLO> plos){
        ArrayList<CLO> clos=null;
        if(plos!=null && !plos.isEmpty()){
            clos =new ArrayList<>();
            for(int i=0; i<plos.size();i++){
                if(plos.get(i).getPlosClos()!=null && !plos.get(i).getPlosClos().isEmpty())
                    for(int j=0; j<plos.get(i).getPlosClos().size();j++){
                        clos.add(plos.get(i).getPlosClos().get(j));
                    }
            }
        }


        return clos;
    }
    private ArrayList<CLO> getUniqueCLOs(ArrayList<Integer> allCLOsIds,ArrayList<CLO> allCLOs) {

        ArrayList<CLO> uniqueCLOS=new ArrayList<>();
        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(allCLOsIds);
        ArrayList<Integer> uniqueTopicIds=new ArrayList<>();
        uniqueTopicIds.addAll(set);

        for(int i=0;i<uniqueTopicIds.size();i++) {
            for(int j=0; j<allCLOsIds.size();j++) {
                if(uniqueTopicIds.get(i)==allCLOs.get(j).getId())
                {
                    uniqueCLOS.add(allCLOs.get(j));
                    break;
                }
            }
        }

        return uniqueCLOS;
    }
    private ArrayList<Integer> getCLOSIds(ArrayList<CLO> clos) {
        ArrayList<Integer> ClosIds =null;
        if(clos!=null && !clos.isEmpty()) {
            ClosIds=new ArrayList<>();
            for (int i = 0; i < clos.size(); i++) {
                ClosIds.add(clos.get(i).getId());
            }
        }
        return ClosIds;
    }
    boolean SearchObjective(String Obj,ArrayList<CLO> clos){
        if(clos!=null && !clos.isEmpty()){
            for (int i = 0; i < clos.size(); i++) {
                if(clos.get(i).getObjective().equals(Obj)){
                    return true;
                }
            }
        }
        return false;
    }
    boolean SearchCourse(String name,ArrayList<Courses> courses){
        if(courses!=null && !courses.isEmpty()){
            for (int i = 0; i < courses.size(); i++) {
                if(courses.get(i).getName().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }
    //functions to print
    private void PrintPrograms() {
        if(this.programs!=null && !this.programs.isEmpty())
        for(int i=0;i<this.programs.size();i++) {
            System.out.println((i+1)+". ID: "+this.programs.get(i).getId()+"   "+this.programs.get(i).getName());
        }
    }
    private void PrintCourses(ArrayList<Courses> courses) {
        for(int i=0;i< courses.size();i++) {
            System.out.println((i+1)+". ID: "+courses.get(i).getId()+"   "+courses.get(i).getName());
        }
    }
    private void PrintTopics(ArrayList<Topics> topics) {
        for(int i=0;i<topics.size();i++)
        System.out.println((i+1)+". ID: "+topics.get(i).getId()+"   "+topics.get(i).getName());
    }
    private void PrintCLOS(ArrayList<CLO>  clos){
        for(int i=0;i<clos.size();i++)
            System.out.println((i+1)+". ID: "+clos.get(i).getId()+"   "+clos.get(i).getObjective());
    }
    private void PrintPrograms(ArrayList<Program> programs){
        if(programs!=null && !programs.isEmpty())
            for(int i=0;i<programs.size();i++) {
                System.out.println((i+1)+". ID: "+programs.get(i).getId()+"   "+programs.get(i).getName());
            }
    }
    private void PrintTeachers(ArrayList<Teacher> teachers){
        if(teachers!=null && !teachers.isEmpty())
            for(int i=0;i<teachers.size();i++) {
                System.out.println((i+1)+". ID: "+teachers.get(i).getId()+"   "+teachers.get(i).getName());
            }
    }
    private void PrintPLos(ArrayList<PLO> plos){
        if(plos!=null && !plos.isEmpty())
            for(int i=0;i<plos.size();i++) {
                System.out.println((i+1)+". ID: "+plos.get(i).getId()+"   "+plos.get(i).getObjective());
            }
    }
    //functions to filter items in list which can be remove
    private ArrayList<Program>FilterPrograms(Courses course){

        ArrayList<Program> filterdPrograms=new ArrayList<>();
        if(course.getPrograms()!=null && !course.getPrograms().isEmpty())
        for(int i=0;i<course.getPrograms().size();i++){
            if(course.getPrograms().get(i).getCourses().size()>1){
                filterdPrograms.add(course.getPrograms().get(i));
            }
        }
        return filterdPrograms;
    }
    private ArrayList<Teacher>FilterTeachers(Courses course){
        ArrayList<Teacher> filteredTeachers=new ArrayList<>();
        if(course.getTeachers()!=null && !course.getTeachers().isEmpty())
        for(int i=0;i<course.getTeachers().size();i++){
            if(course.getTeachers().get(i).getCourses().size()>1){
                filteredTeachers.add(course.getTeachers().get(i));
            }
        }
        return filteredTeachers;
    }
    private ArrayList<CLO>FilterCLOS(Courses course){
        ArrayList<CLO> filteredCLOS=new ArrayList<>();
        if(course.getClos()!=null && !course.getClos().isEmpty())
            for(int i=0;i<course.getClos().size();i++){
                if(course.getClos().get(i).getCourses().size()>1){
                    filteredCLOS.add(course.getClos().get(i));
                }
            }
        return filteredCLOS;
    }
    private ArrayList<Topics>FilterTopics(CLO clo){
        ArrayList<Topics> filterTopics=new ArrayList<>();
        if(clo.getTopics()!=null && !clo.getTopics().isEmpty())
            for(int i=0;i<clo.getTopics().size();i++){
                if(clo.getTopics().get(i).getClos().size()>1){
                    filterTopics.add(clo.getTopics().get(i));
                }
            }
        return filterTopics;
    }
    private ArrayList<Topics> filterTopics(ArrayList<Topics> topics,ArrayList<Topics> allTopics){

        ArrayList<Topics> filteredTopics=new ArrayList<>();
        if(topics!=null &&!topics.isEmpty()){

            for(int i=0;i<allTopics.size();i++){
                boolean found=false;
                for(int j=0; j<topics.size();j++){
                    if(allTopics.get(i).getId()==topics.get(j).getId()) {
                        found = true;
                        break;
                    }
                }
                if(!found)
                    filteredTopics.add(allTopics.get(i));
            }

        }
        return filteredTopics;

    }
    private ArrayList<PLO> FilteredPLO(PLO plo,ArrayList<PLO> plos){
        ArrayList filplos=null;
        {
            if(plo !=null && plos!=null && !plos.isEmpty()){
                filplos=new ArrayList<>();
                for (int i = 0; i < plos.size(); i++) {
                    if(plos.get(i).getId()!=plo.getId())
                        filplos.add(plos.get(i));
                }
            }
        }
        return filplos;
    }
    //functions to print list except already present items
    private ArrayList<Program>getProgramsExceptgivenProg(Courses course){

        if(course.getPrograms()!=null && !course.getPrograms().isEmpty()) {
            ArrayList<Program> alreadyExists = course.getPrograms();
            ArrayList<Program> filteredProg = new ArrayList<>();

            for(int i=0;i<this.programs.size();i++){
                boolean exists=false;
                for(int j=0;j<alreadyExists.size();j++){
                    if(this.programs.get(i).getId()==alreadyExists.get(j).getId()){
                        exists =true;
                        break;
                    }
                }
                if(!exists)
                    filteredProg.add(this.programs.get(i));
            }
            return filteredProg;
        }
        else
        return null;
    }
    private ArrayList<Teacher>getTeachersExceptgivenTeachers(Courses course,ArrayList<Teacher> allTeachers){

        if(course.getTeachers()!=null && !course.getTeachers().isEmpty()) {
            ArrayList<Teacher> alreadyExists = course.getTeachers();
            ArrayList<Teacher> filteredTeacher = new ArrayList<>();

            for(int i=0;i<allTeachers.size();i++){
                boolean exists=false;
                for(int j=0;j<alreadyExists.size();j++){
                    if(allTeachers.get(i).getId()==alreadyExists.get(j).getId()){
                        exists =true;
                        break;
                    }
                }
                if(!exists)
                    filteredTeacher.add(allTeachers.get(i));
            }
            return filteredTeacher;
        }
        else
            return null;
    }
    private ArrayList<CLO>getCLOSExceptgivenClo(Courses course,ArrayList<CLO> allClos){

        if(course.getClos()!=null && !course.getClos().isEmpty()) {
            ArrayList<CLO> alreadyExists = course.getClos();
            ArrayList<CLO> filteredCLO = new ArrayList<>();

            for(int i=0;i<allClos.size();i++){
                boolean exists=false;
                for(int j=0;j<alreadyExists.size();j++){
                    if(allClos.get(i).getId()==alreadyExists.get(j).getId()){
                        exists =true;
                        break;
                    }
                }
                if(!exists)
                    filteredCLO.add(allClos.get(i));
            }
            return filteredCLO;
        }
        else
            return null;
    }

    ////////////////////////getters setters
    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }
    public ArrayList<Program> getPrograms() {
        return programs;
    }
    public ArrayList<Integer> getProgramIds() {
        return programIds;
    }
    public void setProgramIds(ArrayList<Integer> programIds) {
        this.programIds = programIds;
    }

}
