package cn.codeaper.java;

import java.time.LocalDate;

/**
 * @description:
 * @author: qzl
 * @created: 2020/07/15 19:18
 */

public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        while(true){
            ATM.display();
        }
    }
}
