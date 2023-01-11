import java.beans.Introspector;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.File;

import java.util.*;

public class Teacher extends User {
    private ArrayList<Courses> courses = new ArrayList<Courses>();
    private ArrayList<Evaluation> evaluation = new ArrayList<Evaluation>();

    //filing params
    private ArrayList<Integer> coursesId = new ArrayList<Integer>();
    private ArrayList<Integer> evaluationIds = new ArrayList<Integer>();

    //constructor
    public Teacher(int id, String name, String password, ArrayList<Courses> courses, ArrayList<Evaluation> evaluation, ArrayList<Integer> evaluationIds, ArrayList<Integer> coursesId) {
        super(id, name, password);
        this.courses = courses;
        this.evaluation = evaluation;
        this.coursesId = coursesId;
        this.evaluationIds = evaluationIds;
    }

    ///////print teacher////////////
    public void printTeacher() {
        System.out.println("ID: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Password: " + this.getPassword());

        if (evaluation != null) {
            for (int i = 0; i < evaluation.size(); i++)
                System.out.print("evaluation " + evaluation.get(i).getId() + "  ");
            System.out.print("     ||||||       ");
        }

        if (evaluationIds != null) {
            for (int i = 0; i < evaluationIds.size(); i++)
                System.out.print("evaluationIds " + evaluationIds.get(i) + "  ");
        }
        System.out.println();

        if (courses != null) {
            for (int i = 0; i < courses.size(); i++)
                System.out.print("Courses " + courses.get(i).getId() + "  ");
            System.out.print("     ||||||       ");
        }


        if (coursesId != null) {
            for (int i = 0; i < coursesId.size(); i++)
                System.out.print("courseId " + coursesId.get(i) + "  ");
        }
        System.out.println('\n');

    }

    ///////////////////////////////functionalities////////////////////////
    public Evaluation addEvaluation() {

        ///////////////addingEvaluation////////////////////
        System.out.println(".....................(Creating Evaluation).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select Course ..................................");
        if (this.courses != null && !this.courses.isEmpty()) {
            for (int i = 0; i < this.courses.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.courses.get(i).getId() + "  " + this.courses.get(i).getName());
            }
            System.out.print("Enter Course No. : ");
            int selected_Course = scan.nextInt() - 1;
            if (selected_Course > this.courses.size() - 1 || selected_Course < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Evaluation Not Created :(................");
                return null;
            }

            int selected_CourseID = this.courses.get(selected_Course).getId();
            Courses sCourse = this.courses.get(selected_Course);
            System.out.println();
            System.out.println("Select Evaluation Type ..................................");
            System.out.println("1. Assignment");
            System.out.println("2. Quiz");
            System.out.println("3. Exam ");
            System.out.print("Enter Type of Evaluation : ");
            int Type = scan.nextInt();
            if (Type > 3 || Type <= 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Evaluation Not Created :(................");
                return null;
            }

            ArrayList<Questions> selected_Questions = new ArrayList<>();
            ArrayList<Integer> selected_Questions_Ids = new ArrayList<>();
            System.out.println();
            System.out.println("1. Add Questions from existing questions......OR");
            System.out.println("2. Create new question");
            System.out.print("Enter choice : ");
            int o = scan.nextInt();
            if (o > 2 || o < 1) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Evaluation Not Created :(................");
                return null;
            } else if (o == 1) {

                if (OBS.getInstance().getAllQuestions() != null && !OBS.getInstance().getAllQuestions().isEmpty()) {

                    System.out.println();
                    System.out.println("(Optional) Select Questions...........Press 0 for finish selecting questions");
                    this.PrintQuestions(OBS.getInstance().getAllQuestions());   //printing courses of selected program
                    System.out.println("Enter Questions (can choose more than 1) : ");
                    ArrayList<Integer> selectedQuestions = new ArrayList<>();
                    int op = -1;
                    while (op != 0) {
                        op = scan.nextInt();
                        if (op != 0)
                            selectedQuestions.add(selectedQuestions.size(), op - 1);
                    }
                    if (!selectedQuestions.isEmpty()) {
                        // System.out.println(selectedCourses.toString());
                        for (int i = 0; i < selectedQuestions.size(); i++) {
                            if (selectedQuestions.get(i) < 0 || selectedQuestions.get(i) > OBS.getInstance().getAllQuestions().size() - 1) {
                                System.out.println("Sorry you Enter Incorrect Option.........");
                                this.Wait("Returning");
                                return null;
                            }
                            selected_Questions.add(selected_Questions.size(), OBS.getInstance().getAllQuestions().get(selectedQuestions.get(i)));
                            selected_Questions_Ids.add(selected_Questions_Ids.size(), OBS.getInstance().getAllQuestions().get(selectedQuestions.get(i)).getId());
                        }
                    }
                } else {
                    System.out.println("Sorry no Questions to Display.");
                    this.Wait("Skipping Questions Selection");
                }

            } else if (o == 2) {
                Questions q = null;
                q = this.createQuestion(sCourse);
                if (q != null) {

                    selected_Questions.add(q);
                    selected_Questions_Ids.add(q.getId());
                } else return null;
            }


            ////////////making id for evaluation///////////////
            int evid = 1;
            if (OBS.getInstance().getAllEvaluations() != null && !OBS.getInstance().getAllEvaluations().isEmpty()) {
                ArrayList<Integer> evids = new ArrayList<>();
                for (int i = 0; i < OBS.getInstance().getAllEvaluations().size(); i++) {
                    evids.add(OBS.getInstance().getAllEvaluations().get(i).getId());
                }
                evids.sort(Collections.reverseOrder());
                evid = evids.get(0) + 1;
            }

            this.Wait("Creating Evaluation");
            Evaluation ev = null;
            if (Type == 1) {
                ev = new Assignments(evid, Type, selected_Questions, selected_Questions_Ids, selected_CourseID);
            } else if (Type == 2) {
                ev = new Quiz(evid, Type, selected_Questions, selected_Questions_Ids, selected_CourseID);
            } else if (Type == 3) {
                ev = new Exam(evid, Type, selected_Questions, selected_Questions_Ids, selected_CourseID);
            }

            this.getEvaluation().add(ev);
            this.getEvaluationIds().add(ev.getId());

            return ev;

        } else {
            System.out.println("Sorry you teaches no course. First get some course.........");
            this.Wait("Returning");
            System.out.println(".....................Evaluation Not Created :(................");
        }

        return null;
    }

    public Evaluation deleteEvaluation() {

        ///////////////deleting evaluation////////////////////
        System.out.println(".....................(Deleting Evaluation).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select Course ..................................");
        if (this.courses != null && !this.courses.isEmpty()) {
            for (int i = 0; i < this.courses.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.courses.get(i).getId() + "  " + this.courses.get(i).getName());
            }
            System.out.print("Enter Course No. : ");
            int selected_Course = scan.nextInt() - 1;
            if (selected_Course > this.courses.size() - 1 || selected_Course < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Evaluation Not Created :(................");
                return null;
            }
            int selected_CourseID = this.courses.get(selected_Course).getId();

            ArrayList<Evaluation> evalua = null;
            evalua = this.getEvaluationsofSelectedCourse(selected_CourseID);

            if (evalua != null && !evalua.isEmpty()) {
                System.out.println();
                System.out.println("Selecting Evaluation to delete");
                for (int i = 0; i < evalua.size(); i++) {
                    System.out.println((i + 1) + ". " + "ID: " + evalua.get(i).getId() + "  " + evalua.get(i).getCoursesId());
                }
                System.out.print("Enter Evaluation No. : ");
                int selected_EvaluId = scan.nextInt() - 1;
                if (selected_EvaluId > evalua.size() - 1 || selected_EvaluId < 0) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    System.out.println(".....................Evaluation Not Deleted :(................");
                    return null;
                }
                Evaluation selected_Evalu = evalua.get(selected_EvaluId);

                this.Wait("Deleting Evaluation");
                this.getEvaluation().remove(selected_Evalu);
                this.getEvaluationIds().remove(this.getEvaluationIds().indexOf(selected_Evalu.getId()));


                return selected_Evalu;
            } else {
                System.out.println("Sorry no evaluations to delete. First add Evaluation.........");
                this.Wait("Returning");
            }

        } else {
            System.out.println("Sorry you teaches no course. First get some course.........");
            this.Wait("Returning");
            System.out.println(".....................Evaluation Not Deleted :(................");
        }

        return null;
    }

    public int updateEvaluation() {
        ///////////////updating Evaluation////////////////////
        System.out.println(".....................(Updating Evaluation).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select Course ..................................");
        if (this.courses != null && !this.courses.isEmpty()) {
            for (int i = 0; i < this.courses.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.courses.get(i).getId() + "  " + this.courses.get(i).getName());
            }
            System.out.print("Enter Course No. : ");
            int selected_Course = scan.nextInt() - 1;
            if (selected_Course > this.courses.size() - 1 || selected_Course < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Evaluation Not Updated :(................");
                return -1;
            }
            int selected_CourseID = this.courses.get(selected_Course).getId();
            Courses sCourse = this.courses.get(selected_Course);

            ArrayList<Evaluation> evalua = null;
            evalua = this.getEvaluationsofSelectedCourse(selected_CourseID);

            if (evalua != null && !evalua.isEmpty()) {

                System.out.println();
                System.out.println("Selecting Evaluations........................................ ");
                for (int i = 0; i < evalua.size(); i++) {
                    System.out.println((i + 1) + ". " + "ID: " + evalua.get(i).getId() + "  " + evalua.get(i).getCoursesId());
                }
                System.out.print("Enter Evaluation No. : ");
                int selected_EvaluId = scan.nextInt() - 1;
                if (selected_EvaluId > evalua.size() - 1 || selected_EvaluId < 0) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    System.out.println(".....................Evaluation Not Updated :(................");
                    return -1;
                }
                Evaluation selected_Evalu = evalua.get(selected_EvaluId);

                System.out.println();
                System.out.println("Updating Questions of Evaluation..................................");
                System.out.println("1. Add Question");
                System.out.println("2. Remove Question");
                System.out.print("Enter what do you want? : ");

                int op = scan.nextInt();
                if (op > 2 || op <= 0) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    System.out.println(".....................Evaluation Not Updated :(................");
                    return -1;
                }

                //adding questions
                else if (op == 1) {

                    System.out.println();
                    System.out.println("1. Add Questions from existing questions......OR");
                    System.out.println("2. Create new question");
                    System.out.print("Enter choice : ");
                    int o = scan.nextInt();
                    if (o > 2 || o < 1) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        System.out.println(".....................Evaluation Not Created :(................");
                        return -1;
                    } else if (o == 1) {
                        ArrayList<Questions> filtQuestions = this.FilterQuestions(selected_Evalu);

                        if (filtQuestions != null && !filtQuestions.isEmpty()) {
                            System.out.println();
                            System.out.println("Selecting Question to Add..................................");
                            this.PrintQuestions(filtQuestions);
                            System.out.print("Enter Question No. to add : ");
                            int op1 = scan.nextInt() - 1;
                            if (op1 > filtQuestions.size() - 1 || op1 < 0) {
                                System.out.println("Sorry you Enter Incorrect Option.........");
                                this.Wait("Returning");
                                System.out.println(".....................Evaluation Not Updated :(................");
                                return -1;
                            }
                            Questions squestion = filtQuestions.get(op1);

                            this.Wait("Updating Evaluation");
                            selected_Evalu.getQuestions().add(squestion);
                            selected_Evalu.getQuestionsIds().add(squestion.getId());

                            return 0;
                        } else {
                            System.out.println("Sorry Selected Evaluation has no Assignable Question. Please add Questions first.........");
                            this.Wait("Returning");
                            System.out.println(".....................Evaluation Not Updated :(................");
                            return -1;
                        }
                    } else if (o == 2) {
                        Questions q = null;
                        q = this.createQuestion(sCourse);
                        if (q != null) {

                            this.Wait("Updating Evaluation");
                            selected_Evalu.getQuestions().add(q);
                            selected_Evalu.getQuestionsIds().add(q.getId());

                            return 0;
                        } else return -1;
                    }

                }


                //removing questions
                else if (op == 2) {
                    ArrayList<Questions> questions = selected_Evalu.getQuestions();
                    if (questions != null && !questions.isEmpty()) {
                        System.out.println();
                        System.out.println("Selecting Question to remove..................................");
                        this.PrintQuestions(questions);
                        System.out.print("Enter Question No. to remove : ");
                        int op1 = scan.nextInt() - 1;
                        if (op1 > questions.size() - 1 || op1 < 0) {
                            System.out.println("Sorry you Enter Incorrect Option.........");
                            this.Wait("Returning");
                            System.out.println(".....................Evaluation Not Updated :(................");
                            return -1;
                        }
                        Questions squestion = questions.get(op1);

                        this.Wait("Updating Evaluation");
                        selected_Evalu.getQuestions().remove(squestion);
                        selected_Evalu.getQuestionsIds().remove(selected_Evalu.getQuestionsIds().indexOf(squestion.getId()));

                        return 0;
                    } else {
                        System.out.println("Sorry Selected Evaluation has no removable Question.........");
                        this.Wait("Returning");
                        System.out.println(".....................Evaluation Not Updated :(................");
                        return -1;
                    }

                }
            } else {
                System.out.println("Sorry no evaluations to delete. First add Evaluation.........");
                this.Wait("Returning");
            }

        } else {
            System.out.println("Sorry you teaches no course. First get some course.........");
            this.Wait("Returning");
            System.out.println(".....................Evaluation Not Deleted :(................");
        }

        return -1;
    }

    public Questions AddQuestion() {
        //////////////////////////addingQuestions////////////////////////
        System.out.println(".....................(Creating Question).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select Course ..................................");
        if (this.courses != null && !this.courses.isEmpty()) {
            for (int i = 0; i < this.courses.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.courses.get(i).getId() + "  " + this.courses.get(i).getName());
            }
            System.out.print("Enter Course No. : ");
            int selected_Course = scan.nextInt() - 1;
            if (selected_Course > this.courses.size() - 1 || selected_Course < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Question Not Created :(................");
                return null;
            }


            Courses sCourse = this.courses.get(selected_Course);
            ArrayList<Integer> selected_Evaluations_Ids = null;

            if (this.getEvaluationsofSelectedCourse(sCourse.getId()) != null) {

                selected_Evaluations_Ids = new ArrayList<>();
                System.out.println();
                System.out.println(" Select Evaluations...........Press 0 for finish selecting Evaluations");
                this.printEvaluation(this.getEvaluationsofSelectedCourse(sCourse.getId()));   //printing courses of selected program
                System.out.println("Enter Evaluation (can choose more than 1) : ");
                ArrayList<Integer> selectedEval = new ArrayList<>();
                int oppp = -1;
                while (oppp != 0) {
                    oppp = scan.nextInt();
                    if (oppp != 0)
                        selectedEval.add(selectedEval.size(), oppp - 1);
                }

                if (selectedEval.isEmpty()) {
                    System.out.println("Sorry you Selected Nothing.........");
                    this.Wait("Returning");
                    return null;
                }

                // System.out.println(selectedCourses.toString());
                for (int i = 0; i < selectedEval.size(); i++) {
                    if (selectedEval.get(i) < 0 || selectedEval.get(i) > this.getEvaluationsofSelectedCourse(sCourse.getId()).size() - 1) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        return null;
                    }
                    selected_Evaluations_Ids.add(sCourse.getClos().get(selectedEval.get(i)).getId());
                }

                ///////////////////////////////////////////////////////////////////////////
                ArrayList<Integer> selected_Clos_Ids=null;
                if (sCourse.getClos()!=null && !sCourse.getClos().isEmpty()) {
                    selected_Clos_Ids = new ArrayList<>();
                    System.out.println();
                    System.out.println(" Select CLOS...........Press 0 for finish selecting clos");
                    this.printCLOS(sCourse.getClos());   //printing courses of selected program
                    System.out.println("Enter CLOS (can choose more than 1) : ");
                    ArrayList<Integer> selectedClos = new ArrayList<>();
                    int op = -1;
                    while (op != 0) {
                        op = scan.nextInt();
                        if (op != 0)
                            selectedClos.add(selectedClos.size(), op - 1);
                    }

                    if (selectedClos.isEmpty()) {
                        System.out.println("Sorry you Selected Nothing.........");
                        this.Wait("Returning");
                        return null;
                    }

                    // System.out.println(selectedCourses.toString());
                    for (int i = 0; i < selectedClos.size(); i++) {
                        if (selectedClos.get(i) < 0 || selectedClos.get(i) > sCourse.getClos().size() - 1) {
                            System.out.println("Sorry you Enter Incorrect Option.........");
                            this.Wait("Returning");
                            return null;
                        }
                        selected_Clos_Ids.add(sCourse.getClos().get(selectedClos.get(i)).getId());
                    }
                }
                System.out.print("Enter Question Statement : ");
                scan.nextLine();
                String statement = scan.nextLine();

                ////////////making id for questions///////////////
                int qid = 1;
                if (OBS.getInstance().getAllQuestions() != null && !OBS.getInstance().getAllQuestions().isEmpty()) {
                    ArrayList<Integer> qids = new ArrayList<>();
                    for (int i = 0; i < OBS.getInstance().getAllQuestions().size(); i++) {
                        qids.add(OBS.getInstance().getAllQuestions().get(i).getId());
                    }
                    qids.sort(Collections.reverseOrder());
                    qid = qids.get(0) + 1;
                }

                this.Wait("Creating Question");
                Questions q = new Questions(qid, statement, selected_Clos_Ids);

                for (int i =0; i <selected_Evaluations_Ids.size() ; i++) {
                    ArrayList<Integer> quesIds=this.getEvaluation().get(this.getEvaluationIds().indexOf(selected_Evaluations_Ids.get(i))).getQuestionsIds();
                    ArrayList<Questions> ques=this.getEvaluation().get(this.getEvaluationIds().indexOf(selected_Evaluations_Ids.get(i))).getQuestions();
                    if(ques==null)ques=new ArrayList<>();
                    if(quesIds==null)quesIds=new ArrayList<>();
                    ques.add(q);
                    quesIds.add(q.getId());
                    this.getEvaluation().get(this.getEvaluationIds().indexOf(selected_Evaluations_Ids.get(i))).setQuestions(ques);
                    this.getEvaluation().get(this.getEvaluationIds().indexOf(selected_Evaluations_Ids.get(i))).setQuestionsIds(quesIds);
                }

                return q;
            }
            else{
                System.out.println("Sorry you didn't created any evaluation. Please add evaluation fist  to add question");
                this.Wait("Returning");
                return null;
            }
        }
        else {
            System.out.println("Sorry you don't teach any course. Please fist teach a course to update questions ");
            this.Wait("Returning");
            return null;
        }

    }

    public int UpdateQuestion() {
        //////////////////////////updatingQuestions////////////////////////
        System.out.println(".....................(Updating Question).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select Course ..................................");
        if (this.courses != null && !this.courses.isEmpty()) {
            for (int i = 0; i < this.courses.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.courses.get(i).getId() + "  " + this.courses.get(i).getName());
            }
            System.out.print("Enter Course No. : ");
            int selected_Course = scan.nextInt() - 1;
            if (selected_Course > this.courses.size() - 1 || selected_Course < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Question Not Updated :(................");
                return -1;
            }

            Courses sCourse = this.courses.get(selected_Course);

            ArrayList<Evaluation> evalua = null;
            evalua = this.getEvaluationsofSelectedCourse(sCourse.getId());
            if (evalua != null && !evalua.isEmpty()) {

                ArrayList<Questions> questions = this.getQuestions(evalua);
                if (questions != null && !questions.isEmpty()) {

                    System.out.println();
                    System.out.println("Select Question ..................................");
                    for (int i = 0; i < questions.size(); i++) {
                        System.out.println((i + 1) + ". " + "ID: " + questions.get(i).getId() + "  " + questions.get(i).getStatement());
                    }
                    System.out.print("Enter questions No. : ");
                    int selected_Question = scan.nextInt() - 1;
                    if (selected_Question > questions.size() - 1 || selected_Question < 0) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        System.out.println(".....................Question Not Updated :(................");
                        return -1;
                    }

                    Questions q = questions.get(selected_Question);
                    System.out.println();
                    System.out.println("Selecting Options................................................");
                    System.out.println("1. Update Statement");
                    System.out.println("2. Update CLOs");
                    System.out.print("Enter What do you want> : ");
                    int subop = scan.nextInt();
                    if (subop > 2 || subop < 1) {
                        System.out.println("Sorry you Enter Incorrect Option.........");
                        this.Wait("Returning");
                        System.out.println(".....................Question Not Updated :(................");
                        return -1;
                    }

                    if (subop == 1) {
                        System.out.print("Enter New Statement : ");
                        scan.nextLine();
                        String newStat = scan.nextLine();

                        this.Wait("Updating");
                        q.setStatement(newStat);
                        return 0;
                    } else if (subop == 2) {

                        ArrayList<Integer> selected_Clos_Ids = null;
                        if (sCourse.getClos() != null && !sCourse.getClos().isEmpty()) {
                            selected_Clos_Ids = new ArrayList<>();
                            System.out.println();
                            System.out.println("Select CLOS...........Press 0 for finish selecting clos");
                            this.printCLOS(sCourse.getClos());   //printing courses of selected program
                            System.out.println("Enter CLOS (can choose more than 1) : ");
                            ArrayList<Integer> selectedClos = new ArrayList<>();
                            int op = -1;
                            while (op != 0) {
                                op = scan.nextInt();
                                if (op != 0)
                                    selectedClos.add(selectedClos.size(), op - 1);
                            }

                            if (selectedClos.isEmpty()) {
                                System.out.println("Sorry you Selected Nothing.........");
                                this.Wait("Returning");
                                return -1;
                            }

                            // System.out.println(selectedCourses.toString());
                            for (int i = 0; i < selectedClos.size(); i++) {
                                if (selectedClos.get(i) < 0 || selectedClos.get(i) > sCourse.getClos().size() - 1) {
                                    System.out.println("Sorry you Enter Incorrect Option.........");
                                    this.Wait("Returning");
                                    return -1;
                                }
                                selected_Clos_Ids.add(sCourse.getClos().get(selectedClos.get(i)).getId());
                            }
                            this.Wait("Updating");
                            q.setClos(selected_Clos_Ids);
                            return 0;

                        }

                    }
                }
            }
        }
        System.out.println("Sorry you didn't added questions to this course.........");
        this.Wait("Returning");
        return -1;
    }

    public Questions deleteQuestion() {
        //////////////////////////deleting Questions////////////////////////
        System.out.println(".....................(Deleting Question).....................");
        Scanner scan = new Scanner(System.in);


        ArrayList<Evaluation> evalua = OBS.getInstance().getAllEvaluations();
        if (evalua != null && !evalua.isEmpty()) {
            ArrayList<Questions> questions = OBS.getInstance().getAllQuestions();
            if (questions != null && !questions.isEmpty()) {

                for (int i = 0; i < questions.size(); i++) {
                    System.out.println((i + 1) + ". " + "ID: " + questions.get(i).getId() + "  " + questions.get(i).getStatement());
                }
                System.out.print("Enter questions No. : ");
                int selected_Question = scan.nextInt() - 1;
                if (selected_Question > questions.size() - 1 || selected_Question < 0) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    System.out.println(".....................Question Not Deleted :(................");
                    return null;
                }


                Questions q = questions.get(selected_Question);

                this.Wait("Deleting");
                for (int i = 0; i < evalua.size(); i++) {

                    if (evalua.get(i).getQuestions() != null && !evalua.get(i).getQuestions().isEmpty()) {
                        int index = evalua.get(i).getQuestionsIds().indexOf(q.getId());
                        if (index != -1) {
                            evalua.get(i).getQuestions().remove(q);
                            evalua.get(i).getQuestionsIds().remove(index);
                        }
                    }
                }

                return q;
            } else {
                System.out.println("Sorry NO questions to delete.........");
                this.Wait("Returning");
                System.out.println(".....................Question Not Deleted :(................");
                return null;
            }
        }

        System.out.println("Sorry NO questions to delete.........");
        this.Wait("Returning");
        System.out.println(".....................Question Not Deleted :(................");
        return null;

    }

    public void CheckCloTested() {
        System.out.println(".....................(Checking CLO Tested).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select Course ..................................");
        if (this.courses != null && !this.courses.isEmpty()) {
            for (int i = 0; i < this.courses.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.courses.get(i).getId() + "  " + this.courses.get(i).getName());
            }
            System.out.print("Enter Course No. : ");
            int selected_Course = scan.nextInt() - 1;
            if (selected_Course > this.courses.size() - 1 || selected_Course < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                return;
            }

            Courses sCourse = this.courses.get(selected_Course);

            if (sCourse.getClos() != null && !sCourse.getClos().isEmpty()) {
                System.out.println();
                System.out.println(" Select CLO...........Press 0 for finish selecting clos");
                this.printCLOS(sCourse.getClos());   //printing courses of selected program
                System.out.println("Enter a CLO No. : ");
                int selected_clo = scan.nextInt() - 1;
                if (selected_clo > sCourse.getClos().size() - 1 || selected_clo < 0) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    return;
                }
                CLO sCLO = sCourse.getClos().get(selected_clo);

                this.Wait("Processing");
                if (this.TestCLO(sCLO.getId(), sCourse) == true) {
                    System.out.println();
                    System.out.println("Congrats Selected ClO has been tested by evaluations :).....................");
                } else {
                    System.out.println();
                    System.out.println("Selected CLO has not been tested yet......................." + '\n' + "Kindly take evaluations to test selected clo:).....................");
                }
            } else {
                System.out.println("Sorry Selected Course don't have any clo.........");
                this.Wait("Returning");
                return;
            }
        } else {
            System.out.println("Sorry you don't teach any course........");
            this.Wait("Returning");
            return;
        }
    }

    public void CheckAllClosTested() {
        System.out.println(".....................(Checking  CLO Tested).....................");
        Scanner scan = new Scanner(System.in);
        System.out.println("Select Course ..................................");
        if (this.courses != null && !this.courses.isEmpty()) {
            for (int i = 0; i < this.courses.size(); i++) {
                System.out.println((i + 1) + ". " + "ID: " + this.courses.get(i).getId() + "  " + this.courses.get(i).getName());
            }
            System.out.print("Enter Course No. : ");
            int selected_Course = scan.nextInt() - 1;
            if (selected_Course > this.courses.size() - 1 || selected_Course < 0) {
                System.out.println("Sorry you Enter Incorrect Option.........");
                this.Wait("Returning");
                System.out.println(".....................Question Not Created :(................");
                return;
            }

            Courses sCourse = this.courses.get(selected_Course);

            if (sCourse.getClos() != null && !sCourse.getClos().isEmpty()) {

                this.Wait("Processing");
                ArrayList<CLO> tested = new ArrayList<>();
                ArrayList<CLO> notTested = new ArrayList<>();

                for (int i = 0; i < sCourse.getClos().size(); i++) {
                    if (this.TestCLO(sCourse.getClos().get(i).getId(), sCourse) == true) {
                        tested.add(sCourse.getClos().get(i));
                    } else {
                        notTested.add(sCourse.getClos().get(i));
                    }
                }

                if (tested.size() == sCourse.getClos().size()) {
                    System.out.println();
                    System.out.println("Congrats All ClOs of selected course has been tested by evaluations :).....................");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Not  all CLOs of selected course has been tested by Evaluations .....................");
                    System.out.println();
                    System.out.println("..............................<><(Report)><>..............................");
                    System.out.println();
                    if (!tested.isEmpty())
                        System.out.print("Tested CLOs:- ");
                    for (int i = 0; i < tested.size(); i++) {
                        System.out.print("CLO : " + tested.get(i).getId() + "   ");
                    }
                    if (!notTested.isEmpty())
                        System.out.print('\n' + "Not Tested CLOS :- ");
                    for (int i = 0; i < notTested.size(); i++) {
                        System.out.print("CLO : " + notTested.get(i).getId() + "   ");
                    }
                    System.out.println();
                    System.out.println("..........................................................................");
                    System.out.println('\n');
                    System.out.println("Do you want to save report on this System?  [Y/N]");
                    System.out.print("Select choice: ");
                    char op=scan.next().charAt(0);
                    if(op=='Y' || op=='y'){

                        this.Wait("Generating Report");
                        String path = OBS.getInstance().getPath().concat("Courses_CLOs_Reports\\"+"Teacher_"+this.getId()+"\\Course_"+sCourse.getId()+".txt");

                        try {
                              String data="";
                            //Creating a File object
                            File file = new File(OBS.getInstance().getPath().concat("Courses_CLOs_Reports\\Teacher_"+this.getId()));
                            file.mkdir();
                                FileWriter myWriter = new FileWriter(path);
                                data=data.concat("\t\t\t"+sCourse.getName()+'\n');
                                if (!tested.isEmpty())
                                    data=data.concat("Tested CLOs:- ");
                                for (int i = 0; i < tested.size(); i++) {
                                    data=data.concat("CLO : " + tested.get(i).getId() + "\t");
                                }
                                if (!notTested.isEmpty())
                                    data=data.concat('\n' + "Not Tested CLOS :- ");
                                for (int i = 0; i < notTested.size(); i++) {
                                    data= data.concat("CLO : " + notTested.get(i).getId() + "\t");
                                }
                                myWriter.write(data);
                                myWriter.close();
                                System.out.println("Report Generated in System Successfully");
                                System.out.println("You can see Report at " + '"' + path + '"');

                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }


                    }
                    System.out.println("\n\n");


                }

            } else {
                System.out.println("Sorry Selected Course don't have any clo.........");
                this.Wait("Returning");
                return;
            }
        } else {
            System.out.println("Sorry you don't teach any course........");
            this.Wait("Returning");
            return;
        }
    }

    private boolean TestCLO(int clo, Courses course) {

        ArrayList<Evaluation> ev = null;
        ev = this.getEvaluationsofSelectedCourse(course.getId());
        if (ev != null) {
            int count = 0;
            for (int i = 0; i < ev.size(); i++) {
                if (ev.get(i).getQuestions() != null && !ev.get(i).getQuestions().isEmpty()) {
                    for (int q = 0; q < ev.get(i).getQuestions().size(); q++) {
                        if (ev.get(i).getQuestions().get(q).checkCLO(clo) == true)
                            count++;
                    }

                }
            }
            if (count >= 2)
                return true;
        }
        return false;
    }

    //////////////////////////helper functions//////////////////////////////
    private void Wait(String str) {
        try {
            int i = 0;
            System.out.print(str);
            while (i < 3) {
                System.out.print(".");
                Thread.sleep(800);
                i++;
            }
            System.out.println();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void assignCourse(Courses course) {

        if (this.courses == null)
            this.courses = new ArrayList<>();
        if (this.coursesId == null)
            this.coursesId = new ArrayList<>();
        this.courses.add(courses.size(), course);
        this.coursesId.add(coursesId.size(), course.getId());

    }

    private void PrintQuestions(ArrayList<Questions> questions) {
        if (questions != null && !questions.isEmpty()) {
            for (int i = 0; i < questions.size(); i++) {
                System.out.println((i + 1) + ". ID: " + questions.get(i).getId() + "   " + questions.get(i).getStatement());
            }
        }
    }

    private void printCLOS(ArrayList<CLO> clos) {
        if (clos != null && !clos.isEmpty()) {
            for (int i = 0; i < clos.size(); i++) {
                System.out.println((i + 1) + ". ID: " + clos.get(i).getId() + "   " + clos.get(i).getObjective());
            }
        }
    }

    private ArrayList<Evaluation> getEvaluationsofSelectedCourse(int course) {

        ArrayList<Evaluation> evalua = null;
        if (this.getEvaluation() != null && !this.getEvaluation().isEmpty()) {
            evalua = new ArrayList<>();
            for (int i = 0; i < this.getEvaluation().size(); i++) {
                if (getEvaluation().get(i).getCoursesId() == course) {
                    evalua.add(getEvaluation().get(i));
                }
            }
        }
        return evalua;
    }

    private ArrayList<Questions> FilterQuestions(Evaluation evaluation) {

        if (evaluation.getQuestions() != null && !evaluation.getQuestions().isEmpty()) {
            ArrayList<Questions> alreadyExists = evaluation.getQuestions();
            ArrayList<Questions> filteredQuestions = new ArrayList<>();

            for (int i = 0; i < OBS.getInstance().getAllQuestions().size(); i++) {
                boolean exists = false;
                for (int j = 0; j < alreadyExists.size(); j++) {
                    if (OBS.getInstance().getAllQuestions().get(i).getId() == alreadyExists.get(j).getId()) {
                        exists = true;
                        break;
                    }
                }
                if (!exists)
                    filteredQuestions.add(OBS.getInstance().getAllQuestions().get(i));
            }
            return filteredQuestions;
        } else
            return null;
    }

    private ArrayList<Questions> getQuestions(ArrayList<Evaluation> ev) {
        ArrayList<Questions> questions = null;
        if (ev != null && !ev.isEmpty()) {
            questions = new ArrayList<>();
            for (int i = 0; i < ev.size(); i++) {
                ArrayList<Questions> tempQue = ev.get(i).getQuestions();
                if (tempQue != null && !tempQue.isEmpty()) {
                    for (int j = 0; j < tempQue.size(); j++) {
                        if (!Exist(tempQue.get(j), questions)) {
                            questions.add(tempQue.get(j));
                        }
                    }
                }
            }
        }
        return questions;
    }

    private Questions createQuestion(Courses sCourse) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> selected_Clos_Ids = null;
        if (sCourse.getClos() != null && !sCourse.getClos().isEmpty()) {
            selected_Clos_Ids = new ArrayList<>();
            System.out.println();
            System.out.println(" Select CLOS...........Press 0 for finish selecting clos");
            this.printCLOS(sCourse.getClos());   //printing courses of selected program
            System.out.println("Enter CLOS (can choose more than 1) : ");
            ArrayList<Integer> selectedClos = new ArrayList<>();
            int op = -1;
            while (op != 0) {
                op = scan.nextInt();
                if (op != 0)
                    selectedClos.add(selectedClos.size(), op - 1);
            }

            if (selectedClos.isEmpty()) {
                System.out.println("Sorry you Selected Nothing.........");
                this.Wait("Returning");
                return null;
            }

            // System.out.println(selectedCourses.toString());
            for (int i = 0; i < selectedClos.size(); i++) {
                if (selectedClos.get(i) < 0 || selectedClos.get(i) > sCourse.getClos().size() - 1) {
                    System.out.println("Sorry you Enter Incorrect Option.........");
                    this.Wait("Returning");
                    return null;
                }
                selected_Clos_Ids.add(sCourse.getClos().get(selectedClos.get(i)).getId());
            }

        }
        System.out.print("Enter Question Statement : ");
        scan.nextLine();
        String statement = scan.nextLine();

        ////////////making id for questions///////////////
        int qid = 1;
        if (OBS.getInstance().getAllQuestions() != null && !OBS.getInstance().getAllQuestions().isEmpty()) {
            ArrayList<Integer> qids = new ArrayList<>();
            for (int i = 0; i < OBS.getInstance().getAllQuestions().size(); i++) {
                qids.add(OBS.getInstance().getAllQuestions().get(i).getId());
            }
            qids.sort(Collections.reverseOrder());
            qid = qids.get(0) + 1;
        }

        this.Wait("Creating Question");
        Questions q = new Questions(qid, statement, selected_Clos_Ids);

        OBS.getInstance().getAllQuestions().add(q);
        return q;
    }

    boolean Exist(Questions q, ArrayList<Questions> ques) {
        if (ques != null && !ques.isEmpty()) {
            for (int i = 0; i < ques.size(); i++) {
                if (q.getId() == ques.get(i).getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void  printEvaluation(ArrayList<Evaluation> eval) {

        if (eval != null && !eval.isEmpty()) {
            for (int i = 0; i < eval.size(); i++) {
                System.out.println((i + 1) + ". ID: " + eval.get(i).getId() + "  C_ID " + eval.get(i).getCoursesId()+ "  Q_IDs " + eval.get(i).getQuestionsIds().toString());
            }
        }
    }


    ///////////////////////////////////////getters setters////////////////////////
    public ArrayList<Courses> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Courses> courses) {
        this.courses = courses;
    }

    public void setEvaluation(ArrayList<Evaluation> evaluation) {
        this.evaluation = evaluation;
    }

    public void setCoursesId(ArrayList<Integer> coursesId) {
        this.coursesId = coursesId;
    }

    public void setEvaluationIds(ArrayList<Integer> evaluationIds) {
        this.evaluationIds = evaluationIds;
    }

    public ArrayList<Evaluation> getEvaluation() {
        return evaluation;
    }

    public ArrayList<Integer> getCoursesId() {
        return coursesId;
    }

    public ArrayList<Integer> getEvaluationIds() {
        return evaluationIds;
    }
}

