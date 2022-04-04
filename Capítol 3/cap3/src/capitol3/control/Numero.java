package capitol3.control;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Numero {

    private String num;

    public Numero(String n) {
        this.num = n;
    }

    public Numero suma(Numero num2) {
        String str1 = this.num;
        String str2 = num2.getNum();

        // Ens asseguram que el segon nombre (str2) és el més gran dels dos
        if (str1.length() > str2.length()) {
            String t = str1;
            str1 = str2;
            str2 = t;
        }

        String res = "";
        int n1 = str1.length(), n2 = str2.length();
        int diff = n2 - n1;
        int carry = 0;

        // Recorrem des del final de cada string
        for (int i = n1 - 1; i >= 0; i--) {
            // Computam la suma
            int sum = ((int) (str1.charAt(i) - '0') + (int) (str2.charAt(i + diff) - '0') + carry);
            res += (char) (sum % 10 + '0');
            carry = sum / 10;
        }

        // Afegim els digits que queden del segon numero
        for (int i = n2 - n1 - 1; i >= 0; i--) {
            int sum = ((int) (str2.charAt(i) - '0') + carry);
            res += (char) (sum % 10 + '0');
            carry = sum / 10;
        }

        // Afegim el carry restant
        if (carry > 0) {
            res += (char) (carry + '0');
        }

        // Revertim la cadena de caràcters resultant
        return new Numero(new StringBuilder(res).reverse().toString());
    }

    public Numero producte(Numero num2) {
        int len1 = this.num.length();
        int len2 = num2.getNum().length();
        if (len1 == 0 || len2 == 0) {
            return new Numero("0");
        }

        // will keep the res number in vector
        // in reverse order
        int result[] = new int[len1 + len2];

        // Below two indexes are used to
        // find positions in res.
        int i_n1 = 0;
        int i_n2 = 0;

        // Go from right to left in num1
        for (int i = len1 - 1; i >= 0; i--) {
            int carry = 0;
            int n1 = this.num.charAt(i) - '0';

            // To shift position to left after every
            // multipliccharAtion of a digit in num2
            i_n2 = 0;

            // Go from right to left in num2            
            for (int j = len2 - 1; j >= 0; j--) {
                // Take current digit of second number
                int n2 = num2.getNum().charAt(j) - '0';

                // Multiply with current digit of first number
                // and add res to previously stored res
                // charAt current position.
                int sum = n1 * n2 + result[i_n1 + i_n2] + carry;

                // Carry for next itercharAtion
                carry = sum / 10;

                // Store res
                result[i_n1 + i_n2] = sum % 10;

                i_n2++;
            }

            // store carry in next cell
            if (carry > 0) {
                result[i_n1 + i_n2] += carry;
            }

            // To shift position to left after every
            // multipliccharAtion of a digit in num1.
            i_n1++;
        }

        // ignore '0'res from the right
        int i = result.length - 1;
        while (i >= 0 && result[i] == 0) {
            i--;
        }

        // If all were '0'res - means either both
        // or one of num1 or num2 were '0'
        if (i == -1) {
            return new Numero("0");
        }

        String res = "";
        while (i >= 0) {
            res += (result[i--]);
        }

        return new Numero(res);
    }

    public Numero[] xaparNumero() {
        Numero[] res = new Numero[2];
        String t = this.num;
        int n = this.num.length();
        
        if (n%2 == 0) {
            res[0] = new Numero(t.substring(0, n/2));
        } else {
            String zero = "0";
            res[0] = new Numero(zero.concat(t.substring(0, n/2)));
        }
        res[1] = new Numero(t.substring(n));
        
        return res;
    }

    public Numero resta(Numero num2) {
        if (esMenor(num2)) {
            String t = this.num;
            this.setNum(num2.getNum());
            num2.setNum(t);
        }

        // Take an empty string for storing result
        String str = "";

        // Calculate length of both string
        int n1 = this.num.length(), n2 = num2.getNum().length();

        // Reverse both of strings
        String str1 = new StringBuilder(this.num).reverse().toString();
        String str2 = new StringBuilder(num2.getNum()).reverse().toString();

        int carry = 0;

        // Run loop till small string length
        // and subtract digit of str1 to str2
        for (int i = 0; i < n2; i++) {
            // Do school mathematics, compute difference of
            // current digits
            int sub
                    = ((int) (str1.charAt(i) - '0')
                    - (int) (str2.charAt(i) - '0') - carry);

            // If subtraction is less then zero
            // we add then we add 10 into sub and
            // take carry as 1 for calculating next step
            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else {
                carry = 0;
            }

            str += (char) (sub + '0');
        }

        // subtract remaining digits of larger number
        for (int i = n2; i < n1; i++) {
            int sub = ((int) (str1.charAt(i) - '0') - carry);

            // if the sub value is -ve, then make it
            // positive
            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else {
                carry = 0;
            }

            str += (char) (sub + '0');
        }

        return new Numero(new StringBuilder(str).reverse().toString());
    }
    
    public Numero potencia10(int pot) {
        Numero res = new Numero(this.getNum());
        
        if (pot == 0) {
            return new Numero("1");
        } else if (pot == 1) {
            return res;
        }
        
        while (pot != 1) {
            res.setNum(res.getNum()+'0');
            pot--;
        }
        
        return res;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String n) {
        this.num = n;
    }

    private boolean esMenor(Numero num2) {
        int n1 = this.num.length(), n2 = num2.getNum().length();
        if (n1 < n2) {
            return true;
        }
        if (n2 < n1) {
            return false;
        }

        for (int i = 0; i < n1; i++) {
            if (this.num.charAt(i) < num2.getNum().charAt(i)) {
                return true;
            } else if (this.num.charAt(i) > num2.getNum().charAt(i)) {
                return false;
            }
        }

        return false;
    }
}
