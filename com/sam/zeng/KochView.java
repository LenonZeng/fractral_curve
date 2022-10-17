package com.sam.zeng;

import com.sam.zeng.koch.IKochView;
import com.sam.zeng.koch.KochType;
import com.sam.zeng.koch.Point;
import com.sam.zeng.koch.ShapeObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KochView implements ActionListener, DegreeObserver, ShapeObserver, IKochView {
    private KochController controller;
    private IKochModel model;
    private static JFrame viewFrame;
    private JFrame controlFrame;
    private JPanel viewPanel;
    private JPanel controlPanel;
    private JMenu menu;
    private JMenu fractMenu;
    private JMenuBar menuBar;
    private JMenuItem startMenuItem, clearMenuItem, quitMenuItem,
            kochMenuItem, hilbertMenuItem, peanoMenuItem,
            starMenuItem, dragonMenuItem, snowFlakeMenuItem;
    private JTextField degreeTF;
    private JLabel degreeLabel, shapeLabel;
    private Choice shapeChoice;
    private JButton setBtn, increaseBtn, decreaseBtn;
    private static final int DEFAULT_SIZE = 512;
    private static int width = DEFAULT_SIZE;
    private static int height = DEFAULT_SIZE;
    private static BufferedImage offscreenImage, onscreenImage;
    private static Graphics2D offscreen, onscreen;
    private static double penRadius;
    private static Color penColor;
    private static boolean defer = false;
    private static final Color DEFAULT_PEN_COLOR = Color.BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = Color.WHITE;
    private ArrayList<Point> dataList;

    public KochView(KochController controller, IKochModel model) {
        this.controller = controller;
        this.model = model;
        model.registerDegreeObserver(this);
        model.registerShapeObserver(this);
    }

    public void createView() {
        viewFrame = new JFrame("Standard Draw");
        viewFrame.setJMenuBar(createMenuBar());
        offscreenImage = new BufferedImage(2 * width, 2 * height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage = new BufferedImage(2 * width, 2 * height, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen = onscreenImage.createGraphics();
        offscreen.scale(2.0, 2.0);

        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);

        JLabel draw = new JLabel(new ImageIcon(onscreenImage));
        viewFrame.setContentPane(draw);
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.pack();
        viewFrame.requestFocusInWindow();
        viewFrame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(this);
        // Java 10+: replace getMenuShortcutKeyMask() with getMenuShortcutKeyMaskEx()
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);
        return menuBar;
    }

    public void createControls() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100, 100));
        controlPanel = new JPanel(new GridLayout(1, 2));
        menuBar = new JMenuBar();
        menu = new JMenu("Action");
        startMenuItem = new JMenuItem("Start");
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataList = new ArrayList<>();
                dataList.addAll(model.getPoints());
                drawShape(model.getShape());
            }
        });
        clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        quitMenuItem = new JMenuItem("Exit");
        menu.add(startMenuItem);
        menu.add(clearMenuItem);
        menu.add(quitMenuItem);
        menuBar.add(menu);
        fractMenu = new JMenu("Fractal");
        kochMenuItem = new JMenuItem(KochType.Koch.getType());
        kochMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlFrame.setTitle(KochType.Koch.getType());
                controller.setType(KochType.Koch);
                dataList = model.getPoints();
            }
        });
        kochMenuItem.addActionListener(this);
        hilbertMenuItem = new JMenuItem(KochType.Hilbert.getType());
        hilbertMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlFrame.setTitle(KochType.Hilbert.getType());
                controller.setType(KochType.Hilbert);
                dataList = model.getPoints();
            }
        });
        hilbertMenuItem.addActionListener(this);
        peanoMenuItem = new JMenuItem(KochType.Peano.getType());
        peanoMenuItem.addActionListener(this);
        starMenuItem = new JMenuItem(KochType.Star.getType());
        starMenuItem.addActionListener(this);
        dragonMenuItem = new JMenuItem(KochType.Dragon.getType());
        dragonMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlFrame.setTitle(KochType.Dragon.getType());
                controller.setType(KochType.Dragon);
                dataList = model.getPoints();
            }
        });
        snowFlakeMenuItem = new JMenuItem(KochType.Snowflake.getType());
        snowFlakeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlFrame.setTitle(KochType.Snowflake.getType());
                controller.setType(KochType.Snowflake);
                dataList = model.getPoints();
            }
        });
        snowFlakeMenuItem.addActionListener(this);
        fractMenu.add(kochMenuItem);
        fractMenu.add(hilbertMenuItem);
        fractMenu.add(peanoMenuItem);
        fractMenu.add(starMenuItem);
        fractMenu.add(dragonMenuItem);
        fractMenu.add(snowFlakeMenuItem);
        menuBar.add(fractMenu);
        controlFrame.setJMenuBar(menuBar);
        startMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.start();
            }
        });
        clearMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });
        quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        degreeTF = new JTextField(2);
        degreeTF.setText("0");
        degreeLabel = new JLabel("Enter degree: ", SwingConstants.RIGHT);
        setBtn = new JButton("Set");
        setBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String degree = degreeTF.getText();
                controller.setDegree(Integer.parseInt(degree));
            }
        });
        setBtn.setSize(new Dimension(10, 40));
        increaseBtn = new JButton(">>");
        decreaseBtn = new JButton("<<");
        setBtn.addActionListener(this);
        increaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer degree = Integer.parseInt(degreeTF.getText());
                degree++;
                if (degree >= 0){
                    degreeTF.setText("" + degree);
                    degreeTF.invalidate();
                }

            }
        });
        decreaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer degree = Integer.parseInt(degreeTF.getText());
                degree--;
                if (degree >= 0) {
                    degreeTF.setText("" + degree);
                    degreeTF.invalidate();
                }else {
                    degree = 0;
                }
            }
        });

        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        btnPanel.add(decreaseBtn);
        btnPanel.add(increaseBtn);
        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(degreeLabel);
        enterPanel.add(degreeTF);

        JPanel insidePanel = new JPanel(new GridLayout(3, 1));
        insidePanel.add(enterPanel);
        insidePanel.add(setBtn);
        insidePanel.add(btnPanel);
        controlPanel.add(insidePanel);
        degreeLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        controlFrame.getRootPane().setDefaultButton(setBtn);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);
        controlFrame.pack();
        controlFrame.setVisible(true);
    }

    @Override
    public void drawShape(KochType type) {
        if (type == null) return;
        controlFrame.setTitle(type.getType());
        dataList = new ArrayList<>();
        dataList.addAll(model.getPoints());
        if (type.equals(KochType.Koch))
            drawKoch();
        if (type.equals(KochType.Hilbert))
            drawHilbert(model.getDegree());
        if (type.equals(KochType.Dragon))
            drawDragon();
        if (type.equals(KochType.Triangle))
            drawTriangle();
        if (type.equals(KochType.Snowflake))
            drawSnowflake();
    }

    @Override
    public String getType() {
        return model.getShape().name();
    }

    @Override
    public int getDegree() {
        return 0;
    }

    private void drawSnowflake() {
        clear();
        setPenRadius(0.005);
        setPenColor(Color.BLUE);
        setXscale(-2.0, 2.0);
        setYscale(-2.0, 2.0);
        for (int i = 0; i < dataList.size() - 1; i++) {
            Point p0 = dataList.get(i);
            Point p1 = dataList.get(i + 1);
            System.out.println("draw px=" + p0.getX() + " ,py=" + p0.getY());
            line((Double) p0.getX(), (Double) p0.getY(), (Double) p1.getX(), (Double) p1.getY());
        }
    }

    private void drawTriangle() {
        clear();
        setPenRadius(0.005);
        setPenColor(Color.BLUE);
        setXscale(-2.0, 2.0);
        setYscale(-2.0, 2.0);
        for (int i = 0; i < dataList.size() - 1; i++) {
            Point p0 = dataList.get(i);
            Point p1 = dataList.get(i + 1);
            System.out.println("draw px=" + p0.getX() + " ,py=" + p0.getY());
            line((Double) p0.getX(), (Double) p0.getY(), (Double) p1.getX(), (Double) p1.getY());
        }
    }

    private void drawDragon() {
        clear();
        setPenRadius(0.005);
        setPenColor(Color.BLUE);
        setXscale(-1.0, 1.0);
        setYscale(-1.0, 1.0);
        for (int i = 0; i < dataList.size() - 1; i++) {
            Point p0 = dataList.get(i);
            Point p1 = dataList.get(i + 1);
            System.out.println("draw px=" + p0.getX() + " ,py=" + p0.getY());
            line((Double) p0.getX(), (Double) p0.getY(), (Double) p1.getX(), (Double) p1.getY());
        }
    }

    private void drawHilbert(int degree) {
        double s = 1.0 / degree;
        clear();
        setPenRadius(0.005);
        setPenColor(Color.BLUE);
        setXscale(-0.5, 1.5);
        setYscale(-0.5, 1.5);
        for (int i = 0; i < dataList.size() - 1; i++) {
            Point p0 = dataList.get(i);
            Point p1 = dataList.get(i + 1);
            System.out.println("draw px=" + (Integer) p0.getX() * s + " ,py=" + (Integer) p0.getY() * s);
            line(((Integer) p0.getX()).intValue() * s, (Integer) ((Integer) p0.getY()).intValue() * s,
                    ((Integer) p1.getX()).intValue() * s, ((Integer) p1.getY()).intValue() * s);
        }
    }

    @Override
    public void update(int degree) {
        dataList = model.getPoints();
        drawShape(model.getShape());
    }

    @Override
    public void update(KochType type) {
        dataList = model.getPoints();
        drawShape(model.getShape());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionName = e.getActionCommand();
        if (actionName != null && actionName.contains("Save")) {
            FileDialog chooser = new FileDialog(viewFrame, "Use a .png or .jpg extension", FileDialog.SAVE);
            chooser.setVisible(true);
            String filename = chooser.getFile();
            if (filename != null) {
                KochView.save(chooser.getDirectory() + File.separator + chooser.getFile());
            }
        }

    }


    /***************************************************************************
     *  Save drawing to a file.
     ***************************************************************************/

    /**
     * Saves the drawing to using the specified filename.
     * The supported image formats are JPEG and PNG;
     * the filename suffix must be {@code .jpg} or {@code .png}.
     *
     * @param filename the name of the file with one of the required suffixes
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public static void save(String filename) {
        validateNotNull(filename, "filename");
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);

        // png files
        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(onscreenImage, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // need to change from ARGB to RGB for JPEG
        else if ("jpg".equalsIgnoreCase(suffix)) {
            // Credit to arnabanimesh for simpler ARGB to RGB conversion
            BufferedImage rgbBuffer = new BufferedImage(2 * width, 2 * height, BufferedImage.TYPE_INT_RGB);
            Graphics2D rgb2d = rgbBuffer.createGraphics();
            rgb2d.drawImage(onscreenImage, 0, 0, null);
            rgb2d.dispose();
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid image file type: " + suffix);
        }
    }

    private static void validateNotNull(Object x, String name) {
        if (x == null) throw new IllegalArgumentException(name + " is null");
    }

    private static final double BORDER = 0.00;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;
    private static double xmin, ymin, xmax, ymax;

    // helper functions that scale from user coordinates to screen coordinates and back
    private static double scaleX(double x) {
        return width * (x - xmin) / (xmax - xmin);
    }

    private static double scaleY(double y) {
        return height * (ymax - y) / (ymax - ymin);
    }

    private static double factorX(double w) {
        return w * width / Math.abs(xmax - xmin);
    }

    private static double factorY(double h) {
        return h * height / Math.abs(ymax - ymin);
    }

    private static double userX(double x) {
        return xmin + x * (xmax - xmin) / width;
    }

    private static double userY(double y) {
        return ymax - y * (ymax - ymin) / height;
    }

    public static void setXscale() {
        setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
    }

    /**
     * Sets the <em>y</em>-scale to be the default (between 0.0 and 1.0).
     */
    public static void setYscale() {
        setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
    }

    public static void setXscale(double min, double max) {
        validate(min, "min");
        validate(max, "max");
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");

        xmin = min - BORDER * size;
        xmax = max + BORDER * size;

    }

    public static void setYscale(double min, double max) {
        validate(min, "min");
        validate(max, "max");
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");

        ymin = min - BORDER * size;
        ymax = max + BORDER * size;

    }

    public static void setScale(double min, double max) {
        validate(min, "min");
        validate(max, "max");
        double size = max - min;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");

        xmin = min - BORDER * size;
        xmax = max + BORDER * size;
        ymin = min - BORDER * size;
        ymax = max + BORDER * size;

    }

    private static void validate(double x, String name) {
        if (Double.isNaN(x)) throw new IllegalArgumentException(name + " is NaN");
        if (Double.isInfinite(x)) throw new IllegalArgumentException(name + " is infinite");
    }

    private static void validateNonnegative(double x, String name) {
        if (x < 0) throw new IllegalArgumentException(name + " negative");
    }

    public static void setPenRadius(double radius) {
        validate(radius, "pen radius");
        validateNonnegative(radius, "pen radius");

        penRadius = radius;
        float scaledPenRadius = (float) (radius * DEFAULT_SIZE);
        BasicStroke stroke = new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // BasicStroke stroke = new BasicStroke(scaledPenRadius);
        offscreen.setStroke(stroke);
    }

    public static void setPenColor(Color color) {
        validateNotNull(color, "color");
        penColor = color;
        offscreen.setColor(penColor);
    }

    public static void line(double x0, double y0, double x1, double y1) {
        validate(x0, "x0");
        validate(y0, "y0");
        validate(x1, "x1");
        validate(y1, "y1");
        offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    private static void draw() {
        if (!defer) show();
    }

    public static void show() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        viewFrame.repaint();
    }

    public void drawKoch() {
        clear();
        setPenRadius(0.005);
        setPenColor(Color.BLUE);
        setScale(-1.0, 1.0);

        for (int i = 0; i < dataList.size() - 1; i++) {
            Point p0 = dataList.get(i);
            Point p1 = dataList.get(i + 1);
            System.out.println("draw px=" + p0.getX() + " ,py=" + p0.getY());
            line((Double) p0.getX(), (Double) p0.getY(), (Double) p1.getX(), (Double) p1.getY());

        }
    }

    public static void clear() {
        clear(DEFAULT_CLEAR_COLOR);
    }

    public static void clear(Color color) {
        validateNotNull(color, "color");
        offscreen.setColor(color);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
        draw();
    }
}
