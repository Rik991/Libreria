package it.epicode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    //quarto metodo, ricerca per anno
    public static List<Catalogo> cercaPerAnno(int anno){
        return catalogo.stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    //quinto metodo, ricerca per autore libri
    public static List<Libro> cercaPerAutore(String autore){
        return catalogo.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(l -> l.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    //sesto metodo, aggiorna elemento
    public static void aggiornaElemento(String isbn) throws CatalogoException {
        Catalogo elemento = cercaPerISBN(isbn);

        System.out.println("Cosa vuoi aggiornare?");
        System.out.println("1. Titolo");
        System.out.println("2. Anno");
        System.out.println("3. Numero pagine");

        if (elemento instanceof Libro) {
            System.out.println("4. Autore");
            System.out.println("5. Genere");
        } else if (elemento instanceof Rivista) {
            System.out.println("4. Periodicità");
        }

        int scelta = scanner.nextInt();
        scanner.nextLine();

        switch (scelta) {
            case 1:
                System.out.println("Inserisci nuovo titolo");
                elemento.setTitolo(scanner.nextLine());
                break;
            case 2:
                System.out.println("Inserisci nuovo anno");
                elemento.setAnnoPubblicazione(scanner.nextInt());
                scanner.nextLine();
                break;
            case 3:
                System.out.println("Inserisci nuovo numero pagine");
                elemento.setNumeroPagine(scanner.nextInt());
                scanner.nextLine();
                break;
            case 4:
                if (elemento instanceof Libro) {
                    Libro libro = (Libro) elemento;
                    System.out.println("Inserisci nuovo autore");
                    libro.setAutore(scanner.nextLine());
                } else if (elemento instanceof Rivista) {
                    Rivista rivista = (Rivista) elemento;
                    System.out.println("Scegli nuova periodicità (1-SETTIMANALE, 2-MENSILE, 3-SEMESTRALE)");
                    int periodicitaScelta = scanner.nextInt();
                    scanner.nextLine();
                    switch (periodicitaScelta) {
                        case 1: rivista.setPeriodicita(Periodicita.SETTIMANALE); break;
                        case 2: rivista.setPeriodicita(Periodicita.MENSILE); break;
                        case 3: rivista.setPeriodicita(Periodicita.SEMESTRALE); break;
                    }
                }
                break;
            case 5:
                if (elemento instanceof Libro) {
                    Libro libro = (Libro) elemento;
                    System.out.println("Inserisci nuovo genere");
                    libro.setGenere(scanner.nextLine());
                }
                break;
        }
        System.out.println("Elemento aggiornato con successo!");
    }


    //settimo metodo, statistiche catalogo

    public static void statisticheCatalogo(){
        try {
            long numLibri = catalogo.stream()
                    .filter(e -> e instanceof Libro)
                    .count();

            long numRiviste = catalogo.stream()
                    .filter(e -> e instanceof Rivista)
                    .count();

            Catalogo elementoConPiuPagine = catalogo.stream()
                    .max((a, b) -> Integer.compare(a.getNumeroPagine(), b.getNumeroPagine()))
                    .orElse(null);

            double mediaPagine = catalogo.stream()
                    .mapToInt(Catalogo::getNumeroPagine)
                    .average()
                    .orElse(0);
            logger.info("Generazione statistiche catalogo");
            logger.info("Numero Libri: {}", numLibri);
            logger.info("Numero Riviste: {}", numRiviste);

            if (elementoConPiuPagine != null){
                logger.info("Elemento con più pagine: {} (Pagine: {})",
                        elementoConPiuPagine.getTitolo(),
                        elementoConPiuPagine.getNumeroPagine());
            }
            logger.info("Media pagine: {}", mediaPagine);
            System.out.println("Statistiche Catalogo:");
            System.out.println("Numero Libri: " + numLibri);
            System.out.println("Numero Riviste: " + numRiviste);

            if (elementoConPiuPagine != null) {
                System.out.println("Elemento con più pagine: " + elementoConPiuPagine.getTitolo() +
                        " (Pagine: " + elementoConPiuPagine.getNumeroPagine() + ")");
            }

            System.out.printf("Media pagine: %.2f%n", mediaPagine);
        } catch (Exception e) {
            logger.error("Errore durante il calcolo delle statistiche", e);
        }
    }
}
