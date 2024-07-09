import fond.io.*; // la libreria si trova sulla cartella lib.

public class Main {
    public static void main(String[] args) {
        InputWindow in = new InputWindow();
        OutputWindow out = new OutputWindow("Applicazioni Lineari");

        int n = in.readInt("Inserisci la dimensione degli spazi vettoriali (es. 4 -> R^4)");

        Vettore[][] funzione = new Vettore[n][2];

        for(int i = 0; i < n; i++) {
            String vettore = in.readString("Inserisci il vettore "+ (i+1) + " di " + (n) + "\nes. (1,2,3,4)");

            while(!validString(vettore, n)) {
                vettore = in.readString("Valore immesso non valido.\nInserisci il vettore "+ (i+1) + " di " + (n) + "\nes. (1,2,3,4)");
            }

            funzione[i][0] = string2Vector(vettore);
            out.write("L"+funzione[i][0] + " = ");

            // Faccio lo stesso per l'immagine del vettore:

            vettore = in.readString("Inserisci l'immagine del vettore "+ (i+1) + " di " + (n) + "\nes. (1,2,3,4)");
            
            while(!validString(vettore, n)) {
                vettore = in.readString("Valore immesso non valido.\nInserisci l'immagine del vettore "+ (i+1) + " di " + (n) + "\nes. (1,2,3,4)");
            }

            funzione[i][1] = string2Vector(vettore);
            out.writeln(funzione[i][1]);
        }

        AppLineare f = new AppLineare(funzione);

        out.writeln("funzione trovata:");
        out.writeln(f);
        
    }

    // Verifica se la stringa fornita dall'utente e' valida.
    public static boolean validString(String vettore, int n) {
        String[] componenti = vettore.split(",");

        if(componenti.length != n) {
            return false; // non e' valido se l'array di valori non comprende tante stringhe quant'e' il numero di componenti (n)
        }

        for(int i = 0; i < n; i++) {
            boolean trovato = false;
            for(int j = 0; j < componenti[i].length() && !trovato; j++) {
                if(Character.isDigit(componenti[i].charAt(j))) {
                    trovato = true;
                }
            }

            if(!trovato) {
                return false; // non e' valido se nella stringa non vi e' nemmeno un numero.
            }
        }

        return true;
    }

    // Trasforma la stringa validata in un vettore. 
    public static Vettore string2Vector(String vettore) {
        String[] componenti = vettore.split(",");
        int n = componenti.length;
        double[] componentiDouble = new double[n];

        for(int i = 0; i < n; i++) {
            String s = "";

            for(int j = 0; j < componenti[i].length(); j++) {
                char c = componenti[i].charAt(j);
                if(Character.isDigit(c) || 
                c == '.') {
                    s += c;
                }
            }
            componentiDouble[i] = Double.parseDouble(s);
        }

        return new Vettore(componentiDouble);
    }
}
