package model;
import model.Ladestation;
import java.util.Comparator;
import java.util.Objects;

public class Sortierung implements Comparator<Ladestation>
{
    /**
     * @param l1 the first object to be compared.
     * @param l2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(Ladestation l1, Ladestation l2) {
        if (l1.getPostleitzahl() > l2.getPostleitzahl()) {
            return 1;
        }
        if (l1.getPostleitzahl() < l2.getPostleitzahl()) {
            return -1;
        }

        if (l1.getAnschlussleistung() > l2.getAnschlussleistung()) {
            return -2;
        }

        // Wenn PLZ gleich sind
        if (l1.getAnschlussleistung() < l2.getAnschlussleistung()) {
            return 2;
        }

        if (l1.getPostleitzahl() == l2.getPostleitzahl() && l1.getAnschlussleistung() == l2.getAnschlussleistung() && l1.getLaengengrad() == l2.getLaengengrad()
        && l1.getBreitengrad() == l2.getBreitengrad() && l1.getBetreiber().equals(l2.getBetreiber()) && l1.getBundesland().equals(l2.getBundesland()) &&
                l1.getOrt().equals(l2.getOrt()) && l1.getStrasse().equals(l2.getStrasse()) && l1.getHausnummer().equals(l2.getHausnummer())) {
            return 0;
        }
        // Wenn Anschlussleistungen gleich sind
        return -3;
        }
    }

