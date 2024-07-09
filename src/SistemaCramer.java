public class SistemaCramer {
    private Vettore soluzione;
    private Matrice coefficienti;
    private Vettore terminiNoti;
    private int n;

    public SistemaCramer(Matrice coefficienti, Vettore terminiNoti) throws Exception {
        if(coefficienti.getNumCols() != coefficienti.getNumRows()) {
            throw new Exception("La matrice dei coefficienti non e' quadrata.");
        } 
        if(coefficienti.getNumRows() != terminiNoti.getNumComponenti()) {
            throw new Exception("Il numero di termini noti non corrisponde al numero di equazioni.");
        }
        if(coefficienti.getDeterminante() == 0) {
            throw new Exception("Il determinante della matrice dei coefficienti e' nullo.");
        }

        this.n = coefficienti.getNumCols();

        // Creo la matrice dei coefficienti:
        double[][] A = new double[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                A[i][j] = coefficienti.casella(i, j);
            }
        }

        this.coefficienti = new Matrice(A);

        // Creo il vettore dei termini noti:
        double[] B = new double[n];

        for(int i = 0; i < n; i++) {
            B[i] = terminiNoti.getComponente(i);
        }

        this.terminiNoti = new Vettore(B);
    }

    public Vettore getSoluzione() throws Exception {
        if(terminiNoti.equals(Vettore.VETTOR_NULLO(n))) { // Se e' di Cramer e omogeneo,
            return Vettore.VETTOR_NULLO(n); // Il vettor nullo e' l'unica soluzione.
        }

        double[] x = new double[n];
        double detA = coefficienti.getDeterminante();

        for(int i = 0; i < n; i++) {
            double determinante = coefficienti.sostituisciColonna(i, soluzione).getDeterminante();
            x[i] = determinante/detA;
        }

        return new Vettore(x);
    }
}
