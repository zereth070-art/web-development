import { useState } from "react";
import Login from "./Componentes/zonaCliente/login/login";
import Registro from "./Componentes/zonaCliente/registro/Registro";
import Contador from "./Componentes/Contador";
function App() {
    const [pantalla, setPantalla ] = useState('login');
    const [contador, setContador] = useState(0);
    return (
     <div>
        <Contador valor={contador} onIncrementar={() => setContador(contador +1)}/>

        { pantalla === 'login'
          ? <Login onCambiarPantalla={() => setPantalla('registro')} />
          : <Registro onCambiarPantalla={() => setPantalla('login')}/> 
        }
     </div>
    );
}

export default App;