package mainPack;

public class operatorHandling {

    public static String revString (String resReversed){
        StringBuilder resReversedBuilder = new StringBuilder(resReversed);
        return resReversedBuilder.reverse().toString();
    }

    public static char addOneDigit (char u, char v, carryWrapper carry){
        int res = u + v + carry.get() - 96;
        if (res < 10){
            carry.set(0);
            char digit = (char) (res + 48);
            return digit;
        }
        else{
            carry.set(1);
            char digit = (char) (res + 38);
            return digit;
        }
    }

    public static char subtractOneDigit (char u, char v, borrowWrapper borrow){
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

    public static char multiplyOneDigitToOneDigit (char u, int v, carryWrapper carry){
        int res = (u - 48) * v + carry.get();
        int digit = res % 10;
        carry.set(res / 10);
        return (char) (digit + 48); 
    }

    public static char divideOneDigitByTwo (char u, carryWrapper carry){
        int res = u - 48 + carry.get() * 10;
        char digit = (char) (res / 2 + 48);
        carry.set(res % 2);
        return digit;
    }


}
