package table;

import java.util.List;

public class Function {
    private String name;
    private List<String> listParams;
    private String returnType;

    public Function(String name, List<String> listParams, String returnType) {
        this.name = name;
        this.listParams = listParams;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public List<String> getListParams() {
        return listParams;
    }

    public String getReturnType() {
        return returnType;
    }

	@Override
    public boolean equals(Object f) {

		if (this == f) {
            return true; 
        }
        if (f == null || getClass() != f.getClass()) {
            return false;
        }

		Function function = (Function) f;

        if (!name.equals(function.name)) {
            return false;
        }
        if (!returnType.equals(function.returnType)) {
			return false;
        }
		if (!listParams.equals(function.listParams)) {
			return false;
		}
		return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Function { ");
        sb.append("name = '").append(name).append('\'');
        sb.append(", returnType = '").append(returnType).append('\'');
        sb.append(", listParams = ").append(listParams);
        sb.append(" }");
        return sb.toString();
    }


}