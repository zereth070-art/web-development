import express from "express";
const app = express();

import { MongoClient } from "mongodb";

app.use(express.json()); // el PORTERO: convierte el body JSON en un objeto JS y lo mete en req.body  


const cliente = new MongoClient("mongodb://127.0.0.1:27017");
await cliente.connect();
const db = cliente.db("apuntes");
const tareas = db.collection("tareas");

app.get("/tareas", (req,res) => { res.json(tareas) });  

app.get("/tareas/:id", (req,res) => {
  const tarea = tareas.find(t => t.id === Number(req.params.id));
  if(!tarea) return res.status(404).json({ error: "Tarea no encontrada"});
  res.json(tarea);
  });

app.post("/tareas", (req, res) => {
  const nueva = { id: tareas.length + 1, titulo: req.body.titulo, hecha: false };
  tareas.push(nueva);
  res.status(201).json(nueva) //201 = creado != 200 = ok 
})