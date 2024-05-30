import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JPanel{

    private Player player1, player2;
    
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Conquer the world");
        App game = new App();

        frame.add(game);
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public App(){
        this.player1 = new Player(Color.BLUE);
        this.player2 = new Player(Color.RED);

        setLayout(new BorderLayout());
        
        JPanel uiJPanel = new JPanel();
        uiJPanel.setLayout(null);
        uiJPanel.add(test());
        add(uiJPanel, BorderLayout.CENTER);
        

    }
    public JButton test(){
        try {
            BufferedImage test = ImageIO.read(new File("src/img/image.png"));
            JButton button = ImageButton.createImgButton(test);
            button.setBounds(100,100,test.getWidth(),test.getHeight());
            return button;
         } catch (Exception e) {
            e.printStackTrace();
            return null;
         }
    }
}
