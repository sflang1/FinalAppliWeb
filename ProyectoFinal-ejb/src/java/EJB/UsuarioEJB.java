/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EJB;

import Entidad.Usuario;
import Entidad.Varios;
import java.util.ArrayList;
import java.util.List;
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
                    return 0;
                }
            }
            else
            {
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
    public List<Usuario> obtenerUsuarios()
    {
        List<Usuario> users=new ArrayList<>();
        try
        {
            Query query=em.createQuery("SELECT u FROM Usuario u ORDER BY u.lastname1");
            users=query.getResultList();
            return users;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    public String crearUsuario(Usuario u)
    {
        try
        {
            Query query=em.createQuery("SELECT u FROM Usuario u WHERE u.username='"+u.getUsername()+"'");
            Query query2=em.createQuery("SELECT u FROM Usuario u WHERE u.docid='"+u.getDocid()+"'");
            List<Usuario> u1=query.getResultList();
            List<Usuario> u2=query2.getResultList();
            if(!u1.isEmpty()||!u2.isEmpty())
            {
                if(!u1.isEmpty())
                {
                    return "Ya existe un usuario con ese username. Escoge otro";
                }
                else
                {
                    return "Ya existe un usuario con ese documento de identidad. Error";
                }
            }
            else
            {
                System.out.println("Se crea la consulta");
                System.out.println("INSERT INTO Usuario ('name1','name2',"
                    + "'lastname1','lastname2','docid','username','password','email','photo',"
                        + "'profile','active','pregunta','respuesta') VALUES("
                        + "'"+u.getName1()+"',"
                        + "'"+u.getName2()+"',"
                        + "'"+u.getLastname1()+"',"
                        + "'"+u.getLastname2()+"',"
                        + "'"+u.getDocid()+"',"
                        + "'"+u.getUsername()+"',"
                        + "sha1('"+u.getPassword()+"'),"
                        + "'"+u.getEmail()+"',"
                        + "'"+u.getPhoto()+"',"
                        + "'"+u.getProfile()+"',"
                        + "'"+u.getActive()+"',"
                        + "'"+u.getPregunta()+"',"
                        + "'"+u.getRespuesta()+"'"
                        + ");");
                Query query3=em.createNativeQuery("INSERT INTO Usuario (`name1`,`name2`,"
                    + "`lastname1`,`lastname2`,`docid`,`username`,`password`,`email`,`photo`,"
                        + "`profile`,`active`,`pregunta`,`respuesta`) VALUES("
                        + "'"+u.getName1()+"',"
                        + "'"+u.getName2()+"',"
                        + "'"+u.getLastname1()+"',"
                        + "'"+u.getLastname2()+"',"
                        + "'"+u.getDocid()+"',"
                        + "'"+u.getUsername()+"',"
                        + "sha1('"+u.getPassword()+"'),"
                        + "'"+u.getEmail()+"',"
                        + "'"+u.getPhoto()+"',"
                        + "'"+u.getProfile()+"',"
                        + "1,"
                        + "'"+u.getPregunta()+"',"
                        + "'"+u.getRespuesta()+"'"
                        + ");");
                int a=query3.executeUpdate();
                if(a>0)
                {
                    return "success";
                }
                else
                {
                    System.out.println("Error en la consulta");
                    return "Error en la consulta de actualización";
                }
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "Error de la B.D";
        }
    }
}
