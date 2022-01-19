import java.util.HashSet;
import java.util.Optional;

public class Parser {
    private String string;
    private char[] line;
    private HashSet<String> tables;
    private int i;
    private HashSet<Character> chars; //povolene znaky

    public Parser(String string, HashSet<String> tables) {
        this.string = string;
        this.tables = tables;
        line = string.toCharArray();
        i = 0;

        //domysliet na male pismena, _, -
        chars = new HashSet<>(){{
            add('a');
            add('b');
            add('c');
        }};
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

    //doplnit podciarkniky, chybaju podmienky na koniec retazca
    //zmenit na private
    public Predicate readPredicate(){
        spaces();
        StringBuilder sb = new StringBuilder();
        Predicate result = null;
        boolean neg;
        Optional<Boolean> tmpO = negation();

        if (tmpO.isEmpty()) return null;
        neg = tmpO.get();


        /*
        if (line[i] == '\\'){
            i++;
            spaces();
            if (line[i] == '+'){
               i++;
               neg = true;
               spaces();
            }
            else return null;
        }

         */

        while (i < line.length && chars.contains(line[i])){
            sb.append(line[i]);
            i++;
        }
        spaces();

        if (line[i] != '(') return null;

        i++;
        result = new Predicate(sb.toString(), neg);

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
            else if (line[i] != '_' && ((int) line[i] < 65 || (int) line[i] > 90)) return null;

            else {
                result.addAtribute(new Atribute(line[i]));
                i++;
            }
            spaces();
            prve = true;

        }
        i++;
        spaces();

        return result;
    }

    public Predicate parseLine(){
        spaces();
        Predicate result = readPredicate();
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

            //zatial bez porovnavani a rovna sa

            Predicate tmp = readPredicate();

            //overovanie ci sa predikat nachadza v databaze ako tabulka
            if (tmp == null && !tables.contains(tmp.getName())) return null;
            result.addPredicate(tmp);
            prve = true;


        }

        i++;

        return result;
    }

    public Predicate parse() {
        return null;

    }
    }
