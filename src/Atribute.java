public class Atribute {
    private String name;

    public Atribute(String name) {
        this.name = name;
    }

    public Atribute(char name) {
        this.name = String.valueOf(name);
    }

    public String getName(){
        return name;
    }
}
