package edu.cibertec.capitulo7.dao.impl;

import edu.cibertec.capitulo7.dao.SqlConecta;
import edu.cibertec.capitulo7.dao.VentaDAO;
import edu.cibertec.capitulo7.dto.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    private final SqlConecta conecta;

    public VentaDAOImpl() {
        this.conecta = new SqlConecta();
    }

    public List<Venta> ventasQry() {
        List<Venta> list = null;
        String sql = "SELECT codigoventa, cliente, fecha FROM venta";

        Connection cn = conecta.connection();
        if(cn!=null){
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                list = new ArrayList<Venta>();
                while (rs.next()){
                    Venta v = new Venta();
                    v.setCodigoventa(rs.getInt(1));
                    v.setCliente(rs.getString(2));
                    v.setFecha(rs.getTimestamp(3));
                    list.add(v);
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

        return list;
    }

    public String ventasIns(Venta venta) {

        String result = null;
        String sql = "INSERT INTO venta(codigoventa, cliente,fecha) VALUES (?,?,?)";
        Connection cn = conecta.connection();
        if(cn!=null){
            try {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, venta.getCodigoventa());
                ps.setString(2,venta.getCliente());
                ps.setTimestamp(3, venta.getFecha());
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

        return result;
    }

    public String ventasDel(List<Integer> ids) {

        String result = null;
        String sql = "DELETE FROM venta WHERE codigoventa=?";
        Connection cn = conecta.connection();
        if(cn!=null){
            try{
                PreparedStatement ps = cn.prepareStatement(sql);
                for(Integer cod: ids){
                    ps.setInt(1, cod);
                    int cots=ps.executeUpdate();
                    if(cots==0){
                        throw  new SQLException("No se borraron registros");
                    }
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

        return result;
    }

    public Venta ventasGet(Integer idventa) {

        Venta venta = null;
        String sql = "SELECT codigoventa, cliente,fecha FROM venta WHERE codigoventa=?";

        Connection cn = conecta.connection();
        if(cn!=null){
            try {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1,idventa);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    venta = new Venta();
                    venta.setCodigoventa(rs.getInt(1));
                    venta.setCliente(rs.getString(2));
                    venta.setFecha(rs.getTimestamp(3));
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

        return venta;
    }

    public String ventaUpd(Venta venta) {
        String result = null;
        String sql = "UPDATE venta SET cliente=?, fecha=? WHERE codigoventa=?";

        Connection cn = conecta.connection();
        if(cn!=null){
            try{
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, venta.getCliente());
                ps.setTimestamp(2, venta.getFecha());
                ps.setInt(3, venta.getCodigoventa());

                int ctos = ps.executeUpdate();
                if(ctos==0){
                    throw new SQLException("0 Filas afectadas");
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
}
