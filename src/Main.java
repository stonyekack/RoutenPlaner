import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import model.Haversine;
import model.Sortierung;
import util.*;
import view.MyIO;
import model.Ladestation;

import static util.Strings.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        int index = 0;
        int total_length = 1;
        begruessen();
        TreeSet<Ladestation> ladestationen_collection = sortiereDaten();
        if(Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[1]) < 0)
        {
            System.err.println("Fehlerhafte Argumente");
            return;
        }
        int epsilon = Integer.parseInt(args[0]);
        int maxEntfernung = Integer.parseInt(args[1]);
        int removedCounter = 0;

        Iterator<Ladestation> iterator = ladestationen_collection.iterator();

        while (iterator.hasNext()) {
            Ladestation reference = iterator.next();
            for (Ladestation ladestation : ladestationen_collection) {
                if (!reference.equals(ladestation)) {
                    double difference = Haversine.haversine(reference.getBreitengrad(), reference.getLaengengrad(), ladestation.getBreitengrad(), ladestation.getLaengengrad());
                    if (difference <= epsilon || difference >= maxEntfernung) {
                        iterator.remove();
                        removedCounter++;
                        break;
                    }
                }
            }
        }
        System.out.println("removed elements "+removedCounter);

        /*int test = 0;
        int epsilon = Integer.parseInt(args[0]);
        int maxEntfernung = Integer.parseInt(args[1]);
        List<Ladestation> list = new ArrayList<Ladestation>();
        //Ladestation reference = null;
        List<Object> ladestationen_collection_list = Arrays.stream(ladestationen_collection.toArray()).toList();
        for (int i = 0; i < ladestationen_collection_list.size(); i++) {
            Ladestation reference = (Ladestation) ladestationen_collection_list.get(i);
            for(Object o: ladestationen_collection_list.subList(i + 1, ladestationen_collection_list.size())) {
                Ladestation ladestation = (Ladestation) o;
                double difference = Haversine.haversine(reference.getBreitengrad(), reference.getLaengengrad(), ladestation.getBreitengrad(),ladestation.getLaengengrad());
                if (difference<=epsilon){
                    list.add(ladestation);
                    index++;
                }
            }
            ladestationen_collection_list.removeAll(list);
            list.clear();
        }*/

        /*List<Ladestation> list = new ArrayList<Ladestation>();
        for (Ladestation first_loop_ladestation: ladestationen_collection){
            reference = first_loop_ladestation;
            for (Ladestation ladestation : ladestationen_collection){
                if (!reference.equals(ladestation)){
                    double difference = Haversine.haversine(reference.getBreitengrad(), reference.getLaengengrad(), ladestation.getBreitengrad(),ladestation.getLaengengrad());
                    if (difference<=epsilon){
                        list.add(ladestation);
                        //ladestationen_collection.remove(ladestation);
                        index++;
                    }
                }
            }
            list.forEach(ladestationen_collection::remove);
            list.clear();
        }*/

        /*TreeSet<Ladestation> to_remove = new TreeSet<Ladestation>();
        Iterator<Ladestation> first_iterator = ladestationen_collection.iterator();

        while (first_iterator.hasNext())
        {
            Ladestation reference = first_iterator.next();
            Iterator<Ladestation> second_iterator = ladestationen_collection.iterator();
            // Die ganze Collection komplett zusammne mit der Reference durchlaufen
            while(second_iterator.hasNext()) {
                Ladestation ladestation = second_iterator.next();
                if (!ladestation.equals(reference))
                {
                    double difference = (int)Haversine.haversine(reference.getBreitengrad(), reference.getLaengengrad(), ladestation.getBreitengrad(), ladestation.getLaengengrad());
                    if (difference < epsilon || difference > maxEntfernung)
                    {
                       // index++;
                        ladestationen_collection.remove(ladestation);
                    }
                }
                // Inner Schleife wurde komplett durchgelaufen
                // Nächstes Element aus der first_collection nehmen
            }
        }
        System.out.println(index);
        /*for (Object to_removed_object : to_remove){
            ladestationen_collection.remove(to_removed_object);
        }*/
        System.out.println(index);
        System.out.println("Total index: " + ladestationen_collection.size());
    }
    /**
     * Methode leseDaten ermoeglicht das Lesen von der Datei und gleichzeitig das Sortieren von den Daten
     */
    private static TreeSet<Ladestation> sortiereDaten() throws Exception
    {
        String line;
        long startTime = 0;
        /*sortiere die eingelesene Ladestation nach Postleitzahl
                und innerhalb derselben Postleitzahl nach Leistung.
             */
        TreeSet<Ladestation> ladestationen_collection = new TreeSet<>(new Sortierung());

        try
        {
            startTime = System.nanoTime();
            int index = 0;
            //lese Daten von "Ladestation.csv" sequentiell
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

            //füge Ladestation solange es nicht leere Zeilen gibt

            while ((line = br.readLine()) != null)
            {
                String[] values = line.split(TRENNZEICHEN);
                if ((Double.parseDouble(values[6]) < 99) && (Double.parseDouble(values[7]) < 99))
                {
                    Ladestation new_ladestation = new Ladestation((values));
                    if(new_ladestation != null){
                        index++;
                    }
                    ladestationen_collection.add(new_ladestation);
                    ladestationen_collection.size();
                }
                else
                {
                    //System.err.println("Trage die Ladesatation nicht ein : " + " GeoPosition" + " breitenGrad : "+values[6] + " und laengenGrad : "+values[7]);
                }
            }
            /*long stopTime = System.nanoTime();
            long executionTime = (stopTime - startTime) / MeineKonstante.UMRECHNUNGSZAHL;
            System.out.println();
            MyIO.ausgeben(Strings.BEARBEITUNGSZEIT + executionTime +" Millisekunden");
            System.out.println();
            System.out.println(Strings.BEGRUESSUNGS_TEXT);*/

            /*for (Ladestation ladestation : ladestationen_collection)
            {
                MyIO.ausgeben("Betreiber : " + ladestation.getBetreiber() + ITEM_TRENNZEICHEN + "Strasse : "
                        + ladestation.getStrasse() + ITEM_TRENNZEICHEN + "Hausnummer : " + ladestation.getHausnummer()
                        + ITEM_TRENNZEICHEN + "Postleitzahl : " + ladestation.getPostleitzahl() + ITEM_TRENNZEICHEN + "Ort : "
                        + ladestation.getOrt() + ITEM_TRENNZEICHEN + "Bundesland : " + ladestation.getBundesland()
                        + ITEM_TRENNZEICHEN + " Breitengrad : " + ladestation.getBreitengrad() + ITEM_TRENNZEICHEN
                        + "Laengengrad : " + ladestation.getLaengengrad() + ITEM_TRENNZEICHEN + "Anschlussleistung : "
                        + ladestation.getAnschlussleistung());
            }*/
            System.out.println(Strings.NAMEN_ANZAHL +index);
        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        long stopTime = System.nanoTime();
        long executionTime = (stopTime - startTime) / MeineKonstante.UMRECHNUNGSZAHL;
        MyIO.ausgeben(Strings.SORTIERUNGSZEIT + executionTime +" Millisekunden");
        return ladestationen_collection;
    }

    /**
     * Diese Methode gibt den Begruessungstext aus.
     */
    private static void begruessen()
    {
        MyIO.ausgeben(Strings.BEGRUESSUNG);
    }
}
