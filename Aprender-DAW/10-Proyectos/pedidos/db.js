import { MongoClient } from "mongodb";
import { MongoMemoryServer } from "mongodb-memory-server";

const uri = process.env.MONGODB_URI;
let cliente;
if (uri) {
  cliente = new MongoClient(uri); // creo un nuevo cliente mongo con la URI del servidor
  await cliente.connect(); // espero a que el cliente se conecte
} else {
  const servidor = await MongoMemoryServer.create(); // creo el servidor
  cliente = new MongoClient(servidor.getUri());
  await cliente.connect(); // espero a que el cliente se conecte
}
const db = cliente.db("pedidos-AlfaCentauro");
export const pedidos = db.collection("pedidos"); // exporto una coleccion pedidos
