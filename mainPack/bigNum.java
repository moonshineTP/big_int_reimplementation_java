package mainPack;

public class bigNum {
    // debug
    class Debug{
        static void flag(){
            System.out.println("flag"); // create a flag
        }
        
        static void flag(int i){
            System.out.println("flag " + Integer.toString(i)); // similarly but with number
        }

        //print object
        
        static void pr(int x){
            System.out.println(x);
        }
        
        static void pr(String x){
            System.out.println(x);
        }
    
        static void pr(char x){
            System.out.println(x);
        }

        public void pr(bigNum x){
            System.out.println("Object " + x.getNUM());
            System.out.println(x.getNUM());
            System.out.println(x.getABS());
            System.out.println(x.getLENGTH());
            System.out.println(x.getSIGN());
            System.out.println(x.getZERO());
            System.out.println("_____________");
        }
    }

    // variables
    private String NUM; // the number including sign, without leading zeros
    private String ABS; // similarly but without any sign
    private int LENGTH; // length of the ABS, useful in divide() where we create low/high for binary search
    private boolean SIGN = true; // true if positive or equal zero, false otherwise
    private boolean ZERO = true; // is zero or not
    
    // static variables
    private static int TOTAL = 0; // total declared bigNum 
    private static int THRESHOLD = 10; // Recurrence threshold for Karatsuba algorithm in product()

    // constructor
    public bigNum(){
        TOTAL++;
    }

    // setter
    public void setALL (String num){ // set a bigNum from an arbitrary string, throw error when it's not number
        int length = num.length();
        if (length == 0) throw new IllegalArgumentException("Invalid Number.");
        else{
            boolean flag = true;
            for (int i = 1; i < length; i++){
                if (num.charAt(i) < 48 || num.charAt(i) > 57){
                    flag = false;
                    break;
                } 
            }
            if (flag){
                if (num.charAt(0) == '-'){
                    this.SIGN = false;
                    int indexNotZero = -1; 
                    for (int i = 1; i < length; i++){
                        if (num.charAt(i) != '0'){
                            this.ZERO = false;
                            indexNotZero = i;
                            break;
                        }
                    }
                    if (indexNotZero != -1){
                        this.ZERO = false;
                        this.NUM = "-" + num.substring(indexNotZero, length);
                        this.ABS = num.substring(indexNotZero, length);
                        this.LENGTH = length - indexNotZero;
                    }
                    else{
                        this.NUM = "0";
                        this.SIGN = true;
                        this.ABS = "0";
                        this.LENGTH = 1;
                    }       
                }
                else if (num.charAt(0) >= 48 && num.charAt(0) <= 57){
                    this.SIGN = true;
                    int indexNotZero = -1; 
                    for (int i = 0; i < length; i++){
                        if (num.charAt(i) != '0'){
                            this.ZERO = false;
                            indexNotZero = i;
                            break;
                        }
                    }
                    if (indexNotZero != -1){
                        this.ZERO = false;
                        this.NUM = num.substring(indexNotZero, length);
                        this.ABS = num.substring(indexNotZero, length);
                        this.LENGTH = length - indexNotZero;
                    }
                    else{
                        this.NUM = "0";
                        this.ABS = "0";
                        this.LENGTH = 1;
                    }     
                }
                else throw new IllegalArgumentException("Invalid Number.");;
            }
        }
    }

    public void setSIGN(boolean sign){ // set sign of a bigNum directly
        if (!this.ZERO){
            if (this.SIGN && (!sign)){
                this.SIGN = sign;
                this.NUM = "-" + this.NUM;
            }
            if ((!this.SIGN) && sign){
                this.SIGN = sign;
                this.NUM = this.NUM.substring(1);
            }
        } 
    }    // the remaining explains itself well enough

    // getter
    public String getNUM(){
        return this.NUM;
    }

    public String getABS(){
        return this.ABS;
    }

    public int getLENGTH(){
        return this.LENGTH;
    }

    public boolean getSIGN(){
        return this.SIGN;
    }

    public boolean getZERO(){
        return this.ZERO;
    }
    
    public int getTOTAL(){
        return TOTAL;
    }

    // comparator
    public static boolean isEqual (bigNum u, bigNum v){
        if (u.NUM == v.NUM) return true;
        else return false;
    }

    public static boolean isABSequal (bigNum u, bigNum v){
        if (u.ABS == v.ABS) return true;
        else return false;
    }

    public static boolean isABSsmaller (bigNum u, bigNum v){
        if (u.LENGTH < v.LENGTH){
            return true;
        }
        else if (u.LENGTH > v.LENGTH) return false;
        else{
            for (int i = 0; i < u.LENGTH; i++){
                if (u.ABS.charAt(i) < v.ABS.charAt(i)) return true;
                if (u.ABS.charAt(i) > v.ABS.charAt(i)) return false;
            }
            return false;
        }
    }
    
    public static boolean isABSbigger (bigNum u, bigNum v){
        return (!(isABSequal(u, v) || isABSsmaller(u, v)));
    }

    public static boolean isSmaller (bigNum u, bigNum v){
        if (!u.SIGN){
            if (v.SIGN) return true;
            if (isABSsmaller(u, v)) return false;
            return true;
        }
        else{
            if (!v.SIGN) return false;
            if (isABSsmaller(u, v)) return true;
            return false;
        }
    }

    public static boolean isBigger (bigNum u, bigNum v){
        return (!(isEqual(u, v) || isSmaller(u, v)));
    }

    public static boolean isSmallerOrEqual (bigNum u, bigNum v){
        return (isSmaller(u,v) || isEqual(u, v));
    }

    public static boolean isBiggerOrEqual (bigNum u, bigNum v){
        return (isBigger(u, v) || isEqual(u, v));
    }

    // converters
    public static bigNum toBigNum (byte u){
        bigNum result = new bigNum();
        result.setALL(Byte.toString(u));
        return result;
    }
    
    public static bigNum toBigNum (short u){
        bigNum result = new bigNum();
        result.setALL(Short.toString(u));
        return result;
    }

    public static bigNum toBigNum (int u){
        bigNum result = new bigNum();
        result.setALL(Integer.toString(u));
        return result;
    }

    public static bigNum toBigNum (long u){
        bigNum result = new bigNum();
        result.setALL(Long.toString(u));
        return result;
    }

    public static bigNum toBigNum (char u){
        bigNum result = new bigNum();
        result.setALL(Short.toString((short) u));
        return result;
    }

    public static bigNum stringToBigNum (String u){
        bigNum result = new bigNum();
        result.setALL(u);
        return result;
    }

    public static bigNum binaryToBigNum (String u){
        int lu = u.length();
        bigNum result = new bigNum();
        result.setALL("0");
        bigNum two = toBigNum(2);
        bigNum powOfTwo = new bigNum();
        powOfTwo.setALL("1");
        for (int i = 0; i < lu; i++){
            if (u.charAt(lu - i - 1) == '1') result.increment(powOfTwo);
            else if (u.charAt(lu - i - 1) != '0') throw new IllegalArgumentException("Invalid Number.");
            powOfTwo = product(powOfTwo, two);
        }
        return result;
    }

    public static byte toByte (bigNum u){
        bigNum v = modulus(u, toBigNum(Byte.MAX_VALUE + 1));
        return Byte.parseByte(v.NUM);
    }

    public static short toShort (bigNum u){
        bigNum v = modulus(u, toBigNum(Short.MAX_VALUE + 1));
        return Short.parseShort(v.NUM);
    }

    public static int toInt (bigNum u){
        bigNum v = modulus(u, toBigNum(Integer.MAX_VALUE + 1));
        return Integer.parseInt(v.NUM);
    }

    public static long toLong (bigNum u){
        bigNum v = modulus(u, toBigNum(Long.MAX_VALUE + 1));
        return Long.parseLong(v.NUM);
    }
    
    public static long toChar (bigNum u){
        bigNum v = modulus(u, toBigNum(Character.MAX_VALUE + 1));
        return (char) toInt(v);
    }
    
    public static String toString (bigNum u){
        return u.NUM;
    }

    public static String toBinary (bigNum u){
        String resReversed = "";
        bigNum u1 = u.clone();
        while (!u1.ZERO){
            resReversed += (u1.ABS.charAt(u1.LENGTH - 1) % 2 == 0) ? "0" : "1";
            u1 = divideByTwo(u1);
        }
        String result = operatorHandling.revString(resReversed);
        return result; 
    }

    // operators
    public void assign(bigNum u){
        this.setALL(u.getNUM());
    }

    public bigNum clone(){
        bigNum other = new bigNum();
        other.setALL(this.NUM);
        return other;
    }

    public void abs (){
        this.setSIGN(true);
    }

    public void inverse (){
        this.setSIGN(!this.getSIGN());
    }

    public static void swap (bigNum u, bigNum v){
        String w = u.getNUM();
        u.setALL(v.getNUM());
        v.setALL(w);
    }

    public static bigNum add (bigNum u, bigNum v){
        String resReversed = "";
        String sign = "";
        int lu = u.LENGTH;
        int lv = v.LENGTH;
        String u1 = u.ABS;
        String v1 = v.ABS;
        int max = Math.max(lu, lv);
        int min = lu + lv - max;
        
        if (u.SIGN == v.SIGN){
            if (u.SIGN == false) sign += "-";   
            carryWrapper carry = new carryWrapper();
            carry.set(0);
            for (int i = 0; i < min; i++){
                resReversed += operatorHandling.addOneDigit(u1.charAt(lu - i - 1), v1.charAt(lv - i - 1), carry);
            }
            if (max == lu){
                for (int i = min; i < max; i++){
                    resReversed += operatorHandling.addOneDigit(u1.charAt(lu - i - 1), '0', carry);
                }
                if (carry.get() == 1) resReversed += 1;
            }
            else{
                for (int i = min; i < max; i++){
                    resReversed += operatorHandling.addOneDigit('0', v1.charAt(lv - i - 1), carry);
                }
                if (carry.get() == 1) resReversed += 1;
            }
        }
        
        else{
            if (isABSsmaller(u, v)){
                String tmpString = u1;
                u1 = v1;
                v1 = tmpString;
                int tmpInt = lu;
                lu = lv;
                lv = tmpInt;
                if (u.SIGN = true) sign += "-";
            }
            else if (u.SIGN == false){
                sign += "-";
            }
            borrowWrapper borrow = new borrowWrapper();
            borrow.set(0);
            for (int i = 0; i < min; i++){
                resReversed += operatorHandling.subtractOneDigit(u1.charAt(lu - i - 1), v1.charAt(lv - i - 1), borrow);
            }
            for (int i = min; i < max; i++){
                resReversed += operatorHandling.subtractOneDigit(u1.charAt(lu - i - 1), '0', borrow);
            }
        }
        bigNum result = new bigNum();
        result.setALL(sign + operatorHandling.revString(resReversed));
        return result;
    }
    
    public static bigNum subtract (bigNum u, bigNum v){
        bigNum v1 = v.clone();
        v1.inverse();
        return bigNum.add(u, v1);
    }

    public void increment (bigNum v){
        bigNum v1 = add(this, v);
        assign(v1);
    }

    public void decrement (bigNum v){
        bigNum v1 = subtract(this, v);
        assign(v1);
    }

    private void mutiplyBigNumToPowerOfTen (int power){
        String num = this.NUM;
        for (int i = 0; i < power; i++){
            num += "0";
        }
        this.setALL(num);
    }

    private static bigNum productBigNumToOneDigit (bigNum u, int v){
        String resReversed = "";
        String u1 = u.ABS;
        int lu = u.LENGTH;
        carryWrapper carry = new carryWrapper();
        carry.set(0);
        for (int i = 0; i < lu; i++){
            resReversed += operatorHandling.multiplyOneDigitToOneDigit(u1.charAt(lu - i - 1), v, carry);
        }
        resReversed += (char) (carry.get() + 48);
        
        bigNum result = new bigNum();
        result.setALL(operatorHandling.revString(resReversed));
        result.setSIGN(u.SIGN);
        return result;
    }

    private static bigNum productBigNumToBigNumSlow (bigNum u, bigNum v){
        bigNum u1 = u.clone();
        u1.abs();
        String v1 = v.getABS();
        int lv = v.LENGTH;
        bigNum result = new bigNum();
        result.setALL("0");
        for (int i = 0; i < lv; i++){
            bigNum w = productBigNumToOneDigit(u1, v1.charAt(lv - i - 1) - 48);
            w.mutiplyBigNumToPowerOfTen(i);
            result.increment(w);
        }
        return result;
    }

    public static bigNum product(bigNum u, bigNum v){
        bigNum u0 = u.clone();
        bigNum v0 = v.clone();
        if (isSmaller(u, v)) swap(u0, v0);
        String u1 = u0.ABS;
        String v1 = v0.ABS;
        int lu = u0.LENGTH;
        int lv = v0.LENGTH;
        if (lu <= THRESHOLD || lv <= THRESHOLD){
            return productBigNumToBigNumSlow(u, v);
        }
        
        bigNum A = new bigNum();
        bigNum B = new bigNum();
        bigNum C = new bigNum();
        bigNum D = new bigNum();
        int l = lv / 2;
        A.setALL(u1.substring(0, lu - l));
        B.setALL(u1.substring(lu - l));
        C.setALL(v1.substring(0, lv - l));
        D.setALL(v1.substring(lv - l));

        
        bigNum U = new bigNum();
        bigNum V = new bigNum();
        bigNum W = new bigNum();
        U = product(A, C);
        V = product(add(A, B), add(C, D));
        W = product(B, D);
        V.decrement(U);
        V.decrement(W);
        U.mutiplyBigNumToPowerOfTen(2 * l);
        V.mutiplyBigNumToPowerOfTen(l);
        
        bigNum result = new bigNum();
        result.setALL("0");
        result.increment(U);
        result.increment(V);
        result.increment(W);
        result.setSIGN(!(U.SIGN ^ V.SIGN));
        return result;
    }

    private static bigNum divideByTwo (bigNum u){
        bigNum result = new bigNum();
        String res = "";
        carryWrapper carry = new carryWrapper();
        carry.set(0);
        for (int i = 0; i < u.LENGTH; i++){
            res += operatorHandling.divideOneDigitByTwo(u.ABS.charAt(i), carry);
        }
        result.setALL(res);
        return result;
    }

    public static bigNum divide (bigNum u, bigNum v){
        boolean sign = !(u.SIGN ^ v.SIGN);
        bigNum zero = new bigNum();
        zero.setALL("0");
        if (isSmaller(u, v)) return zero;
        else{
            bigNum uABS = u.clone();
            uABS.abs();
            bigNum vABS = v.clone();
            vABS.abs();
            bigNum one = toBigNum(1);
            bigNum low = new bigNum();
            low.setALL("1");
            low.mutiplyBigNumToPowerOfTen(u.LENGTH - v.LENGTH - 1);
            bigNum high = new bigNum();
            high.setALL("1");
            high.mutiplyBigNumToPowerOfTen(u.LENGTH - v.LENGTH + 1);

            while (isSmaller(low, high)){
                bigNum mid = divideByTwo(add(add(low, high), one));
                bigNum tmp = product(mid, vABS);
                if (isBigger(tmp, uABS)){
                    high = subtract(mid, one);
                }
                else{
                    low = mid;
                }
            }
            low.setSIGN(sign);
            return low;
        }
    }

    public static bigNum modulus (bigNum u, bigNum v){
        return subtract(u, product(v, divide(u, v)));
    }

    public static bigNum andOperator (bigNum u, bigNum v){
        String u1 = toBinary(u);
        String v1 = toBinary(v);
        int lu = u1.length();
        int lv = v1.length();
        String result = "";
        if (lu < lv){
            String tmpString = u1;
            u1 = v1;
            v1 = tmpString;
            int tmpInt = lu;
            lu = lv;
            lv = tmpInt;
        }
        for (int i = 0; i < lu - lv; i++) result += '0';
        for (int i = 0; i < lv; i++){
            result += ((u1.charAt(lu - lv + i) == '1') && (v1.charAt(i) == '1')) ? '1' : '0';
        }
        return binaryToBigNum(result);
    }

    public static bigNum orOperator (bigNum u, bigNum v){
        String u1 = toBinary(u);
        String v1 = toBinary(v);
        int lu = u1.length();
        int lv = v1.length();
        String result = "";
        if (lu < lv){
            String tmpString = u1;
            u1 = v1;
            v1 = tmpString;
            int tmpInt = lu;
            lu = lv;
            lv = tmpInt;
        }
        for (int i = 0; i < lu - lv; i++) result += (u1.charAt(i) == '1') ? '1' : '0';
        for (int i = 0; i < lv; i++){
            result += ((u1.charAt(lu - lv + i) == '1') || (v1.charAt(i) == '1')) ? '1' : '0';
        }
        return binaryToBigNum(result);
    }

    public static bigNum xorOperator (bigNum u, bigNum v){
        String u1 = toBinary(u);
        String v1 = toBinary(v);
        int lu = u1.length();
        int lv = v1.length();
        String result = "";
        if (lu < lv){
            String tmpString = u1;
            u1 = v1;
            v1 = tmpString;
            int tmpInt = lu;
            lu = lv;
            lv = tmpInt;
        }
        for (int i = 0; i < lu - lv; i++) result += (u1.charAt(i) == '1') ? '1' : '0';;
        for (int i = 0; i < lv; i++){
            result += (u1.charAt(lu - lv + i) == v1.charAt(i)) ? '0' : '1';
        }
        return binaryToBigNum(result);
    }

    public static bigNum notOperator (bigNum u){
        String u1 = u.toString();
        String result = "";
        for (int i = 0; i < u1.length(); i++) result += 97 - u1.charAt(i);
        return binaryToBigNum(result);
    }

    public static bigNum leftShiftOperator (bigNum u, int n){
        return binaryToBigNum(toBinary(u) + "0".repeat(n));
    }

    public static bigNum rightShiftOperator (bigNum u, int n){
        return binaryToBigNum(toBinary(u).substring(0, toBinary(u).length() - n));
    }  
}
