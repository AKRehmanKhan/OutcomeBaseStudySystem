import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.*;
public class Topics {
    private int id;
    private String name;
    private ArrayList<CLO> clos = new ArrayList<CLO>();

    /////////filing param//////
    private ArrayList<Integer> closIds = new ArrayList<Integer>();

    //constructor
    public Topics( int id,String name, ArrayList<CLO> clos,ArrayList<Integer> closIds) {
        this.id = id;
        this.name = name;
        this.clos = clos;
        this.closIds=closIds;
    }

    //print topic
    public void PrintTopic() {
        System.out.println("ID: "+id);
        System.out.println("Title: "+name);

        if(clos!=null) {
            for (int i = 0; i < clos.size(); i++)
                System.out.print("CLO " + clos.get(i).getId() + "  ");
            System.out.print("      |||     ");
        }

        if(closIds!=null) {
            for (int i = 0; i < closIds.size(); i++)
                System.out.print("CLOIds " + closIds.get(i)+ "  ");
        }
        System.out.println('\n');

    }

    ////custom functions
    public void assignCLO(CLO clo) {
        if(!clos.contains(clo)) {
            if(this.clos==null)
                this.clos=new ArrayList<>();
            if(this.closIds==null)
                this.closIds=new ArrayList<>();
            this.clos.add(clos.size(), clo);
            this.closIds.add(closIds.size(),clo.getId());
        }
        else   System.out.println("You are trying to assign same CLO again");
    }

    //////getters setters
    public void setClos(ArrayList<CLO> clos) {

        this.clos = clos;
    }

    public void setClosIds(ArrayList<Integer> closIds) {
        this.closIds = closIds;
    }

    public ArrayList<CLO> getClos() {
        return clos;
    }

    public ArrayList<Integer> getClosIds() {
        return closIds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
