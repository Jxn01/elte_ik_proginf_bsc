<?php

$shop = [
  ["brand" => "Homemade", "type" => "Dark chocolate", "price" => 2000],
  ["brand" => "Grandma's", "type" => "Milk chocolate", "price" => 1500],
  ["brand" => "Worldsweet", "type" => "Milk chocolate", "price" => 3000],
  ["brand" => "Worldsweet", "type" => "Dark chocolate", "price" => 4000],
  ["brand" => "Worldsweet", "type" => "Orange essence", "price" => 4000],
  ["brand" => "Homemade", "type" => "Milk chocolate", "price" => 1000],
  ["brand" => "Speziale", "type" => "Apple & Cinnamon", "price" => 1000]
];

?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="index.css">
  <title>Task 1</title>
</head>

<body>
  <h1>Task 1: Candies</h1>
  <!-- Your solution goes here! -->
  <table>
    <tr>
      <th></th>
      <?php $types = [];
      $i = 0;
      foreach ($shop as $candy) {
        if (!in_array($candy['type'], $types)) {
          $types[$i] = $candy['type'];
          $i++;
        }
      }

      foreach ($types as $type) {
        echo "<th>$type</th>";
      }?>

      <th>Average price</th>
    </tr>

    <?php $brands = [];
    $i = 0;

    foreach ($shop as $candy) {
      if (!in_array($candy['brand'], $brands)) {
        $brands[$i] = $candy['brand'];
        $i++;
      }
    }

    foreach ($brands as $brand) {
      echo "<tr><th>$brand</th>";
      $prices = [0,0];
      foreach ($types as $type) {
        $price = 0;

        foreach ($shop as $candy) {
          if ($candy['brand'] == $brand && $candy['type'] == $type) {
            $price = $candy['price'];
          }
        }

        if ($price != 0) {
          $prices[0] += $price;
          $prices[1]++;
        }
        

        if($price == 0){
          echo "<td></td>";
        }else{
          echo "<td>$price</td>";
        } 
      }
      echo "<td>" . round($prices[0] / $prices[1]) . "</td>";
      echo "</tr>";
    }?>
  </table>
</body>

</html>