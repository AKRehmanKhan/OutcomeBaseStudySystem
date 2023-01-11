import java.util.*;
public class Program {
    private int id;
    private String name;
    private ArrayList<Courses> courses = new ArrayList<Courses>();
    private ArrayList<PLO> plo = new ArrayList<PLO>();
    private ArrayList<AcademicOfficer> officers = new ArrayList<AcademicOfficer>();

    //filing params
    private ArrayList<Integer> courseIds = new ArrayList<Integer>();
    private ArrayList<Integer> ploIds = new ArrayList<Integer>();
    private ArrayList<Integer> officerIds = new ArrayList<Integer>();

    //constructor
    public Program(int id, String name, ArrayList<Courses> courses, ArrayList<PLO> plo,ArrayList<AcademicOfficer> officers, ArrayList<Integer> courseIds, ArrayList<Integer> ploIds, ArrayList<Integer> officerIds ) {
        this.id = id;
        this.name = name;
        this.courses = courses;
        this.plo= plo;
        this.courseIds=courseIds;
        this.ploIds=ploIds;
        this.officers=officers;
        this.officerIds=officerIds;
    }

    //print program
    public void printProgram() {
        System.out.println("ID: "+id);
        System.out.println("name: "+name);


        if(courses!=null) {
            for (int i = 0; i < courses.size(); i++)
                System.out.print("Courses" + courses.get(i).getId() + "  ");
            System.out.print("      ||      ");
        }


        if(courseIds!=null) {
            for (int i = 0; i < courseIds.size(); i++)
                System.out.print("courseIds " + courseIds.get(i)+ "  ");
        }

        System.out.println();
        if(plo!=null) {
            for (int i = 0; i < plo.size(); i++)
                System.out.print("Plos " + plo.get(i).getId() + "  ");
            System.out.print("      ||      ");
        }


        if(ploIds!=null) {
            for (int i = 0; i < ploIds.size(); i++)
                System.out.print("ploIds " + ploIds.get(i)+ "  ");
        }
        System.out.println();


        if(officers!=null) {
            for (int i = 0; i < officers.size(); i++)
                System.out.print("officers " + officers.get(i).getId() + "  ");
            System.out.print("      ||      ");
        }


        if(officerIds!=null) {
            for (int i = 0; i < officerIds.size(); i++)
                System.out.print("officersIds " + officerIds.get(i)+ "  ");
        }
        System.out.println('\n');
    }

    //custom function
    public void assignCourse(Courses course) {

        if(this.courses==null)
            this.courses=new ArrayList<>();
        if(this.courseIds==null)
            this.courseIds=new ArrayList<>();
        this.courses.add(courses.size(), course);
        this.courseIds.add(courseIds.size(),course.getId());

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourses(ArrayList<Courses> courses) {
        this.courses = courses;
    }

    public void setPlo(ArrayList<PLO> plo) {
        this.plo = plo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Courses> getCourses() {
        return courses;
    }

    public ArrayList<PLO> getPlo() {
        return plo;
    }

    public void setCourseIds(ArrayList<Integer> courseIds) {
        this.courseIds = courseIds;
    }

    public void setPloIds(ArrayList<Integer> ploIds) {
        this.ploIds = ploIds;
    }

    public ArrayList<Integer> getCourseIds() {
        return courseIds;
    }

    public ArrayList<Integer> getPloIds() {
        return ploIds;
    }

    public void setOfficers(ArrayList<AcademicOfficer> officers) {
        this.officers = officers;
    }

    public void setOfficerIds(ArrayList<Integer> officerIds) {
        this.officerIds = officerIds;
    }

    public ArrayList<AcademicOfficer> getOfficers() {
        return officers;
    }

    public ArrayList<Integer> getOfficerIds() {
        return officerIds;
    }


}
