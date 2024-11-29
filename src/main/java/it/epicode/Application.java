package it.epicode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Avvio dell'applicazione Catalogo Bibliotecario");

        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        try {
            while (continua) {
                System.out.println("\n-------- MENU UTENTE --------");
                System.out.println("1- Aggiungi Elemento");
                System.out.println("2- Cerca per ISBN");
                System.out.println("3- Rimuovi per ISBN");
                System.out.println("4- Cerca per Anno");
                System.out.println("5- Cerca per Autore");
                System.out.println("6- Aggiorna Elemento");
                System.out.println("7- Statistiche Catalogo");
                System.out.println("0- Esci");

                System.out.print("Seleziona un'opzione: ");

                try {
                    int scelta = scanner.nextInt();
                    scanner.nextLine();

                    switch (scelta) {
                        case 1:
                            Archivio.aggiungiElemento();
                            break;
                        case 2:
                            System.out.print("Inserisci ISBN da cercare: ");
                            String isbnCerca = scanner.nextLine();
                            Catalogo elemento = Archivio.cercaPerISBN(isbnCerca);
                            System.out.println("Elemento trovato: " + elemento.getTitolo());
                            break;
                        case 3:
                            System.out.print("Inserisci ISBN da rimuovere: ");
                            String isbnRimuovi = scanner.nextLine();
                            Archivio.rimuoviPerISBN(isbnRimuovi);
                            System.out.println("Elemento rimosso.");
                            break;
                        case 4:
                            System.out.print("Inserisci anno da cercare: ");
                            int anno = scanner.nextInt();
                            scanner.nextLine(); // Consuma newline
                            Archivio.cercaPerAnno(anno)
                                    .forEach(e -> System.out.println(e.getTitolo()));
                            break;
                        case 5:
                            System.out.print("Inserisci autore da cercare: ");
                            String autore = scanner.nextLine();
                            Archivio.cercaPerAutore(autore)
                                    .forEach(l -> System.out.println(l.getTitolo()));
                            break;
                        case 6:
                            System.out.print("Inserisci ISBN da aggiornare: ");
                            String isbnAggiorna = scanner.nextLine();
                            Archivio.aggiornaElemento(isbnAggiorna);
                            break;
                        case 7:
                            Archivio.statisticheCatalogo();
                            break;
                        case 0:
                            continua = false;
                            break;
                        default:
                            System.out.println("Scelta non valida, riprova.");
                    }
                } catch (CatalogoException e) {
                    logger.error("Errore gestito: {}", e.getMessage());
                    System.out.println("Errore: " + e.getMessage());
                } catch (Exception e) {
                    logger.error("Errore generico: {}", e.getMessage(), e);
                    System.out.println("Si è verificato un errore: " + e.getMessage());
                    scanner.nextLine(); // Pulisce il buffer
                }
            }
        } catch (Exception e) {
            logger.error("Errore imprevisto nell'applicazione", e);
            System.out.println("Si è verificato un errore inatteso: " + e.getMessage());
        } finally {
            scanner.close();
            logger.info("Chiusura dell'applicazione");
            System.out.println("Programma terminato.");
        }
    }
}
