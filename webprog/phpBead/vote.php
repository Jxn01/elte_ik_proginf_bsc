<?php
session_start();

$pollid = $_POST['pollId'] ?? '';
$user = $_SESSION['user'] ?? null;
$errors = [];

$voted = $_POST['voted'] ?? false;

$users = json_decode(file_get_contents('users.json'), true);
$polls = json_decode(file_get_contents('polls.json'), true);

if ($pollid === '' || $_SESSION['user'] === null) {
  header('Location: index.php');
  exit;
}

$poll = null;

if ($pollid !== '') {
  foreach ($polls as $p) {
    if ($p['id'] === $pollid) {
      $poll = $p;
      break;
    }
  }
}

if ($voted && count($_POST) == 2) {
  $errors['vote'] = 'Nem választottál!';
}

$alreadyvoted = false;
if (in_array($user['id'], $poll['voted'])) {
  $alreadyvoted = true;
}


if (!$alreadyvoted && $voted && count($errors) === 0) {
  $i = 0;
  foreach ($_POST as $j => $v) {
    if ($i >= count($_POST) - 2) {
      break;
    }
    $poll['answers'][$v]['count']++;
    $i++;
  }
  $poll['voted'][] = $user['id'];

  foreach ($polls as $key => $p) {
    if ($p['id'] === $pollid) {
      $polls[$key] = $poll;
      break;
    }
  }

  file_put_contents('polls.json', json_encode($polls));
}
?>

<!DOCTYPE html>
<html lang="hu">

<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="style/bootstrap.css" rel="stylesheet" />
  <link href="style/style.css" rel="stylesheet" />
  <title>Szavazófelület</title>
</head>

<body>
  <header>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div class="container-fluid">
        <a class="navbar-brand">KFMSZ</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" href="index.php">Főoldal</a>
            </li>
            <?php if ($isLoggedin && $_SESSION['user']['isAdmin']): ?>
              <li class="nav-item">
                <a class="nav-link" href="/phpBead/votecreate.php">Új szavazás</a>
              </li>
            <?php endif; ?>
          </ul>
        </div>
        <div class="d-flex">
          <span class="navbar-text me-2">
            <?php echo ($_SESSION['user']['username']) ?>
          </span>
          <form action="index.php" method="post">
            <input type="hidden" name="logout" value="true" />
            <button class="btn btn-outline-danger" type="submit">Kilépés</button>
          </form>
        </div>
      </div>
    </nav>
  </header>
  <main>
    <div class="container">
      <div class="row">
        <div class="col-12">
          <h1 class="text-center mt-3">Kormányzati Felületeket Megszégyenítő Szavazófelület</h1>
          <p class="text-center">A szavazófelület célja, hogy az emberek akik szavazni akarnak, tudjanak szavazni!
            ...ennyi!</p>
        </div>
      </div>

      <div class="row">
        <div class="col-12">
          <div class="card text-center">
            <div class="card-header">
              <h2 class="card-title">Szavazás</h2>
            </div>
            <div class="card-body">
              <form action="vote.php" method="post" novalidate>
                <h3 class="card-text">
                  <?= $poll['question'] ?>
                </h3>

                <?php if ($poll['isMultiple']) {
                  $optiontype = 'checkbox';
                } else {
                  $optiontype = 'radio';
                } ?>

                <?php $i = 0; ?>
                <?php foreach ($poll['answers'] as $a): ?>

                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="<?= $optiontype ?>"
                      name="<?php if ($optiontype == 'radio'): ?> radio <?php else: ?>     <?= $i ?>   <?php endif; ?>"
                      value="<?= $i ?>" <?php if (isset($_POST[$i])): ?> checked <?php endif; ?>   <?php if ($alreadyvoted || $voted): ?> disabled <?php endif; ?>/>
                    <label class="form-check-label">
                      <?= $a['option'] ?>
                    </label>
                  </div>

                  <?php $i++; ?>
                <?php endforeach; ?>
                <?php $i--; ?>
                <br />

                <input type="hidden" name="pollId" value="<?= $poll['id'] ?>" />
                <input type="hidden" name="voted" value="true" />
                <?php if (($voted || $alreadyvoted) && count($errors) == 0): ?>
                  <div class="alert alert-success" role="alert">
                    Szavazat leadva!
                  </div>
                <?php else: ?>
                  <button type="submit" class="btn btn-success">Szavazat leadása</button>
                <?php endif; ?>
                <div>
                  <input type="hidden" class="<?php if (isset($errors['vote'])): ?> is-invalid <?php endif; ?>" />
                  <div class="invalid-feedback">
                    <?= $errors['vote'] ?>
                  </div>
                </div>
              </form>
            </div>
            <div class="card-footer text-muted">
              Létrehozva ekkor: <?= $poll['createdAt'] ?> <br />
              Szavazat leadható eddig: <?= $poll['deadline'] ?>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>

  <script src="script/bootstrap.bundle.js"></script>
</body>

</html>