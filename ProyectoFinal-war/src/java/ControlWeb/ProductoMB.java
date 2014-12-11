
package ControlWeb;

import EJB.ProductoEJB;
import Entidad.Producto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlDataTable;

/**
 *
 * @author Diana Samboní
 */
@ManagedBean
@RequestScoped
public class ProductoMB {

    @EJB
    private ProductoEJB proejb;
    private Producto producto;
    private List<Producto> productos;
    private HtmlDataTable dataTablePro;
    private HtmlDataTable dataTableIna;
    private String mensaje;
    private List<Boolean> stock;
    private Boolean inhabilitar;
   
    public ProductoMB() {
    }
    
    public String listarProductosActivos(){
        productos=proejb.obtenerProductosActivos();
        stock = new ArrayList<Boolean>();
        stock=null;
        mensaje="";
        for(int i=0;i<productos.size();i++)
        {
            if(productos.get(i).getAmount()>0)
            {
                inhabilitar=false;
                stock.add(inhabilitar);
            }
            else if(productos.get(i).getAmount()==0)
            {
                inhabilitar=true;
                stock.add(inhabilitar);
            }
        }
        
        return "ListarProductos.xhtml";
    }
    
    public String listarProductosInactivos()
    {
        productos=proejb.obtenerProductosInactivos();
        return "ProductosInactivos.xhtml";
    }
    
    public String iuModificarProductos(){
        producto=new Producto();
        producto=(Producto) dataTablePro.getRowData();
        return "editarProducto.xhtml";
    }
    
    public String modificarProducto()
    {
        Date fecha= new Date();
        producto.setUpdatedate(fecha);
        mensaje="";
        if(proejb.editarProducto(producto)=="true")
        {
            return listarProductosActivos();
        }
        else
        {
            mensaje = proejb.editarProducto(producto);
            return "editarProducto.xhtml";
        }
    }
    
    public String iuInhabilitarProducto()
    {
        producto=new Producto();
        producto=(Producto) dataTablePro.getRowData();
        Date fecha= new Date();
        producto.setUpdatedate(fecha);
        if(producto.getAmount()==0)
        {
            producto.setState(false);
            if(proejb.editarProducto1(producto)==true)
            {
                return listarProductosActivos();
            }
            else
            {
                mensaje="No se puede inhabilitar producto, error en la base de datos";
                return "errorProducto.xhtml";
            }
        }
        else
        {
            mensaje="No se puede inhabilitar producto, cantidad en stock diferente a cero";
            return listarProductosActivos();
        }
    }
    
    public String HabilitarProducto()
    {
        producto=new Producto();
        producto=(Producto) dataTablePro.getRowData();
        Date fecha= new Date();
        producto.setUpdatedate(fecha);
        producto.setState(true);
        if(proejb.editarProducto1(producto)==true)
        {
            mensaje="Producto habilitado exitosamente";
            return listarProductosInactivos();
        }
        else
        {
            mensaje="Error al habilitar el producto";
            return listarProductosActivos();
        }
        
    }
    
    
    public String verDetalles()
    {
        return "";
    }

    public String iuRegistrarProducto()
    {
        producto= new Producto();
        return "registrarProducto.xhtml";
    }
    
    public String registrarProducto()
    {
        Date fecha = new Date();
        producto.setUpdatedate(fecha);
        producto.setRegistrationdate(fecha);
        producto.setState(true);
        mensaje="";
        if(proejb.ingresarProducto(producto).equals("true"))
        {
            return "inicioEmpleado.xhtml";
            
        }
        else
        {
            mensaje=proejb.ingresarProducto(producto);
            return "registrarProducto.xhtml";
        }
    }
    
    public ProductoEJB getProejb() {
        return proejb;
    }

    public void setProejb(ProductoEJB proejb) {
        this.proejb = proejb;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Boolean> getStock() {
        return stock;
    }

    public void setStock(List<Boolean> stock) {
        this.stock = stock;
    }

    public Boolean isInhabilitar() {
        return inhabilitar;
    }

    public void setInhabilitar(Boolean inhabilitar) {
        this.inhabilitar = inhabilitar;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public HtmlDataTable getDataTablePro() {
        return dataTablePro;
    }

    public void setDataTablePro(HtmlDataTable dataTablePro) {
        this.dataTablePro = dataTablePro;
    }

    public HtmlDataTable getDataTableIna() {
        return dataTableIna;
    }

    public void setDataTableIna(HtmlDataTable dataTableIna) {
        this.dataTableIna = dataTableIna;
    }

    
    
}
