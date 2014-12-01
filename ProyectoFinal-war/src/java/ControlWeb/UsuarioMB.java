/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ControlWeb;

import EJB.UsuarioEJB;
import Entidad.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Asus Pc
 */
@ManagedBean
@SessionScoped
public class UsuarioMB {

    @EJB
    private UsuarioEJB uejb;
    private Usuario usuario;
    private Usuario sesionUsuario;
    private List<Usuario> usuarios;
    private String username;
    private String password;
    public UsuarioMB() 
    {
        
    }
    public String autenticar()
    {
        String redir="";
        if(uejb.autenticarUsuario(username, password)==2)
        {
            redir="inicio.xhtml";
        }
        if(uejb.autenticarUsuario(username, password)==1)
        {
            redir="inicioEmpleado.xhtml";
        }
        else
        {
            redir="error.xhtml";
        }
        return redir;
    }
    
    //----------------GETTERS y SETTERS---------------
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getSesionUsuario() {
        return sesionUsuario;
    }

    public void setSesionUsuario(Usuario sesionUsuario) {
        this.sesionUsuario = sesionUsuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
