const app = express();
import express from "express";import { tareas } from "./db.js";
import { ObjectId } from "mongodb";
app.use(express.json());

app.get("/tareas", async (req, res) => {
  const todas = await tareas.find().toArray();
  res.json(todas);
});

app.get("/tareas/:id", async (req, res) => {
  const tarea = await tareas.findOne({ _id: ObjectId(req.params.id) });
  if (!tarea) {
    res.status(404).json({ error: "Tarea no encontrada" });
  } else {
    res.json(tarea);
  }
});

app.put("/tareas/:id", async (req, res) => {
  const resultado = await tareas.updateOne(
    { _id: new ObjectId(req.params.id) },
    { $set: req.body }
  );
  if (resultado.matchedCount === 0) {
    return res.status(404).json({ error: "Tarea no encontrada" });
  }
  res.json({ ok: true, actualizado: resultado.modifiedCount})
})

app.delete("/tareas/:id", async (req, res) => {
  const resultado = await tareas.deleteOne({ _id: new ObjectId(req.params.id )})
  if (resultado.deletedCount === 0 ){
    return res.status(404).json({ error: "Tarea no encontrada, por tanto no borrada"});

  }

  res.json({ ok: true, borrado: resultado.deletedCount});
});

app.post("/tareas", async (req, res) => {
  if (!req.body.titulo || typeof req.body.titulo !== "string") {
    return res.status(400).json({ error: "El título es obligatorio y debe ser texto" });
  } 
  const tareaConsulta = await tareas.findOne({ titulo: req.body.titulo });
  if (tareaConsulta) {
    return res.status(400).json({ error: "Ya existe una tarea con este titulo" });

  }

  const resultado = await tareas.insertOne(req.body);
  res.status(201).json({ _id: resultado.insertedId,...req.body });
});

app.listen(3000, () => console.log("Servidor escuchando en http://localhost:3000-"));