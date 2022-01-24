import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SQL {
    private String string;
    private List<Predicate> predicates;
    private Database database;

    public SQL(String string, Database database) {
        this.string = string;
        this.database = database;
    }

    private void parse(){
        Parser p = new Parser(string);
        predicates = p.parse();
    }

    public String translateOne(Predicate p){
        StringBuilder main = new StringBuilder();
        StringBuilder select = new StringBuilder();
        StringBuilder from = new StringBuilder();
        StringBuilder where = new StringBuilder();

        boolean tmp = false;
        main.append("CREATE TEMPORARY TABLE ");
        main.append(p.getName());
        main.append(" AS ");
        List<String> columns = new ArrayList<>();

        //domysliet ci netreba premenovavat stlpceky v pomocnych, ako tvorit nove tabulky

        //select vypil_0.s1, vypil_0.s2
        select.append(" SELECT ");
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
                    select.append(database.get(predicate.getName()).getColumn(tmpIndex));
                    break;
                }
                select.append(" AS ");
                String s = "column_" + iS;
                select.append(s);
                columns.add(s);
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
        where.append(" WHERE ");
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

                    if (!tmpPredicates.get(k).getNeg() && tmpPredicates.get(k).containsA(tmpAtributes.get(l))){
                        if (tmp) where.append(" AND ");
                        where.append(tmpPredicates.get(i).getName());
                        where.append("_");
                        where.append(i + 1);
                        where.append(".");
                        where.append(database.get(tmpPredicates.get(i).getName()).getColumn(l));
                        where.append(" = ");
                        where.append(tmpPredicates.get(k).getName());
                        where.append("_");
                        where.append(k + 1);
                        where.append(".");
                        //chcem zistit v ktorom stlpceku je moj hladany atribut
                        int index = tmpPredicates.get(k).indexOf(tmpAtributes.get(l));
                        where.append(database.get(tmpPredicates.get(k).getName()).getColumn(index));
                        tmp = true;
                    }
                }

                used.add(tmpAtributes.get(l).getName());
            }
        }

        StringBuilder negation = new StringBuilder();

        for (int i = 0; i < tmpPredicates.size(); i++) {
            if (!tmpPredicates.get(i).getNeg()) continue;

            if (tmp) where.append(" AND ");
            negation.append("NOT EXISTS (SELECT * FROM ");
            negation.append(tmpPredicates.get(i).getName());
            negation.append(" ");
            String tmpName = tmpPredicates.get(i).getName() + "_" + iF;
            negation.append(tmpName);
            iF++;
            negation.append(" WHERE ");
            List<Atribute> atributeList = tmpPredicates.get(i).getAtributes();
            boolean tmp2 = false;

            for (int l = 0; l < atributeList.size(); l++) {

                for (int k = 0; k < i; k++) {
                    if (!tmpPredicates.get(k).getNeg() && tmpPredicates.get(k).containsA(atributeList.get(l))){
                        if (tmp2) negation.append(" AND ");
                        negation.append(tmpName);
                        negation.append(".");
                        negation.append(database.get(tmpPredicates.get(i).getName()).getColumn(l));
                        negation.append(" = ");
                        negation.append(tmpPredicates.get(k).getName());
                        negation.append("_");
                        negation.append(k + 1);
                        negation.append(".");
                        int index = tmpPredicates.get(k).indexOf(atributeList.get(l));
                        negation.append(database.get(tmpPredicates.get(k).getName()).getColumn(index));
                        tmp2 = true;
                    }
                }
            }
            negation.append(") ");

        }


        select.append(from.toString());
        select.append(where.toString());
        select.append(negation.toString());
        select.append(";");
        main.append(select.toString());

        database.addTable(new Table(p.getName(), columns));
        //pridat novu tabulku do databazy
        return main.toString();

    }
}
