package Model;

public class Route {
    private final int TAILLE_X = 8;
    private final int TAILLE_Y = 8;

    public Case[][] tabCase;

    // R = Rien, D = Décision, G = goudron, A = attente, C = Conflit
    private String intersection =

            "RRRGGRRR" +
            "RRRDGRRR" +
            "RRRAGRRR" +
            "GGGCCADG" +
            "GDACCGGG" +
            "RRRGARRR" +
            "RRRGDRRR" +
            "RRRGGRRR" ;

    public Route()
    {
        tabCase = new Case[TAILLE_X][TAILLE_Y];

        for (int i = 0; i < TAILLE_X; i++)
        {
            for (int j = 0; j < TAILLE_Y; j++)
            {
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

    public Case[][] getTabCase() {return tabCase;}
}
