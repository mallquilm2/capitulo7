package edu.cibertec.capitulo7.servlet;

import edu.cibertec.capitulo7.dao.DetalleVentaDAO;
import edu.cibertec.capitulo7.dao.ProductoDAO;
import edu.cibertec.capitulo7.dao.VentaDAO;
import edu.cibertec.capitulo7.dao.impl.DetalleVentaDAOImpl;
import edu.cibertec.capitulo7.dao.impl.ProductoDAOImpl;
import edu.cibertec.capitulo7.dao.impl.VentaDAOImpl;
import edu.cibertec.capitulo7.dto.DetalleVenta;
import edu.cibertec.capitulo7.dto.Producto;
import edu.cibertec.capitulo7.dto.Venta;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet(name = "DetalleServlet", urlPatterns = {"/DetalleVenta", "/view/detalles/DetalleVenta"})
public class DetalleVentaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String result = null;
        String target = null;

        DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAOImpl();
        ProductoDAO productoDAO = new ProductoDAOImpl();
        VentaDAO ventaDAO = new VentaDAOImpl();

        if(accion==null){
            result = "Solicitud no recibida";
        }else if(accion.equals("QRY")){
            List<Object[]> list =  detalleVentaDAO.detallesQry();
            if(list!=null){
                request.getSession().setAttribute("list",list);
                target = "index.jsp";
            }else {
                result = "Problemas en consulta";
                target = "index.jsp";
            }
        }else if(accion.equals("CBO")){
            List<Venta> list_ventas = ventaDAO.ventasQry();
            List<Producto> list_productos = productoDAO.productosQry();
            request.setAttribute("cbo_ventas",list_ventas);
            request.setAttribute("cbo_productos",list_productos);
            target = "detalleIns.jsp";
        }else if(accion.equals("INS")){
            DetalleVenta d = new DetalleVenta();
            result = valida(request,d);
            if(result==null){
                result = detalleVentaDAO.detalleIns(d);
            }
            if(result==null){
                target = "DetalleVenta?accion=QRY";
            }else{
                request.setAttribute("detalles",d);
                target="detallesIns.jsp";
            }
        }else {
            result="Solicitud no conocida";
        }
        if(result!=null){
            request.getSession().setAttribute("msg",result);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(target);
        dispatcher.forward(request,response);

    }

    private String valida(HttpServletRequest request, DetalleVenta d) {

        String result = "<ol>";
        Integer codigoVenta = Integer.parseInt(request.getParameter("codigoventa"));
        Integer codigoproducto = Integer.parseInt(request.getParameter("codigoproducto"));
        Double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        Double descuento = Double.parseDouble(request.getParameter("descuento"));

        if(cantidad==null || cantidad<=0){
            result+="<li>Ingrese cantidad correcta</li>";
        }
        if(descuento==null || descuento<=0){
            result+="<li>Ingrese descuento correcto</li>";
        }
        d.setCodigoproducto(codigoproducto);
        d.setCodigoventa(codigoVenta);
        d.setCantidad(cantidad);
        d.setDescuento(descuento);


        if(result.equals("<ol>")){
            result = null;
        }else{
            result+="</ol>";
        }
        return result;
    }


}
