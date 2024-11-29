package it.epicode;

public class Libro extends Catalogo{

    private String autore;
    private String genere;

    public Libro(int annoPubblicazione, String ISBN, String titolo, int numeroPagine, String autore, String genere) {
        super(annoPubblicazione, ISBN, titolo, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
