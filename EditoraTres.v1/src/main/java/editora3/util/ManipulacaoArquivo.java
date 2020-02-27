/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package editora3.util;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
 
 

/**
 *
 * @author desenvolvedor
 */
public class ManipulacaoArquivo {

        private static String SeparadorDiretorio=File.separator;
        public static String newline = System.getProperty("line.separator");
        private static File file;
        public static File InstanciaExistenteFile(String cNome){
          
          file = novaInstanciaFile(cNome);
          
          if (file.exists())
              return file;
          else
              return null;        
          
        }
        public static void prepararPastaParaUso(String pasta) {
    		try {
				
    			if(!DiretorioExiste(pasta)) {
    				CriarDiretorio(pasta);
    			}
    			
			} catch (Exception e) {
				 JsfUtil.addErrorMessage(e,"prepararPastaParaUso");
				// TODO: handle exception
			}
    	}
        public static boolean ExcluirTodosArquivoDoDiretorio(String Diretorio){
            try {
                File[] Arquivo  =ListarArquivos("", Diretorio,"");
                for (int i = 0; i < Arquivo.length; i++) {
                    ExcluirArquivo(Arquivo[i].getPath());
                }
                
                return true;
            } catch (Exception e) {
                  JsfUtil.addErrorMessage(e,"ExcluirTodosArquivoDoDiretorio");
                return false;
            }
        }
        public static boolean  CriarDiretorio(String cDiretorio)
        {
          boolean bretorno=false;
            try {

                new File(cDiretorio).mkdir();

                bretorno=true;

            } catch (Exception e) {
                  JsfUtil.addErrorMessage(e,"CriarDiretorio");
            }
          

          return bretorno;
        }
        
        public static boolean CriarArquivo_Enconding(String NomeArquivo, String dados, String Encod){
            
            try {               
                
                OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream(NomeArquivo),Encod);                
                bufferOut.write(dados); 
                bufferOut.close();
                
                return true;
                
            } catch (Exception e) {
                  JsfUtil.addErrorMessage(e,"CriarArquivo_Enconding");
                return false;
            }
        }
        public static boolean CriarArquivo(String NomeArquivo, byte[] dados){
        
            try {
                
                File fArquivoTZip = ManipulacaoArquivo.novaInstanciaFile(NomeArquivo);                
                FileOutputStream f = new FileOutputStream(fArquivoTZip);
                f.write(dados);
                f.close();
                
                return true;
                
            } catch (Exception e) {
                  JsfUtil.addErrorMessage(e,"CriarArquivo");
                return false;
            }
        }
        public static File CriarArquivoFile(String NomeArquivo, byte[] dados){
            File fArquivoTZip =null;
            try {
                
                fArquivoTZip = ManipulacaoArquivo.novaInstanciaFile(NomeArquivo);                
                FileOutputStream f = new FileOutputStream(fArquivoTZip);
                f.write(dados);
                f.close();                
                
                
            } catch (Exception e) {
                  JsfUtil.addErrorMessage(e,"CriarArquivoFile");
                
            }
            return fArquivoTZip;
        }
         public static boolean DiretorioExiste(String cNome){
            
          boolean bretorno=false;
          file= novaInstanciaFile(cNome);
          if(file!=null)
             if(file.isDirectory()) 
                bretorno=true; 
          
          return bretorno;             
        }
        public static boolean ArquivoExiste(String cNome, boolean bUsarCaminhoRelativo){

            boolean bretorno = false;
            String cCaminhoAbsoluto="";
            try {
                if(bUsarCaminhoRelativo)
                    cCaminhoAbsoluto = novaInstanciaFile("").getCanonicalPath() + File.separator + cNome;
                else
                    cCaminhoAbsoluto=cNome;

                file = novaInstanciaFile(cCaminhoAbsoluto);
                //System.out.println(file.getCanonicalFile());    
                if (file != null) {
                    if (file.isFile()) {                        
                        bretorno = true;
                    }
                }
           
            } catch (IOException ex) {
                  JsfUtil.addErrorMessage(ex,"ArquivoExiste");
            }
            return bretorno;
        }

        public static File novaInstanciaFile(String cNomeArquivo)
        {
            File xFile=null;
            try {
                xFile=new File(cNomeArquivo);

            } catch (Exception ex) {
            	 JsfUtil.addErrorMessage(ex,"novaInstanciaFile");
            }
            return xFile;

        }
        private static FileFilter Filtro(final String cString)
        {
            FileFilter ff = new FileFilter() { 
            public boolean accept(File b){ 
                if(cString.equalsIgnoreCase("*.*")){
                    return true;
                }else{
                  return b.getName().endsWith(cString); 
                }
            }}; 
            return ff;

        }
        
        public static boolean  Arquivo_Copiar(String cOrigem, String cDestino)
        {
            boolean bRet=false;
            File src = new File(cOrigem);
            File dst = new File(cDestino);            
            InputStream in=null;
            OutputStream out=null;
            try {

                in = new FileInputStream(src);

                out = new FileOutputStream(dst);

            } catch (FileNotFoundException ex) {
            	JsfUtil.addErrorMessage(ex,"Arquivo_Copiar");
            }


            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                
                bRet=true;
                } catch (IOException ex) {
                      JsfUtil.addErrorMessage(ex,"Arquivo_Copiar");
                }
            
            
            return bRet;
        }

        public static String DiretorioDeTrabalho()
        {
           return System.getProperty("user.dir");
        }
        public static File[] ListarArquivos(String cTipo, String cDiretorio){
            return ListarArquivos(cTipo, cDiretorio, DiretorioDeTrabalho());
        }
        public static File[] ListarArquivos(String cTipo, String cDiretorio, String DiretorioTrabalho )
        {
            File[] files ;

            File f= new File(DiretorioTrabalho + cDiretorio+ "\\");

            files = f.listFiles(Filtro(cTipo));
            
            return files;
        }
        public static String DiretorioPAI()
        {
            String cNomeArquivo="";
            try {
                cNomeArquivo=file.getAbsolutePath();

            } catch (Exception exception) {
            	JsfUtil.addErrorMessage(exception,"DiretorioPAI");

            }
            return cNomeArquivo;

        }

        public static String LerArquivo(String cNomeArquivo,boolean  bUsarNomeRelativo )
        {
            String cStringRetorno="";                     
            InputStream fluxoEntrada = null;
            if(ManipulacaoArquivo.ArquivoExiste(cNomeArquivo, bUsarNomeRelativo))
            {
                try {
                    File f = ManipulacaoArquivo.novaInstanciaFile(cNomeArquivo);
                    fluxoEntrada = new FileInputStream(f);
                    InputStreamReader streamReader = new InputStreamReader(fluxoEntrada,"UTF-8");   
                    BufferedReader reader = new BufferedReader(streamReader); 
                    
                    while (reader.ready())
                    {
                         cStringRetorno += reader.readLine()+ newline;
                    }
                    
                } catch (IOException ex) {
                       JsfUtil.addErrorMessage(ex,"LerArquivo");

                }
                finally
                {
                    try {
                        fluxoEntrada.close();
                    } catch (IOException ex) {
                          JsfUtil.addErrorMessage(ex,"LerArquivo");
                    }
                }
             }                
            return cStringRetorno;
         }
        public static String LerArquivo_Enconding(String cNomeArquivo,boolean  bUsarNomeRelativo,String Encod )
        {
            //byte [] ret = null;
            String cStringRetorno="";                     
            InputStream fluxoEntrada = null;            
            if(ManipulacaoArquivo.ArquivoExiste(cNomeArquivo, bUsarNomeRelativo))
            {
                try {
                    //File f = ManipulacaoArquivo.novaInstanciaFile(cNomeArquivo);
                    //File f= new File("");
                   
                    fluxoEntrada = new FileInputStream(cNomeArquivo);
                    InputStreamReader streamReader = new InputStreamReader(fluxoEntrada,Encod);   
                    BufferedReader reader = new BufferedReader(streamReader); 
                    String Linha ="";
                    while (reader.ready())
                    {
                        Linha= reader.readLine();                        
                        cStringRetorno +=Linha +"\n" ;
                    }
                    
                } catch (IOException ex) {
                       JsfUtil.addErrorMessage(ex,"LerArquivo_Enconding");

                }
                finally
                {
                    try {
                        fluxoEntrada.close();
                    } catch (IOException ex) {
                          JsfUtil.addErrorMessage(ex,"LerArquivo_Enconding");
                    }
                }
            }                            
            return cStringRetorno;
            
         }
      private static final int TAMANHO_BUFFER = 2048; // 2 Kb 
      public static byte[] LerArquivo_Binario(String cNomeArquivo )
      {
          boolean Ret = false;
          byte[] buffer = new byte[TAMANHO_BUFFER];
          try {
          
              int bytesLidos = 0;
          
              OutputStream os = null;
              ByteArrayOutputStream ba = new ByteArrayOutputStream();
              File f = new File(cNomeArquivo);
              
              InputStream is = new FileInputStream(f);
          
              ///os = new FileOutputStream(f);

              while (true) {
                  //os.write(buffer, 0, bytesLidos);
                  bytesLidos = is.read(buffer);
                  if(bytesLidos<0){
                      break;
                  }
                  ba.write(buffer);
              }
              buffer  = ba.toByteArray();
              
          } catch (Exception e) {
        	  JsfUtil.addErrorMessage(e, "LerArquivo_Binario");
          }
          return buffer;
          
      }
    /**
     * @return the SeparadorDiretorio
     */
    public static String getSeparadorDiretorio() {
        return SeparadorDiretorio;
    }

    /**
     * @param aSeparadorDiretorio the SeparadorDiretorio to set
     */
    private static void setSeparadorDiretorio(String aSeparadorDiretorio) {
        SeparadorDiretorio = aSeparadorDiretorio;
    }
    
    public static boolean ExcluirArquivo(String cNomeArquivo) throws Exception
    {
        boolean bRet=false;
       
         bRet =(new File(cNomeArquivo)).delete();
                //bRet=true;
      
        return bRet;

    }
    public static boolean ExcluirArquivo(ArrayList<String> arrayList)
    {
        boolean bRet=true;
        for (int i = 0; i < arrayList.size(); i++) {
            try {
                bRet= ExcluirArquivo(arrayList.get(i));
            } catch (Exception ex) {
                  JsfUtil.addErrorMessage(ex,"ExcluirArquivo");
            }
            if(!bRet)
                break;

        }
        return bRet;
    }

     
                    
}

