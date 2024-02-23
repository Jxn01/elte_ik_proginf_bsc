if(test-path $args[0]){
    $sorok = (get-content $args[0]).Split("\n")
    $jok = new-object string[] $sorok.Count
    $megforditottString = ""
    $szamlalo=0

    for($i = 0; $i -lt $sorok.Count; $i++){
        $megforditott = $sorok[$i].ToCharArray()
        $eredeti = $sorok[$i]
        
        for($j = $megforditott.Count; $j -ge 0; $j--){
            $megforditottString += $megforditott[$j]
        }

        if($eredeti.Equals($megforditottString)){
            $jok[$szamlalo] = $eredeti
            $szamlalo++
        }

        $megforditottString = ""
    }

    for($i = 0; $i -lt $szamlalo; $i++){
        write-host $jok[$i]
    }

}else{
    write-host Nincs ilyen fájl!
}