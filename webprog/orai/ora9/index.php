<?php
$nev = "Nev";
echo "Hello " . $nev . '<br>';
$today = date("F j, Y, g:i a");
echo $today . '<br>';
?>

<?php
    $hex = "#";
    for ($i = 0; $i < 6; $i++) {
        $hex .= dechex(rand(0, 15));
    }
    ?>

    <?php echo $hex; ?> 

    <?php
    $r = hexdec(substr($hex, 1, 2));
    $g = hexdec(substr($hex, 3, 2));
    $b = hexdec(substr($hex, 5, 2));
    ?>

    <?php
    if($r > 128 && $g > 128 && $b > 128){
        $color = $hex;
    }else{
        $color = "#ffffff";
    }
    ?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body style="background-color: <?php echo $color; ?>;">
    <?php for ($i = 0; $i < 10; $i++): ?>
    <h1 style='font-size:<?= $i * 10 ?>'>Helló világ!</h1>
    <?php endfor; ?>
    <?php
    $list = ["kutya", "cica", "mikró"];
    ?>

    <ul>
        <?php foreach ($list as $item): ?>
        <li>
            <<= $item ?>
        </li>
        <?php endforeach; ?>
    </ul>

    <?php
    $tipusok = [
        3 => "Vígjáték",
        5 => "Dráma",
        7 => "Horror"
    ];
    ?>

    <select name="" id="">
        <?php foreach ($tipusok as $id => $tipus): ?>
        <option value="<?= $id ?>">
            <?= $tipus ?>
        </option>
        <?php endforeach; ?>
    </select>
</body>

</html>