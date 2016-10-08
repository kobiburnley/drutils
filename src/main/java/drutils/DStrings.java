package drutils;

/**
 * Created by kobi on 08/10/16.
 */
public class DStrings {

    public static boolean isEmpty(String s) {
        return s == null  || s.length() == 0 || s.trim().length() == 0;
    }

    public static String toPublicStaticFinal(String s){
        String retVal = null;
        if(!isEmpty(s)){
            retVal = String.format("public static final String %s = \"%s\";", s.toUpperCase(), s);
        }
        return retVal;
    }

    public static String toMemberAssignment(String s){
        String retVal = null;
        if(!isEmpty(s)){
            retVal = String.format("this.%s = Const.%s;", s, s.toUpperCase());
        }
        return retVal;
    }

    public static String toMember(String s){
        String retVal = null;
        if(!isEmpty(s)){
            retVal = String.format("private String %s;", s);
        }
        return retVal;
    }
}
