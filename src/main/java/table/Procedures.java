package table;

import java.util.ArrayList;
import java.util.List;

public class Procedures {

    private List<Function> listFunctions;

    public Procedures() {
        listFunctions = new ArrayList<>();
        
    }
    
    public Procedures(List<Function> listFunctions) {
        this.listFunctions = listFunctions;
    }


    public List<Function> getListFunctions(){
        return listFunctions;
    }

    public void setListFunctions(List<Function> listFunctions){
        this.listFunctions = listFunctions;
    }
    
    
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
        
        return diffFunctions(procedures);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Procedures { ");
        sb.append("listFunctions = [");

        for (int i = 0; i < listFunctions.size(); i++) {
            sb.append(listFunctions.get(i).toString());
            if (i < listFunctions.size() - 1) {
                sb.append(", "); // Añade una coma después de cada función, excepto la última.
            }
        }

        sb.append("] }");
        return sb.toString();
    }


    
    private boolean diffFunctions(Procedures p){
        for(Function f : listFunctions){
            boolean aux = false;
            for (Function pFunction : p.listFunctions) {
                
                if (f.equals(pFunction)) {
                    aux = true;
                    break;
                }
            }
            if (!aux) {
                return false;
            }
        }

        return true;
    }
    //Aca me manda a la concha de la lora balestra.

}