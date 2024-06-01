import java.awt.Color;
import javax.swing.JPanel;


public class App extends JPanel{

    private Player player1, player2;
    
    public static void main(String[] args) throws Exception {
        App game = new App();
    }

    public App(){
        this.player1 = new Player(Color.BLUE);
        this.player2 = new Player(Color.RED);
        Gui gui = new Gui();
        gui.createGui();
    }
}
