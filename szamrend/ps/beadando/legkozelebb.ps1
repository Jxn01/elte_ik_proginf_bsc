if(Test-Path adat.txt){

    $file = Get-Content adat.txt
    $sorok = $file.Split("\n")
    $datum = New-Object string[] $sorok.Length
    $ido = New-Object string[] $sorok.Length
    $evek = New-Object int[] $sorok.Length
    $honapok = New-Object int[] $sorok.Length
    $napok = New-Object int[] $sorok.Length
    $orak = New-Object int[] $sorok.Length
    $percek = New-Object int[] $sorok.Length
    $mpercek = New-Object int[] $sorok.Length
    $szpercek = New-Object int[] $sorok.Length
    $sebesseg = New-Object int[] $sorok.Length
    $taveleje = New-Object int[] $sorok.Length
    $tavvege = New-Object int[] $sorok.Length

    for($i = 0; $i -lt $sorok.Length; $i++){
        $reszek = $sorok[$i].Split(",")
        $datumido = $reszek[0].Split(" ")
        $datum[$i] = $datumido[0]
        $ido[$i] = $datumido[1]
        $datumreszek = $datum[$i].Split(".")
        $idoreszek = $ido[$i].Split(":")

        $evek[$i] = $datumreszek[0]
        $honapok[$i] = $datumreszek[1]
        $napok[$i] = $datumreszek[2]
    
        $orak[$i] = $idoreszek[0]
        $percek[$i] = $idoreszek[1]
        $mpercek[$i] = $idoreszek[2]
        $szpercek[$i] = $idoreszek[3]

        $sebesseg[$i] = $reszek[1]
        $taveleje[$i] = $reszek[2]
        $tavvege[$i] = $reszek[3]
    }

    $mikor = ""
    $minimum = 100000

    for ($i = 0; $i -lt $sorok.Length; $i++){
        if($minimum -ge $taveleje[$i] -or $minimum -ge $tavvege[$i]){
            if($taveleje[$i] -ge $tavvege[$i]){
                $mikor = $datum[$i]
                $mikor += " " 
                $mikor += $ido[$i]
                $minimum = $tavvege[$i]
            }else{
                $mikor = $datum[$i]
                $mikor += " " 
                $mikor += $ido[$i]
                $minimum = $taveleje[$i]
            }
        }

    }

    write-host "$mikor időpillanatban volt legközelebb az autóhoz valami."
    sleep 3
}else {
    Write-Host Az adat.txt nem elérhető!
    sleep 3
}