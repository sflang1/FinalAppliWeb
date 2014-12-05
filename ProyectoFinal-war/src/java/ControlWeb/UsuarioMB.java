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
    private String pregunta;
    private String respuesta;
    private String contrasenanueva;
    private String mensaje;
    public UsuarioMB() 
    {
        
    }
    public String autenticar()
    {
        String redir="";
        System.out.println(uejb.autenticarUsuario(username, password));
        if(uejb.autenticarUsuario(username, password)==2)
        {
            sesionUsuario=uejb.obtenerUsuarioporUsername(username);
            redir="inicio.xhtml";
        }
        else
        {
            if(uejb.autenticarUsuario(username, password)==1)
            {
                sesionUsuario=uejb.obtenerUsuarioporUsername(username);
                redir="inicioEmpleado.xhtml";
            }
            else
            {
                mensaje="La autenticación no fue exitosa";
                redir="error.xhtml";
            }
        }
        return redir;
    }
    public String mostrarRecuperarContrasena()
    {
        username="";
        return "recuperarContrasena.xhtml";
    }
    public String mostrarPregunta()
    {
        Usuario u=new Usuario();
        u=uejb.obtenerUsuarioporUsername(username);
        String redir="";
        if(!u.equals(null))
        {
            pregunta=u.getPregunta();
            usuario=u;
            respuesta="";
            mensaje="";
            redir="pregunta.xhtml";
        }
        else
        {
            mensaje="No estás en la base de datos";
            redir="error.xhtml";
        }
        return redir;
    }
    public String recuperarContrasena()
    {
        String redir="";
        if(respuesta.equals(usuario.getRespuesta()))
        {
            redir="nuevacontrasena.xhtml";
            System.out.println("El mensaje es: "+mensaje);
        }
        else
        {
            redir="pregunta.xhtml";
            mensaje="Has respondido incorrectamente la pregunta.";
            System.out.println("Responde incorrectamente. El mensaje es: "+mensaje+".");
                    
        }
        System.out.println(""+ "La redirección es: "+redir);
        return redir;
    }
    public String cambiarContrasena()
    {
        if(uejb.cambiarContrasena(usuario, contrasenanueva))
        {
            mensaje="Contraseña cambiada";
        }
        else
        {
            mensaje="Contraseña no cambiada";
        }
        return "error.xhtml";
    }
    public String logout()
    {
        sesionUsuario=new Usuario();
        return "index.xhtml";
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

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    

    public String getContrasenanueva() {
        return contrasenanueva;
    }

    public void setContrasenanueva(String contrasenanueva) {
        this.contrasenanueva = contrasenanueva;
    }

    //---------------FIN GETTERS Y SETTERS--------
}
