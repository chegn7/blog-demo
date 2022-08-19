package xyz.cheng7.blog.util;

class Solution {
    public String solveEquation(String equation) {
        equation = equation.replace("-", "+-");
        String [] ss = equation.split("=");
        String[] ls = ss[0].split("\\+"), rs = ss[1].split("\\+");
        int factor = 0, sum = 0;
        for (String l : ls) {
            if (l.length() == 0) continue;
            if (l.endsWith("x")) {
                factor += getFactor(l);
            } else  {
                sum -= Integer.parseInt(l);
            }
        }
        for (String r : rs) {
            if (r.length() == 0) continue;
            if (r.endsWith("x")) {
                factor -= getFactor(r);
            } else  {
                sum += Integer.parseInt(r);
            }
        }
        if (factor == 0) {
            if (sum == 0) return "Infinite solutions";
            else return "No solution";
        }
        return "x=" + String.valueOf(sum / factor);
    }

    int getFactor(String s) {
        s = s.replace("x", "");
        if (s.equals("")) return 1;
        else if (s.equals("-")) return -1;
        else return Integer.parseInt(s);
    }
}
