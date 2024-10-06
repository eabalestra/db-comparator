package table;

import java.util.ArrayList;
import java.util.List;

public class Procedures {

    private List<Function> listFunctions;
    private List<Function> diff;

    public Procedures() {
        listFunctions = new ArrayList<>();
        diff = new ArrayList<>();
    }
    
    public Procedures(List<Function> listFunctions) {
        this.listFunctions = listFunctions;
        this.diff = new ArrayList<>();
    }

    //
    public List<Function> getListFunctions(){
        return listFunctions;
    }

    public void setListFunctions(List<Function> listFunctions){
        this.listFunctions = listFunctions;
    }
    
    //
    public List<Function> getDiff(){
        return listFunctions;
    }

    public void setDiff(List<Function> listFunctions){
        this.listFunctions = listFunctions;
    }
    
    //
    public void addFunctions(Function f){
        listFunctions.add(f);
    }

    @Override
    public boolean equals(Object p) {

        if (this == p) {
            return true; 
        }
        if (p == null || getClass() != p.getClass()) {
            return false;
        }

        Procedures procedures = (Procedures) p;
        
        if (listFunctions.size() != procedures.listFunctions.size()) {
            diffFunctions(procedures);
            procedures.diffFunctions(this);
            setDiff(procedures.diff);
            return false;
        }

        diffFunctions(procedures);     
        

        if (!diff.isEmpty()) {
            return false;
        }

        return true;
    }

    
    private void diffFunctions(Procedures p){
        for(Function f : listFunctions){
            boolean aux = false;
            for (Function pFunction : p.listFunctions) {
                
                if (f.equals(pFunction)) {
                    aux = true;   
                    break;
                }
            }
            if (!aux) {
                diff.add(f);
            }
        }
    }
    //Aca me manda a la concha de la lora balestra.

}