import java.util.ArrayList;
import java.util.List;

public class Predicate implements Component{
    private String name;
    private List<Atribute> atributes;
    private List<Predicate> predicates;
    private List<Condition> conditions;
    private boolean neg;

    public Predicate(String name, boolean neg) {
        this.name = name;
        atributes = new ArrayList<>();
        predicates = new ArrayList<>();
        conditions = new ArrayList<>();
        this.neg = neg;
    }

    public Predicate(String name, List<Atribute> atributes) {
        this.name = name;
        this.atributes = atributes;
    }

    public void addAtribute(Atribute a){
        atributes.add(a);
    }

    public void addPredicate(Predicate a){
        predicates.add(a);
    }

    public void negation(){
        neg = true;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public String getName(){
        return name;
    }

    public List<Atribute> getAtributes(){
        return atributes;
    }

    public boolean getNeg(){
        return neg;
    }

    public String toString(){
        String string = "";
        if (neg == true) string = string + "negovane ";

        string = name + "(";
        for (Atribute a : atributes){
            string = string + a.getName() + ", ";
        }
        string = string + ")";
        return string;
    }


    public void addConditions(Condition c) {
        this.conditions.add(c);
    }
}
