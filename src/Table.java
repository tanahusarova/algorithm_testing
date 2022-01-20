import java.util.List;

public class Table {
    private String name;
    private List<String> atributes;

    public Table(String name, List<String> atributes) {
        this.name = name;
        this.atributes = atributes;
    }

    public String getName(){
        return name;
    }

    //cislovane od jednotky

    public String getColumn(int i){
        if (i < atributes.size() && i >= 0)
            return atributes.get(i);
        return null;
    }
}
