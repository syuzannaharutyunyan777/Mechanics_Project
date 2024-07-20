import java.util.Stack;

public class SpringArray {

    public static Spring equivalentSpring(String springExpr) {
        Stack<Spring> stack = new Stack<>();
        int len = springExpr.length();
        for (int i = 0; i < len; i++) {
            char ch = springExpr.charAt(i);
            if (ch == '{') {
                stack.push(new Spring());
            } else if (ch == '[') {
                stack.push(null);
            } else if (ch == '}') {
                double k = 0;
                while (stack.peek() != null) {
                    k += stack.pop().getK();
                }
                stack.pop();
                stack.push(new Spring(k));
            } else if (ch == ']') {
                double k = 0;
                while (stack.peek() != null) {
                    k += 1 / stack.pop().getK();
                }
                stack.pop();
                stack.push(new Spring(1 / k));
            }
        }
        return stack.pop();
    }

    public static Spring equivalentSpring(String springExpr, Spring[] springs) {
        int len = springExpr.length();
        for (int i = 0; i < len; i++) {
            char ch = springExpr.charAt(i);
            if (ch == '{' || ch == '[') {
                continue;
            }
            if (ch == '}') {
                Spring s1 = springs[springs.length - 1];
                Spring s2 = springs[springs.length - 2];
                springs[springs.length - 2] = new Spring(s1.getK() + s2.getK());
                springs[springs.length - 1] = null;
                continue;
            }
            if (ch == ']') {
                double k = 0;
                int count = 0;
                for (int j = springs.length - 1; j >= 0; j--) {
                    if (springs[j] != null) {
                        k += 1 / springs[j].getK();
                        count++;
                    } else {
                        break;
                    }
                }
                Spring[] newSprings = new Spring[springs.length - count + 1];
                System.arraycopy(springs, 0, newSprings, 0, springs.length - count + 1);
                newSprings[newSprings.length - 1] = new Spring(1 / k);
                springs = newSprings;
            }
        }
        return equivalentSpring(springExpr);
    }

}
