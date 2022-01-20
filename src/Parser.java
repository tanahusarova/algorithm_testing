import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Parser {
    private char[] line;
    private HashSet<String> tables;
    private int i;
    private HashSet<Character> charPred;
    private HashSet<Character> charAtrib;

    public Parser(String string) {
        line = string.toCharArray();
        i = 0;

        charPred = new HashSet<>(){{
            add('_');
            add('-');
        }};

        charAtrib = new HashSet<>();

        for (int i = 65; i <= 90; i++){
            charAtrib.add((char) i);
            charPred.add((char) i);
            charAtrib.add((char) (i + 32));
            charPred.add((char) (i + 32));
        }

    }

    private void spaces(){
        while (i < line.length && line[i] == ' ')
            i++;

    }

    private Optional<Boolean> negation(){

        if (line[i] == '\\'){
            i++;
            spaces();
            if (line[i] == '+'){
                i++;
                spaces();
                return Optional.of(true);
            }
            else return Optional.empty();
        }
        return Optional.of(false);
    }

    private String readName(HashSet<Character> chars){

        StringBuilder sb = new StringBuilder();
        while (i < line.length && chars.contains(line[i])){
            sb.append(line[i]);
            i++;
        }
        return sb.toString();
    }

    //chybaju podmienky na koniec retazca
    //zmenit na private

    private Component readNext(){
        spaces();
      //  Component result = null;
        boolean neg;
        Optional<Boolean> tmpO = negation();

        if (tmpO.isEmpty()) return null;
        neg = tmpO.get();

        if (line[i] >= 97 && line[i] <= 122)
            return readPredicate(neg, false);

        if (line[i] >= 65 && line[i] <= 90)
            return readCondition(neg);

        return null;

    }

    public Predicate readPredicate(boolean neg, boolean newP){
        Predicate result;

        String name = readName(charPred);

        spaces();

        if (line[i] != '(') return null;

        i++;
        result = new Predicate(name, neg);

        spaces();

        boolean prve = false;

        while(i < line.length && line[i] != ')') {

            if (prve) {
                if (line[i] != ',') return null;
                i++;
                spaces();
            }

            if (line[i] == '\''){
                i++;
                StringBuilder tmp = new StringBuilder();
                while((int) line[i] != '\''){
                   tmp.append(line[i]);
                   i++;
                }
                result.addAtribute(new Atribute(tmp.toString()));
                i++;
            }
            else if (line[i] == '_' && !newP) {
                result.addAtribute(new Atribute(line[i]));
                i++;
            }
            else if ((int) line[i] < 65 || (int) line[i] > 90 || line[i] == '_') return null;
            else {
                result.addAtribute(new Atribute(readName(charAtrib)));
            }
            spaces();
            prve = true;

        }
        i++;
        spaces();

        return result;
    }

    //doplnit, domysliet condition
    private Condition readCondition(boolean neg){
        String a1;
        if (charAtrib.contains(line[i])) {
            a1 = readName(charAtrib);

        }


        return null;
    }

    public Predicate parseLine(){
        spaces();
        Predicate result = readPredicate(false, true);
        if (result == null) return null;

        if (line[i] == ':'){
            i++;
            spaces();
            if (line[i] == '-'){
                i++;
                spaces();
            }
            else return null;
        }
        boolean prve = false;
        while(i < line.length && line[i] != '.'){
            spaces();

            if (prve) {
                if (line[i] != ',') return null;
                i++;
                spaces();
            }

            Component tmp = readNext();

            if (tmp == null) return null;

            if (tmp instanceof Predicate) result.addPredicate((Predicate) tmp);
            else result.addConditions((Condition) tmp);

            prve = true;

        }

        i++;

        return result;
    }


    public List<Predicate> parse() {
        List<Predicate> result = new ArrayList<>();

        while(i < line.length){
            Predicate tmp = parseLine();
            spaces();
            if (line[i] < 97 || line[i] > 122) return null;
            result.add(tmp);
        }

        return result;

    }

    }
