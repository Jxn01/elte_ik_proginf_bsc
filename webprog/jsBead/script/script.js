//Dokumentum objektmainak inicializálása
//================================================================================================

const rules_div = document.querySelector("#rules-div");
const stageselect_div = document.querySelector("#stageselect-div");
const previousgames_div = document.querySelector("#previousgames-div");
const game_div = document.querySelector("#game-div");
const stagename_div = document.querySelector("#stagename-div");
const lightbulbs_div = document.querySelector("#lightbulbs-div");
const time_div = document.querySelector("#time-div");
const name_txt = document.querySelector("#name-txt");
const name_div = document.querySelector("#name-div");
const stage1_div = document.querySelector("#stage1-div");
const stage2_div = document.querySelector("#stage2-div");
const stage3_div = document.querySelector("#stage3-div");
const unfinished_game_div = document.querySelector("#unfinished-game-div");
const table_div = document.querySelector("#table-div");
const gameover_modal = new bootstrap.Modal(document.querySelector("#gameover-modal"));
const players_div = document.querySelector("#players-div");

const rules_btn = document.querySelector("#rules-btn");
const stageselect_btn = document.querySelector("#stageselect-btn");
const previousgames_btn = document.querySelector("#previousgames-btn");
const gamestart_btn = document.querySelector("#gamestart-btn");
const sendname_btn = document.querySelector("#sendname-btn");
const pause_btn = document.querySelector("#pause-btn");
const quit_btn = document.querySelector("#quit-btn");
const reallyquit_btn = document.querySelector("#reallyquit-btn");
const cancelquit_btn = document.querySelector("#cancelquit-btn");
const cancelquit2_btn = document.querySelector("#cancelquit2-btn");
const continue_btn = document.querySelector("#continue-btn");
const restart_btn = document.querySelector("#restart-btn");
const reallyrestart_btn = document.querySelector("#reallyrestart-btn");
const cancelrestart_btn = document.querySelector("#cancelrestart-btn");
const cancelrestart2_btn = document.querySelector("#cancelrestart2-btn");
const gameover_btn = document.querySelector("#gameover-btn");

//A játék állapotát tároló objektum
//================================================================================================

let gamestate = {
  name: "",
  stage: { name: "", table: "" },
  score: "",
  time: { minutes: 0, seconds: 0 },
  hasStarted: false,
  isCompleted: false,
  incorrectTimes: 0,
};

//A játék pályáit tároló objektumok
//================================================================================================

const stagedata1 = {
  name: "1. pálya (7x7-es könnyű)",
  n: 7,
  list: [
    { x: 0, y: 3, v: 1 },
    { x: 1, y: 1, v: 0 },
    { x: 1, y: 5, v: 2 },
    { x: 3, y: 0, v: -1 },
    { x: 3, y: 3, v: -1 },
    { x: 3, y: 6, v: -1 },
    { x: 5, y: 1, v: -1 },
    { x: 5, y: 5, v: 2 },
    { x: 6, y: 3, v: 3 },
  ],
};
const stagedata2 = {
  name: "2. pálya (7x7-es nehéz)",
  n: 7,
  list: [
    { x: 0, y: 2, v: 0 },
    { x: 0, y: 4, v: -1 },
    { x: 2, y: 0, v: -1 },
    { x: 2, y: 2, v: -1 },
    { x: 2, y: 4, v: 3 },
    { x: 2, y: 6, v: -1 },
    { x: 3, y: 3, v: 1 },
    { x: 4, y: 0, v: 2 },
    { x: 4, y: 2, v: -1 },
    { x: 4, y: 4, v: -1 },
    { x: 4, y: 6, v: -1 },
    { x: 6, y: 2, v: -1 },
    { x: 6, y: 4, v: 2 },
  ],
};
const stagedata3 = {
  name: "3. pálya (10x10-es extrém)",
  n: 10,
  list: [
    { x: 0, y: 1, v: -1 },
    { x: 1, y: 5, v: 3 },
    { x: 1, y: 7, v: 2 },
    { x: 1, y: 9, v: -1 },
    { x: 2, y: 1, v: 0 },
    { x: 2, y: 2, v: -1 },
    { x: 2, y: 7, v: -1 },
    { x: 3, y: 4, v: -1 },
    { x: 4, y: 1, v: 1 },
    { x: 4, y: 4, v: -1 },
    { x: 4, y: 5, v: 1 },
    { x: 4, y: 6, v: -1 },
    { x: 5, y: 3, v: -1 },
    { x: 5, y: 4, v: -1 },
    { x: 5, y: 5, v: -1 },
    { x: 5, y: 8, v: 3 },
    { x: 6, y: 5, v: -1 },
    { x: 7, y: 2, v: 1 },
    { x: 7, y: 7, v: 0 },
    { x: 7, y: 8, v: -1 },
    { x: 8, y: 0, v: 3 },
    { x: 8, y: 2, v: -1 },
    { x: 8, y: 4, v: 0 },
    { x: 9, y: 8, v: 0 },
  ],
};

// A játék pályáit előállító függvények
//================================================================================================

function setStage(stage) {
  table_div.innerHTML = "";
  stagename_div.innerHTML = stage.name;
  table_div.appendChild(stage.table);
}

function stageMaker(stagedata) {
  let name = stagedata.name;
  let n = stagedata.n;
  let list = stagedata.list;
  let table = generate_nxn_table(n);
  let rows = table.rows;
  list.forEach((e) => {
    let cell = rows[e.x].cells[e.y];
    cell.classList.add("black-cell");
    if (e.v != -1) {
      cell.setAttribute("lightbulbs", 0);
      cell.innerHTML = e.v;
      if (e.v == 0 || e.v == -1) cell.classList.add("satisfied-cell");
    } else {
      cell.classList.add("satisfied-cell");
    }
  });
  return { name: name, table: table };
}

function generate_nxn_table(n) {
  let tableN = document.createElement("table");
  tableN.classList.add("table");
  for (let i = 0; i < n; i++) {
    let row = document.createElement("tr");
    for (let j = 0; j < n; j++) {
      let cell = document.createElement("td");
      cell.classList.add("cell");
      cell.setAttribute("row", i);
      cell.setAttribute("col", j);
      cell.setAttribute("illumination-times", 0);
      row.appendChild(cell);
    }
    tableN.appendChild(row);
  }
  return tableN;
}

// A játék állapotának a mentését/betöltését/törlését végző függvények
//================================================================================================

function tableDataFromTable(table) {
  let tableData = [];
  let rows = table.rows;
  for (let i = 0; i < rows.length; i++) {
    for (let j = 0; j < rows[i].cells.length; j++) {
      let cell = rows[i].cells[j];
      let cellData = {
        x: i,
        y: j,
        v: cell.innerHTML,
        lightbulbs: cell.getAttribute("lightbulbs"),
        illuminationTimes: cell.getAttribute("illumination-times"),
        illuminated: cell.classList.contains("illuminated-cell"),
        black: cell.classList.contains("black-cell"),
        satisfied: cell.classList.contains("satisfied-cell"),
        yellow: cell.classList.contains("yellow-cell"),
        red: cell.classList.contains("red-cell"),
      };
      tableData.push(cellData);
    }
  }
  return tableData;
}

function tableFromTableData(tableData) {
  let table = generate_nxn_table(Math.sqrt(tableData.length));
  let rows = table.rows;
  tableData.forEach((e) => {
    let cell = rows[e.x].cells[e.y];
    cell.innerHTML = e.v;
    cell.setAttribute("lightbulbs", e.lightbulbs);
    cell.setAttribute("illumination-times", e.illuminationTimes);
    cell.classList.add("cell");
    if (e.illuminated) cell.classList.add("illuminated-cell");
    if (e.black) cell.classList.add("black-cell");
    if (e.satisfied) cell.classList.add("satisfied-cell");
    if (e.yellow) cell.classList.add("yellow-cell");
    if (e.red) cell.classList.add("red-cell");
  });
  return table;
}

function saveGameInProgress() {
  localStorage.setItem(
    "gamestate",
    JSON.stringify({
      name: gamestate.name,
      stage: gamestate.stage.name,
      time: gamestate.time,
      score: gamestate.score,
      incorrectTimes: gamestate.incorrectTimes,
      data: tableDataFromTable(gamestate.stage.table),
    })
  );
}

function deleteGameInProgress() {
  localStorage.removeItem("gamestate");
}

function saveCompletedGame() {
  let completedGames = JSON.parse(localStorage.getItem("completedGames"));
  if (completedGames == null) completedGames = [];
  completedGames.push({
    name: gamestate.name,
    stage: gamestate.stage.name,
    time: gamestate.time,
    score: gamestate.score,
  });
  localStorage.setItem("completedGames", JSON.stringify(completedGames));
}

function loadCompletedGames() {
  let completedGames = JSON.parse(localStorage.getItem("completedGames"));
  if (completedGames == null) return null;
  return completedGames;
}

// A timer függvény
//================================================================================================

function timerFunction() {
  timer = setInterval(() => {
    gamestate.time.seconds++;
    if (gamestate.time.seconds == 60) {
      gamestate.time.seconds = 0;
      gamestate.time.minutes++;
    }
    time_div.innerHTML = ("0" + gamestate.time.minutes).slice(-2) + ":" + ("0" + gamestate.time.seconds).slice(-2);
  }, 1000);
}

// A navbar gombjainak függvényei
//================================================================================================

rules_btn.addEventListener("click", () => {
  if (!gamestate.hasStarted) {
    rules_div.classList.remove("hidden");
    stageselect_div.classList.add("hidden");
    previousgames_div.classList.add("hidden");
    game_div.classList.add("hidden");
    rules_btn.classList.add("active");
    stageselect_btn.classList.remove("active");
    previousgames_btn.classList.remove("active");
  }
});

stageselect_btn.addEventListener("click", () => {
  if (!gamestate.hasStarted) {
    if (localStorage.getItem("gamestate") != null) {
      unfinished_game_div.classList.remove("hidden");
    }
    rules_div.classList.add("hidden");
    stageselect_div.classList.remove("hidden");
    previousgames_div.classList.add("hidden");
    game_div.classList.add("hidden");
    rules_btn.classList.remove("active");
    stageselect_btn.classList.add("active");
    previousgames_btn.classList.remove("active");
  }
});

previousgames_btn.addEventListener("click", () => {
  if (!gamestate.hasStarted) {
    rules_div.classList.add("hidden");
    stageselect_div.classList.add("hidden");
    previousgames_div.classList.remove("hidden");
    game_div.classList.add("hidden");
    rules_btn.classList.remove("active");
    stageselect_btn.classList.remove("active");
    previousgames_btn.classList.add("active");
    generateLeaderboard();
  }
});

continue_btn.addEventListener("click", () => {
  gamestatePrev = JSON.parse(localStorage.getItem("gamestate"));
  gamestate.name = gamestatePrev.name;
  gamestate.stage.name = gamestatePrev.stage;
  gamestate.time = gamestatePrev.time;
  gamestate.score = gamestatePrev.score;
  gamestate.incorrectTimes = gamestatePrev.incorrectTimes;
  gamestate.stage.table = tableFromTableData(gamestatePrev.data);
  gamestate.hasStarted = true;

  console.log(gamestate);
  stageselect_btn.classList.remove("active");
  rules_div.classList.add("hidden");
  stageselect_div.classList.add("hidden");
  previousgames_div.classList.add("hidden");
  game_div.classList.remove("hidden");
  rules_btn.classList.toggle("n-btn-enabled");
  stageselect_btn.classList.toggle("n-btn-enabled");
  previousgames_btn.classList.toggle("n-btn-enabled");
  name_div.innerHTML = gamestate.name;
  time_div.innerHTML = ("0" + gamestate.time.minutes).slice(-2) + ":" + ("0" + gamestate.time.seconds).slice(-2);
  lightbulbs_div.innerHTML = gamestate.score;
  setStage(gamestate.stage);
  timerFunction();
});

function generateLeaderboard() {
  players_div.innerHTML = "";
  let completedGames = loadCompletedGames();
  completedGames.forEach((game) => {
    let row = document.createElement("tr");
    let name = document.createElement("td");
    let stage = document.createElement("td");
    let time = document.createElement("td");
    let lightbulbs = document.createElement("td");
    name.innerHTML = game.name;
    stage.innerHTML = game.stage;
    time.innerHTML = ("0" + game.time.minutes).slice(-2) + ":" + ("0" + game.time.seconds).slice(-2);
    lightbulbs.innerHTML = game.score;
    row.appendChild(name);
    row.appendChild(stage);
    row.appendChild(time);
    row.appendChild(lightbulbs);
    players_div.appendChild(row);
  });
}

// A játék fő függvényei
//================================================================================================

gamestart_btn.addEventListener("click", () => {
  gamestate.hasStarted = true;
  stageselect_btn.classList.remove("active");
  rules_div.classList.add("hidden");
  stageselect_div.classList.add("hidden");
  previousgames_div.classList.add("hidden");
  game_div.classList.remove("hidden");
  rules_btn.classList.toggle("n-btn-enabled");
  stageselect_btn.classList.toggle("n-btn-enabled");
  previousgames_btn.classList.toggle("n-btn-enabled");

  if (stage1_div.classList.contains("active")) gamestate.stage = stageMaker(stagedata1);
  else if (stage2_div.classList.contains("active")) gamestate.stage = stageMaker(stagedata2);
  else if (stage3_div.classList.contains("active")) gamestate.stage = stageMaker(stagedata3);
  setStage(gamestate.stage);
});

sendname_btn.addEventListener("click", () => {
  gamestate.name = name_txt.value;
  name_div.innerHTML = gamestate.name;
  timerFunction();
});

pause_btn.addEventListener("click", () => {
  if (pause_btn.innerHTML.includes("Játék szüneteltetése")) {
    pause_btn.innerHTML = "Játék folytatása";
    pause_btn.classList.remove("btn-warning");
    pause_btn.classList.add("btn-success");
    clearInterval(timer);
  } else {
    pause_btn.innerHTML = "Játék szüneteltetése";
    pause_btn.classList.remove("btn-success");
    pause_btn.classList.add("btn-warning");
    timerFunction();
  }
});

quit_btn.addEventListener("click", () => clearInterval(timer));
reallyquit_btn.addEventListener("click", () => window.location.reload());
cancelquit_btn.addEventListener("click", () => resumeTimer());
cancelquit2_btn.addEventListener("click", () => resumeTimer());
restart_btn.addEventListener("click", () => clearInterval(timer));
reallyrestart_btn.addEventListener("click", () => {});
cancelrestart_btn.addEventListener("click", () => resumeTimer());
cancelrestart2_btn.addEventListener("click", () => resumeTimer());

function resumeTimer(){
  if(pause_btn.innerHTML = "Játék szüneteltetése") timerFunction();
}

reallyrestart_btn.addEventListener("click", () => {
  clearInterval(timer);
  gamestate.score = 0;
  gamestate.time.seconds = 0;
  gamestate.time.minutes = 0;
  gamestate.incorrectTimes = 0;
  lightbulbs_div.innerHTML = "0";
  time_div.innerHTML = "00:00";
  timerFunction();

  if (stage1_div.classList.contains("active")) gamestate.stage = stageMaker(stagedata1);
  else if (stage2_div.classList.contains("active")) gamestate.stage = stageMaker(stagedata2);
  else if (stage3_div.classList.contains("active")) gamestate.stage = stageMaker(stagedata3);
  setStage(gamestate.stage);
});

table_div.addEventListener("click", (e) => {
  saveGameInProgress();
  if (e.target.classList.contains("cell") && !e.target.classList.contains("black-cell")) {
    if (e.target.classList.contains("illuminated-cell")) {
      //cell with lightbulb
      e.target.classList.remove("illuminated-cell");
      lightbulbs_div.innerHTML = --gamestate.score;
      toggleYellow(e.target, false);
    } else {
      // white cell or yellow cell
      e.target.classList.add("illuminated-cell");
      lightbulbs_div.innerHTML = ++gamestate.score;
      toggleYellow(e.target, true);
    }
    let result = isComplete();
    if (result) complete();
  }
});

function toggleYellow(cell, add) {
  let row = parseInt(cell.getAttribute("row"));
  let col = parseInt(cell.getAttribute("col"));
  let n = gamestate.stage.table.rows.length;

  if (add && cell.getAttribute("illumination-times") == 0) {
    cell.classList.add("yellow-cell");
    cell.setAttribute("illumination-times", parseInt(cell.getAttribute("illumination-times")) + 1);
  } else if (!add && cell.getAttribute("illumination-times") == 1) {
    cell.classList.remove("yellow-cell");
    cell.setAttribute("illumination-times", 0);
  } else if (add) {
    cell.classList.add("red-cell");
    gamestate.incorrectTimes++;
    cell.setAttribute("illumination-times", parseInt(cell.getAttribute("illumination-times")) + 1);
  } else if (!add) {
    cell.classList.remove("red-cell");
    gamestate.incorrectTimes--;
    cell.setAttribute("illumination-times", parseInt(cell.getAttribute("illumination-times")) - 1);
  }

  let currentCell = cell;
  for (let i = row; i < n && !currentCell.classList.contains("black-cell"); i++) {
    //le
    currentCell = gamestate.stage.table.rows[i].cells[col];
    if (i != row && !currentCell.classList.contains("black-cell")) {
      if (add) {
        currentCell.classList.add("yellow-cell");
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) + 1);
        if (currentCell.classList.contains("illuminated-cell") && !currentCell.classList.contains("red-cell")) {
          currentCell.classList.add("red-cell");
          gamestate.incorrectTimes++;
        }
      } else if (currentCell.getAttribute("illumination-times") == 1) {
        currentCell.classList.remove("yellow-cell");
        currentCell.setAttribute("illumination-times", 0);
      } else if (currentCell.getAttribute("illumination-times") == 2 && currentCell.classList.contains("illuminated-cell")) {
        currentCell.classList.remove("red-cell");
        gamestate.incorrectTimes--;
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      } else {
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      }
    } else if (add && i == row + 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) + 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    } else if (!add && i == row + 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) - 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    }
  }

  currentCell = cell;
  for (let i = row; i >= 0 && !currentCell.classList.contains("black-cell"); i--) {
    //fel
    currentCell = gamestate.stage.table.rows[i].cells[col];
    if (i != row && !currentCell.classList.contains("black-cell")) {
      if (add) {
        currentCell.classList.add("yellow-cell");
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) + 1);
        if (currentCell.classList.contains("illuminated-cell") && !currentCell.classList.contains("red-cell")) {
          currentCell.classList.add("red-cell");
          gamestate.incorrectTimes++;
        }
      } else if (currentCell.getAttribute("illumination-times") == 1) {
        currentCell.classList.remove("yellow-cell");
        currentCell.setAttribute("illumination-times", 0);
      } else if (currentCell.getAttribute("illumination-times") == 2 && currentCell.classList.contains("illuminated-cell")) {
        currentCell.classList.remove("red-cell");
        gamestate.incorrectTimes--;
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      } else {
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      }
    } else if (add && i == row - 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) + 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    } else if (!add && i == row - 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) - 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    }
  }

  currentCell = cell;
  for (let i = col; i < n && !currentCell.classList.contains("black-cell"); i++) {
    //jobbra
    currentCell = gamestate.stage.table.rows[row].cells[i];
    if (i != col && !currentCell.classList.contains("black-cell")) {
      if (add) {
        currentCell.classList.add("yellow-cell");
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) + 1);
        if (currentCell.classList.contains("illuminated-cell") && !currentCell.classList.contains("red-cell")) {
          currentCell.classList.add("red-cell");
          gamestate.incorrectTimes++;
        }
      } else if (currentCell.getAttribute("illumination-times") == 1) {
        currentCell.classList.remove("yellow-cell");
        currentCell.setAttribute("illumination-times", 0);
      } else if (currentCell.getAttribute("illumination-times") == 2 && currentCell.classList.contains("illuminated-cell")) {
        currentCell.classList.remove("red-cell");
        gamestate.incorrectTimes--;
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      } else {
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      }
    }
    if (add && i == col + 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) + 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    } else if (!add && i == col + 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) - 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    }
  }

  currentCell = cell;
  for (let i = col; i >= 0 && !currentCell.classList.contains("black-cell"); i--) {
    //balra
    currentCell = gamestate.stage.table.rows[row].cells[i];
    if (i != col && !currentCell.classList.contains("black-cell")) {
      if (add) {
        currentCell.classList.add("yellow-cell");
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) + 1);
        if (currentCell.classList.contains("illuminated-cell") && !currentCell.classList.contains("red-cell")) {
          currentCell.classList.add("red-cell");
          gamestate.incorrectTimes++;
        }
      } else if (currentCell.getAttribute("illumination-times") == 1) {
        currentCell.classList.remove("yellow-cell");
        currentCell.setAttribute("illumination-times", 0);
      } else if (currentCell.getAttribute("illumination-times") == 2 && currentCell.classList.contains("illuminated-cell")) {
        currentCell.classList.remove("red-cell");
        gamestate.incorrectTimes--;
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      } else {
        currentCell.setAttribute("illumination-times", parseInt(currentCell.getAttribute("illumination-times")) - 1);
      }
    } else if (add && i == col - 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) + 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    } else if (!add && i == col - 1 && currentCell.classList.contains("black-cell")) {
      currentCell.setAttribute("lightbulbs", parseInt(currentCell.getAttribute("lightbulbs")) - 1);
      if (currentCell.innerHTML == currentCell.getAttribute("lightbulbs")) {
        currentCell.classList.add("satisfied-cell");
      } else {
        if (currentCell.innerHTML != "") currentCell.classList.remove("satisfied-cell");
      }
    }
  }
}

function isComplete() {
  let rows = gamestate.stage.table.rows;
  let result = true;
  for (let i = 0; i < rows.length; i++) {
    for (let j = 0; j < rows[i].cells.length; j++) {
      let cell = rows[i].cells[j];
      if (!cell.classList.contains("yellow-cell") && !cell.classList.contains("satisfied-cell")) {
        result = false;
      }
    }
  }
  if (gamestate.incorrectTimes != 0) result = false;
  return result;
}

function complete() {
  clearInterval(timer);
  gameover_modal.show();
  deleteGameInProgress();
  saveCompletedGame();
}

gameover_btn.addEventListener("click", () => {
  unfinished_game_div.classList.add("hidden");
  gamestate.hasStarted = false;
  game_div.classList.add("hidden");
  previousgames_div.classList.remove("hidden");
  previousgames_btn.classList.add("active");
  rules_btn.classList.toggle("n-btn-enabled");
  stageselect_btn.classList.toggle("n-btn-enabled");
  previousgames_btn.classList.toggle("n-btn-enabled");

  generateLeaderboard();

  gamestate.score = 0;
  gamestate.time.seconds = 0;
  gamestate.time.minutes = 0;
  name_div.innerHTML = "";
  lightbulbs_div.innerHTML = "0";
  time_div.innerHTML = "00:00";
});