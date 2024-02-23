const task1 = document.querySelector('#task1');
const task2 = document.querySelector('#task2');
const task3 = document.querySelector('#task3');
const task4 = document.querySelector('#task4');

const game = [
  "XXOO",
  "O OX",
  "OOO ",
];

let lines = []
game.forEach(line => lines.push(line.split("")))

let task1m = lines.every(line => line.length == lines[0].length)
task1.innerHTML = task1m

let task2m = lines[0].every(char => char == "X" || char == "O")
task2.innerHTML = task2m

let x_s = 0
let o_s = 0

lines.forEach(line => line.forEach(char => {
  if(char === "X"){
    x_s++
  }

  if(char === "O"){
    o_s++
  }
}))

let task3m = `X: ${x_s}, O: ${o_s}`
task3.innerHTML = task3m

let task4m = game.indexOf(game.filter(l => l.includes("XXX") || l.includes("OOO"))[0])+". index (0-tól)"
task4.innerHTML = task4m
