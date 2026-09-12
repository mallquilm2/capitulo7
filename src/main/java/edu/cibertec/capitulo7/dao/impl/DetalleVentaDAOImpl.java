package edu.cibertec.capitulo7.dao.impl;

import edu.cibertec.capitulo7.dao.DetalleVentaDAO;
import edu.cibertec.capitulo7.dao.SqlConecta;
import edu.cibertec.capitulo7.dto.DetalleVenta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    private final SqlConecta conecta;

    public DetalleVentaDAOImpl() {
        conecta = new SqlConecta();
    }

    public DetalleVentaDAOImpl(SqlConecta conecta) {
        this.conecta = new SqlConecta();
    }

    public List<Object[]> detallesQry() {

        List<Object[]> list = null;
        String sql = "SELECT detalleventa.codigoventa,"
                     +"detalleventa.codigoproducto,"
                     +"venta.cliente,"
                     +"producto.nombre,"
                      +"producto.precio,"
                     +"detalleventa.cantidad,"
                     +"detalleventa.descuento "
                     +"FROM detalleventa "
                     +"INNER JOIN venta "
                     +"ON detalleventa.codigoventa = venta.codigoventa "
                     +"INNER JOIN producto "
                     +"ON detalleventa.codigoproducto=producto.codigoproducto "
                     +"ORDER BY detalleventa.codigoventa, detalleventa.codigoproducto";

        Connection cn = conecta.connection();
        if(cn!=null){
            try{
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                list = new ArrayList<Object[]>();
                while (rs.next()){
                    Object[] reg = new Object[7];
                    reg[0] = rs.getInt(1);
                    reg[1]= rs.getInt(2);
                    reg[2]= rs.getString(3);
                    reg[3]= rs.getString(4);
                    reg[4]= rs.getDouble(5);
                    reg[5]= rs.getDouble(6);
                    reg[6]= rs.getDouble(7);
                    list.add(reg);
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

    public String detalleIns(DetalleVenta detalleVenta) {
        String result = null;
        String sql = "INSERT INTO detalleventa(codigoventa, codigoproducto, cantidad,descuento) VALUES (?,?,?,?)";

        Connection cn = conecta.connection();
        if(cn!=null){
            try{
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, detalleVenta.getCodigoventa());
                ps.setInt(2,detalleVenta.getCodigoproducto());
                ps.setDouble(3,detalleVenta.getCantidad());
                ps.setDouble(4,detalleVenta.getDescuento());

                int ctos = ps.executeUpdate();
                if(ctos==0){
                    throw new SQLException("0 filas afectadas");
                }

            }catch(SQLException e){
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

    public String detalleDel(List<String> ids) {
        return null;
    }

    public DetalleVenta detallesGet(String ids) {
        return null;
    }

    public String detallesUpd(DetalleVenta detalleVenta) {
        return null;
    }
}
