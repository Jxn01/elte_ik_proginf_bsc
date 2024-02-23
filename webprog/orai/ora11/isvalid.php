<?php
    $errors = [];
    
    // IDE KÉSZÍTSD EL A VALIDÁLÁST!
    $d = $_POST;

    function validate($v){
        return isset($v) && strlen(trim($v)) !== 0;
    }

    if(!validate($d["name"])){
        $errors["name"] = "Név nem lehet üres!";
    } else if(strlen(trim($d["name"])) < 3){
        $errors["name"] = "A név minimum 3 karakter legyen!";
    }

    if(!validate($d["expdate"])){
        $errors["expdate"] = "A lejárati dátum helytelen!";
    }

    // ITT ADD HOZZÁ A JSON FÁJLHOZ!
    if(!$errors){
        $f = file_get_contents("food.json");
        $array = json_decode($f, true);

        $new = [
            "name" => $d["name"],
            "number" => $d["number"],
            "type" => $d["type"],
            "date" => date("Y-m-d"),
            "expdate" => $d["expdate"]
        ];
        
        array_push($array, $new);
        file_put_contents("food.json", json_encode($array, JSON_PRETTY_PRINT));
    }
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Új kaja érkezett</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/elte-fi/www-assets@19.10.16/styles/mdss.min.css">
    <link rel="stylesheet" href="style.css">
    <title>Mentés</title>
</head>
<body>
   
    
    <!-- Ez jelenjen meg, ha valid -->
    <?php if(!$errors): ?>
    <h1>Sikeres hozzáadás! 😍</h1>
    <a href='index.php'>Vissza a főoldalra</a>
    
    
    <!-- Ez pedig, ha nem valid -->
    <?php else: ?>
    <h1>Sikertelen hozzáadás! 😢😭</h1>
    <a href='addfood.php'>Új hozzzáadása</a>
    <?php endif;?>

<?php if ($errors) : ?>
        <ul style="font-size: 25px;color: red;">
        <?php foreach($errors as $error) : ?>
            <li><?= $error ?></li>
            <?php endforeach; ?>
        </ul>
<?php endif; ?>
</body>
</html>