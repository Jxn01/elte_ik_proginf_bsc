const inputCircleNumber = document.querySelector("#circle-number");
const buttonStart = document.querySelector("#start");
const divContainer = document.querySelector("#container");
const divOutput = document.querySelector("#output");
init()

// Application state

let canGuess = false;
let solution = [];
let series = [];

// ========= Utility functions =========

function random(a, b) {
  return Math.floor(Math.random() * (b - a + 1)) + a;
}

function toggleHighlight(node) {
  node.classList.toggle("highlight")
  node.addEventListener("animationend", function (e) {
    node.classList.remove("highlight");
  }, {once: true});
}

// =====================================

function init(){
  divContainer.innerHTML = ""
  for(let i = 0; i < inputCircleNumber.value; i++){
    let a = document.createElement("a")
    a.setAttribute("class", "circle")
    divContainer.appendChild(a)
  }
}

inputCircleNumber.addEventListener("change", () => {
  divContainer.innerHTML = ""
  for(let i = 0; i < inputCircleNumber.value; i++){
    let a = document.createElement("a")
    a.setAttribute("class", "circle")
    divContainer.appendChild(a)
  }
})

buttonStart.addEventListener("click", () => {
  solution = []
  series = []
  let buttons = inputCircleNumber.value;
  for(let i = 0; i < 7; i++){
    series.push(random(1, buttons))
  }
  console.log(...series)
  divOutput.innerHTML = "Flashing circles..."
  highlightAll()
  setTimeout(() => divOutput.innerHTML = "Now, your turn...", 7000)
  setTimeout(() => canGuess = true, 7000)
  
})

function highlightAll(i = 0){
  if(i < 7){
    toggleHighlight(divContainer.childNodes[series[i]-1])
    setTimeout(() => highlightAll(i+1), 1000)
  }
}

divContainer.addEventListener("click", (e) => {
  if(canGuess){
      solution.push(Array.from(divContainer.childNodes).indexOf(e.target)+1)
    if(solution.length == series.length){
      console.log("A solution tömb hossza elérte a series tömb hosszát!")
      if(arraysEqual(series, solution)){
        console.log("A két tömb megegyezik!")
        divOutput.innerHTML = "Nice job!"
      }else{
        console.log("A két tömb nem egyezik meg!")
        divOutput.innerHTML = "Failed!"
      }
      canGuess = false;
    }else{
      console.log("A solution tömb hossza még nem érte el a series tömb hosszát!")
    }
  }
})

function arraysEqual(a, b){
  if(a.length != b.length){
    return false;
  }else{
    for(let i = 0; i < a.length; i++){
      if(a[i] != b[i]){
        return false;
      }
    }
  }
  return true;
}

