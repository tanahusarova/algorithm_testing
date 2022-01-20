import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SQL {
    private String string;
    private List<Predicate> predicates;
    private HashMap<String, Table> tableHashMap;

    public SQL(String string, List<Table> tables) {
        this.string = string;
        tableHashMap = new HashMap<>();

        for (Table t : tables)
            tableHashMap.put(t.getName(), t);

    }

    private void parse(){
        Parser p = new Parser(string);
        predicates = p.parse();
    }

    public String translateOne(Predicate p){
        StringBuilder select = new StringBuilder();
        StringBuilder from = new StringBuilder();
        StringBuilder where = new StringBuilder();

        boolean tmp = false;

        //domysliet ci netreba premenovavat stlpceky v pomocnych, ako tvorit nove tabulky

        //select vypil_0.s1, vypil_0.s2
        select.append("SELECT ");
        int iS;
        for (Atribute a : p.getAtributes()){
            iS = 1;
            if (tmp) select.append(", ");
            for (Predicate predicate : p.getPredicates()){
                if (!predicate.getNeg() && predicate.getAtributes().contains(a)){
                    select.append(predicate.getName());
                    select.append("_");
                    select.append(iS);
                    select.append(".");
                    int tmpIndex = predicate.getAtributes().indexOf(a);
                    select.append(tableHashMap.get(predicate.getName()).getColumn(tmpIndex));
                    break;
                }
                iS++;
            }
            tmp = true;
        }

        //from lubi lubi_1, navstivil navstivil_2
        from.append(" FROM ");
        int iF = 1;
        tmp = false;
        for (Predicate predicate : p.getPredicates()){
            if (tmp) from.append(", ");
            from.append(predicate.getName());
            from.append(" ");
            from.append(predicate.getName());
            from.append("_");
            from.append(iF);
            tmp = true;
            iF++;
        }

        //where lubi.s1 = navstivil.s2





        return null;

    }
}
