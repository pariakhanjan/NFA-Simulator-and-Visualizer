import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Polygon;

public class NFA extends JComponent {
    private final ArrayList<Character> alphabet;
    private final ArrayList<String> states;
    private final String initialState;
    private final ArrayList<String> finalStates;
    private final Map<String, Map<Character, ArrayList<String>>> transitions;
    private Map<String, Float> statesPositions = new HashMap<>();

    public NFA(ArrayList<Character> alphabet, ArrayList<String> states, String initialState,
               ArrayList<String> finalStates, Map<String, Map<Character, ArrayList<String>>> transitions) {
        this.alphabet = new ArrayList<>(alphabet);
        this.states = new ArrayList<>(states);
        this.initialState = initialState;
        this.finalStates = new ArrayList<>(finalStates);
        this.transitions = new HashMap<>(transitions);
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 450, 450);
        window.getContentPane().add(this);
        window.setVisible(true);
    }

    void acceptString(String input) {
        if (input.equals("") || input.equals(" ")) {
            input = "ε";
        }
        if (checkTransitions(initialState, 0, input, false)) {
            System.out.println("This NFA accepts this string.\n" + input + " ∈ language");
        } else {
            System.out.println("This NFA doesn't accept this string.\n" + input + " ∉ language");
        }
    }

    boolean checkTransitions(String currState, int charIndex, String input, boolean finish) {
        if (charIndex == input.length()) {
            if (finalStates.contains(currState)) {
                finish = true;
            }
            return finish;
        }
        if (transitions.containsKey(currState)) {
            if (transitions.get(currState).containsKey('ε')) {
                for (int i = 0; i < transitions.get(currState).get('ε').size() && !finish; i++) {
                    finish = checkTransitions(transitions.get(currState).get('ε').get(i), charIndex, input, false);
                }
            } else if (transitions.get(currState).containsKey('λ')) {
                for (int i = 0; i < transitions.get(currState).get('λ').size() && !finish; i++) {
                    finish = checkTransitions(transitions.get(currState).get('λ').get(i), charIndex, input, false);
                }
            }
            if (transitions.get(currState).containsKey(input.charAt(charIndex))) {
                for (int i = 0; i < transitions.get(currState).get(input.charAt(charIndex)).size() && !finish; i++) {
                    finish = checkTransitions(transitions.get(currState).get(input.charAt(charIndex)).get(i), charIndex + 1, input, false);
                }
            }
        }
        return finish;
    }

    public void paint(Graphics g) {
        Ellipse2D circle = new Ellipse2D.Double();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        for (int i = 0; i < states.size(); i++) {
            circle.setFrameFromCenter(100 + i * 100, 250, 125 + i * 100, 275);
            g2.draw(circle);
            g.drawString(states.get(i), 95 + i * 100, 250);
            statesPositions.put(states.get(i), (float) 100 + i * 100);
            if (finalStates.contains(states.get(i))) {
                circle.setFrameFromCenter(100 + i * 100, 250, 120 + i * 100, 270);
                g2.draw(circle);
            }
            if (states.get(i).equals(initialState)) {
                Line2D line = new Line2D.Float(50 + i * 100, 250, 75 + i * 100, 250);
                g2.draw(line);
                g2.drawString("start", 25 + i*100 ,250);
                Polygon triangle = new Polygon(new int[]{65 + i * 100, 65 + i * 100, 75 + i * 100},
                        new int[]{240, 260, 250}, 3);
                g2.drawPolygon(triangle);
            }
        }
        int num = 0;
        for (String s : transitions.keySet()) {
            for (Character c : transitions.get(s).keySet()) {
                for (int i = 0; i < transitions.get(s).get(c).size(); i++) {
                    float endPosition = statesPositions.get(transitions.get(s).get(c).get(i));
                    Line2D line = new Line2D.Float(statesPositions.get(s), 225,
                            (statesPositions.get(s) + endPosition) / 2, 200 - 5 * num);
                    g2.draw(line);
                    Line2D line2 = new Line2D.Float((statesPositions.get(s) + endPosition) / 2,
                            200 - 5 * num, endPosition, 225);
                    g2.draw(line2);
                    Polygon triangle = new Polygon(new int[]{(int) endPosition + 10, (int) endPosition,
                            (int) endPosition - 10}, new int[]{215, 225, 215}, 3);
                    g2.drawPolygon(triangle);
                    g.drawString(String.valueOf(c),
                            (int)((statesPositions.get(s) + endPosition) / 2) + 5 ,200 - 5 * num);
                    num++;
                }
            }
        }
    }
}