/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author HP
 */
public class Formatter {
    private static final NumberFormat RUPIAH_FORMAT =
        NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    
    public static String toRupiah(double amount) {
        return RUPIAH_FORMAT.format(amount);
    }

}
