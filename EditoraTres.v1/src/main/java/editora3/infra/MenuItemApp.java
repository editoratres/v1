package editora3.infra;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author Fernando Zoe
 */
public class MenuItemApp {
     private String ID;
    private String URL;
    private String Label;
    private String Icone;
    private ArrayList<MenuItemApp> itens=new ArrayList<>();

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @param URL the URL to set
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * @return the Label
     */
    public String getLabel() {
        return Label;
    }

    /**
     * @param Label the Label to set
     */
    public void setLabel(String Label) {
        this.Label = Label;
    }

    /**
     * @return the itens
     */
    public ArrayList<MenuItemApp> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(ArrayList<MenuItemApp> itens) {
        this.itens = itens;
    }

    /**
     * @return the Icone
     */
    public String getIcone() {
        return Icone;
    }

    /**
     * @param Icone the Icone to set
     */
    public void setIcone(String Icone) {
        this.Icone = Icone;
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }
    
}
