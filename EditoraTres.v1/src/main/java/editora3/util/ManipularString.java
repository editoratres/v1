package editora3.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.Normalizer;


/**
 *
 * @author Fernando
 */
public class ManipularString {
    public static String DeixarSomenteNumero(final String str) {
        String strSemAcentos="";
        try {
            strSemAcentos = str.replaceAll("[^0-9]", "");
         } catch (Exception ex) {
             JsfUtil.addErrorMessage(ex,ex.getMessage());
        }
	return strSemAcentos;
    }
    public static String Replicate(String Caracter , int Vezes)
    {
        String ret="";
        try {
            
            for (int i = 0; i < Vezes; i++) {
                ret +=Caracter;
            }
            
        } catch (Exception ex) {
             JsfUtil.addErrorMessage(ex,ex.getMessage());
        }
        return ret;
    }
    public static String DeixarSomenteCaracteres(final String str) {
        String strSemAcentos="";
        try {
            strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
            strSemAcentos = strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
         } catch (Exception ex) {
             JsfUtil.addErrorMessage(ex,ex.getMessage());
        }
	return strSemAcentos;
    }
    public static String Padl(int qt, String dados, String caracter){
        String ret="";
        try {
            
            ret = String.format("%"+qt+"s", dados.replace(" ", "!@!")).replace(" ", caracter).replace("!@!", " ");
        } catch (Exception ex) {
             JsfUtil.addErrorMessage(ex,ex.getMessage());
        }
        return ret;
    }
    public static String Padr(int qt, String dados, String caracter){
        String ret="";
        try {
            
            ret = Padl(-qt, dados, caracter);
        } catch (Exception ex) {
             JsfUtil.addErrorMessage(ex,ex.getMessage());
        }
        return ret;
    }
    public static void main(String args[]){
                System.out.println(Padl(5,"123","0"));
                
                System.out.println(Padr(5,"123","0"));
    }
    public static String Left(int tam , String texto ){
        String ret="";
        try {
            if(tam <=texto.length()){
                ret = texto.substring(0,tam);
            }else{
                ret = texto;
            }
        } catch (Exception ex) {
              JsfUtil.addErrorMessage(ex,ex.getMessage());
        }
        return ret;
    }
}
