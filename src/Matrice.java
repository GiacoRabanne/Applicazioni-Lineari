public class Matrice {
    private double[][] matrice;

    public Matrice(double[][] m) {
        this.matrice = new double[m.length][m[0].length];

        for (int i = 0; i < m.length; i++) {
            for(int j = 0; j < m[i].length; j++) {
                this.matrice[i][j] = m[i][j];
            }
        }
    }

    public Matrice(Vettore[] colonne) {

        // Verifico che tutti i vettori giacciano nello stesso spazio k^n:
        for(int i = 1; i < colonne.length; i++) {
            if(colonne[i].getNumComponenti() != colonne[i-1].getNumComponenti()) {
                System.err.println("Non tutti i vettori hanno la stessa quantita' di componenti.");
                return;
            }
        }

        int m = colonne[0].getNumComponenti();
        int n = colonne.length;
        this.matrice = new double[m][n];
            
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                this.matrice[j][i] = colonne[i].getComponente(j);
            }
        }
    }

    public int getNumRows() {
        return this.matrice.length;
    }
    
    public int getNumCols() {
        return this.matrice[0].length;
    }

    public double casella(int i, int j) {
        return this.matrice[i][j];
    }

    public double getDeterminante() {
        // Se la matrice non è quadrata, il determinante non esiste
        if(this.getNumRows() != this.getNumCols()) {
            return Double.NaN;
        } else {
            return getDeterminante(this);
        }
    }

    public static Matrice getMatriceUnita(int n) {
        double[][] m = new double[n][n];

        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m[0].length; j++) {
                if(i == j) {
                    m[i][j] = 1;
                } else {
                    m[i][j] = 0;
                }
            }
        }

        return new Matrice(m);
    }

    private double getDeterminante(Matrice m) {
        double determinante;

        // Caso base (n = 1): Se la matrice è 1x1, il determinante è la cella stessa
        if(m.getNumRows() == 1) {
            determinante = m.casella(0, 0);
        } 
        // Caso ricorsivo (da n > 1): Algoritmo: LaPlace.
        else {
            determinante = 0;
            int n = m.getNumRows();

            for(int i = 0; i < n; i++) {
                // Fisso la prima riga (j = 0):
                determinante += Math.pow(-1, i)*m.casella(0, i)*complemento(0,i).getDeterminante();
            }  
        }

        return arrotonda(determinante);
    }

    public Matrice complemento(int i, int j) {
        if(i < 0 || i >= getNumRows()
        || j < 0 || j >= getNumCols()) {
            return null;
        }
        int n = getNumCols();
        int m = getNumRows();

        double[][] complemento = new double[m - 1][n - 1];

        int iS = 0, jS = 0; // Sono gli indici che scandiscono la sottomatrice;
        boolean riempita = false;
        for(int r = 0; r < m && !riempita; r++) {
            for(int c = 0; c < n && !riempita; c++) {
                if(r != i && c != j) {
                    complemento[iS][jS] = this.casella(r, c);
                    jS++;
                    if(jS == complemento[0].length) {
                        jS = 0;
                        iS++;
                        if(iS == complemento.length) {
                            riempita = true;
                        }
                    }
                }
            }
        }

        return new Matrice(complemento);
    }

    private static double arrotonda(double d) {
        return Math.round(d*1000)/1000.0;
    } 

    public boolean isInvertibile() {
        return this.getDeterminante() != 0;
    }

    public boolean stesseDimensioni(Matrice m) {
        return this.getNumRows() == m.getNumRows() && this.getNumCols() == m.getNumCols();
    }

    public boolean matriceInversaDi(Matrice m) {
        boolean inversa;
        if(this.prodotto(m).equals(Matrice.getMatriceUnita(m.getNumCols()))) {
            inversa = true;
        } else {
            inversa = false;
        }
        return inversa;
    }

    public Matrice somma(Matrice m) {
        double[][] somma = new double[this.matrice.length][this.matrice[0].length]; //posto che m e this abbiano le stesse dimensioni

        for(int i = 0; i < somma.length; i++) {
            for(int j = 0; j < somma[i].length; j++) {
                somma[i][j] = arrotonda(matrice[i][j] + m.casella(i, j));
            }
        }
        return new Matrice(somma);
    }

    public Matrice prodotto(Matrice m) {
        double[][] prodotto = new double[this.matrice.length][m.matrice[0].length];
        double somma;
        for(int i = 0; i < prodotto.length; i++) {
            for(int j = 0; j < prodotto[i].length; j++) {
                somma = 0;
                for(int k = 0; k < this.matrice[0].length; k++) {
                    somma += this.matrice[i][k] * m.casella(k, j);
                }
                prodotto[i][j] = arrotonda(somma);
            }
        }
        return new Matrice(prodotto);
    }

    public Matrice prodottoEsterno(double k) {
        Vettore[] v = estraiColonne();

        for(int i = 0; i < v.length; i++) {
            v[i] = v[i].prodottoEsterno(k);
        }

        return new Matrice(v);
    }

    public Vettore estraiColonna(int j) {
        if(j < 0 || j >= this.getNumCols()) {
            return null;
        }

        double[] k = new double[getNumRows()];
        for(int i = 0; i < getNumRows(); i++) {
            k[i] = this.casella(i, j);
        }

        return new Vettore(k);
    }

    public Matrice trasposta() {
        double[][] t = new double[getNumCols()][getNumRows()];

        for(int i = 0; i < t.length; i++) {
            for(int j = 0; j < t[0].length; j++) {
                t[i][j] = this.casella(j, i);
            }
        }

        return new Matrice(t);
    }

    public Matrice inversa() {
        int n = getNumCols();

        if(n != getNumRows()) {
            return null; // da capire come trovare l'inversa di una matrice non quadrata.
        }
        // 1)   Ricavo la trasposta della matrice:
        Matrice trasposta = this.trasposta();

        /* 2)   Calcolo la matrice inversa, dove la cella i-j-esima
                sara' data dal determinante del complemento della trasposta fratto il determinante della matrice per il segno (+ o - a seconda della posizione):
        */
        double[][] inversa = new double[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                inversa[i][j] = Math.pow(-1, i+j)*trasposta.complemento(i, j).getDeterminante()/this.getDeterminante();
            }
        }
        return new Matrice(inversa);
    }

    public Vettore[] estraiColonne() {
        Vettore[] v = new Vettore[getNumCols()];
        for(int i = 0; i < v.length; i++) {
            v[i] = estraiColonna(i);
        }

        return v;
    }

    public Matrice sostituisciColonna(int j, Vettore sostituto) throws Exception {
        if(sostituto.getNumComponenti() != getNumRows()) {
            throw new Exception("Il numero di componenti del vettore e di righe della matrice non combaciano.");
        }

        Vettore[] v = estraiColonne();
        v[j] = sostituto;

        return new Matrice(v);
    }

    public boolean equals(Matrice m) {
        boolean uguali = this.stesseDimensioni(m);

        for(int i = 0; i < m.getNumRows() && uguali; i++) {
            for(int j = 0; j < m.getNumCols() && uguali; j++) {
                if(this.casella(i, j) != m.casella(i, j)) {
                    uguali = false;
                }
            }
        }

        return uguali;
    }

    public String toString() {
        String s = "";

        Scalare k;
        for(int i = 0; i < this.matrice.length; i++) {
            s += "|";
            for(int j = 0; j < this.matrice[0].length; j++) {
                k = new Scalare(this.matrice[i][j]);
                s += /* arrotonda(this.matrice[i][j])  */ k.toString() + "|";
            }
            s += "\n";
        }

        return s;
    }
}
