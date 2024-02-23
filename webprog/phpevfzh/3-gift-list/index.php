<?php 
  $str = file_get_contents('members.json', true);
  $arr = json_decode($str, true);
  if (is_null($arr)){
    $arr = [];
  }

  $d = $_POST;

  if(trim($d['name']) != ''){
    array_push($arr, $d['name']);
  }
  
  $json = json_encode($arr, JSON_PRETTY_PRINT);
  file_put_contents('members.json', $json);
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Task 3</title>
  <link rel="stylesheet" href="index.css">
</head>
<body>
  <h1>Task 3: Gift list</h1>
  <h2>My family members</h2>
  <form action="" method="post">
    Name: <input type="text" name="name" required>
    <button type="submit">Add</button>
  </form>
  <ul>
    <?php foreach ($arr as $name) : ?>
      <li>
        <a href="member.php?name=<?= $name ?>"><?= $name ?></a>
      </li>
    <?php endforeach; ?>
  </ul>
</body>
</html>