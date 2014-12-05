/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EJB;

import Entidad.Usuario;
import Entidad.Varios;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Asus Pc
 */
@Stateless
@LocalBean
public class UsuarioEJB 
{
    @PersistenceContext(unitName="ProyectoFinal-ejbPU")
    private EntityManager em;
    public int autenticarUsuario(String username,String password)
    {
        try
        {
            int a=0;
            Varios v=new Varios();
            Usuario u=new Usuario();
            String sha1="";
            Query query=em.createNativeQuery("UPDATE Varios SET valor=sha1('"+password+"') WHERE descripcion='sha1'");
            a=query.executeUpdate();
            if(a>0)
            {
                Query query2=em.createQuery("SELECT v FROM Varios v WHERE v.descripcion='sha1'");
                v=(Varios) query2.getSingleResult();
                sha1=v.getValor();
                System.out.println(sha1);
                Query query3=em.createQuery("SELECT u FROM Usuario u WHERE u.username='"+username+"'");
                u=(Usuario) query3.getSingleResult();
                if(sha1.equals(u.getPassword()))
                {
                    if(u.getProfile().equals("Administrador"))
                    {
                        return 2;
                    }
                    else
                    {
                        return 1;
                    }
                }
                else
                {
                    System.out.println("Autenticación incorrecta");
                    return 0;
                }
            }
            else
            {
                System.out.println("No se pudo obtener el sha1");
                return 0;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }
    }
    public Usuario obtenerUsuarioporUsername(String username)
    {
        Usuario u=new Usuario();
        try
        {
            Query query=em.createQuery("SELECT u FROM Usuario u WHERE u.username='"+username+"'");
            u=(Usuario) query.getSingleResult();
            return u;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    public boolean cambiarContrasena(Usuario u,String contrnueva)
    {
        try
        {
            int a;
            Query query=em.createNativeQuery("UPDATE Usuario SET password=sha1('"+contrnueva+"') WHERE username='"+u.getUsername()+"'");
            a=query.executeUpdate();
            if(a>0)
            {
                return true;
            }
            else
            {
                System.out.println("No se ha alterado ningún valor en la B.D");
                return false;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
}
