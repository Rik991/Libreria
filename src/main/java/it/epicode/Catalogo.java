package it.epicode;

public abstract class Catalogo {
    private final String ISBN;
    private String titolo;
    private int annoPubblicazione;
    private int numeroPagine;

    public Catalogo(int annoPubblicazione, String ISBN, String titolo, int numeroPagine) {
        this.annoPubblicazione = annoPubblicazione;
        this.ISBN = ISBN;
        this.titolo = titolo;
        this.numeroPagine = numeroPagine;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
