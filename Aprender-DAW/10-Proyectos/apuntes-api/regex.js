const texto = "Mi correo es hola@prueba.com y otro es test@correo.es";

console.log(texto.match(/@/g));        // pregunta 1: predice
console.log(texto.replace(/[0-9]/g, "#"));  // pregunta 2: predice
console.log(texto.match(/[a-z]@[a-z]/g));   // pregunta 3: la nueva
console.log(texto.match(/[a-z]+@[a-z]+/g));
console.log(texto.match( /[a-z]*@[a-z]*/g));
const texto2 = "solo@ @fin @ @medio@medio";
console.log("+ ->", texto2.match(/[a-z]+@[a-z]+/g));
console.log("* ->", texto2.match(/[a-z]*@[a-z]*/g));
