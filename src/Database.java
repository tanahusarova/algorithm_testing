import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Database {
    private HashSet<Table> tables;
    private Database database = new Database();
    private HashMap<String, Table> tableHashMap;


    private Database() {
        tables = new HashSet<>();
        //doplnit nacitane uz hotove tabulky do setu


        tableHashMap = new HashMap<>();
        for (Table t : tables)
            tableHashMap.put(t.getName(), t);
    }

    public Database getDatabase(){
        return database;
    }

    public void addTable(Table table){
        tables.add(table);
        tableHashMap.put(table.getName(), table);
    }

    public Table get (String name){
        if (tableHashMap.containsKey(name))
            return tableHashMap.get(name);
        return null;
    }
}
