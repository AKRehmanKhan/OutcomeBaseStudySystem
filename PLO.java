import java.util.ArrayList;

public class PLO extends LearningOutcomes{

    private Program program;
    private ArrayList<CLO> plosClos=new ArrayList<>();

    //filing params
    private int programId;
    private ArrayList<Integer> plosClosIds=new ArrayList<>();

    //constructor
    public PLO(int id, String objective, Program program,int programId,ArrayList<CLO> plosClos,ArrayList<Integer> plosClosIds) {
        super(id, objective);
        this.program = program;
        this.programId= programId;
        this.plosClos=plosClos;
        this.plosClosIds=plosClosIds;
    }

    //print plo
    public void printPLO() {
        System.out.println("ID: "+this.getId());
        System.out.println("objective: "+this.getObjective());

        if(program!=null)
            System.out.print("Program: "+program.getId()+"       |||         ");

        System.out.println("ProgramId: "+programId);
        System.out.println();

        if(plosClos!=null) {
            for (int i = 0; i < plosClos.size(); i++)
                System.out.print("plosClos " + plosClos.get(i).getId() + "  ");
            System.out.print("       |||         ");
        }

        if(plosClosIds!=null) {
            for (int i = 0; i < plosClosIds.size(); i++)
                System.out.print("PlosClosId " + plosClosIds.get(i) + "  ");
        }

        System.out.println('\n');
    }

    //custom function
    public void assignCLO(CLO clo) {
        if(this.plosClos==null)
            this.plosClos=new ArrayList<>();
        if(this.plosClosIds==null)
            this.plosClosIds=new ArrayList<>();
        this.plosClos.add(this.plosClos.size(),clo);
        this.plosClosIds.add(this.plosClosIds.size(),clo.getId());
    }

    ////////////////////////////////////////////////getter setters///////////////////////////
    public void setPlosClos(ArrayList<CLO> plosClos) {
        this.plosClos = plosClos;
    }

    public void setPlosClosIds(ArrayList<Integer> plosClosIds) {
        this.plosClosIds = plosClosIds;
    }

    public ArrayList<CLO> getPlosClos() {
        return plosClos;
    }

    public ArrayList<Integer> getPlosClosIds() {
        return plosClosIds;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

}
