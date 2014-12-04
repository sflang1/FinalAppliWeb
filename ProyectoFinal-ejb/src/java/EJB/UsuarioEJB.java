/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EJB;

import Entidad.Usuario;
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
            System.out.println("SELECT sha1('"+password+"') FROM Usuario");
            Query query=em.createNativeQuery("SELECT sha1('"+password+"') FROM Usuario");
            System.out.println(query.getFirstResult());
            Usuario u=new Usuario();
//            u.setIduser((int) query.getParameterValue("iduser"));
//            u.setName1((String) query.getParameterValue("name1"));
//            u.setName2((String) query.getParameterValue("name2"));
//            u.setLastname1((String) query.getParameterValue("lastname1"));
//            u.setLastname2((String) query.getParameterValue("lastname2"));
//            u.setDocid((String) query.getParameterValue("docid"));
//            u.setUsername((String) query.getParameterValue("username"));
//            u.setPassword((String) query.getParameterValue("password"));
//            u.setEmail((String) query.getParameterValue("email"));
//            u.setPhoto((String) query.getParameterValue("photo"));
//            u.setProfile((String) query.getParameterValue("profile"));
//            u.setActive((boolean) query.getParameterValue("active"));
//            u.setPregunta((String) query.getParameterValue("pregunta"));
//            u.setRespuesta((String) query.getParameterValue("respuesta"));
            if(u.getProfile().equals("Administrador"))
            {
                return 2;
            }
            else
            {
                return 1;
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
