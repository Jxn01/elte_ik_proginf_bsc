if($args.Count -eq 0){
    write-host Nincs paraméter!
}else{
    $minimum = ""
    $minimumLength = 1000

    for($i = 0; $i -lt $args.Count; $i++){
        if($args[$i].ToString().Length -le $minimumLength){
            $minimum = $args[$i].ToString()
            $minimumLength = $args[$i].ToString().Length
        }
    }

    write-host $minimum
}