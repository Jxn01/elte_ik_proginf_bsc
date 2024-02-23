<!DOCTYPE html>
<html lang="en">

<?php
function startsWith($string, $startString)
{
  $len = strlen($startString);
  return (substr($string, 0, $len) === $startString);
}

$input = $_GET;
$errors = [];
$data = [];

validate($input, $errors, $data);

function validate($i, &$errors, &$data)
{
  //név validálása: létezzen, legyen legalább 3 hosszú
  if (!isset($i['name'])) {
    $errors[] = "Nem adtál meg nevet!";
  } else if (strlen($i['name']) < 3) {
    $errors[] = "A névnek legalább 3 karakter hosszúnak kell lennie!";
  } else {
    $data['name'] = $i['name'];
  }

  //kor validálása
  if (!isset($i['age']) && strlen($i['age']) === 0) {
    $errors[] = "Nem adtál meg kort!";
  } else if (!is_numeric($i['age'])) {
    $errors[] = "Az életkor csak szám lehet!";
  } else if ($i['age'] < 0) {
    $errors[] = "Az életkor nem lehet negatív!";
  } else {
    $data['age'] = $i['age'];
  }

  //háttérszín validálása: nem lehet fekete, nem lesz kötelező mező
  if (isset($i['bg']) && $i['bg'] !== '#000000') {
    $data['bg'] = $i['bg'];
  } else {
    $data['bg'] = '#efefef';
  }



}


echo $_SERVER['REMOTE_ADDR'];
echo "\n";
echo phpversion();
echo "\n";
if (startsWith($_SERVER['REMOTE_ADDR'], "157.181.")) {
  echo "\nYay, elte whooo";
} else {
  echo "\nNo elte?";
}
?>

<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Document</title>
  <style>
    @import url("https://cdn.jsdelivr.net/gh/elte-fi/www-assets@19.10.16/styles/mdss.min.css");

    ul {
      color: red;
    }

    div {
      padding: 5px;
      border-radius: 1em;
    }
  </style>
</head>

<body>
  <h1>
    <?php echo var_dump($_GET) ?>
    Hello
    <?= $_GET['name'] ?? 'regisztrálj!' ?>!
  </h1>

  <h2>Regisztrálj!</h2>
  <form action="index.php" method="get">
    <h3>Név</h3>
    <input type="text" name="name" value='<?= $data['name'] ?? '' ?>' />

    <h3>Kor</h3>
    <input type="text" name="age" value='<?= $data['age'] ?? '' ?>' />

    <h3>Háttérszín</h3>
    <input type="color" name="bg" value='<?= $data['bg'] ?? '' ?>' />

    <h3>Legyen-e kép?</h3>
    <input type="checkbox" name="pic" value='<?= $data['pic'] ?? '' ?>' />

    <h3>Link</h3>
    <input type="text" name="link" value='<?= $data['link'] ?? '' ?>' />

    <h3>Ide kattintva küldheted el</h3>
    <input type="submit" value="Küldés">
  </form>

  <?php if (count($errors) === 0): ?>
  <div style='background-color: <?= $data['bg'] ?>'>
    <h2>
      Sikeres regisztráció
    </h2>
    <table>
      <thead>
        <tr>
          <th>Cím</th>
          <th>Adat</th>
        </tr>
      </thead>
      <?php foreach ($data as $key => $value): ?>
      <tr>
        <td>
          <?= $key ?>
        </td>
        <td>
          <?= $value ?>
        </td>
      </tr>
      <?php endforeach; ?>
    </table>
    <h3>Név: <?= $data['name'] ?>
    </h3>
    <h3>Kor: <?= $data['age'] ?>
    </h3>
  </div>
  <?php else: ?>
  <h2>Sikertelen regisztráció</h2>
  <ul>
    <?php foreach ($errors as $e): ?>
    <li>
      <?= $e ?>
    </li>
    <?php endforeach; ?>
  </ul>
  <?php endif; ?>
</body>

</html>