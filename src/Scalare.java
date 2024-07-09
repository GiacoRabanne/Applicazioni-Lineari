public class Scalare {
    double s;

    Scalare(double scalare) {
        this.s = scalare;
    }

    Scalare() {}

    public void setScalare(double scalare) {
        this.s = scalare;
    }

    public double valueOf() {
        return this.s;
    }

    public Scalare abs() {
        return new Scalare(Math.abs(this.s));
    }

    public String toString() { // Serve a rappresentare in forma piu' pulita lo scalare
        String s = "";
        if(this.s == (int) this.s) {
            s += (int) this.s;
        } else {
            s += Math.round(this.s*100)/100.0;
        }
        return s;
    }
}
