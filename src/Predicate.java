import java.util.ArrayList;
import java.util.List;

public class Predicate implements Component{
    private String name;
    private List<Atribute> atributes;
    private List<Predicate> predicates;
    private List<Condition> conditions;
    private boolean neg;
    private Verification v;

    public Predicate(String name, boolean neg) {
        this.name = name;
        atributes = new ArrayList<>();
        predicates = new ArrayList<>();
        conditions = new ArrayList<>();
        this.neg = neg;
        v = new Verification(this);

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

    public void addConditions(Condition c) {
        this.conditions.add(c);
    }

    public void negation(){
        neg = true;
    }

    public String getName(){
        return name;
    }

    public List<Atribute> getAtributes(){
        return atributes;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public List<Condition> getConditions(){
        return conditions;
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

    public boolean checkNeg(){
        return v.checkNeg();
    }

    public boolean checkWhole() throws NotSafeRule {
        return v.checkNotRecursive() && v.checkSafety();
    }


}
