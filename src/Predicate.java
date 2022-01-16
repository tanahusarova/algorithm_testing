import java.util.ArrayList;
import java.util.List;

public class Predicate {
    private String name;
    private List<Atribute> atributes;
    private List<Predicate> predicates;
    private boolean neg;

    public Predicate(String name) {
        this.name = name;
        atributes = new ArrayList<>();
        predicates = new ArrayList<>();
        neg = false;
    }

    public Predicate(String name, List<Atribute> atributes) {
        this.name = name;
        this.atributes = atributes;
    }

    public void add(Atribute a){
        atributes.add(a);
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
}
