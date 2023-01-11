import java.util.ArrayList;

public class Quiz extends Evaluation{

    public  Quiz(int id,int Type, ArrayList<Questions> questions, ArrayList<Integer> questionIds, int courseId) {
        super(id,Type,questions,questionIds,courseId);
    }
}
