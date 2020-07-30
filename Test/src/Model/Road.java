package Model;

/**
 * This is the class manage the road.
 *
 * @author B2RJ
 */
public class Road {

    /**
     * This is the array who contains the representation of the road
     */
    public Case[][] tabCase;

    /**
     * This is the constructor
     */
    public Road()
    {
        int SIZE_X = 8;
        int SIZE_Y = 8;
        tabCase = new Case[SIZE_X][SIZE_Y];
        String intersection = "RRRGGRRR" +
                "RRRDGRRR" +
                "RRRAGRRR" +
                "GGGCCADG" +
                "GDACCGGG" +
                "RRRGARRR" +
                "RRRGDRRR" +
                "RRRGGRRR";

        for (int i = 0; i < SIZE_X; i++)
        {
            for (int j = 0; j < SIZE_Y; j++)
            {
                // R = Nothing, D = Decision, G = Tar, A = wait, C = conflict
                switch (intersection.charAt(SIZE_X * i +j))
                {
                    case 'G':
                        tabCase[i][j] = new Tar();
                        break;
                    case 'D':
                        tabCase[i][j] = new Decision();
                        break;
                    case 'C':
                        tabCase[i][j] = new Conflict();
                        break;
                    case 'A':
                        tabCase[i][j] = new Wait();
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
