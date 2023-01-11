import java.util.ArrayList;

public class Exam extends Evaluation{

    public  Exam(int id, int Type,ArrayList<Questions> questions, ArrayList<Integer> questionIds, int courseId) {
        super(id,Type,questions,questionIds,courseId);
    }
}
