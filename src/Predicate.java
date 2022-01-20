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

    }

    public void verificate(Verification v) {
        this.v = v;
        v.setP(this);
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

        StringBuilder sb = new StringBuilder();

        if (neg) sb.append("negovane "); //zistit preco nejde

        sb.append(name + "(");
        boolean tmp = false;

        for (Atribute a : atributes){
            if (tmp) sb.append(", ");
            sb.append(a.getName());
            tmp = true;
        }

        sb.append(")");
        return sb.toString();
    }

    public boolean checkNeg(){
        return v.checkNeg();
    }

    public boolean checkWhole() {
        return v.checkNotRecursive() && v.checkSafety();
    }


    public boolean containsA(Atribute a){
        for (Atribute atribute : atributes)
            if (atribute.getName().equals(a.getName())) return true;

        return false;
    }

    public int indexOf(Atribute a){
        for (int i = 0; i < atributes.size(); i++){
            if (a.getName().equals(atributes.get(i).getName())) return i;
        }
        return -1;
    }


}
