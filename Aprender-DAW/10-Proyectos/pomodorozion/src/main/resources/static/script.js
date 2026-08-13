console.log("Script loaded");
const titleInput = document.getElementById("titleInput");
const estimatedPomodorosInput = document.getElementById(
  "estimatedPomodorosInput",
);
const createBtn = document.getElementById("createBtn");
let selectedTaskId = 0;
const API_URL = "/tasks";

function validateInput() {
  const title = titleInput.value.trim();
  const estimatedPomodoros = Number(estimatedPomodorosInput.value);

  const isTitleValid = title !== "";
  const isPomodorosValid =
    !Number.isNaN(estimatedPomodoros) && estimatedPomodoros > 0;

  createBtn.disabled = !(isTitleValid && isPomodorosValid);
}

titleInput.addEventListener("input", validateInput);
estimatedPomodorosInput.addEventListener("input", validateInput);

async function loadTasks() {
  const response = await fetch(API_URL);
  const tasks = await response.json();
  console.log(tasks);
  const taskList = document.getElementById("taskList");

  taskList.innerHTML = "";

  tasks.forEach((task) => {
    const li = document.createElement("li");
    let statusTexto;
    if (task.status === "PENDING") statusTexto = "Pendiente";
    else if (task.status === "IN_PROGRESS") statusTexto = "En progreso";
    else statusTexto = "Completada";

    li.innerHTML = `
                <div class ="task-title">${task.title}</div>
                
                <button class="select-btn">
                   ${task.id === selectedTaskId ? "✓ Seleccionada" : "Seleccionar"}
                </button>
                
                <div class="task-progress">
                  ${task.completedPomodoros} / ${task.estimatedPomodoros} pomodoros
                </div>

                <div class="task-status">
                  ${statusTexto}
                </div>

                <button class="pomodoro-btn">
                   +1 Pomodoro
                </button>
                `;

    li.dataset.status = task.status;

    const btn = li.querySelector(".pomodoro-btn");
    const selectBtn = li.querySelector(".select-btn");
    if (task.status === "COMPLETED") {
      btn.disabled = true;
    }

    btn.addEventListener("click", () => completePomodoro(task.id));
    selectBtn.addEventListener("click", () => selectTaskId(task.id));

    taskList.appendChild(li);
    li.dataset.selected = task.id === selectedTaskId;
  });
}

async function completePomodoro(id) {
  await fetch(`${API_URL}/${id}/pomodoro`, {
    method: "POST",
  });

  loadTasks();
}

async function selectTaskId(id) {
  await fetch("/api/timer/task/" + id, { method: "POST" });
  await refleshTimer();
  loadTasks();
}

document.getElementById("createBtn").addEventListener("click", createTask);

async function createTask() {
  const title = titleInput.value.trim();
  const estimatedPomodoros = Number(estimatedPomodorosInput.value);
  if (title.trim() === "") {
    alert("Please enter a task title.");
    return;
  }

  if (
    estimatedPomodorosInput.value.trim() === "" ||
    Number.isNaN(estimatedPomodoros) ||
    estimatedPomodoros <= 0
  ) {
    alert("Please enter a valid number of estimated pomodoros.");
    return;
  }
  await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      title: title,
      estimatedPomodoros: estimatedPomodoros,
    }),
  });

  titleInput.value = "";
  estimatedPomodorosInput.value = "";
  loadTasks();
}
loadTasks();

function formatTimer(totalSeconds) {
  const mins = Math.floor(totalSeconds / 60);
  const secs = totalSeconds % 60;
  return mins + ":" + String(secs).padStart(2, "0");
}

function renderTimer(state) {
  document.getElementById("timer").textContent = formatTimer(
    state.remainingSeconds,
  );
  document.getElementById("phase").textContent = state.phase;

  document.getElementById("startTimerBtn").disabled = state.running;
  document.getElementById("pauseTimerBtn").disabled = !state.running;

  selectedTaskId = state.selectedTaskId;
}

async function fetchState() {
  const response = await fetch("/api/timer");
  return await response.json();
}

async function refleshTimer() {
  const state = await fetchState();
  renderTimer(state);

  if (state.remainingSeconds === 0 && state.running) {
    await fetch("/api/timer/finish", { method: "POST" });
    await refleshTimer();
  }
}

async function doAction(action) {
  await fetch("/api/timer/" + action, { method: "POST" });
  await refleshTimer();
}

document
  .getElementById("startTimerBtn")
  .addEventListener("click", () => doAction("start"));
document
  .getElementById("resetTimerBtn")
  .addEventListener("click", () => doAction("reset"));
document
  .getElementById("pauseTimerBtn")
  .addEventListener("click", () => doAction("pause"));

refleshTimer();
setInterval(refleshTimer, 1000);
