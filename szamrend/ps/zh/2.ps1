param($szam)

for($i = 1; $i -le $szam; $i++){
    for($j = 0; $j -lt $szam; $j++){
        if($i % 2 -eq 0){
            write-host -NoNewline b
        }else{
            write-host -NoNewline a
        }
    }
    write-host
}