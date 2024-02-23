const form = document.querySelector("form");
const divContainer = document.querySelector(".container");

let inputs = form.querySelectorAll("fieldset>div>input")

let last_years_consumptions = []
inputs.forEach(i => last_years_consumptions.push(parseInt(i.getAttribute("data-consumption"))))
let M = last_years_consumptions.reduce((currentSum, current) => currentSum+current, 0)
console.log("Tavalyi évi összfogyasztás: " + M)

let current_consumptions = []
inputs.forEach(i => current_consumptions.push((parseInt(i.getAttribute("value") / parseInt(i.getAttribute("max"))) * parseInt(i.getAttribute("data-consumption")))))
console.log("Aktuális fogyasztások:")
current_consumptions.forEach(c => console.log(c))

inputs.forEach(i => {
    let label = divContainer.querySelector(`label[for=${i.id}]`)
    label.style.width = (parseInt(i.getAttribute("value")) / parseInt(i.getAttribute("max"))) * parseInt(i.getAttribute("data-consumption")) / M * 100+"%" 
    
})

form.addEventListener("input", ()=>{
    inputs.forEach(i => {
        let label = divContainer.querySelector(`label[for=${i.id}]`)
        label.style.width = (parseInt(i.getAttribute("value")) / parseInt(i.getAttribute("max"))) * parseInt(i.getAttribute("data-consumption")) / M * 100+"%" 
        console.log(i.getAttribute("value"))
    })
})
