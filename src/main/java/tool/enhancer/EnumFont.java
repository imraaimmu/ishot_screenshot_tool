package tool.enhancer;

import java.util.ArrayList;
import java.util.List;

public enum EnumFont {
    AGENCY_FB("Agency Fb", "AGENCYB"),
    ALGER("Algerian", "ALGER"),
    ARIAL("Arial", "arial"),
    ARIMO_REGULAR("Arimo", "Arimo-Regular"),
    BELL("Bell MT", "BELL"),
    CALIBRI("Calibri", "calibri"),
    CAMBRIA("Cambria", "cambria"),
    CANDARA("Candara", "Candara"),
    CASTELAR("Castellar", "CASTELAR"),
    CENTAUR("Centaur", "CENTAUR"),
    CENTURY("Century", "CENTURY"),
    CHILLER("Chiller", "CHILLER"),
    COLONNA("Colonna MT", "COLONNA"),
    COMIC("Comic Sans MS", "comic"),
    CONSTAN("Constantia", "constan"),
    CORBEL("Corbel", "corbel"),
    EBRIMA("Ebrima", "ebrima"),
    ELEPHNT("Elephant", "ELEPHNT"),
    GABRIOLA("Gabriola", "Gabriola"),
    GARA("Garamond", "GARA"),
    GEORGIA("Georgia", "georgia"),
    HIMALAYA("High Tower Text", "himalaya"),
    IMPACT("Impact", "impact"),
    IMPRISHA("Imprint MT Shadow", "IMPRISHA"),
    ITCBLKAD("Blackadder ITC", "ITCBLKADnn"),
    JOKERMAN("Jokerman", "JOKERMAN"),
    PALA("Palace Script MT", "pala"),
    RAGE("Rage Italic", "RAGE"),
    RAVIE("Ravie", "RAVIE"),
    ROCK("Rockwell", "ROCK"),
    SCRIPT("Script MT Bold", "script"),
    SIMSUN("SimSun", "simsun"),
    SITKA("Sitka Banner", "Sitka"),
    STENCIL("Stencil", "STENCIL"),
    SYMBOL("Symbol", "symbol"),
    SYSTEM_REGULAR("System Regular", "jvgasys"),
    TAHOMA("Tahoma", "tahoma"),
    TIMES("Times New Roman", "times"),
    VERDANA("Verdana", "verdana"),
    VLADIMIR("Vladimir Script", "VLADIMIR");
    
    protected String fontName;
    protected String fontTTFName;

    EnumFont(String fontName, String fontTTFName) {
        this.fontName = fontName;
        this.fontTTFName = String.valueOf(fontTTFName);
    }

    public static List<String> getAllFontNames() {
    	 EnumFont[] values = EnumFont.values();
    	 List<String> list = new ArrayList<String>();
    	 for(EnumFont value : values){
    		 list.add(value.getFontName());
    	 }
    	 return list;
    }

    public static String getTTFFontName(String fontName) {
   	 EnumFont[] values = EnumFont.values();
   	 for(EnumFont value : values){
   		 if (value.getFontName().equals(fontName)) {
   			 return value.getFontTTFName();
   		 }
   	 }
       return "";
   }

    protected void setFontName(String fontName) {
        this.fontName = fontName;
    }

    protected void setFontTTFName(String fontTTFName) {
        this.fontTTFName = fontTTFName;
    }

    public String getFontName() {
        return this.fontName;
    }

    public String getFontTTFName() {
        return this.fontTTFName;
    }
}
