<?php 
session_start();

if(isset($_GET["logout"])){
    session_destroy();
}

if($_POST && $_POST["name"] === "Jxn" && $_POST["pw"]){
    $_SESSION["user"] = $_POST["name"];
    $_SESSION["pw"] = $_POST["pw"];
    $logged = true;

    echo "Hello " . $_SESSION["user"] . "!";
} else {
    $logged = false;
    echo "Nem vagy bejelentkezve!";
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi van a hűtőmben?</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/elte-fi/www-assets@19.10.16/styles/mdss.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    
    <h1>Mi van a hűtőmben?</h1>
    <table>
        <tr>
            <th>Név</th>
            <th>Darab</th>
            <th>Típus</th>
            <th>Hűtőberakás dátuma</th>
            <th>Lejárati dátum</th>
        </tr>
        <!-- Ide írd a kódod-->
        <?php 
            $f = file_get_contents("food.json");
            $array = json_decode($f, true);  
        ?>

        <?php foreach($array as $item) : ?>
            <tr <?php if(date($item["expdate"] < date($item["date"]))) echo "class='lejart'" ?>>
                <td><?= $item["name"] ?></td>
                <td><?= $item["number"] ?></td>
                <td><?= $item["type"] ?></td>
                <td><?= $item["date"] ?></td>
                <td><?= $item["expdate"] ?></td>
            </tr>
        <?php endforeach; ?>
    </table>
    <?php if($logged): ?>
        <a href='addfood.php'>Elem hozzáadása</a><br>
        <a href='index.php?logout=1'>Kijelentkezés</a>
    <?php else: ?>
        <a href='login.php'>Bejelentkezés</a>
    <?php endif; ?>

    
</body>
</html>