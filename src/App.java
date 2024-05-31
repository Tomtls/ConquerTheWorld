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

    //    uiJPanel.add(test());
        int x = 310;
        int y = 100;
        uiJPanel.add(createImgButton("SH", x, y));
        uiJPanel.add(createImgButton("MV", x+232, y+91));
        uiJPanel.add(createImgButton("NI", x-219, y+197));
        uiJPanel.add(createImgButton("BB", x+305, y+266));
        uiJPanel.add(createImgButton("SA", x+223, y+365));
        uiJPanel.add(createImgButton("NRW", x-309, y+458));
        uiJPanel.add(createImgButton("SN", x+378, y+617));
        uiJPanel.add(createImgButton("HE", x-91, y+619));
        uiJPanel.add(createImgButton("TH", x+150, y+626));
        uiJPanel.add(createImgButton("RP", x-283, y+748));
        uiJPanel.add(createImgButton("BY", x*44, y+815));
        uiJPanel.add(createImgButton("BW", x-124, y+950));

        add(uiJPanel, BorderLayout.CENTER);
        
    }

    public JButton createImgButton(String imgName, int x, int y){
        try {
            BufferedImage img = ImageIO.read(new File("src/img/" + imgName + ".png"));
            JButton button = ImageButton.createImgButton(img);
            button.setBounds(x,y,img.getWidth(),img.getHeight());
            return button;
         } catch (Exception e) {
            e.printStackTrace();
            return null;
         }
    }
}
