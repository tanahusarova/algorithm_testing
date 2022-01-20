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
                if (!predicate.getNeg() && predicate.containsA(a)){
                    select.append(predicate.getName());
                    select.append("_");
                    select.append(iS);
                    select.append(".");
                    int tmpIndex = predicate.indexOf(a);
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
        where.append(" where ");
        tmp = false;

        for (Condition c : p.getConditions()){
            if (tmp) where.append(" AND ");
            where.append(c.toString());
            tmp = true;
        }

        HashSet<String> used = new HashSet<>();

        List<Predicate> tmpPredicates = p.getPredicates();

        for (int i = 0; i < tmpPredicates.size(); i++){
            List<Atribute> tmpAtributes = tmpPredicates.get(i).getAtributes();

            for (int l = 0; l < tmpAtributes.size(); l++){
                if (used.contains(tmpAtributes.get(l).getName())) continue;

                for (int k = i + 1; k < tmpPredicates.size(); k++){

                    if (tmpPredicates.get(k).containsA(tmpAtributes.get(l))){
                        if (tmp) where.append(" AND ");
                        where.append(tmpPredicates.get(i).getName());
                        where.append("_");
                        where.append(i + 1);
                        where.append(".");
                        where.append(tableHashMap.get(tmpPredicates.get(i).getName()).getColumn(l));
                        where.append(" = ");
                        where.append(tmpPredicates.get(k).getName());
                        where.append("_");
                        where.append(k + 1);
                        where.append(".");
                        //chcem zistit v ktorom stlpceku je moj hladany atribut
                        int index = tmpPredicates.get(k).indexOf(tmpAtributes.get(l));
                        where.append(tableHashMap.get(tmpPredicates.get(k).getName()).getColumn(index));
                        tmp = true;
                    }
                }

                used.add(tmpAtributes.get(l).getName());
            }
        }

        select.append(from.toString());
        select.append(where.toString());
        select.append(";");

        return null;

    }
}
