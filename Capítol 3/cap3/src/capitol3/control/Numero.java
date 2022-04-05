package capitol3.control;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Numero {
    public static String suma(String num1, String num2) {
        // Ens asseguram que el segon nombre (num2) és el més gran dels dos
        if (num1.length() > num2.length()) {
            String t = num1;
            num1 = num2;
            num2 = t;
        }

        String res = "";
        int n1 = num1.length(), n2 = num2.length();
        int diff = n2 - n1;
        int carry = 0;

        // Recorrem des del final de cada string
        for (int i = n1 - 1; i >= 0; i--) {
            // Computam la suma
            int sum = ((int) (num1.charAt(i) - '0') + (int) (num2.charAt(i + diff) - '0') + carry);
            res += (char) (sum % 10 + '0');
            carry = sum / 10;
        }

        // Afegim els digits que queden del segon numero
        for (int i = n2 - n1 - 1; i >= 0; i--) {
            int sum = ((int) (num2.charAt(i) - '0') + carry);
            res += (char) (sum % 10 + '0');
            carry = sum / 10;
        }

        // Afegim el carry restant
        if (carry > 0) {
            res += (char) (carry + '0');
        }

        // Revertim la cadena de caràcters resultant
        return new StringBuilder(res).reverse().toString();
    }

    public static String producte(String str1, String str2) {
        String res = "0";

        int count = 0;
        for (int i = str2.length() - 1; i >= 0; i--) {
            int d2 = str2.charAt(i) - '0';

            int carry = 0;
            StringBuilder prod = new StringBuilder();
            for (int j = str1.length() - 1; j >= 0; j--) {
                int d1 = str1.charAt(j) - '0';
                int p = carry + (d1 * d2);
                prod.append(p % 10);
                carry = p / 10;
            }

            if (carry != 0) {
                prod.append(carry);
            }

            prod.reverse();

            for (int k = 0; k < count; k++) {
                prod.append(0);
            }

            res = suma(res, prod.toString());
            count++;
        }

        return res;
    }

    public static String[] xaparNumeros(String num1, String num2, int n) {
        String[] res = new String[4];
        int n1 = num1.length();
        int n2 = num2.length();
        num1 = posarZeros(num1, (2*n)-n1);
        num2 = posarZeros(num2, (2*n)-n2);
        res[0] = num1.substring(0, n);
        res[1] = num1.substring(n);
        res[2] = num2.substring(0, n);
        res[3] = num2.substring(n);

        return res;
    }

    public static String resta(String num1, String num2) {
        if (esMenor(num1, num2)) {
            String t = num1;
            num1 = num2;
            num2 = t;
        }
        String res = "";
        int n1 = num1.length(), n2 = num2.length();
        String str1 = new StringBuilder(num1).reverse().toString();
        String str2 = new StringBuilder(num2).reverse().toString();
        int carry = 0;

        for (int i = 0; i < n2; i++) {
            int sub = ((int) (str1.charAt(i) - '0') - (int) (str2.charAt(i) - '0') - carry);

            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else {
                carry = 0;
            }

            res += (char) (sub + '0');
        }

        for (int i = n2; i < n1; i++) {
            int sub = ((int) (str1.charAt(i) - '0') - carry);

            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else {
                carry = 0;
            }

            res += (char) (sub + '0');
        }

        return new StringBuilder(res).reverse().toString();
    }

    public static String potencia10(String num, int pot) {
        StringBuilder s = new StringBuilder(num);
        
        while(pot>0) {
            s = s.append("0");
            pot--;
        }
        
        return s.toString();
    }
    
    public static String posarZeros(String num, int zeros) {
        StringBuilder s = new StringBuilder(num).reverse();
        
        while(zeros>0) {
            s = s.append("0");
            zeros--;
        }
        
        return s.reverse().toString();
    }
    
    public static boolean esZero(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) != '0') return false;
        }
        
        return true;
    }

    private static boolean esMenor(String num1, String num2) {
        int n1 = num1.length(), n2 = num2.length();
        if (n1 < n2) {
            return true;
        }
        if (n2 < n1) {
            return false;
        }

        for (int i = 0; i < n1; i++) {
            if (num1.charAt(i) < num2.charAt(i)) {
                return true;
            } else if (num1.charAt(i) > num2.charAt(i)) {
                return false;
            }
        }

        return false;
    }
}
