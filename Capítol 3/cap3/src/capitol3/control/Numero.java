package capitol3.control;

/**
 * Classe que implementa la funcionalitat pròpia dels nombres pels càlculs que
 * facin falta fer per dur a terme el producte de grans nombres emprant Strings.
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
        int diff = n2 - n1; // diferència de llongitud entre els dos nombres
        int carry = 0;

        // Recorrem des del final de cada string
        for (int i = n1 - 1; i >= 0; i--) {
            // Computam la suma
            int sum = ((int) (num1.charAt(i) - '0') + (int) (num2.charAt(i + diff) - '0') + carry);
            res += (char) (sum % 10 + '0');
            carry = sum / 10;
        }

        // Afegim els dígits que queden del segon nombre
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

        // Recorrem el primer nombre de dreta a esquerra
        for (int i = str2.length() - 1; i >= 0; i--) {
            // Agafam el dígit actual del segon nombre
            int d2 = str2.charAt(i) - '0';

            int carry = 0;
            StringBuilder prod = new StringBuilder();
            // Recorrem el segon nombre de dreta a esquerra
            for (int j = str1.length() - 1; j >= 0; j--) {
                // Agafam el dígit actual del primer nombre
                int d1 = str1.charAt(j) - '0';

                // Multiplicam els dos dígits actuals (d1 i d2) i sumam la 
                // quantitat restant de l'operació anterior
                int p = carry + (d1 * d2);
                prod.append(p % 10);
                carry = p / 10; // actualitzam el carry per la següent operació
            }

            if (carry != 0) { // si el carry no és zero l'afegim al resultat
                prod.append(carry);
            }

            prod.reverse(); // invertim la cadena de caràcters

            for (int k = 0; k < count; k++) {
                prod.append(0); // afegim els zeros que pertoquin
            }

            res = suma(res, prod.toString()); // computam la suma del valor anterior amb el calculat en aquesta iteració
            count++;
        }

        return res;
    }

    public static String[] xaparNumeros(String num1, String num2, int n) {
        String[] res = new String[4];
        int n1 = num1.length(), n2 = num2.length();
        // Posam els zeros que facin falta davant perque tenguin la mateixa llargària
        num1 = posarZeros(num1, (2 * n) - n1, false);
        num2 = posarZeros(num2, (2 * n) - n2, false);
        // Separam els nombres en dues parts cadascun
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
        String res = "", str1 = new StringBuilder(num1).reverse().toString(), str2 = new StringBuilder(num2).reverse().toString();
        int n1 = num1.length(), n2 = num2.length(), carry = 0;

        for (int i = 0; i < n2; i++) {
            // Computam la resta dels dos dígits actuals
            int sub = ((int) (str1.charAt(i) - '0') - (int) (str2.charAt(i) - '0') - carry);

            if (sub < 0) { // si la resta dona negatiu (p.ex.: 2-6=-4)
                sub = sub + 10;
                carry = 1; // augmentam el carry
            } else {
                carry = 0; // sinó el reinicialitzam a 0
            }

            res += (char) (sub + '0'); // actualitzam el resultat
        }

        // Pels digits que hi hagi de diferència entre els dos nombres
        for (int i = n2; i < n1; i++) {
            // Afegim la resta amb el carry, si existeix
            int sub = ((int) (str1.charAt(i) - '0') - carry);

            if (sub < 0) { // si la resta dona negatiu (p.ex.: 2-6=-4)
                sub = sub + 10;
                carry = 1; // augmentam el carry
            } else {
                carry = 0; // sinó el reinicialitzam a 0
            }

            res += (char) (sub + '0'); // actualitzam el resultat
        }

        return new StringBuilder(res).reverse().toString();
    }

    public static String posarZeros(String num, int zeros, boolean darrera) {
        StringBuilder s = darrera ? new StringBuilder(num) : new StringBuilder(num).reverse();

        while (zeros > 0) {
            s = s.append("0");
            zeros--;
        }

        return darrera ? s.toString() : s.reverse().toString();
    }

    public static boolean esZero(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) != '0') {
                return false;
            }
        }

        return true;
    }

    private static boolean esMenor(String num1, String num2) {
        int n1 = num1.length(), n2 = num2.length();
        // si un dels dos és més llarg que l'altre és directe
        if (n1 < n2) {
            return true;
        }
        if (n2 < n1) {
            return false;
        }
        
        // si la llargària és igual, miram quin és el major (si una posició és igual es passa a la següent)
        for (int i = 0; i < n1; i++) {
            if (num1.charAt(i) < num2.charAt(i)) {
                return true;
            } else if (num1.charAt(i) > num2.charAt(i)) {
                return false;
            }
        }

        return false; // si els dos nombres són iguals es retorna fals per conveni
    }
}
