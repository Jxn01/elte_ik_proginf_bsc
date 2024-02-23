<?php
    $fullname = $_GET['fullname'];
    $email = $_GET['email'];
    $colour = $_GET['colour'];
    $cardnumber = $_GET['cardnumber'];
    $errors = [];

    if(count($_GET)){
        if (trim($fullname) === '')
            $errors['fullname'] = 'Full name is required!'; 
        else if (count(explode(' ', $fullname)) < 2)
            $errors['fullname'] = 'Full name must contain at least two words!'; 
        if (trim($email) === '')
            $errors['email'] = 'E-mail address is required!'; 
        else if (!filter_var($email, FILTER_VALIDATE_EMAIL))
            $errors['email'] = 'E-mail address is not valid!'; 
        if (trim($colour) === '')
            $errors['colour'] = 'Colour is required!'; 
        else if (!in_array($colour, ['black', 'white', 'gold', 'pink', 'blue']))
            $errors['colour'] = 'Colour is not valid!';
        if (trim($cardnumber) === '')
            $errors['cardnumber'] = 'Credit card number is required!'; 
        else if (!preg_match('/^[0-9]{4}(-[0-9]{4}){3}$/', $cardnumber))
            $errors['cardnumber'] = 'Credit card number is not valid!';
    }

    $success = count($_GET) && $errors === [];
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="index.css">
    <title>Task 2</title>
</head>
<body>
    <h1>Task 2: You've just won a new phone!</h1>
    <form action="index.php" method="get" novalidate>
        <label for="i1">Your full name:</label> <input type="text" name="fullname" id="i1"> <?php if (isset($errors['fullname'])) echo "<span style='color: red'>" . $errors['fullname'] . "</span>"; ?> <br>
        <label for="i2">Your e-mail address:</label> <input type="text" name="email" id="i2"> <?php if (isset($errors['email'])) echo "<span style='color: red'>" . $errors['email'] . "</span>"; ?> <br>
        <label>Choose colour:</label> <?php if (isset($errors['colour'])) echo "<span style='color: red'>" . $errors['colour'] . "</span>"; ?> <br>
        <input type="radio" value="black" name="colour" id="i3a"> <label for="i3a">black</label><br>
        <input type="radio" value="white" name="colour" id="i3b"> <label for="i3b">white</label><br>
        <input type="radio" value="gold" name="colour" id="i3c"> <label for="i3c">gold</label><br>
        <input type="radio" value="pink" name="colour" id="i3d"> <label for="i3d">pink</label><br>
        <input type="radio" value="blue" name="colour" id="i3e"> <label for="i3e">blue</label><br>
        <label for="i4">Your credit card number:</label>
        <input type="text" name="cardnumber" id="i4"> <?php if (isset($errors['cardnumber'])) echo "<span style='color: red'>" . $errors['cardnumber'] . "</span>"; ?> <br>
        <button type="submit">Click here to get your free phone today!</button>
    </form>

    <?php if ($success): ?>
        <div id="success">
            <h2></h2>
		    <div id="progress-bar">
			    <div id="progress-bar-fill"></div>
		    </div>
	    </div>
    <?php endif; ?>


    <h2>Hyperlinks for testing</h2>
    <a href="index.php?fullname=&email=&cardnumber=">fullname=&email=&cardnumber=</a><br>
    <a href="index.php?fullname=Grandma&email=&cardnumber=">fullname=Grandma&email=&cardnumber=</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=&cardnumber=">fullname=Lovely+Grandma&email=&cardnumber=</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=nagyi&cardnumber=">fullname=Lovely+Grandma&email=nagyi&cardnumber=</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=nagyi%40webprog.hu&cardnumber=">fullname=Lovely+Grandma&email=nagyi%40webprog.hu&cardnumber=</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=red&cardnumber=">fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=red&cardnumber=</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=">fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=1234">fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=1234</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=1234.5678.1234.5678">fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=1234.5678.1234.5678</a><br>
    <a href="index.php?fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=1234-5678-1234-5678"><span style="color: green">Correct input: </span>fullname=Lovely+Grandma&email=nagyi%40webprog.hu&colour=pink&cardnumber=1234-5678-1234-5678</a><br>

    <script src="index.js"></script>
    </body>
</html>
