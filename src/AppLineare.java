public class AppLineare {
    private int n; // Dimensione k^n
    private Vettore[][] funzione;

    public AppLineare(Vettore[][] funzione) {
        this.n = funzione.length; // Assumendo che funzione.length == funzione[0][0].getNumComponenti();.

       this.funzione = new Vettore[n][2]; 
       // 0: vettore di partenza. 1: la sua immagine

       for(int i = 0; i < n; i++) {
            for(int j = 0; j < 2; j++) {
                this.funzione[i][j] = funzione[i][j];
            }
       }
    }

    public Vettore[] getBase() {
        Vettore[] v = new Vettore[n];
        
        for(int i = 0; i < n; i++) {
            v[i] = this.funzione[i][0];
        }

        return v;
    } 

    public Vettore[] getImmagini() {
        Vettore[] v = new Vettore[n];
        
        for(int i = 0; i < n; i++) {
            v[i] = this.funzione[i][1];
        }

        return v;
    } 

    public Matrice matriceAssociataCC() {
        // matrice associata = mB->C(L) * mC->B(id).

        // mB->C(L):
        Matrice mBC = new Matrice(this.getImmagini());

        // mC->B(id):
        Matrice mCB = new Matrice(this.getBase()).inversa();

        return mBC.prodotto(mCB);
    }

    public String toString() {
        String s = "L(";
        
        String[] incognite = new String[this.n];

        if(this.n <= 6) { // se le incognite sono meno di 7, vanno da x a v
            for(int i = 0; i < this.n; i++) {
                char x = (char) ('t' + (i + 'x' - 't')%('z' - 's')); 
                incognite[i] = "" + x;
            }
        } else { // altrimenti le scrivo come x1, x2, ...
            for(int i = 0; i < this.n; i++) {
                incognite[i] = "x" + (i+1);
            }
        }

        s += incognite[0];
        for(int i = 1; i < this.n; i++) {
            s += "," + incognite[i];
        }

        s += ") = (";

        Matrice associata = matriceAssociataCC();

        for(int i = 0; i < associata.getNumRows(); i++) {
            if(i != 0) {
                s += ",";
            }
            boolean primo = true;
            for(int j = 0; j < associata.getNumCols(); j++) {
                Scalare k = new Scalare(associata.casella(i, j));
                double value = k.valueOf();
                if(value != 0) {
                    if(!primo) {
                        s += " ";
                    }

                    if(value < 0) {
                        s += "-";
                        if(j != 0) {
                            s += " ";
                        }
                    } else if(value > 0 && !primo) {
                        s += "+";
                    }

                    if(value != 1 && value != -1) {
                        s += k.abs().toString();
                    }
                    s += incognite[j]; 
                    primo = false;
                }
            }
        }

        s += ")";

        s += "\n\n";

        s += "ImL : <";

        Vettore[] v = getImmagini();
        s += v[0].toString();

        for(int i = 1; i < v.length; i++) {
            s += ", " + v[i].toString();
        }
        s += ">\n\n";

        s += "Matrice Associata ad L da B a C:\n";

        s += associata.toString();

        s += "\nove B = {";

        v = getBase();
        s += v[0].toString();

        for(int i = 1; i < v.length; i++) {
            s += ", " + v[i].toString();
        }
        s += "}";

        s += "\ne C e' la base canonica in k^" + this.n;
        
        return s;
    }
}
