import java.util.*;
public class CLO extends LearningOutcomes {

    private ArrayList<Topics> topics = new ArrayList<Topics>();
    private ArrayList<Courses> courses =new ArrayList<Courses>();
    private PLO plo;

    ////filing params
    private int ploId;
    private ArrayList<Integer> topicsIds = new ArrayList<Integer>();
    private ArrayList<Integer> courseIds = new ArrayList<Integer>();

    ///constructor
    public CLO(int id, String objective, ArrayList<Topics> topics, ArrayList<Courses> courses,ArrayList<Integer> topicsIds,  ArrayList<Integer> coursesIds,PLO plo,int ploId ) {
        super(id, objective);
        this.topics = topics;
        this.courses = courses;
        this.topicsIds=topicsIds;
        this.courseIds=coursesIds;
        this.plo=plo;
        this.ploId=ploId;
    }

    //printing clos
    public void printCLO() {
        System.out.println("ID: "+this.getId());
        System.out.println("objective: "+this.getObjective());
        if(plo!=null)
        {
            System.out.println("plo: "+this.getPlo().getId());
        }
        System.out.println("plo Id: "+this.getPloId());

        if(courses!=null) {
            for (int i = 0; i < courses.size(); i++)
                System.out.print("Courses " + courses.get(i).getId() + "  ");
            System.out.print("      |||     ");
        }

        if(courseIds!=null) {
            for (int i = 0; i < courseIds.size(); i++)
                System.out.print("courseIds " + courseIds.get(i)+ "  ");
        }
        System.out.println();

        if(topics!=null) {
            for (int i = 0; i < topics.size(); i++)
                System.out.print("Topics " + topics.get(i).getId() + "  ");
            System.out.print("      |||     ");
        }

        if(topicsIds!=null) {
            for (int i = 0; i < topicsIds.size(); i++)
                System.out.print("TopicsId " + topicsIds.get(i)+ "  ");
        }
        System.out.println('\n');
    }

    //custom functions
    public void assignTopic(Topics topic) {

        if(this.topics==null)
            this.topics=new ArrayList<>();
        if(this.topicsIds==null)
            this.topicsIds=new ArrayList<>();
        this.topics.add(topics.size(), topic);
        this.topicsIds.add(topicsIds.size(),topic.getId());

    }
    public void assignCourse(Courses course) {

        if(this.courses==null)
            this.courses=new ArrayList<>();
        if(this.courseIds==null)
            this.courseIds=new ArrayList<>();
        this.courses.add(courses.size(), course);
        this.courseIds.add(courseIds.size(),course.getId());

    }


    /////////////////////////getter setters/////////////////////

    public void setTopicsIds(ArrayList<Integer> topicsIds) {
        this.topicsIds = topicsIds;
    }

    public void setCourseId(ArrayList<Integer> courseId) {
        this.courseIds = courseId;
    }

    public ArrayList<Integer> getTopicsIds() {
        return topicsIds;
    }

    public ArrayList<Integer> getCourseId() {
        return courseIds;
    }

    public PLO getPlo() {
        return plo;
    }

    public int getPloId() {
        return ploId;
    }

    public void setPlo(PLO plo) {
        this.plo = plo;
    }

    public void setPloId(int ploId) {
        this.ploId = ploId;
    }

    public void setCourses(ArrayList<Courses> courses) {
        this.courses = courses;
    }

    public void setCourseIds(ArrayList<Integer> courseIds) {
        this.courseIds = courseIds;
    }

    public ArrayList<Courses> getCourses() {
        return courses;
    }

    public ArrayList<Integer> getCourseIds() {
        return courseIds;
    }

    public void setTopics(ArrayList<Topics> topics) {
        this.topics = topics;
    }

    public ArrayList<Topics> getTopics() {
        return topics;
    }
}
