function redireccionaru(valor)
{
    var embed=document.getElementById("embed");
    var alto=window.innerHeight;
    var ancho=window.innerWidth;
    embed.style.height=Math.round(alto*0.7);
    embed.style.width=ancho;
    if(valor=="listar")
    {
        var embed = document.getElementById("embed");
        var fuente="IUlistarUsuarios.xhtml";
        var clone=embed.cloneNode(true);
        clone.setAttribute('src',fuente);
        embed.parentNode.replaceChild(clone,embed);
    }
}