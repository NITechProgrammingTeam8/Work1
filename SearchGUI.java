import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SearchGUI extends JFrame {
    private Map<NodePanel, ArrayList<NodePanel>> links = new HashMap<>();
    private Point start;
    private Point end;
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
        for(Map.Entry<Node, NodePanel> entry : map.entrySet()) {
            NodePanel panel = entry.getValue();
            links.put(panel, new ArrayList<NodePanel>());
            ArrayList<NodePanel> values = links.get(panel);
            for(Node child : panel.getNode().getChildren()) {
                NodePanel childPanel = map.get(child);
                values.add(childPanel);
            }
        }
    }

    
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintArrows(g);
	}
    
	void paintArrows(Graphics g) {
        g.setColor(Color.BLUE);
        for(Map.Entry<NodePanel, ArrayList<NodePanel>> entry : links.entrySet()) {
            NodePanel key = entry.getKey();
            Rectangle source = key.getBounds();
            for(NodePanel child : entry.getValue()) {
                Rectangle distance = child.getBounds();
                setShortestDistance(source, distance);
                // SpinnerNumberModel model = new SpinnerNumberModel(key.getNode().getCost(child.getNode()), 0, 9999, 1);
                // JSpinner spinner = new JSpinner(model);
                // spinner.setPreferredSize(new Dimension(50, 25));
                g.drawLine(start.x, start.y, end.x, end.y);
            }
        }

    }
    
        void setShortestDistance(Rectangle source, Rectangle distance) {
        Point[] fromMidPoints = getMidPoints(source);
        Point[] toMidPoints = getMidPoints(distance);

        double min = Double.MAX_VALUE;
        for(int i = 0; i < 4; i++) {
            Point from = fromMidPoints[i].getLocation();
            for(int j = 0; j < 4; j++) {
                Point to = toMidPoints[j].getLocation();
                double value = (from.getX() - to.getX()) * (from.getX() - to.getX()) + (from.getY() - to.getY()) * (from.getY() - to.getY());
                if(value < min) {
                    min = value;
                    start = from;
                    end = to;
                }
            }
        }
    }

    Point[] getMidPoints(Rectangle r) {
        Point[] midPoints = new Point[4];
        for(int i = 0; i < midPoints.length; i++){
            midPoints[i] = new Point();
        }
            midPoints[0].setLocation(r.x + r.width / 2.0, r.y); // 上の中点
            midPoints[1].setLocation(r.x + r.width, r.y + r.height / 2.0); // 右の中点
            midPoints[2].setLocation(r.x + r.width / 2.0, r.y + r.height); // 下の中点
            midPoints[3].setLocation(r.x, r.y + r.height / 2.0); // 左の中点
        return midPoints;
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
        
        public Node getNode() {
            return node;
        }
    }

// class Arrow extends JPanel {
//     Point start;
//     Point end;
//     public Arrow (Map<JPanel, ArrayList<JPanel>> links) {
//         for(Map.Entry<JPanel, ArrayList<JPanel>> entry : links.entrySet()) {
//             System.out.println(entry.getKey().getBounds());
//         }
//     }

//     public Arrow () {
//     }

//     @Override
//     public void paintComponent(Graphics g){
//         super.paintComponent(g);
//         // paintLine(g);
//         g.setColor(Color.RED);
//         g.fillOval(50, 50, 100, 100);
//     }

//     void paintLine(Graphics g) {
//         g.setColor(Color.RED);
//         g.drawLine(start.x, start.y, end.x, end.y);
//     }

//     void setShortestDistance(Rectangle source, Rectangle distance) {
//         Point[] fromMidPoints = getMidPoints(source);
//         Point[] toMidPoints = getMidPoints(distance);

//         double min = Double.MAX_VALUE;
//         for(int i = 0; i < 4; i++) {
//             Point from = fromMidPoints[i].getLocation();
//             for(int j = 0; j < 4; j++) {
//                 Point to = toMidPoints[j].getLocation();
//                 double value = (from.getX() - to.getX()) * (from.getX() - to.getX()) + (from.getY() - to.getY()) * (from.getY() - to.getY());
//                 if(value < min) {
//                     min = value;
//                     start = from;
//                     end = to;
//                 }
//             }
//         }
//     }

//     Point[] getMidPoints(Rectangle r) {
//         Point[] midPoints = new Point[4];
//         for(int i = 0; i < midPoints.length; i++){
//             midPoints[i] = new Point();
//         }
//             midPoints[0].setLocation(r.x + r.width / 2.0, r.y); // 上の中点
//             midPoints[1].setLocation(r.x + r.width, r.y + r.height / 2.0); // 右の中点
//             midPoints[2].setLocation(r.x + r.width / 2.0, r.y + r.height); // 下の中点
//             midPoints[3].setLocation(r.x, r.y + r.height / 2.0); // 左の中点
//         return midPoints;
//     }
// }