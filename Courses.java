import java.util.*;
public class Courses {
    private int id;
    private String name;
    private ArrayList<Program>  programs= new ArrayList<Program>();
    private ArrayList<CLO> clos= new ArrayList<CLO>();
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private int creditHours;

    public void printCourses() {
        System.out.println("ID: "+id);
        System.out.println("Title: "+name);
        System.out.println("Credit Hours: "+creditHours);

        if(programs!=null) {
            for (int i = 0; i < programs.size(); i++)
                System.out.print("programs " + programs.get(i).getId() + "  ");
            System.out.print("      |||     " );
        }

        if(programIds!=null) {
            for (int i = 0; i < programIds.size(); i++)
                System.out.print("programids " + programIds.get(i)+ "  ");
        }
        System.out.println();
        if(clos!=null) {
            for (int i = 0; i < clos.size(); i++)
                System.out.print("CLO " + clos.get(i).getId() + "  ");
            System.out.print("      |||     " );
        }

        if(cloIds!=null) {
            for (int i = 0; i < cloIds.size(); i++)
                System.out.print("CLOIds " + cloIds.get(i)+ "  ");
        }
        System.out.println();
        if(teachers!=null) {
            for (int i = 0; i < teachers.size(); i++)
                System.out.print("teachers " + teachers.get(i).getId() + "  ");
            System.out.print("      |||     " );
        }

        if(teacherIds!=null) {
            for (int i = 0; i < teacherIds.size(); i++)
                System.out.print("teacherIds " + teacherIds.get(i)+ "  ");
        }
        System.out.println('\n');
    }

    //filing params
    private ArrayList<Integer>  programIds= new ArrayList<Integer>();
    private ArrayList<Integer> cloIds= new ArrayList<Integer>();
    private ArrayList<Integer> teacherIds= new ArrayList<Integer>();

    //constructor
    public Courses(int id, String name, ArrayList<Program>  programs, ArrayList<CLO> clos, ArrayList<Teacher> teachers,int creditHours,ArrayList<Integer>  programIds,ArrayList<Integer> cloIds,ArrayList<Integer> teacherIds) {
        this.id = id;
        this.name = name;
        this.programs = programs;
        this.clos = clos;
        this.teachers=teachers;
        this.creditHours = creditHours;
        this.programIds=programIds;
        this.cloIds=cloIds;
        this.teacherIds=teacherIds;
    }

    public void assignCLO(CLO clo) {
        if(!clos.contains(clo)) {
            if(this.clos==null)
                this.clos=new ArrayList<>();
            if(this.cloIds==null)
               this. cloIds=new ArrayList<>();
            this.clos.add(clos.size(), clo);
            this.cloIds.add(clo.getId());
        }
        else   System.out.println("You are trying to assign same CLO again");

    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setCloIds(ArrayList<Integer> cloIds) {
        this.cloIds = cloIds;
    }

    public void setTeacherIds(ArrayList<Integer> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Integer> getCloIds() {
        return cloIds;
    }

    public ArrayList<Integer> getTeacherIds() {
        return teacherIds;
    }

    public ArrayList<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }

    public void setProgramIds(ArrayList<Integer> programIds) {
        this.programIds = programIds;
    }

    public ArrayList<Integer> getProgramIds() {
        return programIds;
    }

    public void setClosIds(ArrayList<Integer> closIds) {
        this.cloIds = closIds;
    }

    public ArrayList<Integer> getClosIds() {
        return cloIds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CLO> getClos() {
        return clos;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClos(ArrayList<CLO> clos) {
        this.clos = clos;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }


}
