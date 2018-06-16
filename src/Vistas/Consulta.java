/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Dao.FiltroDao;
import Modelo.Filtro;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LN710Q
 */
public class Consulta extends JFrame {
    public JLabel lblCodigo, lblMarca, lblStock, lblExistencia;
    public JTextField codigo, descripcion, stock;
    public JComboBox marca;
    
    ButtonGroup existencia = new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;
    
    public JPanel table;
    public JButton  buscar, eliminar, insertar, limpiar, actualizar;
    
    private static final int ANCHOC=130, ALTOC=30;
    
    DefaultTableModel tm;
    
    public Consulta(){
        super ("Inventario");
        SetDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        aregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        Container.add(lblCodigo);
        Container.add(lblMarca);
        Container.add(lblStock);
        Container.add(lblExistencia);
        Container.add(codigo);
        Container.add(marca);
        Container.add(stock);
        Container.add(si);
        Container.add(no);
        Container.add(buscar);
        Container.add(insertar);
        Container.add(actualizar);
        Container.add(eliminar);
        Container.add(limpiar);
        Container.add(table);
        setSize(600, 600);
        eventos();
    }
    
    public final void agregarLabels(){
        lblCodigo= new JLabel("Codigo");
        lblCodigo= new JLabel("Marca");
        lblCodigo= new JLabel("Stock");
        lblCodigo= new JLabel("Stock en la tienda");
        lblCodigo.setBounds(10, 10, ANCHOC, ALTOC);
        lblMarca.setBounds(10, 60, ANCHOC, ALTOC);
        lblStock.setBounds(10, 100, ANCHOC, ALTOC);
        lblExistencia.setBounds(10, 140, ANCHOC, ALTOC);
    }
    
    public final void Formulario(){
        codigo = new JTextField();
        marca = new JComboBox();
        stock = new JTextField();
        si = new JRadioButton("si", true);
        no = new JRadioButton("no");
        resultados = new JTable();
        buscar = new JButton("Buscar");
        insertar = new JButton("Insertar");
        eliminar = new JButton("Eliminar");
        actualizar = new JButton("Actualizar");
        limpiar = new JButton("Limpiar");
        
        table = new JPanel();
        marca.addItem("FRAM");
        marca.addItem("WIX");
        marca.addItem("Luber Finer");
        marca.addItem("OSK");
        existencia = new ButtonGroup();
        existencia.add(si);
        existencia.add(no);
        
        codigo.setBounds(140, 10, ANCHOC, ALTOC);
        marca.setBounds(140, 60, ANCHOC, ALTOC);
        stock.setBounds(140, 100, ANCHOC, ALTOC);
        si.setBounds(140, 140, 50, ALTOC);
        no.setBounds(210, 140, 50,  ALTOC);
        
        buscar.setBounds(300, 10, ANCHOC, ALTOC);
        insertar.setBounds(10, 210, ANCHOC, ALTOC);
        actualizar.setBounds(150, 210, ANCHOC, ALTOC);
        eliminar.setBounds(300, 210, ANCHOC, ALTOC);
        limpiar.setBounds(450, 210, ANCHOC, ALTOC);
        resultados = new JTable();
        table.setBounds(10, 250, 500, 200);
        table.add(new JScrollPane(resultados));
        
    }
    
    public void llenarTabla(){
        tm = new DefaultTableModel(){
            public Class<?> getColumnClass(int column){
                switch (column){
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        tm.addColumn("Codigo");
        tm.addColumn("Marca");
        tm.addColumn("Stock");
        tm.addColumn("Stock en la Sucursal");
        
        FiltroDao fd = new FiltroDao();
        ArrayList<Filtro> filtros = fd.readAll();
        for (Filtro fi : filtros){
            tm.addRow(new object[]{fi.getCodigo(), fi.getMarca(), fi.getStock(), fi.getExistencia()});
        }
        resultados.setModel(tm);
    }
    
    public void eventos(){
        insertar.addActionListener(new ActionListener(){
            FiltroDao fd= new FiltroDao();
            Filtro f = new Filtro(codigo.getText(), marca.getSelectedItem().toString(),
            Integer.parseInt(stock.getText()), true); 
            
            if(no.isSelected()){
  {
                f.setExistencia(false);
                return null;
            }
            if(fd.create(f)){
                JOptionPane.showMessageDialog(null, "Filtro regitrado con exito");
                limpiarCampos();
                llenarCampos();
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de crear el filtro");
            }
    }
          
        });
    
}
