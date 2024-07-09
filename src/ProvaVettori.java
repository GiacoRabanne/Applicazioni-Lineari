public class ProvaVettori {
    public static void main(String[] args) throws Exception {
        /* Vettore v1 = new Vettore(new double[] {1,2,1.2});
        Vettore v2 = new Vettore(new double[] {3,2,1});

        Matrice m = new Matrice(new Vettore[] {v1, v2});

        Matrice m1 = new Matrice(new double[][] {{1,1,0}, {-1,0,1}, {0,3,1}});
        Matrice m2 = new Matrice(new double[][] {{3/2.0,1/2.0,-1/2.0}, {-1/2.0,-1/2.0,1/2.0}, {3/2.0,3/2.0,-1/2.0}});

        System.out.println(m1 + "\n\n" + m2);
        System.out.println("matrice inversa:\n" +m2.prodotto(m1));
        */
        
        Matrice m1 = new Matrice(new double[][] {{1,1,0}, {-1,0,1}, {0,3,1}});
        Matrice m2 = new Matrice(new double[][] {{3/2.0,1/2.0,-1/2.0}, {-1/2.0,-1/2.0,1/2.0}, {3/2.0,3/2.0,-1/2.0}});

        System.out.println("Determinante: " +m2.getDeterminante());

        System.out.println("Matrice 1:\n" + m1);

        //System.out.println("Matrice 1 complemento:\n" + m1.complemento(0, 2));

        /* System.out.println("Matrice 1 trasposta:\n" + m1.trasposta());

        System.out.println("Matrice 1 trasposta fattore 2:\n" + m1.trasposta().prodottoEsterno(2)); */

        System.out.println("Matrice 1 inversa:\n"+ m1.inversa());

        Vettore v1 = new Vettore(new double[] {1,-1,0});
        Vettore fv1 = new Vettore(new double[] {1,0,0});

        Vettore v2 = new Vettore(new double[] {1,0,3});
        Vettore fv2 = new Vettore(new double[] {0,3,0});

        Vettore v3 = new Vettore(new double[] {0,1,1});
        Vettore fv3 = new Vettore(new double[] {-1,0,1});

        AppLineare app = new AppLineare(new Vettore[][] {{v1,fv1}, {v2,fv2}, {v3,fv3}});

        
        System.out.println("Matrice associata alla funzione:\n"+ app.matriceAssociataCC());

        app.toString();
    }
}
