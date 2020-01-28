package editora3.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.math.BigDecimal;

/**
 *
 * @author dti
 */
public class TratamentoNulos{
    private static TratarNulos<String> TratarString=new TratarNulos<String>();
    private static TratarNulos<Integer> TratarInt=new TratarNulos<Integer>();
    private static TratarNulos<Float> TratarFloat=new TratarNulos<Float>();
    private static TratarNulos<Double> TratarDouble=new TratarNulos<Double>();
    private static TratarNulos<BigDecimal> TratarBigDecimal=new TratarNulos<BigDecimal>();
    private static TratarNulos<Long> TratarLong=new TratarNulos<Long>();
    private static TratarNulos<Object> TratarObject=new TratarNulos<Object>();
    //private TratarNulos<T> TratarGenerico=new TratarNulos<T>();

    /**
     * @return the TratarString
     */
    public static TratarNulos<String> getTratarString() {
        return TratarString;
    }

    /**
     * @param aTratarString the TratarString to set
     */
    public static void setTratarString(TratarNulos<String> aTratarString) {
        TratarString = aTratarString;
    }

    /**
     * @return the TratarInt
     */
    public static TratarNulos<Integer> getTratarInt() {
        return TratarInt;
    }

    /**
     * @param aTratarInt the TratarInt to set
     */
    public static void setTratarInt(TratarNulos<Integer> aTratarInt) {
        TratarInt = aTratarInt;
    }

    /**
     * @return the TratarFloat
     */
    public static TratarNulos<Float> getTratarFloat() {
        return TratarFloat;
    }

    /**
     * @param aTratarFloat the TratarFloat to set
     */
    public static void setTratarFloat(TratarNulos<Float> aTratarFloat) {
        TratarFloat = aTratarFloat;
    }
    
     public static TratarNulos<Double> getTratarDouble() {
        return TratarDouble;
    }

    /**
     * @param aTratarFloat the TratarFloat to set
     */
    public static void setTratarDouble(TratarNulos<Double> aTratarDouble) {
        TratarDouble = aTratarDouble;
    }

    /**
     * @return the TratarBigDecimal
     */
    public static TratarNulos<BigDecimal> getTratarBigDecimal() {
        return TratarBigDecimal;
    }

    /**
     * @param aTratarBigDecimal the TratarBigDecimal to set
     */
    public static void setTratarBigDecimal(TratarNulos<BigDecimal> aTratarBigDecimal) {
        TratarBigDecimal = aTratarBigDecimal;
    }

    /**
     * @return the TratarLong
     */
    public static TratarNulos<Long> getTratarLong() {
        return TratarLong;
    }

    /**
     * @param aTratarLong the TratarLong to set
     */
    public static void setTratarLong(TratarNulos<Long> aTratarLong) {
        TratarLong = aTratarLong;
    }

    /**
     * @return the TratarObject
     */
    public static TratarNulos<Object> getTratarObject() {
        return TratarObject;
    }

    /**
     * @param aTratarObject the TratarObject to set
     */
    public static void setTratarObject(TratarNulos<Object> aTratarObject) {
        TratarObject = aTratarObject;
    }

    /**
     * @return the TratarGenerico
     */
   
  
}
