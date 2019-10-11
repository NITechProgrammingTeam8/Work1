import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SearchGUI extends JFrame {

    public static void main(String args[]) {
        SearchGUI frame = new SearchGUI("探索");
        frame.setVisible(true);
    }

    SearchGUI(String title) {
        setTitle(title);
        int appWidth = 1000;
        int appHeight = 700;
        setBounds(100, 100, appWidth, appHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(appWidth, appHeight));
        mainPanel.setLayout(layout);

        Search search = new Search();
        Map<Node, NodePanel> map = new HashMap<>();
        Node[] node = search.getNode();
        for (int i = 0; i < 10; i++) {
            map.put(node[i], new NodePanel(node[i]));
        }
        mainPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

        layout.putConstraint(SpringLayout.NORTH, map.get(node[0]), 300, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, map.get(node[0]), 100, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[1]), -25, SpringLayout.NORTH, map.get(node[0]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[1]), 50, SpringLayout.EAST, map.get(node[0]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[2]), 0, SpringLayout.NORTH, map.get(node[1]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[2]), 50, SpringLayout.EAST, map.get(node[1]));
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[7]), 0, SpringLayout.NORTH, map.get(node[2]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[7]), 50, SpringLayout.EAST, map.get(node[2]));
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[9]), -25, SpringLayout.NORTH, map.get(node[7]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[9]), 50, SpringLayout.EAST, map.get(node[7]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[4]), 0, SpringLayout.NORTH, map.get(node[7]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[4]), 50, SpringLayout.EAST, map.get(node[9]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[5]), 100, SpringLayout.SOUTH, map.get(node[0]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[5]), 75, SpringLayout.WEST, map.get(node[0]));
        layout.putConstraint(SpringLayout.SOUTH, map.get(node[6]), -50, SpringLayout.NORTH, map.get(node[5]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[6]), 50, SpringLayout.EAST, map.get(node[5]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[3]), 25, SpringLayout.SOUTH, map.get(node[6]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[3]), 50, SpringLayout.EAST, map.get(node[6]));
        layout.putConstraint(SpringLayout.NORTH, map.get(node[8]), 0, SpringLayout.NORTH, map.get(node[3]));
        layout.putConstraint(SpringLayout.WEST, map.get(node[8]), 50, SpringLayout.EAST, map.get(node[3]));

        for (int i = 0; i < 10; i++) {
            mainPanel.add(map.get(node[i]));
        }

        Container contentPane = getContentPane();
        contentPane.add(mainPanel);

        pack();
        for (Map.Entry<Node, NodePanel> entry : map.entrySet()) {
            NodePanel panel = entry.getValue();
            for (Node child : panel.getNode().getChildren()) {
                NodePanel childPanel = map.get(child);

                PathPanel path = new PathPanel(panel, childPanel);
                int left = path.getStart().x;
                int right = path.getEnd().x;
                int top = path.getStart().y;
                int bottom = path.getEnd().y;
                if(path.getStart().x > path.getEnd().x) {
                    int tmp = left;
                    left = right;
                    right = tmp;
                }
                if(path.getStart().y > path.getEnd().y) {
                    int tmp = top;
                    top = bottom;
                    bottom = tmp;
                }
                layout.putConstraint(SpringLayout.WEST, path, left, SpringLayout.WEST, mainPanel);
                layout.putConstraint(SpringLayout.EAST, path, right, SpringLayout.WEST, mainPanel);
                layout.putConstraint(SpringLayout.NORTH, path, top, SpringLayout.NORTH, mainPanel);
                layout.putConstraint(SpringLayout.SOUTH, path, bottom, SpringLayout.NORTH, mainPanel);
                mainPanel.add(path);
            }
        }
    }

    
}

class NodePanel extends JPanel {
    private Node node;
    ArrayList<NodePanel> children;

    NodePanel(Node node) {
        this.node = node;
        setLayout(new GridLayout(2, 1));
        setBackground(Color.ORANGE);
        setBorder(new BevelBorder(BevelBorder.RAISED));

        JLabel label = new JLabel(node.getName());
        SpinnerNumberModel model = new SpinnerNumberModel(node.getHValue(), 0, 9999, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(50, 25));

        add(label);
        add(spinner);
    }

    Node getNode() {
        return node;
    }
}

class PathPanel extends JPanel {
    private Point start;
    private Point end;

    PathPanel(NodePanel par, NodePanel child) {
        setOpaque(false);
        Rectangle source = par.getBounds();
        Rectangle distance = child.getBounds();
        setShortestDistance(source, distance);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintArrows(g);
    }

    void paintArrows(Graphics g) {
        g.setColor(Color.BLUE);
        // SpinnerNumberModel model = new
        // SpinnerNumberModel(key.getNode().getCost(child.getNode()), 0, 9999, 1);
        // JSpinner spinner = new JSpinner(model);
        // spinner.setPreferredSize(new Dimension(50, 25));
        int fromX = start.x;
        int fromY = start.y;
        int toX = end.x;
        int toY = end.y;

        int constX = (fromX < toX ? fromX : toX);
        int constY = (fromY < toY ? fromY : toY);
        g.drawLine(fromX - constX, fromY - constY, toX - constX, toY - constY);
        
        double[] fromDouble = new double[] {fromX, fromY};
        double[] toDouble = new double[] {toX, toY};
        double angle = StepUtils.getAngle( fromDouble, toDouble );
        double oneAngle = angle + 150.0;
        double anotherAngle = angle - 150.0;
        double pitch = 10.0;
        g.drawLine( ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian( oneAngle ) ) ) - constX,
                    ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( oneAngle ) ) ) - constY,
                    toX - constX, toY - constY );
        g.drawLine( ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian( anotherAngle ) ) ) - constX,
                    ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( anotherAngle ) ) ) - constY,
                    toX - constX, toY - constY );
        g.drawLine( ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian( oneAngle ) ) ) - constX,
                    ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( oneAngle ) ) ) - constY,
                    ( int ) ( toX + pitch * Math.cos( StepUtils.degreeToRadian( anotherAngle ) ) ) - constX,
                    ( int ) ( toY + pitch * Math.sin( StepUtils.degreeToRadian( anotherAngle ) ) ) - constY);
    }

    void setShortestDistance(Rectangle source, Rectangle distance) {
        Point[] fromMidPoints = getMidPoints(source);
        Point[] toMidPoints = getMidPoints(distance);

        double min = Double.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            Point from = fromMidPoints[i].getLocation();
            for (int j = 0; j < 4; j++) {
                Point to = toMidPoints[j].getLocation();
                double value = (from.getX() - to.getX()) * (from.getX() - to.getX())
                        + (from.getY() - to.getY()) * (from.getY() - to.getY());
                if (value < min) {
                    min = value;
                    start = from;
                    end = to;
                }
            }
        }
    }

    Point[] getMidPoints(Rectangle r) {
        Point[] midPoints = new Point[4];
        for (int i = 0; i < midPoints.length; i++) {
            midPoints[i] = new Point();
        }
        midPoints[0].setLocation(r.x + r.width / 2.0, r.y); // 上の中点
        midPoints[1].setLocation(r.x + r.width, r.y + r.height / 2.0); // 右の中点
        midPoints[2].setLocation(r.x + r.width / 2.0, r.y + r.height); // 下の中点
        midPoints[3].setLocation(r.x, r.y + r.height / 2.0); // 左の中点
        return midPoints;
    }

    Point getStart() {
        return start;
    }

    Point getEnd() {
        return end;
    }
}

class StepUtils {
    static double getAngle( double[] from , double[] to ) {
        double xDiff = to[ 0 ] - from[ 0 ];
        double yDiff = to[ 1 ] - from[ 1 ];

        // 【注意】
        // DEGREE(度)で返す -- atan(傾き)だと傾きの正負を意識する必要がでてくる
        //
        return Math.atan2( yDiff, xDiff ) * 180.0 / Math.PI;
    }

    /** 角度をラジアンに変換して返す */
    static double degreeToRadian( double angle ) {

        return angle * Math.PI / 180.0;
    }
}