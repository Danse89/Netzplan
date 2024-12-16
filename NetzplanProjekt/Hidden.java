package org.example;


// Importierter Scanner
import java.util.Scanner;

// Name der Klasse
public class Hidden {






    public class Easter_Egg {

        // Ganzen öffentlichen Variabeln
        private static String YN = " ";
       private static boolean neueRunde;
       private static int eingabe;
       private static String spieler1 = " ";
       private static String spieler2 = " ";
       private static String name;
       private static char symbol;
       private static Scanner scanner = new Scanner(System.in);

        // Das 2D Array um das Spielfeld anzuzeigen
        static char[][] spielfeld = { { '1', ' ', '2', ' ', '3' }, { '4', ' ', '5', ' ', '6' },
                { '7', ' ', '8', ' ', '9' } };

        // Begrüßung mit Regel erklärung
        static void regeln() {
            System.out.println("Herzlich Willkommen beim Tic-Tac-Toe");
            System.out.print("Brauchen Sie eine Anleitung ?  Y/N ");
            YN = scanner.nextLine();
            if (YN.contains("Y") || YN.contains("y")) {
                System.out.println(
                        "Spielverlauf. Auf einem quadratischen, 3×3 Felder großen Spielfeld setzen die beiden Spieler abwechselnd "
                                + "ihr Zeichen (ein Spieler Kreuze, der andere Kreise) in ein freies Feld. Der Spieler, der als Erster drei Zeichen in eine Zeile, "
                                + "Spalte oder Diagonale setzen kann, gewinnt.");
                System.out.println("Haben Sie es verstanden ? Y/N");
                YN = scanner.nextLine();
            } else if (YN.equals("n") || YN.equals("N")) {
                System.out.println("Okay dann gehts los");
            } else {

            }

        }

        // Methode um die Namensgebung zu regeln und um die Namen in Variabeln zu
        // speichern
        static String namensRegeln() {

            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine().toLowerCase();
            while (name.length() < 3 == true) {
                if (name.length() < 3 == true) {
                    System.out.println("Name muss mindesten 3 Buchstaben entahlten");
                    name = scanner.nextLine().toLowerCase();

                }
            }

            while (name.equals(spieler1) == true) {
                if (name.equals(spieler1) == true) {
                    System.out.println("Name bereits vergeben!");
                    name = scanner.nextLine().toLowerCase();

                }

            }

            while (name.matches("[a-zA-Z- ',]+") == false || name.matches(spieler1.toLowerCase()) == true
                    || name.contains(" ") == true || name.length() < 3 == true) {
                System.out.println("Bitte nur Buchstaben verwenden");
                name = scanner.nextLine().toLowerCase();

                if (name.matches("[a-zA-Z- ',]+") == false && name.length() < 3 == true) {
                    System.out.println("Bitte nur Buchstaben verwenden");
                    name = scanner.nextLine().toLowerCase();

                }
            }

            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            return name;
        }

        // Methode um das Spielfeld anzuzeigen
        public static void spielfeldanzeigen() {

            for (int i = 0; i < spielfeld.length; i++) {
                System.out.print(spielfeld[i]);
                System.out.println();
            }
        }

        // Methode um Spielsteine zu setzen.
        static void spielsteineSetzen(String player) {

            while (true) {
                try {
                    int pruefer = scanner.nextInt();
                    eingabe = pruefer;
                    break;

                } catch (Exception e) {
                    System.out.println("Bitte nuz zahlen eingeben");
                    scanner.next();
                    continue;

                }

            }

            if (eingabe == 1 || eingabe == 2 || eingabe == 3 || eingabe == 4 || eingabe == 5 || eingabe == 6 || eingabe == 7
                    || eingabe == 8 || eingabe == 9) {
                if (player == spieler1) {
                    symbol = 'X';
                } else {
                    symbol = 'O';
                }

                switch (eingabe) {
                    case 1:
                        if (belegtesFeldPruefen(0, 0, player) == false) {
                            break;
                        }
                        spielfeld[0][0] = symbol;
                        gewonnen();
                        break;
                    case 2:
                        if (belegtesFeldPruefen(0, 2, spieler1) == false) {
                            break;
                        }
                        spielfeld[0][2] = symbol;
                        gewonnen();
                        break;
                    case 3:
                        if (belegtesFeldPruefen(0, 4, spieler1) == false) {
                            break;
                        }
                        spielfeld[0][4] = symbol;
                        gewonnen();
                        break;
                    case 4:
                        if (belegtesFeldPruefen(1, 0, spieler1) == false) {
                            break;
                        }
                        spielfeld[1][0] = symbol;
                        gewonnen();
                        break;
                    case 5:
                        if (belegtesFeldPruefen(1, 2, spieler1) == false) {
                            break;
                        }
                        spielfeld[1][2] = symbol;
                        gewonnen();
                        break;
                    case 6:
                        if (belegtesFeldPruefen(1, 4, spieler1) == false) {
                            break;
                        }
                        spielfeld[1][4] = symbol;
                        gewonnen();
                        break;
                    case 7:
                        if (belegtesFeldPruefen(2, 0, spieler1) == false) {
                            break;
                        }
                        spielfeld[2][0] = symbol;
                        gewonnen();
                        break;
                    case 8:
                        if (belegtesFeldPruefen(2, 2, spieler1) == false) {
                            break;
                        }
                        spielfeld[2][2] = symbol;
                        gewonnen();
                        break;
                    case 9:
                        if (belegtesFeldPruefen(2, 4, spieler1) == false) {
                            break;
                        }
                        spielfeld[2][4] = symbol;
                        gewonnen();
                        break;
                }
            } else {
                System.out.println("Bitte nur eine Zahl wischen 1 und 9");
                spielsteineSetzen(player);

            }
        }

        // Methode um zu prüfen ob das Feld belegt ist oder nicht
        static boolean belegtesFeldPruefen(int a, int b, String player) {
            if (spielfeld[a][b] == 'X' || spielfeld[a][b] == 'O') {
                System.out.println("Diese Feld ist schon belegt. Bitte anderes Feld wähler");
                spielsteineSetzen(player);
                gewonnen();
                return false;
            }
            return true;
        }

        // Methode um das Spiel zu beenden wenn das Feld voll ist ( unentschieden ) oder
        // wenn jemand gewonnen hat
        static void spielBeenden() {
            System.out.println("Das Spiel wird beendet");
            System.exit(0);
        }

        // Methode um zu prüfen ob sich 3 Steine in eine Reihe befinden ( vertikal,
        // horizontal und diagonal)
        static void gewonnen() {
            for (int i = 0; i < 4; i += 2) {

                if (spielfeld[i][0] == symbol && spielfeld[i][2] == symbol && spielfeld[i][4] == symbol) {
                    System.out.println("Gewonnen");
                    spielfeldanzeigen();
                    spielBeenden();
                }
            }

            for (int j = 0; j < 3; ++j) {
                if (spielfeld[0][j] == symbol && spielfeld[1][j] == symbol && spielfeld[2][j] == symbol) {
                    System.out.println("Gewonnen");
                    spielfeldanzeigen();
                    spielBeenden();
                }

            }

            if (spielfeld[0][0] == symbol && spielfeld[1][2] == symbol && spielfeld[2][4] == symbol) {
                System.out.println("Gewonnen");
                spielfeldanzeigen();
                spielBeenden();
            }
            if (spielfeld[0][4] == symbol && spielfeld[1][2] == symbol && spielfeld[2][0] == symbol) {
                System.out.println("Gewonnen");
                spielfeldanzeigen();
                spielBeenden();
            }
        }

        public static void alleAufrufen () {
            regeln();

            // Es wird nach den Namen gefragt um die Namen in die Variabeln zu speichern
            System.out.println("Spieler 1 bitte Namen eingeben: ");
            spieler1 = namensRegeln();
            System.out.println("Spieler 2 bitte Namen eingeben: ");
            spieler2 = namensRegeln();

            // Wenn alles soweit passt wird das Spielfeld angezeigt
            spielfeldanzeigen();

            int durchgänge = 0;
            int reihenfolge = 2;

            // Schleifen um zwichen den Spieler zu Wechseln und und nach 9 Durchgängen wird
            // es beendet da man nicht mehr als 9 Züge zur Verfügung stehen.
            while (durchgänge < 9) {

                if (reihenfolge % 2 == 0) {
                    System.out.println();
                    System.out.println(spieler1 + " ist dran mit X");
                    spielsteineSetzen(spieler1);
                    spielfeldanzeigen();
                    gewonnen();

                    reihenfolge++;
                    durchgänge++;

                } else {
                    System.out.println();
                    System.out.println(spieler2 + " ist dran mit O");
                    spielsteineSetzen(spieler2);
                    spielfeldanzeigen();
                    gewonnen();

                    reihenfolge++;
                    durchgänge++;

                }
            }

            System.out.println("Unentschieden ihr DEPPEN !!!");

        }
        }





}

