public class LearningOutcomes {
    private int id;
    private String objective;

    //constructor
    public LearningOutcomes(int id, String objective) {
        this.id = id;
        this.objective = objective;
    }

    //getter setters
    public void setId(int id) {
        this.id = id;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public int getId() {
        return id;
    }

    public String getObjective() {
        return objective;
    }
}
