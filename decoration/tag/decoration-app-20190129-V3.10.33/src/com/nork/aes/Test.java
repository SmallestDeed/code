/**
 * 
 */
package com.nork.aes;

/**
 * @author Administrator
 *
 */
public class Test {
    public static void main(String[] args) {
        
        String currentURL="/app/online/web/design/intelligenceDecoration/findGeneratePlanInfo.htm?opType";
        currentURL= currentURL.substring(0, currentURL.indexOf("?"));
        System.out.println(currentURL);
    }

}
