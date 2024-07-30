package mainPack;

public class operatorHandling {
    // output the reversed string of a string.
    public static String revString (String resReversed){
        StringBuilder resReversedBuilder = new StringBuilder(resReversed);
        return resReversedBuilder.reverse().toString();
    }

    // add a digit u to a digit v, return char type. 
    
    public static char addOneDigit (char u, char v, carryWrapper carry){
        int res = u + v + carry.get() - 96; // actual result
        if (res < 10){ 
            carry.set(0); // no more carry
            // digit is the character that need to be returned
            char digit = (char) (res + 48);
            return digit;
        }
        else{ // result bigger than 9
            carry.set(1); // one carry
            char digit = (char) (res + 38);
            return digit;
        }
    }

    public static char subtractOneDigit (char u, char v, borrowWrapper borrow){ // probably the same but for subtraction
        int res = u - v - borrow.get();
        if (res > -1){
            borrow.set(0);
            char digit = (char) (res + 48);
            return digit;
        }
        else{
            borrow.set(1);
            char digit = (char) (res + 58);
            return digit;
        }
    }

    public static char multiplyOneDigitToOneDigit (char u, int v, carryWrapper carry){ // for multiplying
        int res = (u - 48) * v + carry.get();
        int digit = res % 10; // as it should be
        carry.set(res / 10);
        return (char) (digit + 48); 
    }

    public static char divideOneDigitByTwo (char u, carryWrapper carry){ //and for dividing by two.
        int res = u - 48 + carry.get() * 10;
        char digit = (char) (res / 2 + 48);
        carry.set(res % 2);
        return digit;
    }


}
