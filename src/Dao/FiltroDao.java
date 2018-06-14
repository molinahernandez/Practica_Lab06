/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexion.Conexion;
import Modelo.Filtro;
import com.sun.istack.internal.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author LN710Q
 */
public class FiltroDao implements metodos<Filtro> {
    private static final String SQL_INSERT = "INSERT INTO filtros_aceite (codFiltro,marca,stock,existencia)VALUES(?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE filtros_aceite SET marca =? ,stock=?,existencia=? WHERE codFiltro=?";
    private static final String SQL_DELETE = "DELETE FROM filtros_aceite WHERE codFiltro=?";
    private static final String SQL_READ = "SELECT * FROM filtros_aceite WHERE codFiltro=?";
    private static final String SQL_READALL = "SELECT * FROM filtros_aceite";
    
    private static final Conexion con = Conexion.conectar();
    
    public boolean create(Filtro g){
        PreparedStatement ps;
        try{
            ps = con.getCnx().prepareStatement(SQL_INSERT);
            ps.setString(1, g.getCodigo());
            ps.setString(2, g.getMarca());
            ps.setInt(3, g.getStock());
            ps.setBoolean(4, true);
            if (ps.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(FiltroDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.cerrarConexion();
        }
        return false;
    }
    
    public boolean delete(Object key) throws SQLException{
        PreparedStatement ps;
        try{
            ps= con.getCnx().prepareStatement(SQL_DELETE);
            ps.setString(1, key.toString());
            
            if(ps.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(FiltroDao.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            con.cerrarConexion();
        }
        return false;
    }
    
    public Filtro read(Object key){
        Filtro f=null;
        PreparedStatement ps;
        ResultsSet rs;
        try{
            ps= con.getCnx().prepareStatement(SQL_READ);
            ps.setString(1, key.toString());
            
            rs=ps.executeQuery();
            
            while (rs.next()){
                f= new Filtro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
                
            }
            rs.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}
