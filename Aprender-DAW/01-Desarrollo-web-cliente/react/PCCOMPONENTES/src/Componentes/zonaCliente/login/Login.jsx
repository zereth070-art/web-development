import { useState } from 'react'
import './Login.css'
import { validateEmail } from '../../../utils/validaciones';
function Login( {onCambiarPantalla}) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errores, setErrores] = useState({});
    const [exito, setExito] = useState(false);

    function handleSubmit(e) {
        e.preventDefault();
        const nuevosErrores = validar();
        if (Object.keys(nuevosErrores).length > 0) {
            setErrores(nuevosErrores);
        } else {
            setExito(true);
        }
    }
    
    if (exito) {
        return (
            <div>
                <h2>Inicio de sesion exitoso</h2>
                <p>Ya puedes navegar por PCCOMPONENTES</p>
            </div>

        );
    }

    function validar(){
        const nuevosErrores = {};
        if (!validateEmail(email)) nuevosErrores.email = 'El email no es valido';
        if (password.trim() === '') nuevosErrores.password = 'La contraseña es obligatoria'
        return nuevosErrores

    }

    return <form onSubmit={handleSubmit} noValidate>
            <input 
                type='email'
                placeholder='Ingrese el email'
                value={email} 
                onChange={(e) => {
                    setEmail(e.target.value);
                    setErrores({ ...errores, email: undefined });
                }}           
            />
            {errores.email && <p className='error'>{errores.email}</p>}
            <input
                type='password'
                placeholder='Contraseña'
                value={password}    
                onChange={(e) => {
                    setPassword(e.target.value);
                    setErrores({ ...errores, password: undefined });
                }}
            />
            
            {errores.password && <p className="error">{errores.password}</p>}
        <button type='submit'>Iniciar sesion</button>
<p>¿No tienes cuenta? <button type="button" onClick={onCambiarPantalla}>Regístrate</button></p>
    </form>
}

export default Login;