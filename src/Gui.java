import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class Gui {
    private static int initialPanelWidth = 1050;
    private static int initialPanelHeight = 1400;
    private static double panelAspectRatio = (double) initialPanelWidth / initialPanelHeight;
    private int[][] initialBtnPosition = {{309,0},{541,91},{90,197},{614,266},{532,365},{0,458},{687,617},{218,619},{459,626},{26,748},{353,815},{185,950}};
    private boolean	resizing;

    public void createGui(){
        JFrame frame = new JFrame("Conquer the world");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1075);
        frame.setLayout(null);   
        frame.setVisible(true);

        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, initialPanelWidth, initialPanelHeight);
//----------------------TEST-----------------------------------------
        panel.setBackground(Color.CYAN);
//----------------------TEST-----------------------------------------
        frame.add(panel);

        JButton[] buttons = new JButton[12];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = createImgButton(i);
            panel.add(buttons[i]);
            System.out.println("button"+i+" größe: "+ buttons[i].getHeight()+ " breite: "+buttons[i].getWidth());
        }


        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (!resizing) {
                    resizing = true;
                    SwingUtilities.invokeLater(() -> {
                        adjustPanel(frame, panel);
                        adjustButtons(panel, buttons);
                        panel.revalidate();
                        panel.repaint();
                        resizing = false;
                    });
                }
            }
        });
        adjustPanel(frame, panel);
        adjustButtons(panel, buttons);
    }

    private JButton createImgButton(int imgNumber){
        try {
            BufferedImage img = ImageIO.read(new File("src/imgNumber/" + imgNumber + ".png"));
            ImageIcon imgIcon = new ImageIcon(img);
            JButton button = new JButton(imgIcon){
                @Override
                public boolean contains (int x, int y){
                    Dimension size = getSize();
                    double scaleX = (double) size.width / img.getWidth();
                    double scaleY = (double) size.height / img.getHeight();
                    int imgX = (int) (x / scaleX);
                    int imgY = (int) (y / scaleY);
                    if (imgX >= 0 && imgX < img.getWidth() && imgY >= 0 && imgY < img.getHeight()) {
                        int alpha = (img.getRGB(imgX, imgY) >> 24) & 0xff;
                        return alpha > 0;
                    }
                    return false;
                }
            };


            button.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            int[] coords = initialBtnPosition[imgNumber];
            button.setBounds(coords[0], coords[1], img.getWidth(), img.getHeight()); 
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
//----------------------TEST-----------------------------------------
            button.addActionListener(new ActionListener(){
                @Override 
                public void actionPerformed(ActionEvent e){
                    System.out.println("test: "+(imgNumber));
                }
            });
//----------------------TEST-----------------------------------------
            return button;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    private void adjustPanel (JFrame frame, JPanel panel){

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        int panelWidth = (int) (frameWidth * 0.8);
        int panelHeight = (int) (panelWidth / panelAspectRatio);

        if (panelHeight > frameHeight * 0.8) {
            panelHeight = (int) (frameHeight * 0.8);
            panelWidth = (int) (panelHeight * panelAspectRatio);
        }

        int panelX = (frameWidth - panelWidth) / 2;
        int panelY = (frameHeight - panelHeight) / 2;

        panel.setBounds(panelX, panelY, panelWidth, panelHeight);
        System.out.println("panal adjusted");
    }

    private void adjustButtons (JPanel panel, JButton[] buttons){

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        for (int i = 0; i < buttons.length && i < initialBtnPosition.length; i++) {
            int[] position = initialBtnPosition[i];
            int xPosition = position[0] * panelWidth / initialPanelWidth;
            int yPosition = position[1] * panelHeight / initialPanelHeight;
            try {
                BufferedImage bImg = ImageIO.read(new File("src/imgNumber/" + i + ".png"));
                
                double widthRatio = (double) panelWidth / initialPanelWidth;
                double heightRatio = (double) panelHeight / initialPanelHeight;
                
                int buttonWidth = (int) (bImg.getWidth() * widthRatio);
                int buttonHeight = (int) (bImg.getHeight() * heightRatio);

                Image scaledImg = bImg.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
                ImageIcon imgIcon = new ImageIcon(scaledImg);

                buttons[i].setIcon(imgIcon);
                buttons[i].setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
                buttons[i].revalidate();
                buttons[i].repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}