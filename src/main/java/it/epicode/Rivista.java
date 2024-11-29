package it.epicode;

public class Rivista extends Catalogo{

    private Periodicita periodicita;

    public Rivista(int annoPubblicazione, String ISBN, String titolo, int numeroPagine, Periodicita periodicita) {
        super(annoPubblicazione, ISBN, titolo, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }
}
