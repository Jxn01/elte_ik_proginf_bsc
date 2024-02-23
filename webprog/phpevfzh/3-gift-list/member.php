<?php
  $str = file_get_contents('ideas.json', true);
  $arr = json_decode($str, true);
  if (is_null($arr)){
    $arr = [];
  }

  $d = $_POST;
  
  if($d["idea"] != ''){
    $idea = ["member" => $_GET['name'], "idea" => $d["idea"], "active" => true, "ready" => false, "comments" => []];
  }
  
  array_push($arr, $idea);

  $json = json_encode($arr, JSON_PRETTY_PRINT);
  file_put_contents('ideas.json', $json);
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
  <a href="index.php">Back to main page</a>
  <h2>Ideas for <?php echo $_GET['name'] ?></h2>
  <form action="" method="post">
    <fieldset>
      <legend>New idea</legend>
      Idea: <input type="text" name="idea" required>
      <button name="function-add" type="submit">Add new idea</button>
    </fieldset>
  </form>
  <?php 
    $ideas = [];
    foreach($arr as $a){
      if($a['member'] == $_GET['name']){
        array_push($ideas, $a);
      }
    }
  ?>
  
  <?php foreach($ideas as $idea): ?>
  <details>
    <summary>
      <?= $idea["idea"] ?> <?php if($idea["ready"]){ echo "<span>✓</span>"; } ?>
    </summary>
    <form action="" method="post">
      <input type="hidden" name="idea-id" value="">
      Comment: <input type="text" name="comment" required>
      <button type="submit" name="add-comment">Add comment</button> <br>
    </form>
    <form action="" method="post">
      <input type="hidden" name="idea-id" value="">
      <button type="submit" name="complete">Complete</button>
      <button type="submit" name="hide">Hide</button>
    </form>
    <ul>
      <?php foreach($idea["comments"] as $comment): ?>
        <li><?= $comment ?></li>
      <?php endforeach; ?>
    </ul>
  </details>
  <?php endforeach; ?>
</body>
</html>
