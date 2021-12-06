package Gui;

import javax.swing.*;
import java.awt.*;


class Gui extends JFrame{

    public Gui() {
        super();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screensize.height;
        int screenWidth = screensize.width;
        this.setTitle("Najeeb and Yehudit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(screenWidth,screenHeight);
        this.setVisible(true);
        ImageIcon image= new ImageIcon("green fade.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(196, 144, 65));

    }
    public static void main(String[] args) {
        Gui myGui=new Gui();


        /*
        Dimension screensize= Toolkit.getDefaultToolkit().getScreenSize();
        JFrame j = new JFrame();
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //j.setSize(500,500);
       // j.setSize(screensize);
        j.setSize(screensize.width/2,screensize.height/2);
        j.getContentPane().setBackground(new Color(65, 196, 166));
        j.setTitle("Najeeb and Yehudit");
        ImageIcon image= new ImageIcon("green fade.png");
        j.setIconImage(image.getImage());
       // JMenuBar mb=new JMenuBar();
       // JMenu file=new JMenu("File");
       // mb.add(file);
       // j.add(mb);




        j.setVisible(true);


    */
    }
}