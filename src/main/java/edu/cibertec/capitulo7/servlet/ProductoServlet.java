package edu.cibertec.capitulo7.servlet;

import edu.cibertec.capitulo7.dao.ProductoDAO;
import edu.cibertec.capitulo7.dao.impl.ProductoDAOImpl;
import edu.cibertec.capitulo7.dto.Producto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/Productos","/view/productos/Productos"})
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String result = null;
        String target = null;

        ProductoDAO daoProductos = new ProductoDAOImpl();

        if(accion==null){
            result = "Solicitud no recibida";
        }else if(accion.equals("QRY")){
            List<Producto> list = daoProductos.productosQry();
            if(list!=null){
                request.getSession().setAttribute("list",list);
                target = "index.jsp";
            }else {
                target = "index.jsp";
                result = "Problemas en consulta";
            }
        }else if(accion.equals("INS")){
            Producto p = new Producto();
            result = valida(request,p);
            if(result==null){
                result = daoProductos.productosIns(p);
            }

            if(result==null){
                target = "Productos?accion=QRY";
            }else{
                request.setAttribute("productos",p);
                target = "productosIns.jsp";
            }
        }else if(accion.equals("DEL")){
            String _ids = request.getParameter("ids");
            List<Integer>  Idel = ids(_ids);
            if(Idel==null){
                result = "Lista de idproductos incorrecta";
            }else{
                result = daoProductos.productosDel(Idel);
            }
                target = "Productos?accion=QRY";
        }else if(accion.equals("GET")){
            Integer codigoproducto = Integer.parseInt(request.getParameter("cod"));
            if(codigoproducto!=null){
                Producto p = daoProductos.productosGet(codigoproducto);
                if(p!=null){
                    request.setAttribute("productos",p);
                    target = "productosUpd.jsp";
                }else{
                    result = "Codigo incorrecto de Producto";
                    target = "Productos?accion=QRY";
                }
            }
        }else if(accion.equals("UPD")){
            Producto p = new Producto();
            result = valida(request,p);
            if(result==null){
                result=daoProductos.productosUpd(p);
            }
            if(result==null){
                target = "Productos?accion=QRY";
            }else{
                request.setAttribute("productos",p);
                target = "productosUpd.jsp";
            }
        }else {
            result = "Solicitud no reconocida";
        }

        if(result!=null){
            request.getSession().setAttribute("msg",result);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(target);
        dispatcher.forward(request,response);

    }

    private String valida(HttpServletRequest request, Producto p) {
        String result = "<ol>";
        Integer codigoproducto = Integer.parseInt(request.getParameter("codigoproducto"));
        String nombre = request.getParameter("nombre");
        Double precio = Double.parseDouble(request.getParameter("precio"));

        if(codigoproducto==null){
            result+="<li>Codigo incorrecto</li>";
        }
        if(nombre==null || nombre.trim().length()==0){
            result+="<li>Ingrese Nombre de Producto</li>";
        }
        if(precio == null){
            result+="<li>Precio incorrecto</li>";
        }else {
            if(precio<=0){
                result+="<li>Precio debe ser mayor que CERO";
            }
        }
        p.setCodigoproducto(codigoproducto);
        p.setNombre(nombre);
        p.setPrecio(precio);

        if(result.equals("<ol>")){
            result = null;
        }else {
            result+="</ol>";
        }

        return result;
    }

    private List<Integer> ids (String _ids){
        List<Integer> list = null;
        if(_ids!=null && _ids.trim().length()>0){
            String[] id = _ids.split(",");
            list = new LinkedList<>();
            for(String ix: id){
                Integer x = Integer.parseInt(ix);
                if(x!=null){
                    list.add(x);
                }else{
                    list = null;
                    break;
                }
            }
        }
        return list;
    }
}
