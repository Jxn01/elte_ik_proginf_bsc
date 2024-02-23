const canvas = document.querySelector("canvas")
const ctx = canvas.getContext("2d")

function draw(){
    ctx.fillStyle = "red"
    ctx.fillRect(10,10,30,50)

    ctx.strokeStyle = "#00ff00"
    ctx.strokeRect(50,50,50,50)

    ctx.beginPath()
    ctx.arc(200,75,50,0,2* Math.PI)
    ctx.stroke()

    ctx.beginPath()
    ctx.moveTo(150,150)
    ctx.lineTo(300,300)
    ctx.lineTo(300,150)
    ctx.closePath()
    ctx.fill()
    ctx.stroke()
}

// FLAPPY BIRD

// VÁLTOZÓK
const madar = {
    x: 50,
    y: canvas.height / 2,
    w: 30,
    h: 30,
    vy: 0, // px/s
    ay: 250 // px/s^2
}

let elozoido = performance.now()

const oszlopok = []
const res = 150
const o_seb = -200

let points = 0
let hscore = 0

// FÜGGVÉNYEK
function render(){
    ctx.fillStyle = "lightblue"
    ctx.fillRect(0, 0, canvas.width, canvas.height)
    ctx.fillStyle = "brown"
    ctx.fillRect(madar.x, madar.y, madar.w, madar.h)
    oszlopok.forEach(o => ctx.fillRect(o.x, o.y, o.w, o.h))
}

function update(dt){
    if(oszlopok[0].x < 0){ 
        oszlopok.shift()
        oszlopok.shift()
        ujOszlop()
        points++
    }

    madar.vy += madar.ay * dt
    madar.y += madar.vy * dt

    oszlopok.forEach(o => o.x += o_seb * dt)
}

function jatekciklus(most = performance.now()){
    const dt = (most - elozoido) / 1000
    elozoido = most
    update(dt)
    render()
    requestAnimationFrame(jatekciklus)
}

function utkozik(a,b){
    return !(
      b.y + b.h < a.y ||
      a.x + a.w < b.x ||
      a.y + a.h < b.y ||
      b.x + b.w < a.x 
    )
  }

document.addEventListener("keydown", (e)=>{
    if(e.code === "Space") madar.vy = -250
})

function random(a, b){
    return Math.floor(Math.random() * (b-a+1)+a)
}

function ujOszlop(){
    const h = random(10, canvas.height / 2)
    oszlopok.push(
        {
            x: canvas.width,
            y: 0,
            w: 30,
            h: h
        },
        {
            x: canvas.width,
            y: h + res,
            w: 30,
            h: canvas.height - (res + h)
        }
    )
}
// FUTTATÁS
ujOszlop()
jatekciklus()