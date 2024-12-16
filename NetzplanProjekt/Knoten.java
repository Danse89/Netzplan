package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Knoten {
    static Scanner scanner = new Scanner(System.in);
    private int dauer;
    private int paketnummer;
    private String paketname;
    public List<Knoten> vorgaenger;
    public List<Knoten> nachfolger;

    // Konstruktor der Klasse
    public Knoten(int dauer, int paketnummer, String paketname, List<Knoten> vorgaenger) {
        this.dauer = dauer;
        this.paketnummer = paketnummer;
        this.paketname = paketname;
        this.vorgaenger = vorgaenger;
        this.nachfolger = new ArrayList<>();
    }

    // Getter und Setter
    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public int getPaketnummer() {
        return paketnummer;
    }

    public void setPaketnummer(int paketnummer) {
        this.paketnummer = paketnummer;
    }

    public String getPaketname() {
        return paketname;
    }

    public void setPaketname(String paketname) {
        this.paketname = paketname;
    }

    public List<Knoten> getVorgaenger() {
        return vorgaenger;
    }

    public void setVorgaenger(List<Knoten> vorgaenger) {
        this.vorgaenger = vorgaenger;
    }

    public void addNachfolger(Knoten nachfolger) {
        if (!this.nachfolger.contains(nachfolger)) {
            this.nachfolger.add(nachfolger);
        }
    }

    // Methode um den FAZ zu berechnen
    public int getFAZ() {
        if (vorgaenger.isEmpty()) {
            return 0;
        } else {
            int maxFez = 0;
            for (Knoten v : vorgaenger) {
                int fez = v.getFEZ();
                if (fez > maxFez) {
                    maxFez = fez;
                }
            }
            return maxFez;
        }
    }

    // Methode um den FEZ zu berechnen
    public int getFEZ() {
        return getFAZ() + getDauer();
    }

    // Methode um den SAZ zu berechnen
    public int getSAZ() {
        if (nachfolger == null || nachfolger.isEmpty()) {
            return getFEZ() - getDauer();
        } else {
            int minSaz = Integer.MAX_VALUE;
            for (Knoten n : nachfolger) {
                int saz = n.getSAZ() - getDauer();
                if (saz < minSaz) {
                    minSaz = saz;
                }
            }
            return minSaz;
        }
    }

    // Methode um den SEZ zu berechnen
    public int getSEZ() {
        return getSAZ() + getDauer();
    }

    // Methode um den Freier Puffer zu berechnen
    public int getFreierPuffer() {
        if (nachfolger.isEmpty()) {
            return 0;
        } else {
            int minFAZ = Integer.MAX_VALUE;
            for (Knoten n : nachfolger) {
                int faz = n.getFAZ();
                if (faz < minFAZ) {
                    minFAZ = faz;
                }
            }
            return minFAZ - getFEZ();
        }
    }

    // Methode um den Gesamtpuffer zu berechnen
    public int getGesamtPuffer() {
        return getSAZ() - getFAZ();
    }

    // Override der toString-Methode
    @Override
    public String toString() {
        return "Knoten [Paketname=" + paketname +
                ", Dauer=" + dauer +
                ", Paketnummer=" + paketnummer +
                ", FAZ=" + getFAZ() +
                ", FEZ=" + getFEZ() +
                ", SAZ=" + getSAZ() +
                ", SEZ=" + getSEZ() +
                ", Freier Puffer=" + getFreierPuffer() +
                ", Gesamtpuffer=" + getGesamtPuffer() + "]";
    }

    // Methode zum Speichern
    public static void alleWerteInTextfileSpeichern(List<Knoten> arbeitspakete) {
        System.out.println("Bitte geben Sie den Namen für die Datei ein:");
        String filePath = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Knoten paket : arbeitspakete) {
                writer.println(paket.getPaketnummer() + "," + paket.getPaketname() + "," + paket.getDauer() + "," + paket.vorgaengerToString());
            }
            System.out.println("Arbeitspakete erfolgreich gespeichert.");
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in die Datei: " + e.getMessage());
        }
    }

    private String vorgaengerToString() {
        StringBuilder sb = new StringBuilder();
        for (Knoten v : vorgaenger) {
            sb.append(v.getPaketnummer()).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : ""; // Entferne das letzte Komma und Leerzeichen
    }

    // Methode zum Laden einer Textdatei

    public static List<Knoten> laden(Scanner scanner) {
        List<Knoten> geladenePakete = new ArrayList<>();
        System.out.println("Bitte geben Sie den Namen ein (z.B. Netzplan Baustelle):");
        String filePath = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", 4); // Nur in die ersten 4 Spalten aufteilen
                if (values.length < 3) {
                    System.err.println("Ungültige Zeile (zu wenig Spalten): " + line);
                    continue;
                }
                try {
                    int paketnummer = Integer.parseInt(values[0]);
                    String paketname = values[1];
                    int dauer = Integer.parseInt(values[2]);

                    List<Knoten> vorgaenger = new ArrayList<>();
                    if (values.length > 3 && !values[3].isEmpty()) {
                        String vorgaengerStr = values[3].replaceAll("[\\[\\]]", "");
                        String[] vorgaengerNums = vorgaengerStr.split(", ");
                        for (String vNummer : vorgaengerNums) {
                            if (!vNummer.isEmpty()) {
                                Knoten vKnoten = getKnotenByPaketnummer(Integer.parseInt(vNummer), geladenePakete);
                                if (vKnoten != null) {
                                    vorgaenger.add(vKnoten);
                                }
                            }
                        }
                    }

                    Knoten knoten = new Knoten(dauer, paketnummer, paketname, vorgaenger);
                    geladenePakete.add(knoten);
                } catch (NumberFormatException e) {
                    System.err.println("Ungültige Zahl in Zeile: " + line);
                }
            }

            for (Knoten knoten : geladenePakete) {
                for (Knoten vorgaenger : knoten.getVorgaenger()) {
                    vorgaenger.addNachfolger(knoten);
                }
            }
            System.out.println("Arbeitspakete erfolgreich geladen.");
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen aus der Datei: " + e.getMessage());
        }

        return geladenePakete;
    }


    private static Knoten getKnotenByPaketnummer(int paketnummer, List<Knoten> knotenList) {
        for (Knoten k : knotenList) {
            if (k.getPaketnummer() == paketnummer) {
                return k;
            }
        }
        return null;
    }

    // Methode zum Löschen einer Textdatei
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Datei erfolgreich gelöscht: " + filePath);
            } else {
                System.out.println("Fehler beim Löschen der Datei: " + filePath);
            }
        } else {
            System.out.println("Die Datei existiert nicht: " + filePath);
        }
    }

    // Methode zum Anzeigen gespeicherter Textdateien
    public static void datenLaden(String directoryPath) {
        File directory = new File(directoryPath);
        System.out.println(" ");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println(file.getName());
                }
            }
        } else {
            System.out.println("Verzeichnis nicht gefunden: " + directoryPath);
        }
    }

    // Methode zur Berechnung des kritischen Pfads
    public static List<Knoten> berechneKritischenPfad(List<Knoten> knotenList) {
        List<Knoten> kritischerPfad = new ArrayList<>();
        for (Knoten k : knotenList) {
            if (k.getGesamtPuffer() == 0) {
                kritischerPfad.add(k);
            }
        }
        return kritischerPfad;
    }
}