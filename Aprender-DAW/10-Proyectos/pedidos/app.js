import express from "express"; 
import { pedidos } from "./db.js";

const app = express();
app.use(express.json());
app.listen(3000, () => console.log("Servidor en http://localhost:3000"));

app.get("/", (req,res) => {
    res.json({ mensaje : "Servidor de pedidos funcionando "});
});

app.post("/api/pedidos", async (req,res) => {
    const resultado  = await pedidos.insertOne(req.body);
    res.status(201).json( { _id: resultado.insertedId })
});

app.get("/api/pedidos", async (req,res) =>
{
    const lista = await pedidos.find().toArray();
    res.json(lista);
});