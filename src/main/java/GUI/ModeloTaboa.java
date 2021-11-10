/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author antonio
 */
public class ModeloTaboa extends AbstractTableModel{
    
    private ArrayList<String> empresas;
    private ArrayList<Double> valores;

    public ModeloTaboa(ArrayList<String> empresas, ArrayList<Double> valores) {
        this.empresas = empresas;
        this.valores = valores;
    }

    public ModeloTaboa() {
        this.empresas = new ArrayList();
        this.valores = new ArrayList();
               
    }
    


    @Override
    public int getRowCount() {
        return empresas.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int col){
    String nombre="";

    switch (col){
        case 0: nombre = "Empresa"; break;
        case 1: nombre = "Valor"; break;
    }
    return nombre;
    }
    
    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.Double.class; break;
        }
        return clase;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    
    @Override
    public Object getValueAt(int row, int col) {
        Object resultado=null;

        switch (col){
            case 0: resultado= empresas.get(row); break;
            case 1: resultado= valores.get(row);break;
        }
        return resultado;
    }
    
    public void setFilas(ArrayList<String> empresas, ArrayList<Double> valores){
        this.empresas = empresas;
        this.valores = valores; 
        fireTableDataChanged();
    }
    
    public String obterEmpresa(int i){
        return this.empresas.get(i);
    }
    
    public Double obterValor(int i){
        return this.valores.get(i);
    }
    
}
