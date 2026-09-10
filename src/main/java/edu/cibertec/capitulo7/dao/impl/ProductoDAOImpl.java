package edu.cibertec.capitulo7.dao.impl;

import edu.cibertec.capitulo7.dao.ProductoDAO;
import edu.cibertec.capitulo7.dao.SqlConecta;
import edu.cibertec.capitulo7.dto.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private final SqlConecta conecta;

    public ProductoDAOImpl() {
        this.conecta = new SqlConecta();
    }

    public List<Producto> productosQry() {
        List<Producto> list = null;
        String sql = "SELECT codigoproducto,nombre,precio FROM producto";
        Connection cn = conecta.connection();

        if(cn!=null){
            try{
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                list = new ArrayList<Producto>();
                while (rs.next()){
                    Producto p = new Producto();
                    p.setCodigoproducto(rs.getInt(1));
                    p.setNombre(rs.getString(2));
                    p.setPrecio(rs.getDouble(3));
                    list.add(p);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return list;
    }

    public String productosIns(Producto productos) {
        String result = null;
        String sql = "INSERT INTO producto(codigoproducto, nombre, precio) VALUES (?,?,?)";
        Connection cn = conecta.connection();
    if(cn!=null){
        try{
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1,productos.getCodigoproducto());
            ps.setString(2, productos.getNombre());
            ps.setDouble(3,productos.getPrecio());

            int ctos = ps.executeUpdate();
            if(ctos==0){
                throw new SQLException("0 filas afectadas");
            }

        }catch (Exception e){
            result = e.getMessage();
        }finally {
            try {
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
        return result;
    }

    public String productosDel(List<Integer> ids) {

        String result = null;
        String sql = "DELETE FROM producto WHERE codigoproducto=?";

        Connection cn = conecta.connection();
        if(cn!=null){
            try{
                PreparedStatement ps = cn.prepareStatement(sql);
                for (Integer cod: ids){
                    ps.setInt(1,cod);
                    int ctos = ps.executeUpdate();
                    if(ctos==0){
                        throw new SQLException("Código "+cod+" Incorrecto");
                    }
                }
            }catch (SQLException e){
                result = e.getMessage();
            }finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return result;
    }

    public Producto productosGet(Integer idproducto) {
        Producto producto = null;
        String sql = "SELECT codigoproducto, nombre, precio FROM producto WHERE codigoproducto=?";

        Connection cn = conecta.connection();
        if(cn!=null){
            try {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1,idproducto);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    producto = new Producto();
                    producto.setCodigoproducto(rs.getInt(1));
                    producto.setNombre(rs.getString(2));
                    producto.setPrecio(rs.getDouble(3));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return producto;
    }

    public String productosUpd(Producto productos) {
        String result = null;
        String sql = "UPDATE producto SET nombre=?, precio=? WHERE codigoproducto=?";
        Connection cn = conecta.connection();
        if(cn!=null){
            try {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, productos.getNombre());
                ps.setDouble(3, productos.getCodigoproducto());

                int ctos = ps.executeUpdate();
                if(ctos==0){
                    throw new SQLException("0 filas afectadas.");
                }

            }catch(SQLException e){
                result = e.getMessage();
            }finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }
}
