package edu.cibertec.capitulo7.dao;

import edu.cibertec.capitulo7.dto.DetalleVenta;

import java.util.List;

public interface DetalleVentaDAO {

    public List<Object[]> detallesQry();
    public String detalleIns(DetalleVenta detalleVenta);
    public String detalleDel(List<String> ids);
    public DetalleVenta detallesGet(String ids);
    public String detallesUpd(DetalleVenta detalleVenta);

}
