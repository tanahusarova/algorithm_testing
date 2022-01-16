import java.util.ArrayList;
import java.util.List;

public class Predicate {
    private String name;
    private List<Atribute> atributes;
    private List<Predicate> predicates;
    private boolean neg;

    public Predicate(String name, boolean neg) {
        this.name = name;
        atributes = new ArrayList<>();
        predicates = new ArrayList<>();
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
        if (neg) string = "negovane ";

        string = name + "(";
        for (Atribute a : atributes){
            string = string + a.getName() + ", ";
        }
        string = string + ")";
        return string;
    }
}
