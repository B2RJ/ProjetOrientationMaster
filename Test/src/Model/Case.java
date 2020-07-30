package Model;

import java.util.Observable;

/**
 * This is the mother class of each case.
 *
 * @author B2RJ
 */

public class Case extends Observable {
    /**
     * Thanks this variable we can know which case we have.
     */
    public String valeur;

    /**
     * This is the constructor.
     */
    public Case(){}
}
