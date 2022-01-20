import java.util.HashMap;
import java.util.HashSet;

public class Verification {
    private Predicate p;
    private HashSet<String> tables;

    public Verification(HashSet<String> tables) {
        this.tables = tables;
    }

    public void setP(Predicate p) {
        this.p = p;
    }

    public boolean checkNeg(){

        if (p.getNeg()){
            for (Atribute a : p.getAtributes())
                if (a.getName().equals("_")) return false;
        }
        return true;
    }

    public boolean checkSafety(){
        HashSet<String> positive = new HashSet<>();
        HashMap<String, Integer> numOsOccurr = new HashMap<>();

        for (Atribute a : p.getAtributes()){
            numOsOccurr.put(a.getName(), 0);
        }

        for (Predicate predicate : p.getPredicates()){
            Boolean neg = predicate.getNeg();
            if (!predicate.checkNeg() || !tables.contains(predicate.getName())) return false;

            //toto asi nebude dobre, verifikacia je nastavena len na hlavny predikat, treba domysliet

            for (Atribute a : predicate.getAtributes()) {

                if (!numOsOccurr.containsKey(a.getName()))
                    numOsOccurr.put(a.getName(), 0);
                else
                    numOsOccurr.put(a.getName(), numOsOccurr.get(a.getName()) + 1);

                if (!neg)
                    positive.add(a.getName());
                else {
                    if (!positive.contains(a.getName())) return false;
                    //atribut negovaneho predikatu nieje predtym v pozitivnom vyzname
                }
            }

        }

        for (Atribute a : p.getAtributes()){
            if (numOsOccurr.get(a.getName()) == 0) return false; //atributy predikatu su neohranicene
        }

        // doupravovat podla parsera a podla condition

        for (Condition c : p.getConditions()){
            String a = c.getA1().getName();

            for (int i = 0; i < 2; i++) {
                if (a == null){
                    a = c.getA2().getName();  //ak by a1 nikdy nebolo null, staci nechat continue
                    continue;
                }

                if (!numOsOccurr.containsKey(a))
                    numOsOccurr.put(a, 0);
                else
                    numOsOccurr.put(a, numOsOccurr.get(a) + 1);

                a = c.getA2().getName();
            }

        }

        for (String s : numOsOccurr.keySet()){
            if (numOsOccurr.get(s) == 0) return false; //nejaka premenna sa objavuje len raz
        }

        return true;


    }

    public boolean checkNotRecursive(){
        for (Predicate predicate : p.getPredicates())
            if (predicate.getName().equals(p.getName())) return false;

        return true;
    }
}
