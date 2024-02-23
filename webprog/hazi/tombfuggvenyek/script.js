let tomb = [11, 42, 33, 36, 27, 48, 11, 7, 16, 29]
document.querySelector("h2>span#tomb").innerHTML = tomb
let task1 = document.querySelector("h3>span#task1")
let task2 = document.querySelector("h3>span#task2")
let task3 = document.querySelector("h3>span#task3")
let task4 = document.querySelector("h3>span#task4")
let task5 = document.querySelector("h3>span#task5")
let task6 = document.querySelector("h3>span#task6")
let task7 = document.querySelector("h3>span#task7")

let task1m      = tomb.filter(elem => elem < 0)
task1.innerHTML = [...task1m]
if(task1m.length == 0) task1.innerHTML = "Nincs"

let task2m      = tomb.map(elem => elem+"C")
task2.innerHTML = [...task2m]

let task3m      = Math.max(...tomb)
task3.innerHTML = task3m

let task4m      = tomb.filter(elem => elem < 20).length
task4.innerHTML = task4m

let task5m      = tomb.some(elem => elem > 30)
task5.innerHTML = task5m

let task6m      = tomb.every(elem => elem >= 0)
task6.innerHTML = task6m

let task7m      = tomb.filter(elem => elem > 10).sort() 
task7.innerHTML = task7m[0]