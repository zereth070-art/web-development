function recogerDatos(){
    let datos = {};
    const forro = document.getElementById("forro");
    const camisa = document.getElementById("camisa");
    const observaciones = document.getElementById("observaciones");

    datos.cliente = {
        nombre: document.getElementById("nombre").value,
        email: document.getElementById("email").value,
        seccion: document.getElementById("seccion").value,
    }

    if (forro.checked) {
        datos.forro = {
            tallaForro: document.getElementById("tallaForro").value,
            cantidadForro: Number(document.getElementById("cantidadForro").value),
        }
    }
    
    if (camisa.checked) {
        datos.camisa = {
            tallaCamisa: document.getElementById("tallaCamisa").value,
            cantidadCamisa: Number(document.getElementById("cantidadCamisa").value),
        }
    }

    if (observaciones.value !== "") {
        datos.observaciones = observaciones.value;
    }

    return datos;
}

document.getElementById("pedidoForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const paquete = recogerDatos();
  const res = await fetch("/api/pedidos", {
    method: "POST",
    headers: {"Content-Type" : "application/json "},
    body:  JSON.stringify(paquete),
  });
  if (!res.ok) {
    let msg = "no se puede mandar el formulario";
    try {
        const err = await res.json();
        if (err.erros) msg = Object.values(err.erros).join(". ");

    } catch (_) { }
  }
});


const datosForro = document.getElementById("datosForro");
const datosCamisa = document.getElementById("datosCamisa");

function actualizarFormulario() {
  datosForro.hidden = !forro.checked;
  datosCamisa.hidden = !camisa.checked;
}

forro.addEventListener("change", actualizarFormulario);
camisa.addEventListener("change", actualizarFormulario);