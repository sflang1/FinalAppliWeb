function validar()
{
    var cont1=document.getElementById("cont1").value;
    var cont2=document.getElementById("cont2").value;
    if(cont1==cont2)
    {
        alert("Contraseñas iguales");
        return true;
    }
    else
    {
        alert("Contraseñas distintas");
        return false;
    }
}
function prueba()
{
    alert("Entra a la funcion");
}


