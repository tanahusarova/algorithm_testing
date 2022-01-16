public class Main {
    public static void main(String[] args) {
        Parser p = new Parser(" aaaabc ('Katka', A) :- aab (M, K), \\ + accab (N, K).", null);
        Parser pz = new Parser(" *kk (P, A)", null);
        Parser pz2 = new Parser(" \\ + aab (M, K) ", null);

        Predicate p1 = p.parse();
        Predicate p2 = pz.readPredicate();
        Predicate p3 = pz2.readPredicate();

        if (p1 != null) {
            if (p1.getNeg()) System.out.println("Negovane");
            System.out.println(p1.toString() + ":");

            for (Predicate r : p1.getPredicates())
                System.out.println(r.toString());

        }
        else System.out.println("je null");

        /*
        if (p2 != null)
            System.out.println(p2.getName());
        else System.out.println("je null");

        if (p3 != null) {
            if (p3.getNeg()) System.out.println("Negovane");
            System.out.println(p3.getName());
            for (Atribute a : p3.getAtributes())
                System.out.println(a.getName());
        }
        else System.out.println("je null");

         */

    }
}
