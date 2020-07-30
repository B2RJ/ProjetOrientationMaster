package Model;

/**
 * This is the class manage the road.
 *
 * @author B2RJ
 */
public class Route {

    /**
     * This is the array who contains the representation of the road
     */
    public Case[][] tabCase;

    /**
     * This is the constructor
     */
    public Route()
    {
        int TAILLE_X = 8;
        int TAILLE_Y = 8;
        tabCase = new Case[TAILLE_X][TAILLE_Y];
        String intersection = "RRRGGRRR" +
                "RRRDGRRR" +
                "RRRAGRRR" +
                "GGGCCADG" +
                "GDACCGGG" +
                "RRRGARRR" +
                "RRRGDRRR" +
                "RRRGGRRR";

        for (int i = 0; i < TAILLE_X; i++)
        {
            for (int j = 0; j < TAILLE_Y; j++)
            {
                // R = Rien, D = Décision, G = goudron, A = attente, C = Conflit
                switch (intersection.charAt(TAILLE_X * i +j))
                {
                    case 'G':
                        tabCase[i][j] = new Goudron();
                        break;
                    case 'D':
                        tabCase[i][j] = new Decision();
                        break;
                    case 'C':
                        tabCase[i][j] = new Conflit();
                        break;
                    case 'A':
                        tabCase[i][j] = new Attente();
                        break;
                    default:
                        tabCase[i][j] = new Case();
                        break;
                }
            }
        }
    }

    /**
     * This is the getter of the representation of the road.
     * @return tabCase
     */
    public Case[][] getTabCase() {return tabCase;}
}
