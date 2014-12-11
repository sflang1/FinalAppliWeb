

package EJB;

import Entidad.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Diana Samboní
 */
@Stateless
@LocalBean
public class ProductoEJB {

    @PersistenceContext(unitName="ProyectoFinal-ejbPU")
    private EntityManager em;
    
    public List<Producto> obtenerProductosActivos()
    {
         List<Producto> products=new ArrayList<>();
        try
        {
            Query query=em.createQuery("SELECT p FROM Producto p WHERE p.state=1 ORDER BY p.name");
            products=query.getResultList();
            return products;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<Producto> obtenerProductosInactivos()
    {
        List<Producto> products=new ArrayList<>();
        try
        {
            Query query=em.createQuery("SELECT p FROM Producto p WHERE p.state=0 ORDER BY p.name");
            products=query.getResultList();
            return products;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        
    }
    
    public String editarProducto(Producto p)
    {
        Query query=em.createQuery("SELECT p FROM Producto p WHERE p.productcode'"+p.getProductcode()+"'");
        List<Producto> p1=query.getResultList();
            
            if(p1.size()>1)
            {
                return "Ya existe un producto con este código. Ingresa uno diferente";
            }
            else
            {
                try{
                    em.merge(p);
                }catch(Exception ex){
                    ex.printStackTrace();
                    return "false";
                }
                return "true";
            }
        
    }
    public boolean editarProducto1(Producto p)
    {
        try{
            em.merge(p);
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String ingresarProducto(Producto p)
    {
        Query query=em.createQuery("SELECT p FROM Producto p WHERE p.productcode'"+p.getProductcode()+"'");
        List<Producto> p1=query.getResultList();
            
            if(!p1.isEmpty())
            {
                return "Ya existe un producto con este código. Ingresa uno diferente";
            }
            else
            {
                try {
                em.persist(p);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return "false";
                }
                return "true";
            }
               
    }
    
}
