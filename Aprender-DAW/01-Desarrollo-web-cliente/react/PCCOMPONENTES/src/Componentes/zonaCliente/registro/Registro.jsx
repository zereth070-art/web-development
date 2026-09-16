import './Registro.css';              // estilos propios del componente
import { useState } from 'react';     // trae el hook useState (estado + re-render) de React
import { validateEmail } from '../../../utils/validaciones';
function Registro() {
     // —— ESTADO DEL FORMULARIO ——
     // Cada campo tiene su useState: [valorActual, funcionQueLoCambia]
     // valorActual  -> para LEER  (va en value del input)
     // funcionQueLoCambia -> para ESCRIBIR (va en onChange del input)
     const [nombre, setNombre] = useState('');        // estado del Nombre
     const [email, setEmail] = useState('');          // estado del E-mail
     const [password, setPassword] = useState('');    // estado de la Contraseña
     const [repetir, setRepetir] = useState('');      // estado de Repetir contraseña
     const [errores, setErrores] = useState({});
     let [exito, setExito] = useState(false);

     // —— MANEJADOR DEL ENVÍO ——
     // Función que tú defines y el <form> llama vía onSubmit al pulsar "Crear cuenta".
     function handleSubmit(e) {
          e.preventDefault();
          const nuevosErrores = validar();
          if (Object.keys(nuevosErrores).length > 0) {
               setErrores(nuevosErrores);
          } else {
               setExito(true);
          }
     }

  

     const validatePassword = (password, repetir) => {
          return repetir === password;
     }

     function validar() {
          const nuevosErrores = {};
          if (nombre.trim() === '') nuevosErrores.nombre = 'El nombre es obligatorio';
          if (!validateEmail(email)) nuevosErrores.email = 'El email no es valido';
          if (!validatePassword(password, repetir)) nuevosErrores.password = 'Las contraseñas deben coincidir';
          if (password.trim() === '') nuevosErrores.password = 'La contraseña es obligatoria';
          if (repetir.trim() === '') nuevosErrores.repetir = 'Repite la contraseña';
          return nuevosErrores;
     }

     if (exito) {
          return (
               <di className="exito">
                    <h2>Cuenta creada con exito!!</h2>
                    <p>Ya puedes inciar sesion</p>
               </di>

          );
     }
     // —— VISTA (JSX) ——
     // El return devuelve SIEMPRE un único nodo raíz (aquí, el <form>).
     return (
          // onSubmit en el FORM (no en el botón); así también funciona al pulsar Enter
          <form onSubmit={handleSubmit} noValidate>

               {/* INPUT CONTROLADO: value lee del estado y onChange lo actualiza; React repinta solo */}
               <input
                    type='text'
                    placeholder='Nombre'
                    value={nombre}
                    onChange={(e) => setNombre(e.target.value)}

               />
               {errores.nombre && <p className="error">{errores.nombre}</p>}
               <input
                    type='email'
                    placeholder='E-mail*'
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
               />
               {errores.email && <p className="error">{errores.email}</p>}
               <input
                    type='password'
                    placeholder='Contraseña*'
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}

               />
               {errores.password && <p className="error">{errores.password}</p>}
               <input
                    type='password'
                    placeholder='Repetir contraseña*'
                    value={repetir}
                    onChange={(e) => setRepetir(e.target.value)}

               />
               <p>He leido y aceptado la <a href="/politica-privacidad">politica de privacidad</a></p>
               <input type="checkbox" />
               <p>Recibir <strong>descuentos exclusivos</strong>, novedades y tendencias por e-mail. Me puedo dar de baja desde mi panel</p>
               <input type="checkbox" />
               {/* type="submit" -> dispara el onSubmit del form */}
               <button type='submit'>Crear cuenta</button>

          </form>
     );
}

export default Registro;   // hace el componente importable/reutilizable en otros ficheros