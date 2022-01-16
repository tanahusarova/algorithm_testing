import java.util.HashSet;

public class Parser {
    private String string;
    private char[] line;
    private HashSet<String> tables;
    private String predicate;
    private int i;
    private HashSet<Character> chars; //povolene znaky

    public Parser(String string, HashSet<String> tables) {
        this.string = string;
        this.tables = tables;
        line = string.toCharArray();
        i = 0;

        //domysliet
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

    //doplnit podciarkniky a negaciu, chybaju podmienky na koniec retazca

    public Predicate readPredicate(){
        spaces();
        StringBuilder sb = new StringBuilder();
        Predicate result = null;

        while (i < line.length && chars.contains(line[i])){
            sb.append(line[i]);
            i++;
        }
        spaces();

        if (line[i] != '(') return null;

        i++;
        result = new Predicate(sb.toString());
        spaces();

        boolean prve = false;

        while(i < line.length && line[i] != ')') {

            if (prve) {
                if (line[i] != ',') return null;
                i++;
                spaces();
            }

            if ((int) line[i] < 65 || (int) line[i] > 90) return null;

            result.add(new Atribute(line[i]));
            i++;
            spaces();
            prve = true;

        }

        return result;
    }

    public void parse(){

    }


}
