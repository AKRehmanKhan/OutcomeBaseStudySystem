import java.util.ArrayList;

public class Evaluation {
    private int id;
    private ArrayList<Questions> questions;
    private int coursesId;

    //filing param
    private int Type;
    private ArrayList<Integer> questionsIds;

    //constructor
    public  Evaluation(int id,int Type,ArrayList<Questions> questions,ArrayList<Integer> questionsIds,int coursesIds){
        this.id=id;
        this.Type=Type;
        this.questions= questions;
        this.questionsIds=questionsIds;
        this.coursesId=coursesIds;
    }

    //print evaluation
    public void printEvaluation(){
        if(this!=null)
        {
            System.out.println("ID : "+this.getId());
            System.out.println("Statement : "+this.getType());
            System.out.println("Type : "+this.getCoursesId());

            if(this.questions!=null && !questions.isEmpty()){

                for (int i = 0; i < questions.size(); i++) {
                    System.out.print("Clos "+questions.get(i).getId()+"       ");
                }
                System.out.print("      |||        ");
            }

            if(this.questionsIds!=null && !questionsIds.isEmpty())
            for (int i = 0; i < questionsIds.size(); i++) {
                System.out.print("QuestionsIds "+questionsIds.get(i)+"       ");
            }

            System.out.println('\n');
        }
    }

    //getter setters
    public int getId() {
        return id;
    }

    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    public ArrayList<Integer> getQuestionsIds() {
        return questionsIds;
    }

    public int getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(int coursesId) {
        this.coursesId = coursesId;
    }

    public void setQuestionsIds(ArrayList<Integer> questionsIds) {
        this.questionsIds = questionsIds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }

    public int getType() {
        return Type;
    }
}
