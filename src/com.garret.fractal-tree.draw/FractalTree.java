import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Contributors: Garret Tullio, Mason Hernandez
 */
public class FractalTree extends JPanel
{

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 800;
    private static final int ROOT_X = SCREEN_WIDTH/2 - 100;
    private static final int ROOT_Y = SCREEN_HEIGHT + 70;
    private static final int SIZE = 12;
    private static final double ANGLE = -90;

    private int size = 0;

    private ArrayList<TreeLine> lines = new ArrayList<>();

   public void paintComponent(Graphics g)
   {
       super.paintComponent(g);
       for (int i = 0; i < lines.size(); i++){
           TreeLine currentLine = lines.get(i);
           g.setColor(currentLine.size>7?Color.BLACK:Color.GREEN);
           g.drawLine(currentLine.x0, currentLine.y0, currentLine.x1, currentLine.y1);
       }
   }

    private void otherRecurseDraw(int x0, int y0, int size, double angle)
    {

        this.size = size;

        if (size == 0){
            return;
        } else {
            //make thread wait for animation effect
            try {
                Thread.sleep(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            int x1 = x0 + (int) (Math.cos(Math.toRadians(angle)) * size * 10);
            int y1 = y0 + (int) (Math.sin(Math.toRadians(angle)) * size * 10);

            lines.add(new TreeLine(x0, y0, x1, y1, size));
            repaint();
            otherRecurseDraw(x1, y1, size - 1, angle + 15);
            otherRecurseDraw(x1, y1, size - 1, angle - 15);
        }
    }


    private void drawTree()
    {
        while(true){
            otherRecurseDraw(ROOT_X, ROOT_Y, SIZE, ANGLE);
            removeLine();
        }
    }

    private void removeLine() {

        while(lines.size()>0){
            try {
                Thread.sleep(2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            lines.remove(lines.size()-1);
            repaint();
        }
    }

    private class TreeLine {
        int x0, y0, x1, y1, size;
        TreeLine(int x0, int y0, int x1, int y1, int size)
        {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            this.size = size;
        }
    }

    public static void main(String[] args){
           FractalTree tree = new FractalTree();
           JFrame frame = new JFrame();
           frame.setTitle("Fractal Tree Screen Saver");
           frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.add(tree);
           frame.setVisible(true);
           tree.drawTree();
    } 
}
