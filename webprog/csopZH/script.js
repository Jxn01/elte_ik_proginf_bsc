const nev = document.querySelector("input#nev")
const osszeg = document.querySelector("input#osszeg")
const buntetes = document.querySelector("input#buntetes")
const lopasBtn = document.querySelector("button#lopas")
const table = document.querySelector("tbody")
const hozzaadBtn = document.querySelector("button#hozzaad")
let sum = 0

tizmillio()
legnagyobb()

hozzaadBtn.addEventListener("click", () => {
    let row = document.createElement("tr")
    let tnev = document.createElement("td")
    let tosszeg = document.createElement("td")
    let tbuntetes = document.createElement("td")
    tnev.innerHTML = nev.value
    tosszeg.innerHTML = osszeg.value
    tbuntetes.innerHTML = buntetes.value
    tnev.classList.add("nev")
    tosszeg.classList.add("sikkosszeg")
    tbuntetes.classList.add("sikkszama")
    row.append(tnev)
    row.append(tosszeg)
    row.append(tbuntetes)
    table.append(row)
    tizmillio()
    legnagyobb()
})

table.addEventListener("click", (e) => {
    if(e.target.matches("td")){
        e.target.parentElement.classList.toggle("kivalasztott")
    }
})

lopasBtn.addEventListener("click", () => {
    let sorok = document.querySelectorAll("table>tbody>tr.kivalasztott")
    sorok.forEach((e) => {
        sum += parseInt(e.querySelector("td.sikkosszeg").innerHTML)
        let sszam = e.querySelector("td.sikkszama")
        sszam.innerHTML = parseInt(sszam.innerHTML)+1
    })
    document.querySelector("span#sum").innerHTML = sum
})

function tizmillio(){
    let sikkosszegek = []
    let osszegek = document.querySelectorAll("table>tbody>tr>td.sikkosszeg")
    osszegek.forEach(e => sikkosszegek.push(parseInt(e.innerHTML)))
    
    sikkosszegek = sikkosszegek.filter(e => e > 10000000)
    
    document.querySelector("span#tizmillio").innerHTML = sikkosszegek.length
}

function legnagyobb() {
    let nevek = document.querySelectorAll("tr")
    let legnagyobbNev = nevek[1].querySelector("td.nev").innerHTML
    let legnagyobbSzam = parseInt(nevek[1].querySelector("td.sikkosszeg").innerHTML)
    
    for(let i = 1; i < nevek.length; i++){
        jelenlegiSzam = parseInt(nevek[i].querySelector("td.sikkosszeg").innerHTML)
        jelenlegiNev = nevek[i].querySelector("td.nev").innerHTML
        if(jelenlegiSzam > legnagyobbSzam){
            legnagyobbSzam = jelenlegiSzam
            legnagyobbNev = jelenlegiNev
        }
    }
    
    document.querySelector("span#leggazdagabb").innerHTML = legnagyobbNev
}
