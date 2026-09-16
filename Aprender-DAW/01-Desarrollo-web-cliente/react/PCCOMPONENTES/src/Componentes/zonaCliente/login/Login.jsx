import { useState } from 'react'
import './Login.css'
import { validateEmail } from '../../../utils/validaciones';
function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errores, setErrores] = useState({});
    const   [exito, setExito] = useState(false);

    function handleSubmit(e) {
        e.preventDefault();
        const nuevosErrores = validar();
        if (Object.keys(nuevosErrores).length > 0) {
            setErrores(nuevosErrores);
        } else {
            setExito(true);
        }
    }

    function validar(){
        const nuevosErrores = {};
        if (!validateEmail(email)) nuevosErrores.email = 'El email no es valido';
        return nuevosErrores

    }

    return <form onSubmit={handleSubmit} noValidate>
            <input 
                type='email'
                placeholder='Ingrese el email'
                value={email} 
                onChange={(e) => setEmail(e.target.value)}           
            />

            <input
                type='password'
                value={password}    
                onChange={password}
            />
        <button type='submit'>Crear cuenta</button>

    </form>
}