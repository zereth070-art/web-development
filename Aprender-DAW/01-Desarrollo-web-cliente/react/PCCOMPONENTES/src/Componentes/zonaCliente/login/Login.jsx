import { useState } from 'react'
import './Login.css'
import { validateEmail } from '../../../utils/validaciones';
function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errores, setErrores] = useState('');
    let   [exito, setExito] = useState('');

    function validar() {
        const nuevosErrores = {};
        if (!validateEmail(email))
    }
}