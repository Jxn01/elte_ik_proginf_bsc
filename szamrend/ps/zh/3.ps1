if(test-path $args[0]){
    $sorok = (get-content $args[0]).Split("\n")
    $osszeolvasott = ""

    for($i = 0; $i -lt $sorok.Count; $i++){
        $osszeolvasott+=$sorok[$i].ToCharArray()[2]
    }

    write-host $osszeolvasott

}else{
    write-host Nincs ilyen fájl!
}