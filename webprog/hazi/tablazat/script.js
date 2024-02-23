const rows = document.querySelector("p>input#rows")
const cols = document.querySelector("p>input#cols")
const generateBtn = document.querySelector("p>input#tableGeneratorBtn")
const table = document.querySelector("table")
const eredmeny = document.querySelector("p#eredmeny")

generateBtn.addEventListener("click", () => {
    if(table.hasChildNodes) table.replaceChildren()

    let rowsCount = rows.value
    let colsCount = cols.value

    for(let i=0; i < rowsCount; i++){
        const row = document.createElement("tr")

        for(let j=1; j<= colsCount; j++){
            const cell = document.createElement("td")
            cell.innerHTML = j + i * rowsCount
            row.append(cell)
        }

        table.append(row)
    }
})

let counter = 0
let num1 = 0
let num2 = 0
let product = 0

table.addEventListener("click", (e) => {
    if(e.target.matches("td")){
        if(counter == 2){
            document.querySelectorAll("table>tr>td").forEach((e) => {
                e.style.backgroundColor = "unset"
            })
            counter = 0
        }

        e.target.style.backgroundColor = "blue"

        if(counter == 0){
            num1 = e.target.innerHTML
        }else if(counter == 1){
            num2 = e.target.innerHTML
            product = num1 * num2
            eredmeny.innerHTML = num1+"*"+num2+" = "+product
        }

        counter++
    }
})