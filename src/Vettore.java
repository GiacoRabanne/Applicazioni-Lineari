import java.util.ArrayList;

public class Vettore {
    private ArrayList<Double> componenti;

    public Vettore(double[] componenti) {
        this.componenti = new ArrayList<Double>();
        for(int i = 0; i < componenti.length; i++) {
            this.componenti.add(componenti[i]);
        }
    }

    public int getNumComponenti() {
        return this.componenti.size();
    }

    public double getComponente(int indice) {
        return this.componenti.get(indice);
    }

    public double[] getArrayDouble() {
        double[] d = new double[componenti.size()];

        for(int i = 0; i < d.length; i++) {
            d[i] = componenti.get(i);
        }

        return d;
    }

    public Vettore sommaInterna(Vettore v) {
        int n1 = getNumComponenti();
        int n2 = v.getNumComponenti();

        double[] d;
        if(n1 >= n2) {
            d = new double[n1];
            int i;
            for(i = 0; i < n2; i++) {
                d[i] = Double.valueOf(this.getComponente(i) + v.getComponente(i));
            }

            if(n1 > n2) {
                while(i < n1) {
                    d[i] = this.getComponente(i);
                }
            }
        } else {
            d = new double[n2];
            int i;
            for(i = 0; i < n1; i++) {
                d[i] = Double.valueOf(this.getComponente(i) + v.getComponente(i));
            }

            while(i < n2) {
                d[i] = v.getComponente(i);
            }
        }
        return new Vettore(d);
    }

    public Vettore prodottoEsterno(double fattore) {
        int n = getNumComponenti();
        double[] d = new double[n];
        
        for(int i = 0; i < n; i++) {
            double a = Double.valueOf(this.componenti.get(i));
            d[i] = a*fattore;
        }

        return new Vettore(d);
    }

    public boolean equals(Vettore v) {
        int n = this.getNumComponenti();
        if(n != v.getNumComponenti()) {
            return false;
        }

        for(int i = 0; i < n; i++) {
            if(this.getComponente(i) != v.getComponente(i)) {
                return false;
            }
        }

        return true;
    }

    public static Vettore VETTOR_NULLO(int n) {
        return new Vettore(new double[n]);
    }

    public String toString() {
        String s = "(";

        int n = getNumComponenti();
        Scalare k = new Scalare(this.componenti.get(0));
        s += k.toString();

        for(int i = 1; i < n; i++) {
            k.setScalare(this.componenti.get(i));
            s += "," + k.toString();
        }

        s += ")";

        return s;
    }
}
