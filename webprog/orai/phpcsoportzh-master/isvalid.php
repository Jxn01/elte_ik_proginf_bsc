<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Új kutyus érkezett!</title>
    <link rel="stylesheet" href="style.css">
    <title>Mentés</title>
</head>
<body>
<?php
    function validate($d){
        return isset($d) && strlen(trim($d)) !== 0;
    }
    $errors = [];
    $d = $_GET;

    if(!validate($d["nev"])){
        $errors["nev"] = "Név megadása kötelező!";
    } else if (strlen($d["nev"]) < 3){
        $errors["nev"] = "A név legalább 3 karakter hosszú legyen!";
    }

    if(preg_match("/^[0-6]$/", $d["szin"])){
        $errors["szin"] = "Szín megadása kötelező!";
    }

    if(!validate($d["szulido"])) $errors["szulido"] = "Születési idő megadása kötelező!";

    if(!$errors){
        $f = file_get_contents("kutyuk.json");
        $tomb = json_decode($f, true);

        $new = [
            "nev" => $d["nev"],
            "szin" => $d["szin"],
            "szulido" => $d["szulido"],
            "nem" => $d["nem"]
        ];
        
        array_push($tomb, $new);

        file_put_contents("kutyuk.json", json_encode($tomb));
    }

?>
<div id="main">
        <h1><?= $errors ? "Sikertelen hozzáadás" : "Sikeres hozzáadás" ?></h1>
</div>

<?php if($errors): ?>
    <button onclick="window.location.href='adddoggo.php'">Új kutya érkezett</button>
<?php else: ?>
    <button onclick="window.location.href='index.php'">Vissza a főoldalra</button>
<?php endif; ?>

<?php if ($errors) : ?>
        <ul style="font-size: 25px;color: red;">
        <?php foreach($errors as $error) : ?>
            <li><?= $error ?></li>
            <?php endforeach; ?>
        </ul>
<?php endif; ?>


</body>
</html>