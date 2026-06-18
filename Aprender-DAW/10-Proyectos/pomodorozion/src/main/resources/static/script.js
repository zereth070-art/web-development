console.log("Script loaded");

const API_URL = "/tasks";

async function loadTasks() {
  const response = await fetch(API_URL);

  const tasks = await response.json();

  const taskList = document.getElementById("taskList");

  taskList.innerHTML = "";

  tasks.forEach((task) => {
    const li = document.createElement("li");

    li.textContent = task.title;

    taskList.appendChild(li);
  });
}

document.getElementById("createBtn").addEventListener("click", createTask);

async function createTask() {
  const titleInput = document.getElementById("titleInput");
  const title = titleInput.value;
  const estimatedPomodorosInput = document.getElementById("estimatedPomodorosInput");
  const estimatedPomodoros = Number(estimatedPomodorosInput.value);
  await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      title: title,
      estimatedPomodoros: estimatedPomodoros
    }),
  });

  titleInput.value = "";
  estimatedPomodorosInput.value = "";

  loadTasks();
}
