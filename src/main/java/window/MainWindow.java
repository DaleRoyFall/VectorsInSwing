package window;

import vector.Operation;
import vector.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class MainWindow {
    Logger LOGGER = Logger.getLogger(Vector.class.getName());

    private JFrame frame;

    private JTextField frstVectorField;
    private JTextField scndVectorField;

    private JLabel frstVectorLabel;
    private JLabel scndVectorLabel;

    private JLabel chooseSummationLabel;
    private JLabel chooseDifferenceLabel;

    private JLabel formStatus;

    private Operation operation;

    private JButton calculateButton;

    private JLabel frstVectorInfoLabel;
    private JLabel scndVectorInfoLabel;
    private JLabel thrdVectorInfoLabel;

    private JLabel graphExtraInfoVectors;

    JPanel graph;

    Vector frstVector;
    Vector scndVector;
    Vector thrdVector;

    public MainWindow() {
        setFrame();
        addTextFields();
        addLabels();
        addButton();
        drawGraphWithoutVectors();

        frame.setVisible(true);
    }

    private void setFrame() {
        frame = new JFrame();
        frame.setSize(800, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addTextFields() {
        frstVectorField = new JTextField("");
        frstVectorField.setBounds(600, 50, 160, 30);

        scndVectorField = new JTextField("");
        scndVectorField.setBounds(600, 120, 160, 30);

        frame.add(frstVectorField);
        frame.add(scndVectorField);

        frame.setLayout(null);
    }

    private void addLabels() {
        frstVectorLabel = new JLabel("Enter first vector:");
        frstVectorLabel.setBounds(600, 20, 160, 30);

        scndVectorLabel = new JLabel("Enter second vector:");
        scndVectorLabel.setBounds(600, 90, 160, 30);

        chooseSummationLabel = new JLabel("+", SwingConstants.CENTER);
        chooseSummationLabel.setBounds(650, 160, 30, 30);
        chooseSummationLabel.setBackground(Color.WHITE);
        chooseSummationLabel.setOpaque(true);
        chooseSummationLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(!chooseDifferenceLabel.getBackground().equals(Color.WHITE))
                    chooseDifferenceLabel.setBackground(Color.WHITE);

                chooseSummationLabel.setBackground(Color.PINK);
                operation = Operation.SUMMATION;
            }
        });

        chooseDifferenceLabel = new JLabel("-", SwingConstants.CENTER);
        chooseDifferenceLabel.setBounds(680, 160, 30, 30);
        chooseDifferenceLabel.setBackground(Color.WHITE);
        chooseDifferenceLabel.setOpaque(true);
        chooseDifferenceLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(!chooseSummationLabel.getBackground().equals(Color.WHITE))
                    chooseSummationLabel.setBackground(Color.WHITE);

                chooseDifferenceLabel.setBackground(Color.PINK);
                operation = Operation.DIFFERENCE;
            }
        });

        formStatus = new JLabel("", SwingConstants.CENTER);
        formStatus.setBounds(590, 190, 180, 30);
        formStatus.setForeground(Color.RED);
        formStatus.setOpaque(true);

        frstVectorInfoLabel = new JLabel("First vector: ()");
        frstVectorInfoLabel.setBounds(600, 270, 160, 30);

        scndVectorInfoLabel = new JLabel("Second vector: ()");
        scndVectorInfoLabel.setBounds(600, 290, 160, 30);

        thrdVectorInfoLabel = new JLabel("Third vector: ()");
        thrdVectorInfoLabel.setBounds(600, 310, 160, 30);

        graphExtraInfoVectors = new JLabel("Vectors are represents just in bidimensional!");
        graphExtraInfoVectors.setBounds(10, 440, 260, 30);

        frame.add(frstVectorLabel);
        frame.add(scndVectorLabel);

        frame.add(chooseSummationLabel);
        frame.add(chooseDifferenceLabel);

        frame.add(formStatus);

        frame.add(frstVectorInfoLabel);
        frame.add(scndVectorInfoLabel);
        frame.add(thrdVectorInfoLabel);

        frame.add(graphExtraInfoVectors);
    }

    private void addButton() {
        calculateButton = new JButton("Calculate");
        calculateButton.setVerticalTextPosition(AbstractButton.CENTER);
        calculateButton.setHorizontalTextPosition(AbstractButton.LEADING);
        calculateButton.setBounds(600, 220, 160, 30);

        calculateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                formStatus.setText("");

                String frstVectorString = frstVectorField.getText();
                String scndVectorString = scndVectorField.getText();

                if(!frstVectorString.isEmpty() && !scndVectorString.isEmpty()) {
                    frstVector = new Vector(getVectorCoordinates(frstVectorString));
                    scndVector = new Vector(getVectorCoordinates(scndVectorString));

                    if(frstVector.getVector().length == scndVector.getVector().length) {

                        if(operation != null) {
                            thrdVector = Vector.operationsWithVectors(frstVector, scndVector, operation);

                            int[] frstVectorCoordinates = frstVector.getVector();
                            int[] scndVectorCoordinates = scndVector.getVector();
                            int[] thrdVectorCoordinates = thrdVector.getVector();

                            drawGraphWithVectors();

                            String frstVectorInString = "First vector: ( ";
                            String scndVectorInString = "Second vector: ( ";
                            String thrdVectorInString = "Third vector: ( ";

                            for (int i = 0; i < frstVectorCoordinates.length; i++) {
                                frstVectorInString = frstVectorInString.concat(String.valueOf(frstVectorCoordinates[i]) + "  ");
                                scndVectorInString = scndVectorInString.concat(String.valueOf(scndVectorCoordinates[i]) + "  ");
                                thrdVectorInString = thrdVectorInString.concat(String.valueOf(thrdVectorCoordinates[i]) + "  ");
                            }

                            frstVectorInString = frstVectorInString.concat(")");
                            scndVectorInString = scndVectorInString.concat(")");
                            thrdVectorInString = thrdVectorInString.concat(")");

                            frstVectorInfoLabel.setText(frstVectorInString);
                            scndVectorInfoLabel.setText(scndVectorInString);
                            thrdVectorInfoLabel.setText(thrdVectorInString);

                            javax.swing.JOptionPane.showMessageDialog(null,
                                    thrdVectorInString);
                        } else {
                            formStatus.setText("Choose a operation!");
                        }
                    } else {
                        formStatus.setText("Vectors have different lengths!");
                    }
                } else {
                    formStatus.setText("Fields can't be empty!");
                }
            }
        });

        frame.add(calculateButton);
    }

    private void drawGraphAxes(Graphics graphics) {
        // OX
        graphics.drawLine(20, 244, 560, 244);
        graphics.drawLine(560, 244, 550, 239);
        graphics.drawLine(560, 244, 550, 249);

        for (int i = 1; i <= 20; i++)
            graphics.drawLine(20 + 26 * i, 239, 20 + 26 * i, 249);

        // OY
        graphics.drawLine(280, 40, 280, 440);
        graphics.drawLine(280, 40, 275, 50);
        graphics.drawLine(280, 40, 285, 50);

        for (int i = 16; i > 1; i--)
            graphics.drawLine(275,26 * i + 10, 285, 26 * i  + 10);
    }

    private void drawGraphWithoutVectors() {
        graph = new JPanel() {

            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                drawGraphAxes(graphics);
            }
        };

        graph.setSize(600,500);

        frame.add(graph);
    }

    private void drawGraphWithVectors() {
        int graphCenterX = 280;
        int graphCenterY = 244;

        int[][] vectorsArray = new int[][] {frstVector.getVector(), scndVector.getVector(), thrdVector.getVector()};

        int maxX = Math.abs(vectorsArray[0][0]);
        int maxY = Math.abs(vectorsArray[0][1]);

        for (int i = 1; i < vectorsArray.length; i++) {
            if(Math.abs(vectorsArray[i][0]) > maxX)
                maxX = Math.abs(vectorsArray[i][0]);

            if(Math.abs(vectorsArray[i][1]) > maxY)
                maxY = Math.abs(vectorsArray[i][1]);
        }

        int finalMaxX = 280 / maxX;
        int finalMaxY = 200 / maxY;

        System.out.println((float)maxX / 10 + " " + (float)maxY / 7);

        graph = new JPanel() {

            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                drawGraphAxes(graphics);

                graphics.drawLine(graphCenterX, graphCenterY, graphCenterX + finalMaxX * vectorsArray[0][0],
                        graphCenterY - finalMaxY * vectorsArray[0][1]);
                graphics.drawLine(graphCenterX, graphCenterY, graphCenterX + finalMaxX * vectorsArray[1][0],
                        graphCenterY - finalMaxY * vectorsArray[1][1]);
                graphics.drawLine(graphCenterX, graphCenterY, graphCenterX + finalMaxX * vectorsArray[2][0],
                        graphCenterY - finalMaxY * vectorsArray[2][1]);
            }

        };

        graph.setSize(600,500);

        frame.getContentPane().removeAll();
        frame.repaint();

        addAllComponents();
    }

    private void addAllComponents() {
        frame.add(frstVectorField);
        frame.add(scndVectorField);

        frame.add(frstVectorLabel);
        frame.add(scndVectorLabel);

        frame.add(chooseSummationLabel);
        frame.add(chooseDifferenceLabel);

        frame.add(frstVectorInfoLabel);
        frame.add(scndVectorInfoLabel);
        frame.add(thrdVectorInfoLabel);

        frame.add(graphExtraInfoVectors);

        frame.add(formStatus);

        frame.add(calculateButton);

        frame.add(graph);
    }

    private int[] getVectorCoordinates(String vectorInString) {
        String[] vectorCoordinates = vectorInString.split(" ");

        int[] vectorCoordinatesInInt = new int[vectorCoordinates.length];

        for (int i = 0; i < vectorCoordinates.length; i++)
            vectorCoordinatesInInt[i] = Integer.parseInt(vectorCoordinates[i]);

        return vectorCoordinatesInInt;
    }
}
