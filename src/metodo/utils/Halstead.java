package metodo.utils;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import metodo.Metodo;
public class Halstead {
	private final int TAM = 1000;
	private String [] operadores1= new String[]{"\\{","\\++","\\+=","\\*","\\[","\\(","\\--","\\==","\\!=","\\<=","\\>=","\\;","\\&&","\\|"};
	private String [] operadores2= new String[]{"\\+","\\-","\\="};
	private String [] operadores3= new String[]{"if","for","while","switch"};
	private String [] tipoDeDato={"int","char","String","boolean","float","double"};
	private String [] operandos = new String [400];
	private String [] operandos2 = new String [TAM];
	private int[] cantoperadoresReales1= new int[40]; 
	private int[] cantoperadoresReales2= new int[10]; 
	private int[] cantoperadoresReales3= new int[10]; 
	private int[] cantoperandos= new int[1000];
	private int flag=2;
	private int k=0;
	private int N1=0;
	private int N2=0;
	private int n1=0;
	private int n2=0;
	private int n=0;
	private int N=0;
	private double volumen=0;
	private int cantidadOperandosUnicos=0;
	public int getN1() {
		return N1;
	}
	public int getN2() {
		return N2;
	}
	public int get_n2() {
		return n2;
	}
	public int get_n1() {
		return n1;
	}
	
	public int get__n() {
		return n;
	}
	public int getN() {
		return N;
	}
	public Halstead(Metodo metodo) {
	  	String definicion[]=metodo.getDefinicion().clone(); 
	  	//System.out.println(definicion+"\n");
		for (int i = 0; i < metodo.getDefinicion().length; i++) {
	  		for (int k = 0; k < tipoDeDato.length; k++) {
	  			definicion[i]=regexChecker2(tipoDeDato[k]+"[\\s][a-z0-9A-Z]*", metodo.getDefinicion()[i]);
				int indice=definicion[i].indexOf(tipoDeDato[k]+" ");
				if(indice>-1){
					indice+=tipoDeDato[k].length()+1;
					guardarOperando(definicion[i],indice);
				}
	  		}
	  		regexChecker("[\\d]*\\d[\\d]*",metodo.getDefinicion()[i]);
	  	 }	  	 
	  	definicion= metodo.getDefinicion().clone();
	  	for (int j = 0; j < metodo.getDefinicion().length; j++){	
	  		flag=1;
	  		 for(int i=0;i<operadores1.length;i++){	  			  
	  			regexChecker(operadores1[i],definicion[j]);  
	  		 }
	  		for(int i=0;i<operadores2.length;i++){	  			 	  		  
	  			regexChecker("(\\d|\\w|\\s)"+operadores2[i]+"(\\d|\\w|\\s)",definicion[j]);
	  		 }
	  		flag=3;
	  		for(int i=0;i<operadores3.length;i++){	  			  
		  		regexChecker("(\\W|^)"+operadores3[i]+"(\\W|$)",definicion[j]);  
		  	 }
	  	flag=0;	 
	  		for(int i=0;i<cantidadOperandosUnicos;i++){		
		  		regexChecker("\\b"+operandos[i]+"(\\W|\\s|;|$)",definicion[j]);  
		  	 }
	  	 }
	 	for(int i=0;i<operadores1.length;i++){
	  		//System.out.print(cantoperadoresReales1[i]);
	  		//System.out.println(operadores1[i]); 
	  		N1+=cantoperadoresReales1[i];
	  		if(cantoperadoresReales1[i]!=0)
	  			n1++;
	 	}
	  	cantoperadoresReales1[14]=cantoperadoresReales1[14]/2;
	  	for(int i=0;i<operadores2.length;i++){
	  		//System.out.print(cantoperadoresReales2[i]);
	  		//System.out.println(operadores2[i]); 
	  		N1+=cantoperadoresReales2[i];
	  		if(cantoperadoresReales2[i]!=0)
	  			n1++;
	  	}
	  	for(int i=0;i<operadores3.length;i++){
	  		//System.out.print(cantoperadoresReales3[i]);
	  		//System.out.println(operadores3[i]); 	
	  		N1+=cantoperadoresReales3[i];
	  		if(cantoperadoresReales3[i]!=0)
	  			n1++;
	  	}
	  	for(int i=0;i<cantidadOperandosUnicos;i++){
	  		//System.out.print(operandos[i]);
	  		//System.out.println(cantoperandos[i]);
	  		N2+=cantoperandos[i];
	  	}
		for(int i=0;i<k;i++){
	  		//System.out.println(operandos2[i]);
	  	}
		N2+=k;
		n2+=cantidadOperandosUnicos;	
		n2+=contarOperandosLiterales(operandos2,k);
		N=N1+N2;
		n=n1+n2;
		volumen=N*(Math.log(n)/Math.log(2));
		//System.out.println("N1 ES:"+N1);
		//System.out.println("n1 ES:"+n1);
		//System.out.println("N2 ES:"+N2);
		//System.out.println("n2 ES:"+n2);
		//System.out.println("El volumen es:"+volumen);	
	}
	public double getVolumen() {
		return Math.rint(volumen);
	}
	public int contarOperandosLiterales(String vectorOperandosLiterales[], int limite){
		  HashMap<String, Integer> operandosLiterales = new HashMap <String, Integer> ();
		  for (int i = 0; i < limite; i++) {
		   operandosLiterales.put(vectorOperandosLiterales[i],1);
		  }
		  return operandosLiterales.size();
		 }
	private void guardarOperando(String linea,int indice){ 
		String vector []=linea.substring(indice).split(",");
		for (int i = 0; i < vector.length; i++) {
				vector[i]=vector[i].replace(";","");
				operandos[cantidadOperandosUnicos]=vector[i];
				//System.out.println("Operando :" +vector[i]);
				cantidadOperandosUnicos++;
			}
		}
	public  void buscar(String buscar){
		for(int i=0;i<operadores1.length;i++){
			if(operadores1[i].equals(buscar)){
				cantoperadoresReales1[i]++;
			}		
		}
		
		for(int i=0;i<operadores3.length;i++){
			if((operadores3[i]+"(").equals(buscar)){
				cantoperadoresReales3[i]++;
			}	
		if((operadores3[i]).equals(buscar)){
				cantoperadoresReales3[i]++;
			}	
		}
		
		for(int i=0;i<cantidadOperandosUnicos;i++){
			if(operandos[i].equals(buscar)){
				cantoperandos[i]++;
			}		
		}
		if(buscar.length()>3)
			buscar="\\"+buscar.charAt(2);
		for(int i=0;i<operadores2.length;i++){
			if(operadores2[i].equals(buscar)){
				cantoperadoresReales2[i]++;
			}
		}
	}
	public void regexChecker(String theRegex, String str2Check){
        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher( str2Check );     
        while ( regexMatcher.find() ){
            if (regexMatcher.group().length() != 0){
                //System.out.println( regexMatcher.group().trim() );		                             
            	if(flag==1)
            		buscar("\\"+regexMatcher.group().trim());
            	else if(flag==0)
            		buscar(regexMatcher.group().trim().substring(0, regexMatcher.group().trim().length()-1));
            	else if(flag==2)
            	{
            		operandos2[k]=regexMatcher.group().trim();
            		k++;
            	}
            	else if(flag==3)
            	{
            		buscar(regexMatcher.group().trim());
            	}
            }
        }
    }
	public String regexChecker2(String theRegex, String str2Check){
        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher( str2Check );     
        while ( regexMatcher.find() )
        {
            if (regexMatcher.group().length() != 0){
                //System.out.println( regexMatcher.group().trim() );		                             
            	 return regexMatcher.group().trim();
            }
           
        }
		return str2Check;    
    }
}
