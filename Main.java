import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

      Scanner scan=new Scanner(System.in);
      AcademicOfficer officer = null;
      Teacher teacher = null;
      int op=-1;

      OBS obs = OBS.getInstance(); //Creating OBS System

        while(op!=0) {

          int option = -1;
          int loginId = obs.Login();  //Login

          /////////////////////////////////Retrieving/////////////////////////////////
          officer = obs.RetrieverLogedInOfficer(loginId);  //retrieving logged in officer

          if (officer == null)
              teacher = obs.RetrieverLogedInTeacher(loginId);//retrieving logged in teacher

          //////////////////////////////////Menu and Working///////////////////////////////
          if (officer != null)
              while (option != 0) {
                  Wait("Loading Main Menu"); //loading Menu
                  obs.PrintOfficerMenu();       //printing Officers Menu
                  System.out.print("Select Option: ");
                  option = scan.nextInt();    //Taking option Input

                  //incorrect option
                  if (option < 0 || option > 3) {
                      System.out.println("Sorry you enter incorrect option");
                      System.out.println("Please Enter again");
                  }

                  //closing System
                  else if (option == 0) {
                      Wait("Logging Out");
                      obs.Save_OBS_System();    //Saving OBS
                  }

                  //Managing Courses
                  else if (option == 1) {
                      int subop = -1;
                      while (subop != 0) {
                          obs.Print_ManageCOURSE_Menu();
                          System.out.print("Select Option: ");
                          subop = scan.nextInt();

                          //incorrect option
                          if (subop < 0 || subop > 3) {
                              System.out.println("Sorry you enter incorrect option");
                              System.out.println("Please Enter again");
                          }

                          //adding Course
                          else if (subop == 1) {
                              obs.AddCourse(officer);
                          }

                          //updating Course
                          else if (subop == 2) {
                              obs.updateCourse(officer);
                          }

                          // deleting Course
                          else if (subop == 3) {
                              obs.deleteCourse(officer);
                          }
                      }
                  }

                  //Managing CLOS
                  else if (option == 2) {
                      int subop = -1;
                      while (subop != 0) {
                          obs.Print_ManageCLOS_Menu();
                          System.out.print("Select Option: ");
                          subop = scan.nextInt();

                          if (subop < 0 || subop > 3) {
                              System.out.println("Sorry you enter incorrect option");
                              System.out.println("Please Enter again");
                          }

                          //adding CLO
                          else if (subop == 1) {
                              obs.AddCLO(officer);
                          }

                          //updating CLO
                          else if (subop == 2) {
                              obs.updateCLO(officer);
                          }

                          // deleting CLO
                          else if (subop == 3) {
                              obs.deleteClo(officer);
                          }
                      }
                  }

                  //Special FUnctionalities
                  else if (option == 3) {
                      obs.Special_Officer();
                  }


              }

          else if (teacher != null)
              while (option != 0) {
                  Wait("Loading Main Menu"); //loading Menu
                  obs.PrintTeacherMenu(); //Printing Teacher's Menu
                  System.out.print("Select Option: ");
                  option = scan.nextInt();

                  //incorrect option
                  if (option < 0 || option > 5) {
                      System.out.println("Sorry you enter incorrect option");
                      System.out.println("Please Enter again");
                  }

                  //closing System
                  else if (option == 0) {
                      Wait("Logging Out");
                      obs.Save_OBS_System();    //Saving OBS
                  }

                  //managing Questions
                  else if (option == 1) {
                      int subop = -1;
                      while (subop != 0) {
                          obs.Print_ManageQuestions_Menu();
                          System.out.print("Select Option: ");
                          subop = scan.nextInt();

                          //incorrect option
                          if (subop < 0 || subop > 3) {
                              System.out.println("Sorry you enter incorrect option");
                              System.out.println("Please Enter again");
                          }

                          //add question
                          else if (subop == 1) {
                              obs.AddQuestion(teacher);
                          }

                          //updating question
                          else if (subop == 2) {
                              obs.updateQuestion(teacher);
                          }

                          //delete question
                          else if (subop == 3) {
                              obs.DeleteQuestion(teacher);
                          }

                      }
                  }

                  //managing Evaluations
                  else if (option == 2) {
                      int subop = -1;
                      while (subop != 0) {
                          obs.Print_ManageEvaluations_Menu();
                          System.out.print("Select Option: ");
                          subop = scan.nextInt();

                          //incorrect option
                          if (subop < 0 || subop > 3) {
                              System.out.println("Sorry you enter incorrect option");
                              System.out.println("Please Enter again");
                          }

                          //adding evaluation
                          else if (subop == 1) {
                              obs.AddEvaluation(teacher);
                          }

                          //updating evaluation
                          else if (subop == 2) {
                              obs.updateEvaluation(teacher);
                          }

                          // deleting evaluation
                          else if (subop == 3) {
                              obs.deleteEvaluation(teacher);
                          }
                      }
                  }

                  //check a clo tested
                  else if (option == 3) {
                      obs.CheckCLOTested(teacher);
                  }

                  //check all clo tested
                  else if (option == 4) {
                      obs.CheckAllCLOTested(teacher);
                  }

                  //Special FUnctionalities
                  else if (option == 5) {
                      obs.Special_Officer();
                  }


              }

          else {
              System.out.println("Oops! Some Unexpected Thing Happened.................");
              Wait("Safely Closing System");
              return;
          }

          obs.printLogoutMenu();
          op=scan.nextInt();
      }
      Wait("closing");  //Closing System
      return;
    }

    //helper function
    private static void Wait(String str) {

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


}

