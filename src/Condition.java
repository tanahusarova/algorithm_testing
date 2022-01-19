import java.util.Optional;

public class Condition implements Component{
    private boolean neg;
    private Atribute a1;
    private Atribute a2;
    private String string;

    public Condition(boolean neg, Atribute a1, Atribute a2, String s) {
        this.neg = neg;
        this.a1 = a1;
        this.a2 = a2;
        string = s;
    }


}
