package editora3.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Fernando
 */
public class ValidacoesDiversas {
    
    public static boolean ValidarCNPJ_Ou_CPF(String cpfOrCnpj){  
        try {            
               if (cpfOrCnpj == null) {
                   return false;
               }  
              String n = cpfOrCnpj.replaceAll("[^0-9]*","");  
              boolean isCnpj = n.length() == 14;  
              boolean isCpf = n.length() == 11;  
              if (!isCpf && !isCnpj) {
                  return false;
              }  
              int i; int j;   // just count   
              int digit;      // A number digit  
              int coeficient; // A coeficient    
              int sum;        // The sum of (Digit * Coeficient)  
              int[] foundDv = {0,0}; // The found Dv1 and Dv2  
              int dv1 = Integer.parseInt(String.valueOf(n.charAt(n.length()-2)));  
              int dv2 = Integer.parseInt(String.valueOf(n.charAt(n.length()-1)));         
              for (j = 0; j < 2; j++) {  
                  sum = 0;  
                  coeficient = 2;  
                  for (i = n.length() - 3 + j; i >= 0 ; i--){  
                      digit = Integer.parseInt(String.valueOf(n.charAt(i)));                 
                      sum += digit * coeficient;  
                      coeficient ++;  
                      if (coeficient > 9 && isCnpj) coeficient = 2;                  
                  }                  
                  foundDv[j] = 11 - sum % 11;  
                  if (foundDv[j] >= 10) foundDv[j] = 0;  
              }  
              return dv1 == foundDv[0] && dv2 == foundDv[1];  

        } catch (Exception e) {
            
            return false;
        }
   
}  
      public static boolean CPF_E_CNPJ(String strCpf) {  
        
        try {
        if (strCpf.equals("")) {  
            return false;  
        }        
        
        strCpf=strCpf.trim();
        
        strCpf = strCpf.replaceAll("[^0-9]", "");
        
        if(strCpf.trim().length()!=11){
            return false;
        }      
                
        //strCpf=strCpf.substring(0,9);        
        
        int d1, d2;  
        int digito1, digito2, resto;  
        int digitoCPF;  
        String nDigResult;  
  
        d1 = d2 = 0;  
        digito1 = digito2 = resto = 0;  
  
        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {  
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();  
  
            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.  
            d1 = d1 + (11 - nCount) * digitoCPF;  
  
            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.  
            d2 = d2 + (12 - nCount) * digitoCPF;  
        }  
  
        //Primeiro resto da divisão por 11.  
        resto = (d1 % 11);  
  
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
        if (resto < 2) {  
            digito1 = 0;  
        } else {  
            digito1 = 11 - resto;  
        }  
  
        d2 += 2 * digito1;  
  
        //Segundo resto da divisão por 11.  
        resto = (d2 % 11);  
  
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
        if (resto < 2) {  
            digito2 = 0;  
        } else {  
            digito2 = 11 - resto;  
        }  
  
        //Digito verificador do CPF que está sendo validado.  
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());  
  
        //Concatenando o primeiro resto com o segundo.  
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);  
  
        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.  
        return nDigVerific.equals(nDigResult);  
        } catch (Exception e) {
                 
                return false;
        }
    }  
}
