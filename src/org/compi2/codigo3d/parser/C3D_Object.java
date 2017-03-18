package org.compi2.codigo3d.parser;

import java.util.ArrayList;


public class C3D_Object {
    Tipo_Objeto Tipo;
    final ArrayList<String> True_list;
    final ArrayList<String> False_list;
    String Cuerpo;
    String Valor;
    
    public C3D_Object (){
        True_list = new ArrayList<>();
        False_list = new ArrayList<>();
    }
    
    
    public void generarRelacional(C3D_Object iz, C3D_Object der, String operador){
        Tipo = Tipo_Objeto.Relacional;
        String resultado;
        String True = ControlC3D.generaEtiqueta();
        String False = ControlC3D.generaEtiqueta();
        resultado = "if("+iz.getValor()+operador+der.getValor()+") goto "+ True + "\n";
        resultado = resultado + "goto " + False + "\n";
        True_list.add(True);
        False_list.add(False);
        Cuerpo = iz.getCuerpo() + der.getCuerpo() + resultado;        
    }
    
    public void generarSimple(String Val){
        Tipo = Tipo_Objeto.Aritmetico;
        String resultado;
        Valor = ControlC3D.generaTemp();
        resultado = Valor + " = " + Val+";\n";
        Cuerpo = resultado;
    }
    
    public void generarAritmetico(C3D_Object iz, C3D_Object der, String operador){
        Tipo = Tipo_Objeto.Aritmetico;
        String resultado;
        Valor = ControlC3D.generaTemp();
        resultado = Valor + " = " + iz.getValor() + operador + der.getValor()+";\n";
        Cuerpo = iz.getCuerpo() + der.getCuerpo() + resultado;   
    }
    
    public void generarLogicoAnd(C3D_Object iz, C3D_Object der){
        Tipo = Tipo_Objeto.Logico;
        String resultado;
        String EtqTrue = "";
        for(String etq: iz.True_list){
            EtqTrue = EtqTrue + etq + ":\n";
        }        
        resultado = iz.Cuerpo + EtqTrue + der.Cuerpo;
        True_list.addAll(der.True_list);
        False_list.addAll(iz.False_list);
        False_list.addAll(der.False_list);
        Cuerpo = resultado;    
    }
    
    public void generarLogicoOr(C3D_Object iz, C3D_Object der){
        Tipo = Tipo_Objeto.Logico;
        String resultado;
        String EtqTrue = "";
        for(String etq: iz.False_list){
            EtqTrue = EtqTrue + etq + ":\n";
        }        
        resultado = iz.Cuerpo + EtqTrue + der.Cuerpo;
        True_list.addAll(iz.True_list);
        True_list.addAll(der.True_list);
        False_list.addAll(der.False_list);

        Cuerpo = resultado;    
    }    
    
    
    
    public void generarNegadoAritmetico(C3D_Object Val){
        Tipo = Tipo_Objeto.Aritmetico;
        String resultado;
        Valor = ControlC3D.generaTemp();
        resultado = Valor + " = -" + Val.Valor+";\n";
        Cuerpo = Val.Cuerpo + resultado;
    }    
    
    public void generarNegadoLogico(C3D_Object Val){
        Tipo = Val.Tipo;
        Cuerpo = Val.Cuerpo;
        False_list.addAll(Val.True_list);
        True_list.addAll(Val.False_list);
    }        
    
    
    public String getCuerpo(){
        return Cuerpo;
        
    }
    
    public String getValor(){
        return Valor;
    }
    
    
    public String Show(){
        if(Valor == null){
            String EtqTrue = "";
            String EtqFalse = "";
            for(String etq: True_list){
                EtqTrue = EtqTrue + etq + " ";
            }  
            for(String etq: False_list){
                EtqFalse = EtqFalse + etq + " ";
            }            
            return Cuerpo + "\n" +  "//T " +EtqTrue + "\n"+ "//F " +EtqFalse;
        }else{
            return Cuerpo + "\n" + "// final = "+ Valor;
        }
    }
    
}
