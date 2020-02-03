package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableNumberValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Util {

    private static int getPrecision(char c) {
        switch (c) {
            case 'm':
            case 'M':
                return 8;
            case '*':
            case '/':
                return 6;
            case '+':
            case '-':
                return 4;
        }
        return -1;
    }

    public static Object multiply(Object a, Object b) throws RuntimeException {
        if (a instanceof Integer && b instanceof Integer) return Double.valueOf(String.valueOf(a)) *
                Double.valueOf(String.valueOf(b));
        if (a instanceof Double && b instanceof Double) return Double.valueOf(String.valueOf(a)) *
                Double.valueOf(String.valueOf(b));
        if (a instanceof Float && b instanceof Float) return Double.valueOf(String.valueOf(a)) *
                Double.valueOf(String.valueOf(b));

        if (a instanceof Integer) return Bindings.multiply(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b)
                ;
        if (b instanceof Integer)
            return Bindings.multiply((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Double) return Bindings.multiply(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b)
                ;
        if (b instanceof Double) return Bindings.multiply((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Float) return Bindings.multiply(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Float) return Bindings.multiply((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        return Bindings.multiply((ObservableNumberValue) a, (ObservableNumberValue) b);
    }

    public static Object divide(Object a, Object b) throws RuntimeException {
        if (a instanceof Integer && b instanceof Integer)
            return Double.valueOf(String.valueOf(a)) / Double.valueOf(String.valueOf(b));
        if (a instanceof Double && b instanceof Double)
            return Double.valueOf(String.valueOf(a)) / Double.valueOf(String.valueOf(b));
        if (a instanceof Float && b instanceof Float)
            return Double.valueOf(String.valueOf(a)) / Double.valueOf(String.valueOf(b));

        if (a instanceof Integer) return Bindings.divide(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Integer) return Bindings.divide((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Double) return Bindings.divide(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Double) return Bindings.divide((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Float) return Bindings.divide(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Float) return Bindings.divide((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        return Bindings.divide((ObservableNumberValue) a, (ObservableNumberValue) b);
    }

    public static Object add(Object a, Object b) throws RuntimeException {
        if (a instanceof Integer && b instanceof Integer)
            return Double.valueOf(String.valueOf(a)) + Double.valueOf(String.valueOf(b));
        if (a instanceof Double && b instanceof Double)
            return Double.valueOf(String.valueOf(a)) + Double.valueOf(String.valueOf(b));
        if (a instanceof Float && b instanceof Float)
            return Double.valueOf(String.valueOf(a)) + Double.valueOf(String.valueOf(b));

        if (a instanceof Integer) return Bindings.add(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Integer) return Bindings.add((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Double) return Bindings.add(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Double) return Bindings.add((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Float) return Bindings.add(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Float) return Bindings.add((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        return Bindings.add((ObservableNumberValue) a, (ObservableNumberValue) b);
    }

    public static Object sub(Object a, Object b) throws RuntimeException {
        if (a instanceof Integer && b instanceof Integer)
            return Double.valueOf(String.valueOf(a)) - Double.valueOf(String.valueOf(b));
        if (a instanceof Double && b instanceof Double)
            return Double.valueOf(String.valueOf(a)) - Double.valueOf(String.valueOf(b));
        if (a instanceof Float && b instanceof Float)
            return Double.valueOf(String.valueOf(a)) - Double.valueOf(String.valueOf(b));

        if (a instanceof Integer)
            return Bindings.subtract(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Integer)
            return Bindings.subtract((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Double) return Bindings.subtract(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Double) return Bindings.subtract((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Float) return Bindings.subtract(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Float) return Bindings.subtract((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        return Bindings.subtract((ObservableNumberValue) a, (ObservableNumberValue) b);
    }

    public static Object max(Object a, Object b) throws RuntimeException {
        if (a instanceof Integer && b instanceof Integer)
            return Math.max(Double.valueOf(String.valueOf(a)), Double.valueOf(String.valueOf(b)));
        if (a instanceof Double && b instanceof Double)
            return Math.max(Double.valueOf(String.valueOf(a)), Double.valueOf(String.valueOf(b)));
        if (a instanceof Float && b instanceof Float)
            return Math.max(Double.valueOf(String.valueOf(a)), Double.valueOf(String.valueOf(b)));

        if (a instanceof Integer) return Bindings.max(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Integer) return Bindings.max((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Double) return Bindings.max(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Double) return Bindings.max((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Float) return Bindings.max(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Float) return Bindings.max((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        return Bindings.max((ObservableNumberValue) a, (ObservableNumberValue) b);
    }

    public static Object min(Object a, Object b) throws RuntimeException {
        if (a instanceof Integer && b instanceof Integer)
            return Math.min(Double.valueOf(String.valueOf(a)), Double.valueOf(String.valueOf(b)));
        if (a instanceof Double && b instanceof Double)
            return Math.min(Double.valueOf(String.valueOf(a)), Double.valueOf(String.valueOf(b)));
        if (a instanceof Float && b instanceof Float)
            return Math.min(Double.valueOf(String.valueOf(a)), Double.valueOf(String.valueOf(b)));

        if (a instanceof Integer) return Bindings.min(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Integer) return Bindings.min((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Double) return Bindings.min(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Double) return Bindings.min((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        if (a instanceof Float) return Bindings.min(Double.valueOf(String.valueOf(a)), (ObservableNumberValue) b);
        if (b instanceof Float) return Bindings.min((ObservableNumberValue) a, Double.valueOf(String.valueOf(b)));

        return Bindings.min((ObservableNumberValue) a, (ObservableNumberValue) b);
    }

    private static Object calc(char opertor, Object a, Object b) throws RuntimeException {
        switch (opertor) {
            case '*':
                return Util.multiply(a, b);
            case '/':
                return Util.divide(a, b);
            case '+':
                return Util.add(a, b);
            case '-':
                return Util.sub(a, b);
            case 'm':
                return Util.min(a, b);
            case 'M':
                return Util.max(a, b);
        }
        throw new RuntimeException();
    }

    public static Object bind(String format, Object... arr) throws RuntimeException {
        Stack<Character> operatorStack = new Stack<>();
        Stack<Integer> countStack = new Stack<>();

        ArrayList<String> postfix = new ArrayList<>();
        ArrayList<Object> arguments = new ArrayList<>(Arrays.asList(arr));
        Iterator<Object> iter = arguments.iterator();

        countStack.push(1);

        StringBuilder number = null;
        boolean numberSign = true;

        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);
            switch (c) {
                case ' ':
                    if (number != null) {
                        postfix.add("$" + (numberSign ? "" : "-") + number);
                        number = null;
                        numberSign = true;
                    }
                    break;
                case '~':
                    numberSign = false;
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                case '.':
                    if (number == null)
                        number = new StringBuilder();
                    number.append(c);
                    break;
                case '$':
                    postfix.add("$");
                    break;
                case '*':
                case '/':
                case '+':
                case '-':
                case 'm':
                    if (number != null) {
                        postfix.add("$" + (numberSign ? "" : "-") + number);
                        number = null;
                        numberSign = true;
                    }
                    if (format.charAt(i) == 'm') {
                        if (format.charAt(i + 1) == 'a' && format.charAt(i + 2) == 'x') c = 'M';
                        else if (format.charAt(i + 1) == 'i' && format.charAt(i + 2) == 'n') c = 'm';
                        else
                            throw new RuntimeException("Not Supported Character " + format.charAt(i + 1) + " was in the input");
                        i += 2;
                    }
                    int p = Util.getPrecision(c);

                    while (!operatorStack.empty() && Util.getPrecision(operatorStack.peek()) >= p)
                        postfix.add(operatorStack.pop() + "");
                    operatorStack.push(c);
                    break;
                case '(':
                    if (number != null) {
                        postfix.add("$" + (numberSign ? "" : "-") + number);
                        number = null;
                        numberSign = true;
                    }
                    operatorStack.push(c);
                    countStack.push(1);
                    break;
                case ')':
                case ',':
                    if (number != null) {
                        postfix.add("$" + (numberSign ? "" : "-") + number);
                        number = null;
                        numberSign = true;
                    }
                    while (operatorStack.peek() != '(') {
                        postfix.add(operatorStack.pop() + "");
                    }
                    if (c == ')') {
                        operatorStack.pop();
                        boolean flag = false;
                        if (!operatorStack.empty() && (operatorStack.peek() == 'm' || operatorStack.peek() == 'M')) {
                            postfix.add(operatorStack.pop() + "" + countStack.peek());
                            flag = true;
                        }
                        if (!flag && countStack.peek() != 1)
                            throw new RuntimeException("Syntax Error at " + i);
                        countStack.pop();
                    } else
                        countStack.push(countStack.pop() + 1);
                    break;
                default:
                    throw new RuntimeException("Syntax Error at " + i);
            }
        }

        if (number != null)
            postfix.add("$" + (numberSign ? "" : "-") + number);

        while (!operatorStack.empty())
            postfix.add(operatorStack.pop() + "");

        Stack<Object> calculatingStack = new Stack<>();

        for (String s : postfix) {
            if (s.equals("$")) {
                calculatingStack.push(iter.next());
                continue;
            }

            if (s.charAt(0) == '$') {
                calculatingStack.push(Double.valueOf(s.substring(1)));
                continue;
            }

            if (s.charAt(0) == 'm' || s.charAt(0) == 'M') {
                int t = Integer.valueOf(s.substring(1));
                for (int j = 1; j < t; j++) {
                    Object b = calculatingStack.pop(), a = calculatingStack.pop();
                    calculatingStack.push(calc(s.charAt(0), a, b));
                }
                continue;
            }

            Object b = calculatingStack.pop(), a = calculatingStack.pop();
            calculatingStack.push(calc(s.charAt(0), a, b));
        }

        if (calculatingStack.size() != 1) throw new RuntimeException("Stack size is not 1");

        return calculatingStack.pop();
    }

    public static double smoothAnimation(int total, int now, boolean startEase, boolean endEase) {
        if (!startEase && !endEase) return (double) now / (double) total;
        if (startEase && !endEase)
            return 1d - 1.00469971902111342130248353342d * Util.math.erf(2d - (double) now / (double) total * 2d);
        if (!startEase)
            return 1.00469971902111342130248353342d * Util.math.erf((double) now / (double) total * 2d);
        return 0.502349859510556710651241766710d * Util.math.erf((double) now / (double) total * 4d - 2d) + 0.5d;
    }

    public static double map(double value, double fromS, double toS, double fromE, double toE) {
        return (value - fromS) / (toS - fromS) * (toE - fromE) + fromE;
    }

    public static class math {
        /******************************************************************************
         *  Compilation:  javac ErrorFunction.java
         *  Execution:    java ErrorFunction z
         *
         *  Implements the Gauss error function.
         *
         *              erf(z) = 2 / sqrt(pi) * integral(exp(-t*t), t = 0..z)
         *
         *
         *  % java ErrorFunction 1.0
         *  erf(1.0) = 0.8427007877600067         // actual = 0.84270079294971486934
         *  Phi(1.0) = 0.8413447386043253         // actual = 0.8413447460
         *
         *
         *  % java ErrorFunction -1.0
         *  erf(-1.0) = -0.8427007877600068
         *  Phi(-1.0) = 0.15865526139567465
         *
         *  % java ErrorFunction 3.0
         *  erf(3.0) = 0.9999779095015785         // actual = 0.99997790950300141456
         *  Phi(3.0) = 0.9986501019267444
         *
         *  % java ErrorFunction 30
         *  erf(30.0) = 1.0
         *  Phi(30.0) = 1.0
         *
         *  % java ErrorFunction -30
         *  erf(-30.0) = -1.0
         *  Phi(-30.0) = 0.0
         *
         *  % java ErrorFunction 1E-20
         *  erf(1.0E-20)  = -3.0000000483809686E-8     // true anser 1.13E-20
         *  Phi(1.0E-20)  = 0.49999998499999976
         *
         *
         ******************************************************************************/

        // fractional error in math formula less than 1.2 * 10 ^ -7.
        // although subject to catastrophic cancellation when z in very close to 0
        // from Chebyshev fitting formula for erf(z) from Numerical Recipes, 6.2
        public static double erf(double z) {
            double t = 1.0 / (1.0 + 0.5 * java.lang.Math.abs(z));

            // use Horner's method
            double ans = 1 - t * java.lang.Math.exp(-z * z - 1.26551223 +
                    t * (1.00002368 +
                            t * (0.37409196 +
                                    t * (0.09678418 +
                                            t * (-0.18628806 +
                                                    t * (0.27886807 +
                                                            t * (-1.13520398 +
                                                                    t * (1.48851587 +
                                                                            t * (-0.82215223 +
                                                                                    t * (0.17087277))))))))));
            if (z >= 0) return ans;
            else return -ans;
        }
    }
}