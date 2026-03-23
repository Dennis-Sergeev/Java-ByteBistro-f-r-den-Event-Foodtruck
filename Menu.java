import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu
{

    public void printMainMenu()
    {
        System.out.println("0 | Artikel liste Anzeigen");
        System.out.println("1 | Bestellung anlegen");
        System.out.println("2 | Bestellung bearbeiten");
        System.out.println("3 | Bestellung löschen");
        System.out.println("4 | Bestellung anzeigen");
        System.out.println("5 | Bestellung buchen");
        System.out.println("6 | Admin Menu");
        System.out.println("7 | Programm beenden");
    }

    public void printPositionMenu()
    {

        System.out.println("0 | Position hinzufügen");
        System.out.println("1 | Position bearbeiten");
        System.out.println("2 | Position löschen");
        System.out.println("3 | Raus aus Unter Menu");
    }

    public void printAdminMenu()
    {
        // User: Administrator Password : Test123!    Wurde nicht realisiert
        System.out.println("0 | Tagesüberblick"); //Ohne aus Datei Einlesen
        //System.out.println("1 | Letzte Bestellung anzeigen"); Optionale Aufgabe
        System.out.println("1 | Raus aus Unter Menu");
    }


    Scanner userInput = new Scanner(System.in);
    ProductList list = new ProductList();

    boolean programmIsRunning = true;
    int ChooseProductID;
    ArrayList<OrderNummber> OrderNummberList = new ArrayList<>();
    int orderNumm = 0;

    int UserQuantity;
    float tagesUmsatz = 0.0f;
    public void userMenu()
    {
        list.initInventory();
        printMainMenu();
        while (programmIsRunning)

        {
            try{
                Scanner scr = new Scanner(System.in);
                System.out.println();
                System.out.print("Wähle ein Menupunkt aus: ");

                int UserIpt = scr.nextInt();
                if(UserIpt >= 0 && UserIpt <= 7)
                {
                    //MainMenu
                    switch (UserIpt)
                    {
                        case 0:
                                System.out.println();
                                list.showInventory();
                        break;
                        case 1:

                            if (!OrderNummberList.isEmpty()) {
                                int aktuellerIndex = OrderNummberList.size() - 1;
                                if (!OrderNummberList.get(aktuellerIndex).getOrderCompleted()) {
                                    System.out.println("Bestellung Nr. " + OrderNummberList.get(aktuellerIndex).getOrderNumm() +
                                            " ist offen, mit Menupunkt 3 schließen");
                                    break;
                                }
                            }

                            // Produkt ID abfragen
                            do {
                                System.out.print("Produkt ID: ");
                                try {
                                    ChooseProductID = scr.nextInt();
                                    if (ChooseProductID >= 0 && ChooseProductID < list.inventory.size()) { // Dynamisch prüfen
                                        break;
                                    } else {
                                        System.out.println("Ist keine gültige Produkt ID");
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Die Eingabe ist keine Zahl");
                                    scr.next();
                                }
                            } while (true);

                            //Menge abfragen
                            do {
                                System.out.print("Menge: ");
                                try {
                                    UserQuantity = scr.nextInt();
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Die Eingabe ist keine Zahl");
                                    scr.next();
                                }
                            } while (true);

                            //Bestand prüfen
                            int aktuellerBestand = list.getStock(ChooseProductID);
                            if (aktuellerBestand < UserQuantity) {
                                System.out.println("Produktbestand ist zu gering für Ihre Bestellung (Vorhanden: " + aktuellerBestand + ")");
                            } else {
                                String name = list.inventory.get(ChooseProductID).getName();
                                //Jetzt die Bestellung in die Liste eintragen
                                OrderNummber neueBestellung = new OrderNummber(orderNumm, false, ChooseProductID, UserQuantity, name);
                                OrderNummberList.add(neueBestellung);

                                //Bestand im Lager abziehen
                                list.setStock(ChooseProductID, aktuellerBestand - UserQuantity);

                                System.out.println("Bestellung Nr. " + orderNumm + " angelegt.");
                                if ((aktuellerBestand - UserQuantity) <= 3) {
                                    System.out.println("Warnung: Bestand ist knapp: " + (aktuellerBestand - UserQuantity));
                                }
                                orderNumm++;
                            }
                            break;
                        case 2:
                            if (OrderNummberList.isEmpty()) {
                                System.out.println("Keine Bestellungen zum Bearbeiten vorhanden.");
                                break;
                            }

                            // Wir bearbeiten immer die aktuellste (letzte) Bestellung
                            int aktuelleBestellNummer = OrderNummberList.get(OrderNummberList.size() - 1).getOrderNumm();

                            // Prüfen, ob die letzte Bestellung schon abgeschlossen ist
                            if (OrderNummberList.get(OrderNummberList.size() - 1).getOrderCompleted()) {
                                System.out.println("Abgeschlossene Bestellungen können nicht mehr bearbeitet werden.");
                                break;
                            }

                            boolean subMenuRunning = true;
                            while (subMenuRunning) {
                                System.out.println("\n--- Bearbeite Bestellung Nr. " + aktuelleBestellNummer + " ---");
                                printPositionMenu();
                                System.out.print("Wähle eine Option: ");
                                int subChoice = scr.nextInt();

                                switch (subChoice) {
                                    case 0:
                                        // Hier kopieren wir die Logik aus Case 1 (ID & Menge abfragen)
                                        System.out.print("Produkt ID zum Hinzufügen: ");
                                        int addID = scr.nextInt();
                                        System.out.print("Menge: ");
                                        int addQty = scr.nextInt();

                                        if (list.getStock(addID) >= addQty) {
                                            String name = list.inventory.get(addID).getName();
                                            OrderNummber position = new OrderNummber(aktuelleBestellNummer, false, addID, addQty, name);
                                            OrderNummberList.add(position);
                                            list.setStock(addID, list.getStock(addID) - addQty);
                                            System.out.println(name + " hinzugefügt.");
                                        } else {
                                            System.out.println("Nicht genug Bestand!");
                                        }
                                        break;

                                    // Innerhalb von Case 2 -> Case 1 (Menge ändern):
                                    case 1:
                                        System.out.print("Produkt ID zum Ändern: ");
                                        int editID = scr.nextInt();
                                        boolean gefunden = false;
                                        for (OrderNummber on : OrderNummberList) {
                                            if (on.getOrderNumm() == aktuelleBestellNummer && on.getProductId() == editID && !on.getOrderCompleted()) {
                                                System.out.print("Neue Menge: ");
                                                int newQty = scr.nextInt();
                                                int diff = newQty - on.getQuantity();

                                                if (list.getStock(editID) >= diff) {
                                                    list.setStock(editID, list.getStock(editID) - diff);
                                                    on.setQuantity(newQty);
                                                    System.out.println("Menge angepasst.");
                                                } else {
                                                    System.out.println("Lagerbestand reicht nicht!");
                                                }
                                                gefunden = true;
                                                break;
                                            }
                                        }
                                        if (!gefunden) System.out.println("Position nicht in dieser Bestellung gefunden.");
                                        break;

                                    case 2: // Position löschen
                                        System.out.print("Produkt ID zum Entfernen aus dieser Bestellung: ");
                                        int remID = scr.nextInt();
                                        for (int i = OrderNummberList.size() - 1; i >= 0; i--) {
                                            OrderNummber on = OrderNummberList.get(i);
                                            if (on.getOrderNumm() == aktuelleBestellNummer && on.getProductId() == remID) {
                                                // Bestand zurückgeben
                                                list.setStock(remID, list.getStock(remID) + on.getQuantity());
                                                OrderNummberList.remove(i);
                                                System.out.println("Position entfernt.");
                                                break;
                                            }
                                        }
                                        break;

                                    case 3: // Raus aus Unter Menu
                                        subMenuRunning = false;
                                        break;

                                    default:
                                        System.out.println("Ungültige Wahl.");
                                }
                            }
                        break;
                        case 3:

                            if (!OrderNummberList.isEmpty()) {
                                //Die Nummer der letzten Bestellung herausfinden
                                int gesuchteNummer = OrderNummberList.get(OrderNummberList.size() - 1).getOrderNumm();
                                boolean wurdeGeloescht = false;

                                //Die Liste RÜCKWÄRTS durchgehen
                                for (int i = OrderNummberList.size() - 1; i >= 0; i--) {
                                    OrderNummber aktuelle = OrderNummberList.get(i);

                                    // Nur löschen, wenn Nummer stimmt und sie noch nicht abgeschlossen ist
                                    if (aktuelle.getOrderNumm() == gesuchteNummer) {
                                        if (!aktuelle.getOrderCompleted()) { // Nur wenn false

                                            //Bestand Zurückgeben
                                            int aktuellerLagerBestand = list.getStock(aktuelle.getProductId());
                                            int neueMenge = aktuellerLagerBestand + aktuelle.getQuantity();
                                            list.setStock(aktuelle.getProductId(), neueMenge);
                                            // ------------------------------------

                                            OrderNummberList.remove(i);
                                            wurdeGeloescht = true;
                                        } else {
                                            System.out.println("Bestellung Nr. " + gesuchteNummer + " ist bereits abgeschlossen und kann nicht gelöscht werden!");
                                            //Abgeschlossene Bestellung darf man nicht löschen auf der Liste
                                            break;
                                        }
                                    }
                                }

                                if (wurdeGeloescht) {
                                    System.out.println("Bestellung Nr. " + gesuchteNummer + " wurde gelöscht und Bestand korrigiert.");
                                }
                            } else {
                                System.out.println("Keine Bestellungen vorhanden.");
                            }

                        break;
                        case 4:
                                //Bestellung Anzeigen
                            if(OrderNummberList.isEmpty())
                            {
                                System.out.println("Keine Bestellungen angelegt.");
                            }else
                            {
                                System.out.println("----Deine Bestellungen----");
                                for (int i = 0; i < OrderNummberList.size(); i++)
                                {
                                    OrderNummber aktuelleBestellung = OrderNummberList.get(i);
                                    System.out.println("Bestellungsnummer: " + aktuelleBestellung.getOrderNumm() + " Bestellung abgeschlossen: " + aktuelleBestellung.getOrderCompleted() + " Produkt: "
                                            + aktuelleBestellung.getProductName() + " Mänge: " + aktuelleBestellung.getQuantity());
                                }
                            }

                        break;

                        case 5:
                            if (!OrderNummberList.isEmpty()) {
                                //Nummer der letzten Bestellung herausfinden
                                int gesuchteNummer = OrderNummberList.get(OrderNummberList.size() - 1).getOrderNumm();

                                // Prüfen, ob die Bestellung bereits bezahlt wurde
                                if (OrderNummberList.get(OrderNummberList.size() - 1).getOrderCompleted()) {
                                    System.out.println("Diese Bestellung (Nr. " + gesuchteNummer + ") wurde bereits bezahlt.");
                                    break;
                                }

                                // Gesamtpreis berechnen
                                float gesamtPreis = 0;
                                for (OrderNummber on : OrderNummberList) {
                                    if (on.getOrderNumm() == gesuchteNummer) {
                                        // Preis aus der ProductList holen (Preis * Menge)
                                        float einzelPreis = list.inventory.get(on.getProductId()).getPrice();
                                        gesamtPreis += (einzelPreis * on.getQuantity());
                                    }
                                }

                                System.out.printf("Gesamtbetrag für Bestellung Nr. % d: %.2f€\n", gesuchteNummer, gesamtPreis);
                                System.out.print("Gegebenes Geld vom Kunden: ");
                                float gegebenesGeld = scr.nextFloat();

                                //Zahlung prüfen
                                if (gegebenesGeld < gesamtPreis) {
                                    //Wenn, zu wenig Geld
                                    float fehlt = gesamtPreis - gegebenesGeld;
                                    System.out.printf("Das reicht nicht! Es fehlen noch %.2f€\n", fehlt);
                                } else {
                                    //Wenn, genug oder mehr Geld (Rückgeld)
                                    if (gegebenesGeld > gesamtPreis) {
                                        float rueckgeld = gegebenesGeld - gesamtPreis;
                                        System.out.printf("Zahlung erfolgreich. Rückgeld: %.2f€\n", rueckgeld);
                                    } else {
                                        System.out.println("Zahlung erfolgreich. Passend bezahlt.");
                                    }

                                    //Alle Positionen dieser Bestellung auf abgeschlossen setzen
                                    for (OrderNummber on : OrderNummberList) {
                                        if (on.getOrderNumm() == gesuchteNummer) {
                                            on.setOrderCompleted(true);
                                        }
                                    }
                                    System.out.println("Bestellung Nr. " + gesuchteNummer + " wurde gebucht.");
                                    orderNumm++;
                                    // In Case 5, nachdem die Zahlung erfolgreich war:
                                    tagesUmsatz += gesamtPreis;
                                }
                            } else {
                                System.out.println("Keine Bestellungen vorhanden.");
                            }

                        break;
                        case 6:
                            boolean adminMenuRunning = true;
                            while (adminMenuRunning) {
                                System.out.println("\n--- ADMIN BEREICH (Tagesüberblick) ---");
                                printAdminMenu();
                                System.out.print("Wahl: ");
                                int adminChoice = scr.nextInt();

                                if (adminChoice == 0) {
                                    System.out.println("======================================");
                                    System.out.println("      Tagesstatistik Event-Kasse      ");
                                    System.out.println("======================================");

                                    // Umsatz anzeigen
                                    System.out.printf("Gesamtumsatz heute: %.2f€\n", tagesUmsatz);

                                    System.out.println("Abgeschlossene Bestellungen: " + orderNumm);

                                    //Kritische Bestände anzeigen (Anforderung "knappste Artikel")
                                    System.out.println("\nKritische Bestände (weniger als 3 Stück):");
                                    boolean allesVoll = true;
                                    for (int i = 0; i < list.inventory.size(); i++) {
                                        if (list.getStock(i) < 3) {
                                            System.out.println("Warnung " + list.inventory.get(i).getName() + ": nur noch " + list.getStock(i) + " Übrig!");
                                            allesVoll = false;
                                        }
                                    }
                                    if (allesVoll) {
                                        System.out.println("Alle Artikel sind ausreichend vorhanden.");
                                    }
                                    System.out.println("======================================");

                                } else if (adminChoice == 1) {
                                    adminMenuRunning = false; // Zurück zum Hauptmenü
                                } else {
                                    System.out.println("Ungültige Option.");
                                }
                            }
                            break;

                        case 7:
                                programmIsRunning = false;
                        break;
                    }

                }else
                {
                    System.out.println();
                    System.out.print("Die Zahl (" + UserIpt + ") ist kein Menupunkt ");
                    while (true)
                    {
                        System.out.println();
                        System.out.print("Wähle ein Menupunkt aus: ");
                        UserIpt = scr.nextInt();
                        if(UserIpt >= 0 && UserIpt <= 7)
                        {
                            break;
                        }
                    }
                }

            } catch (InputMismatchException e)
            {
                System.out.println("Der Userinput ist keine Zahl");
            }
        }

    }
}