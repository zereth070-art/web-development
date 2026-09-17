
function Contador( { valor, onIncrementar }) {
    
    return (

        <div>
            <p>El contador vale: {valor}</p>
            <button onClick={onIncrementar}>Sumar 1</button>
        </div>
    );
}

export default Contador;