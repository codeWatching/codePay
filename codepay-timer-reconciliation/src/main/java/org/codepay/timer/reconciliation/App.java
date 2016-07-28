package org.codepay.timer.reconciliation;

/**
 * Hello world!
 *
 */
public class App 
{
    static String str0="0123456789";
    static String str1="0123456789";
    public static void main( String[] args )
    {
        String str2=str1.substring(5);
        String str3=new String(str2);
        String str4=new String(str3.toCharArray());
        str0=null;
        System.out.println( "Hello World!" );
        System.gc();
        System.out.println(str3.length());
        System.out.println(str2.length());
        System.out.println(str4.length());
    }
}
