package it.epicode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Archivio {
    private static final Logger logger = LoggerFactory.getLogger(Archivio.class);

    private static List<Catalogo> catalogo = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    //primo metodo, aggiungi elemento

    public static void aggiungiElemento() throws CatalogoException {
        try {
            System.out.println("Cosa vuoi creare? Digita 1 per un libro o 2 per una rivista");
            int scelta = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Inserisci ISBN");
            String isbn = scanner.nextLine();
            if (catalogo.stream().anyMatch(e -> e.getISBN().equals(isbn))) {
                throw new CatalogoException("ISBN già presente in archivio, non puoi inserire due ISBN uguali!");
            }
            System.out.println("Inserisci il titolo");
            String titolo = scanner.nextLine();
            System.out.println("Inserisci anno di pubblicazione");
            int anno = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Inserisci il numero di pagine");
            int pagine = scanner.nextInt();
            scanner.nextLine();

            Catalogo nuovoElemento; //dichiaro nuovoElemento che può essere o libro o rivista

            if (scelta == 1) { //se è un libro mettiamo autore e genere
                System.out.println("Inserisci autore");
                String autore = scanner.nextLine();
                System.out.println("Inserisci genere");
                String genere = scanner.nextLine();

                nuovoElemento = new Libro(anno, isbn, titolo, pagine, autore, genere); //assegno a nuovoElemento la classe Libro
            } else if (scelta == 2) { //se è una rivista mettiamo la periodicità
                System.out.println("Con quale periodicità verrà pubblicata la rivista? 1-SETTIMANALE, 2-MENSILE, 3-SEMESTRALE");
                int periodicitaScelta = scanner.nextInt();
                scanner.nextLine();
                Periodicita periodicita;
                switch (periodicitaScelta) {
                    case 1:
                        periodicita = Periodicita.SETTIMANALE;
                        break;
                    case 2:
                        periodicita = Periodicita.MENSILE;
                        break;
                    case 3:
                        periodicita = Periodicita.SEMESTRALE;
                        break;
                    default:
                        throw new CatalogoException("Periodicità non valida");
                }
                nuovoElemento = new Rivista(anno, isbn, titolo, pagine, periodicita); //qui assegniamo la rivista a nuovoElemento
            } else {
                throw  new CatalogoException("Scelta non valida");
            }
            catalogo.add(nuovoElemento); //aggiungo quindi l'elemento al catalogo
            logger.info("Nuovo elemento aggiunto con ISBN: {}", isbn);
        } catch (CatalogoException e) {
            logger.error("Errore durante l'aggiunta dell'elemento: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Errore generico durante l'aggiunta dell'elemento", e);
            throw new CatalogoException("Errore durante l'aggiunta dell'elemento: " + e.getMessage());
        }
    }

    //secondo metodo, ricerca per ISBN

    public static Catalogo cercaPerISBN (String isbn) throws CatalogoException {
        try {
            return catalogo.stream()
                    .filter(e -> e.getISBN().equals(isbn))
                    .findFirst()
                    .orElseThrow(()->{logger.warn("Nessun elemento trovato con questo ISBN: {}", isbn);
                    return new CatalogoException("Nessun elemento trovato con questo ISBN " + isbn);
                    });
        } catch (CatalogoException e) {
           logger.error("Errore durante la ricerca per ISBN: {}", e.getMessage(), e);
           throw e;
        }
    }

    //terzo metodo, rimozione per ISBN

    public static void rimuoviPerISBN(String isbn) throws CatalogoException {
        try {
            boolean rimosso = catalogo.removeIf(e -> e.getISBN().equals(isbn));
            if (!rimosso){
                logger.warn("Questo ISBN non esiste: {}", isbn);
                throw new CatalogoException("ISBN non trovato");
            }
            logger.info("Elemento rimosso con successo. ISBN: {}", isbn);
        } catch (CatalogoException e) {
            logger.error("Errore durante la rimozione dell'elemento: {}", e.getMessage(), e);
            throw e;
        }
    }





}
