package synergy.Views;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;


public class PhotosPanel extends JPanel {
    JFileChooser fileChooser;
    JPanel mainPanel;
    JPanel mainImagePanel;
    JPanel mainThumbnailPanel;
    JLabel mainImage;

    JPanel mainGridPanel;
    JPanel gridPanel;

    public static ArrayList<File> listOfImageFiles;
   int currentListSize;

    TagPanel tagPanel;

    boolean isMainView;

    public PhotosPanel() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        listOfImageFiles = new ArrayList<File>();

        mainImage = new JLabel();
        mainImage.setPreferredSize(new Dimension(800, 600));

        isMainView = true;

        setGridImages();
        mainGridPanel.setVisible(false);

        setUpJMenuBar();
        setUpMainFrame();
        setMainImagePanel(null);

        currentListSize = 0;

        tagPanel = new TagPanel(listOfImageFiles, this);
        add(tagPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public void setUpJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("View");
        JMenu fileMenu = new JMenu("File");

        JMenuItem importMenuItem = new JMenuItem("Import");
        JMenuItem exportMenuItem = new JMenuItem("Export");
        fileMenu.add(importMenuItem);
        fileMenu.add(exportMenuItem);

        importMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                int returnValue = fileChooser.showOpenDialog(PhotosPanel.this);
                System.out.println(returnValue);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] file = fileChooser.getSelectedFiles();
                    for (int i = 0; i < file.length; i++) {
                        listOfImageFiles.add(file[i]);
                    }
                }

                System.out.println(listOfImageFiles);
                tagPanel.initiateListOfMetaDataValues();
                System.out.println(tagPanel.listOfMetaData);
                setImportedImages();


            }

        });

        JMenuItem switchToMainView = new JMenuItem("Switch to Main View");
        switchToMainView.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMainView == false) {
                    mainPanel.setVisible(true);
                    mainGridPanel.setVisible(false);

                    isMainView = true;
                    tagPanel.updateLocationTags();
                }

            }
        });

        JMenuItem switchToGridView = new JMenuItem("Switch to Grid View");
        switchToGridView.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMainView == true) {
                    mainGridPanel.setVisible(true);
                    mainPanel.setVisible(false);
                    isMainView = false;
                    tagPanel.updateLocationTags();
                }
            }
        });


        viewMenu.add(switchToMainView);
        viewMenu.add(switchToGridView);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        add(menuBar, BorderLayout.NORTH);

    }

    public void setUpMainFrame() {
        mainPanel = new JPanel();
        mainImagePanel = new JPanel();
        mainImagePanel.add(mainImage);


        mainThumbnailPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainThumbnailPanel);
        scrollPane.setPreferredSize(new Dimension(200, 600));
        mainThumbnailPanel.setLayout(new GridLayout(2, 1));


        mainPanel.add(scrollPane, BorderLayout.WEST);
        mainPanel.add(mainImagePanel, BorderLayout.CENTER);


        add(mainPanel, BorderLayout.CENTER);

    }

    public void setImportedImages() {
       // mainThumbnailPanel.removeAll();
        mainThumbnailPanel.setLayout(new GridLayout(0, 1));
        setImagesToPanel(mainThumbnailPanel, 200, 200);
        setImagesToPanel(gridPanel, 300, 300);
        if (listOfImageFiles.size() > 0) {
            setMainImagePanel(listOfImageFiles.get(listOfImageFiles.size() - 1).toString());
        }
        this.validate();
        this.repaint();
    }

    public void setMainImagePanel(String fileName) {
        if (fileName == null) {
            mainImage.setText("Please import files");
        } else {
            ImageIcon pic1Icon = new ImageIcon(fileName);
            Image pic1img = pic1Icon.getImage();
            Image newimg = pic1img.getScaledInstance(mainImage.getWidth(), mainImage.getHeight(),
                    java.awt.Image.SCALE_SMOOTH);
            pic1Icon = new ImageIcon(newimg);
            mainImage.setIcon(pic1Icon);
        }
    }


    public void setGridImages() {
        mainGridPanel = new JPanel();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 3, 20, 20));
        setImagesToPanel(gridPanel, 300, 300);
        JScrollPane gridPanelPane = new JScrollPane(gridPanel);
        gridPanelPane.setPreferredSize(new Dimension(1000, 600));
        mainGridPanel.add(gridPanelPane, BorderLayout.CENTER);
        add(mainGridPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();

    }


    public void setImagesToPanel(JPanel panel, int imageWidth, int imageHeight) {
        final File checkBoxFile = new File("check box icon.png");
        BufferedImage checkBoxImage = null;
        try {
            checkBoxImage = ImageIO.read(checkBoxFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int width = imageWidth;
        final int height = imageHeight;


        for (int i = currentListSize; i < listOfImageFiles.size(); i++) {
            final int index = i;

            final JLabel pic = new JLabel();


            File imageFile = new File(listOfImageFiles.get(i).toString());
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(imageFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_BGR);
            final Graphics g = image.getGraphics();
            g.drawImage(bufferedImage, 0, 0, imageWidth, imageHeight, null);

            final BufferedImage finalBufferedImage = bufferedImage;
            final BufferedImage finalCheckBoxImage = checkBoxImage;



            pic.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    setMainImagePanel(listOfImageFiles.get(index).toString());
                    tagPanel.setIndex(index);
                    tagPanel.updateLocationTags();

                    if(isMainView == false && tagPanel.listOfSelectedIndex.get(index) == 0){
                        tagPanel.addToSelectedIndexList(index);
                        System.out.println("Selected Index List: " + tagPanel.listOfSelectedIndex);
                        g.drawImage(finalCheckBoxImage, 0, 0, 50, 50, null);
                        tagPanel.updateLocationTags();
                        pic.repaint();
                    } else if(isMainView == false && tagPanel.listOfSelectedIndex.get(index) == 1){
                        tagPanel.removeFromSelectedIndexList(index);
                        System.out.println("Selected Index List: " + tagPanel.listOfSelectedIndex);
                        g.drawImage(finalBufferedImage, 0, 0, width, height, null);
                        tagPanel.updateLocationTags();
                        pic.repaint();

                    }


                    if (arg0.getClickCount() == 2 && isMainView == false) {
                        mainPanel.setVisible(true);
                        mainGridPanel.setVisible(false);
                        isMainView = true;
                    }
                }

            });

            pic.setIcon(new ImageIcon(image));
            panel.add(pic);
        }

    }

    public TagPanel getTagPanel(){
        return tagPanel;
    }


}





