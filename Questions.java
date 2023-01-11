import java.util.ArrayList;

public class Questions {
    private int id;
    private String Statement;
    private ArrayList<Integer> clos;

    //constructor
    public Questions(int id,String statement, ArrayList<Integer> clos) {
        this.id=id;
        Statement = statement;
        this.clos = clos;
    }

    //print Question
    public void printQuestion() {
        if(this!=null)
        {
            System.out.println("ID : "+this.getId());
            System.out.println("Statement : "+this.getStatement());
            if(this.clos!=null && !clos.isEmpty()){
                for (int i = 0; i < clos.size(); i++) {
                    System.out.print("Clos "+clos.get(i)+"       ");
                }
            }

            System.out.println('\n');
        }
    }

    //custom function
    public boolean checkCLO(int clo){
        if(this.clos!=null && !this.clos.isEmpty())
        for (int i = 0; i < this.clos.size(); i++) {
            if(this.clos.get(i)==clo)
            {
                return true;
            }
        }
        return false;
    }

    //////////////getter setters////////////
    public int getId() {
        return id;
    }

    public String getStatement() {
        return Statement;
    }

    public ArrayList<Integer> getClos() {
        return clos;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }

    public void setClos(ArrayList<Integer> clos) {
        this.clos = clos;
    }
}
