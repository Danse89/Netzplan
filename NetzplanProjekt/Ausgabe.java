package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.Hidden.Easter_Egg.alleAufrufen;


public class Ausgabe {



    Hidden easteEgg = new Hidden();
    private final List<Knoten> knotenList = new ArrayList<>();


    // wird einmalig ausgegeben
    public void intro() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("███    ██ ███████ ████████ ███████ ██████  ██       █████  ███    ██     ███████ ██████  ███████ ████████ ███████ ██      ██      ███████ ███    ██ \n" +
                "████   ██ ██         ██       ███  ██   ██ ██      ██   ██ ████   ██     ██      ██   ██ ██         ██    ██      ██      ██      ██      ████   ██ \n" +
                "██ ██  ██ █████      ██      ███   ██████  ██      ███████ ██ ██  ██     █████   ██████  ███████    ██    █████   ██      ██      █████   ██ ██  ██ \n" +
                "██  ██ ██ ██         ██     ███    ██      ██      ██   ██ ██  ██ ██     ██      ██   ██      ██    ██    ██      ██      ██      ██      ██  ██ ██ \n" +
                "██   ████ ███████    ██    ███████ ██      ███████ ██   ██ ██   ████     ███████ ██   ██ ███████    ██    ███████ ███████ ███████ ███████ ██   ████ \n" +
                "                                                                                                                                                    \n" +
                "                                                                                                                                                    \n" +
                "\n");

        System.out.println("Willkommen bei Netzplan, wir helfen Ihnen ");
    }

    // Das "Menu" Fenster wird ausgegeben und der User kann wählen was er möchte
    public void auswahl() {
        Scanner scanner = new Scanner(System.in);
        int auswahl = 0;
        intro();


        while (auswahl != 10) {
            System.out.println("1. Arbeitspaket hinzufügen");
            System.out.println("2. Arbeitspaket bearbeiten");
            System.out.println("3. Netzplan anzeigen");
            System.out.println("4. Projektdauer anzeigen");
            System.out.println("5. Kritischer Pfad anzeigen");
            System.out.println("6. Löschen aller Pakete");
            System.out.println("7. Datei Speichern");
            System.out.println("8. Datei Laden");
            System.out.println("9. Datei löschen");
            System.out.println("10. Beenden");

            // Trotz falscher eingabe wird das Programm nicht beendet
            try {
                auswahl = scanner.nextInt();
                scanner.nextLine(); // Consume newline character after nextInt()
            } catch (Exception e) {
                System.out.println("Falsche Eingabe, bitte tragen Sie eine Zahl von 1 bis 10 ein");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            // Durch die Auswahl wird auch das Switch Case aktiviert
            switch (auswahl) {
                case 0:                 alleAufrufen();
                    break;
                case 1:
                    paketErstellen(scanner);
                    break;
                case 2:
                    knotenpunktBearbeiten(scanner);
                    break;
                case 3:
                    netzplanAnzeigen();
                    break;
                case 4:
                    projektdauerAnzeigen();
                    break;
                case 5:
                    kritischerPfadAnzeigen();
                    break;
                case 6:
                    knotenList.clear();
                    break;
                case 7:
                    Knoten.alleWerteInTextfileSpeichern(knotenList);
                    break;
                case 8:
                    String sicher = "";
                    System.out.println("der aktuelle Netzplan wird dann gelöscht ! Bitte mit J bestätigen ");
                    sicher = scanner.nextLine();
                    if (sicher.equalsIgnoreCase("j")) {
                        knotenList.clear(); // Clear current list before loading
                        Knoten.datenLaden("/home/danielhofmann/IdeaProjects/NetzplanProjekt/Saves/");
                        knotenList.addAll(Knoten.laden(scanner));
                        System.out.println("Daten erfolgreich geladen.");
                    }
                    break;
                case 9:
                    System.out.println("Welche Datei soll geslöscht werden ? ");
                    Knoten.datenLaden("/home/danielhofmann/IdeaProjects/NetzplanProjekt/Saves/");
                    Knoten.deleteFile("/home/danielhofmann/IdeaProjects/NetzplanProjekt/Saves/" +  scanner.nextLine() );

                    knotenList.clear();
                    break;
                case 10:
                    System.out.println("Auf Wiedersehen");


            }
        }
        scanner.close();
    }

    // User soll eingeben was er haben möchte
    public void paketErstellen(Scanner scanner) {
        String X = "j";
        while (X.equalsIgnoreCase("j")) {
            System.out.println("Bitte geben Sie den Namen des Arbeitspakets ein:");
            String paketname = scanner.nextLine();
            System.out.println("Bitte geben Sie die Dauer des Arbeitspakets in Stunden ein:");
            int dauer = scanner.nextInt();
            int paketnummer = knotenList.size() + 1;
            scanner.nextLine();

            System.out.println("Bitte geben Sie die Vorgänger ein (z.B. 1 oder 1,2):");
            String vorgaengerInput = scanner.nextLine();
            List<Knoten> vorgaengerList = new ArrayList<>();
            if (!vorgaengerInput.isEmpty()) {
                String[] vorgaengerStr = vorgaengerInput.split(",");
                for (String v : vorgaengerStr) {
                    try {
                        int vNummer = Integer.parseInt(v.trim());
                        Knoten vKnoten = getKnotenByPaketnummer(vNummer);
                        if (vKnoten != null) {
                            vorgaengerList.add(vKnoten);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ungültige Eingabe für Vorgänger: " + v);
                    }
                }
            }
            // Angaben werden in der Liste gespeichert
            Knoten neuerKnoten = new Knoten(dauer, paketnummer, paketname, vorgaengerList);
            knotenList.add(neuerKnoten);

            for (Knoten v : vorgaengerList) {
                v.addNachfolger(neuerKnoten);
            }

            // Nutzer kann wählen ob er weitere Pakete hinzufügen möchte
            System.out.println("Arbeitspaket hinzugefügt: " + neuerKnoten);
            System.out.println("weitere Pakete hinzufügen ? J / N ");
            X = scanner.nextLine();

        }


    }
    // Schleife um Paketnummer in die Liste hinzufügen
    private Knoten getKnotenByPaketnummer(int paketnummer) {
        for (Knoten k : knotenList) {
            if (k.getPaketnummer() == paketnummer) {
                return k;
            }
        }
        return null;
    }

    // Methode um Pakete zu bearbeiten
    public void knotenpunktBearbeiten(Scanner scanner) {
        netzplanAnzeigen();
        System.out.println("Bitte geben Sie die Paketnummer des zu bearbeitenden Arbeitspakets ein:");
        int paketnummer = scanner.nextInt();
        scanner.nextLine();

        Knoten knoten = getKnotenByPaketnummer(paketnummer);

        if (knoten != null) {
            System.out.println("Aktuelle Paketinformationen: " + knoten);
            System.out.println("Bitte geben Sie den neuen Namen des Arbeitspakets ein:");
            String paketname = scanner.nextLine();
            System.out.println("Bitte geben Sie die neue Dauer des Arbeitspakets in Stunden ein:");
            int dauer = scanner.nextInt();
            scanner.nextLine();

            knoten.setPaketname(paketname);
            knoten.setDauer(dauer);
            System.out.println("Arbeitspaket aktualisiert: " + knoten);
        } else {
            System.out.println("Kein Arbeitspaket mit dieser Nummer gefunden.");
        }
    }

    // Methode um den Netzplan anzuzeigen
    public void netzplanAnzeigen() {
        if (knotenList.isEmpty()) {
            System.out.println("Der Netzplan ist leer.");
        } else {
            System.out.println("Netzplan:");
            printAsciiNetzplan();
        }
    }

    // Zeigt die gesamte Projektdauer an
    public void projektdauerAnzeigen() {
        int projektdauer = 0;
        for (Knoten k : knotenList) {
            int fez = k.getFEZ();
            if (fez > projektdauer) {
                projektdauer = fez;
            }
        }
        System.out.println("Die Projektdauer beträgt: " + projektdauer + " Stunden.");
    }

    // Der Kritische Pfad wird ausgegeben
    public void kritischerPfadAnzeigen() {
        if (knotenList.isEmpty()) {
            System.out.println("Der Netzplan ist leer.");
        } else {
            System.out.println("Kritischer Pfad:");
            List<Knoten> kritischerPfad = Knoten.berechneKritischenPfad(knotenList);
            printAsciiNetzplanMitKritischemPfad(kritischerPfad);
        }
    }

    // Methode um die Legende auszugeben
    private void printAsciiNetzplan() {
        boolean legendeAnzeigen = true;
        for (Knoten k : knotenList) {
            printKnoten(k, false, legendeAnzeigen);
            legendeAnzeigen = false;
        }
    }

    // Kritischer Pfad wird ausgegeben in Farbe
    private void printAsciiNetzplanMitKritischemPfad(List<Knoten> kritischerPfad) {
        boolean legendeAnzeigen = true; // Legende nur einmal anzeigen
        for (Knoten k : knotenList) {
            boolean istKritisch = kritischerPfad.contains(k);
            printKnoten(k, istKritisch, legendeAnzeigen);
            legendeAnzeigen = false;
        }
    }

    // Methode um Knoten auszugeben in "stylisch"
    private void printKnoten(Knoten k, boolean istKritisch, boolean legendeAnzeigen) {
        String colorStart = istKritisch ? "\u001B[31m" : "";
        String colorEnd = istKritisch ? "\u001B[0m" : "";
        if (legendeAnzeigen) {
            printLegende();
        }
        // Schablone für das Design
        String[] frame = {
                colorStart + "╭────────────┬───────────┬────────────╮" + colorEnd,
                colorStart + String.format("│     %-6s │     %-5s │     %-6s │", k.getFAZ(), k.getDauer(), k.getFEZ()) + colorEnd,
                colorStart + "├────────────┴───────────┴────────────┤" + colorEnd,
                colorStart + "│                                     │" + colorEnd,
                colorStart + String.format("│ %-35s │", k.getPaketname()) + colorEnd,
                colorStart + "│                                     │" + colorEnd,
                colorStart + "├─────────┬────────┬────────┬─────────┤" + colorEnd,
                colorStart + String.format("│   %-5s │   %-4s │   %-4s │   %-5s │", k.getSAZ(), k.getFreierPuffer(), k.getGesamtPuffer(), k.getSEZ()) + colorEnd,
                colorStart + "╰─────────┴────────┴────────┴─────────╯" + colorEnd
        };

        for (String line : frame) {
            System.out.println(line);
        }



        System.out.println();
    }


    // Wird verwendet in der Methode printKnoten() verwendet
    private void printLegende() {
        System.out.println("Legende:");
        System.out.println("FAZ: Frühester Anfangszeitpunkt         ╭────────────┬───────────┬────────────╮");
        System.out.println("FEZ: Frühester Endzeitpunkt             │    FAZ     │   Dauer   │    FEZ     │");
        System.out.println("SAZ: Spätester Anfangszeitpunkt         ├────────────┴───────────┴────────────┤");
        System.out.println("SEZ: Spätester Endzeitpunkt             │              Paketname              │");
        System.out.println("FP: freier Puffer                       ├─────────┬────────┬────────┬─────────┤");
        System.out.println("GP: gesamter Puffer                     │   SAZ   │   FP   │   GP   │   SEZ   │");
        System.out.println("Kritischer Pfad Rot markiert            ╰─────────┴────────┴────────┴─────────╯");

        System.out.println();    }
}



