import org.json.JSONArray;
import org.json.JSONObject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PolynomialSolver {

    public static void main(String[] args) {
        String jsonInput = """
                {
                "keys": {
                    "n": 10,
                    "k": 7
                  },
                  "1": {
                    "base": "6",
                    "value": "13444211440455345511"
                  },
                  "2": {
                    "base": "15",
                    "value": "aed7015a346d635"
                  },
                  "3": {
                    "base": "15",
                    "value": "6aeeb69631c227c"
                  },
                  "4": {
                    "base": "16",
                    "value": "e1b5e05623d881f"
                  },
                  "5": {
                    "base": "8",
                    "value": "316034514573652620673"
                  },
                  "6": {
                    "base": "3",
                    "value": "2122212201122002221120200210011020220200"
                  },
                  "7": {
                    "base": "3",
                    "value": "20120221122211000100210021102001201112121"
                  },
                  "8": {
                    "base": "6",
                    "value": "20220554335330240002224253"
                  },
                  "9": {
                    "base": "12",
                    "value": "45153788322a1255483"
                  },
                  "10": {
                    "base": "7",
                    "value": "1101613130313526312514143"
                  }
                }
                """;


        List<BigInteger> roots = new ArrayList<>();
        JSONObject obj = new JSONObject(jsonInput);


        JSONObject keys = obj.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");
        System.out.println("Metadata: n=" + n + ", k=" + k);


        for (String key : obj.keySet()) {
            if (key.equals("keys")) continue;

            JSONObject rootData = obj.getJSONObject(key);

            String baseStr = rootData.getString("base");
            String valueStr = rootData.getString("value");
            int base = Integer.parseInt(baseStr);

            BigInteger root = new BigInteger(valueStr, base);
            roots.add(root);
        }

        System.out.println("Decoded Roots: " + roots);


        if (roots.isEmpty()) {
            System.out.println("[]");
            return;
        }

        List<BigInteger> polyCoeffs = new ArrayList<>();
        polyCoeffs.add(BigInteger.ONE);
        polyCoeffs.add(roots.get(0).negate());


        for (int i = 1; i < roots.size(); i++) {
            BigInteger r = roots.get(i);


            List<BigInteger> newCoeffs = new ArrayList<>();
            for (int j = 0; j < polyCoeffs.size() + 1; j++) {
                newCoeffs.add(BigInteger.ZERO);
            }


            for (int j = 0; j < polyCoeffs.size(); j++) {

                newCoeffs.set(j, newCoeffs.get(j).add(polyCoeffs.get(j)));

                newCoeffs.set(j + 1, newCoeffs.get(j + 1).add(polyCoeffs.get(j).negate().multiply(r)));
            }

            polyCoeffs = newCoeffs;
        }


        System.out.println("Final Coefficients: " + polyCoeffs);


        JSONArray finalOutput = new JSONArray();
        for (BigInteger coeff : polyCoeffs) {
            finalOutput.put(coeff.toString());
        }
        System.out.println("Formatted Output for Submission:");
        System.out.println(finalOutput.toString());
    }
}
