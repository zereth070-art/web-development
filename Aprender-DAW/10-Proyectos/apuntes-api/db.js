import { MongoMemoryServer } from 'mongodb-memory-server';
import { MongoClient } from 'mongodb';

const servidor = await MongoMemoryServer.create();
console.log("Servidor MongoDB en memoria iniciado en: " + servidor.getUri());

const cliente = new MongoClient(servidor.getUri());
await cliente.connect();
const db = cliente.db("apuntes");
export const tareas = db.collection("tareas");