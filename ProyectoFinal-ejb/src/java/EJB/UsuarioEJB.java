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
            Query query=em.createQuery("SELECT u FROM Usuario u WHERE u.username='"+username+"' AND u.password='"+password+"'");
            Usuario u=new Usuario();
            u=(Usuario) query.getSingleResult();
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
            return 0;
        }
        
    }
}
