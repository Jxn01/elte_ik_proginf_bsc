<?php
session_start();
$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';
$login = $_POST['login'] ?? false;
$logout = $_POST['logout'] ?? false;

$newEmail = $_POST['newEmail'] ?? '';
$newUsername = $_POST['newUsername'] ?? '';
$newPassword = $_POST['newPassword'] ?? '';
$newPassword2 = $_POST['newPassword2'] ?? '';
$register = $_POST['register'] ?? false;
$errors = [];

$users = json_decode(file_get_contents('users.json'), true);
$polls = json_decode(file_get_contents('polls.json'), true);

if ($logout) {
  $_SESSION['user'] = null;
}

if ($login) {
  if ($username === '') {
    $errors['username'] = 'Nem adtál meg felhasználónevet!';
  }
  if ($password === '') {
    $errors['password'] = 'Nem adtál meg jelszót!';
  }
  if (count($errors) === 0) {
    $user = null;
    foreach ($users as $u) {
      if ($u['username'] === $username) {
        $user = $u;
        break;
      }
    }
    if ($user === null) {
      $errors['login'] = 'Nincs ilyen felhasználó!';
    } else {
      if ($user['password'] !== $password) {
        $errors['password'] = 'Hibás jelszó!';
      } else {
        $_SESSION['user'] = $user;
      }
    }
  }
}

$isLoggedin = $_SESSION['user'] !== null;

if ($register) {
  if ($newEmail === '') {
    $errors['regemail'] = 'Nem adtál meg email címet!';
  }

  if (!filter_var($newEmail, FILTER_VALIDATE_EMAIL)) {
    $errors['regemail'] = 'Nem megfelelő email cím!';
  }
  if ($newUsername === '') {
    $errors['regusername'] = 'Nem adtál meg felhasználónevet!';
  }
  if ($newPassword === '') {
    $errors['regpassword1'] = 'Nem adtál meg jelszót!';
  }
  if ($newPassword2 === '') {
    $errors['regpassword2'] = 'Nem adtál meg jelszót!';
  }
  if ($newPassword !== $newPassword2) {
    $errors['regpassword2'] = 'A két jelszó nem egyezik!';
  }
  $user = null;
  foreach ($users as $u) {
    if ($u['username'] === $newUsername) {
      $user = $u;
      break;
    }
  }
  if ($user !== null) {
    $errors['register'] = 'Már létezik ilyen felhasználó!';
  }
  if (count($errors) === 0) {
    $newUser = [
      'id' => uniqid(),
      'username' => $newUsername,
      'email' => $newEmail,
      'password' => $newPassword,
      'isAdmin' => false
    ];
    $users[] = $newUser;
    file_put_contents('users.json', json_encode($users));
  }
}

$reversedPolls = array_reverse($polls);
$reversedExpiredPolls = [];
$reversedActivePolls = [];

foreach ($reversedPolls as $poll) {
  if ($poll['deadline'] < date("Y-m-d")) {
    $reversedExpiredPolls[] = $poll;
  } else {
    $reversedActivePolls[] = $poll;
  }
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
  <title>Főoldal</title>
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
              <a class="nav-link active" href="#">Főoldal</a>
            </li>
            <?php if ($isLoggedin && $_SESSION['user']['isAdmin']): ?>
              <li class="nav-item">
                <a class="nav-link" href="/phpBead/votecreate.php">Új szavazás</a>
              </li>
            <?php endif; ?>
          </ul>
        </div>
        <?php if (!$isLoggedin): ?>
          <form action="index.php" method="post" novalidate>
            <div class="input-group">
              <div>
                <input type="text" name="username"
                  class="form-control <?php if (isset($errors['username']) || isset($errors['login'])): ?> is-invalid <?php endif; ?>"
                  placeholder="Felhasználónév" value="<?= $username ?>" />
                <div class="invalid-feedback">
                  <?php if (isset($errors['username'])): ?>
                    <?= $errors['username'] ?>
                  <?php elseif (isset($errors['login'])): ?>
                    <?= $errors['login'] ?>
                  <?php endif; ?>
                </div>
              </div>
              <div>
                <input type="password" name="password"
                  class="form-control <?php if (isset($errors['password'])): ?> is-invalid <?php endif; ?>"
                  placeholder="Jelszó" />
                <div class="invalid-feedback">
                  <?= $errors['password'] ?>
                </div>
              </div>
              <input type="hidden" name="login" value="true" />
              <button class="btn btn-outline-success me-2" type="submit">Belépés</button>
              <button class="btn btn-sm btn-outline-<?php if ($register && count($errors) != 0) {
                echo 'danger';
              } else {
                echo 'secondary';
              } ?>" type="button" data-bs-toggle="modal" data-bs-target="#registerModal">Regisztráció</button>
            </div>
          </form>
        <?php else: ?>
          <div class="d-flex">
            <span class="navbar-text me-2">
              <?php echo ($_SESSION['user']['username']) ?>
            </span>
            <form action="index.php" method="post">
              <input type="hidden" name="logout" value="true" />
              <button class="btn btn-outline-danger" type="submit">Kilépés</button>
            </form>
          </div>
        <?php endif; ?>
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
        <?php if ($register && count($errors) != 0): ?>
          <div class="alert alert-danger" role="alert">
            A regisztráció nem sikerült!
          </div>
        <?php elseif ($register && count($errors) == 0): ?>
          <div class="alert alert-success" role="alert">
            A regisztráció sikerült!
          </div>
        <?php endif; ?>
      </div>
      <div class="row">
        <div class="col-12">
          <h2 class="mt-3 mb-3">Még elérhető szavazások</h2>

          <?php foreach ($reversedActivePolls as $poll): ?>
            <div class="card">
              <div class="card-body">
                <h5 class="card-title"><?= $poll['id'] ?>. számú szavazás</h5>
                <p class="card-text">
                  <?= $poll['question'] ?>
                </p>
                <p class="card-text">Létrehozva ekkor: <?= $poll['createdAt'] ?></p>
                <p class="card-text">Szavazat leadható eddig: <?= $poll['deadline'] ?></p>
                <form action="vote.php" method="post" novalidate>
                  <input type="hidden" name="pollId" value="<?= $poll['id'] ?>" />
                  <?php if (!$isLoggedin): ?>
                    <span data-bs-toggle="tooltip" data-bs-placement="left" data-bs-title="A szavazáshoz jelentkezzen be!">
                    <?php endif; ?>
                    <button class="btn btn-primary <?php if (!$isLoggedin): ?> disabled" <?php endif; ?>
                      type="submit">Szavazás</button>
                    <?php if (!$isLoggedin): ?>
                    </span>
                  <?php endif; ?>
                </form>
              </div>
            </div>
          <?php endforeach; ?>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <h2 class="mt-3 mb-3">Már nem elérhető szavazások</h2>
          <?php foreach ($reversedExpiredPolls as $poll): ?>
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">
                  <?= $poll['id'] ?>. számú szavazás
                </h5>
                <p class="card-text">
                  <?= $poll['question'] ?>
                </p>
                <p class="card-text">Létrehozva ekkor: <?= $poll['createdAt'] ?></p>
                <p class="card-text">Szavazat leadható eddig: <?= $poll['deadline'] ?></p>
                <h5 class="card-text">Eredmények: </h5>
                <ul>
                  <?php foreach ($poll['answers'] as $answer): ?>
                    <li><?= $answer['option'] ?>: <?= $answer['count'] ?></li>
                  <?php endforeach; ?>
                </ul>
              </div>
            </div>
          <?php endforeach; ?>
        </div>
      </div>

      <div class="modal fade" id="registerModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="registerModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="registerModalLabel">Regisztráció</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="index.php" method="post" novalidate>
              <div class="modal-body">
                <div class="mb-3">
                  <label for="emailInput" class="form-label">Email-cím</label>
                  <input type="email"
                    class="form-control <?php if (isset($errors['regemail'])): ?> is-invalid <?php endif; ?>"
                    id="emailInput" aria-describedby="emailHelp" name="newEmail" value="<?= $newEmail ?>" />
                  <div class="invalid-feedback">
                    <?= $errors['regemail'] ?>
                  </div>
                  <div id="emailHelp" class="form-text">Email-címét bizalmasan kezeljük! (xd)</div>
                </div>
                <div class="mb-3">
                  <label for="usernameInput" class="form-label">Felhasználónév</label>
                  <input type="text"
                    class="form-control <?php if (isset($errors['regusername']) || isset($errors['register'])): ?> is-invalid <?php endif; ?>"
                    id="usernameInput" aria-describedby="usernameHelp" name="newUsername" value="<?= $newUsername ?>" />
                  <div class="invalid-feedback">
                    <?php if (isset($errors['regusername'])): ?>
                      <?= $errors['regusername'] ?>
                    <?php elseif (isset($errors['register'])): ?>
                      <?= $errors['register'] ?>
                    <?php endif; ?>
                  </div>
                  <div id="usernameHelp" class="form-text">Lehetőleg egyedi nevet válasszon!</div>
                </div>
                <div class="mb-3">
                  <label for="passwordInput1" class="form-label">Jelszó</label>
                  <input type="password"
                    class="form-control <?php if (isset($errors['regpassword1'])): ?> is-invalid <?php endif; ?>"
                    name="newPassword" id="passwordInput1" />
                  <div class="invalid-feedback">
                    <?= $errors['regpassword1'] ?>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="passwordInput2" class="form-label">Jelszó még egyszer</label>
                  <input type="password"
                    class="form-control <?php if (isset($errors['regpassword2'])): ?> is-invalid <?php endif; ?>"
                    name="newPassword2" id="passwordInput2" />
                  <div class="invalid-feedback">
                    <?= $errors['regpassword2'] ?>
                  </div>
                </div>
                <input type="hidden" name="register" value="true" />
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Mégsem</button>
                <button type="submit" class="btn btn-primary">Elküldés</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </main>

  <script src="script/bootstrap.bundle.js"></script>
  <script>
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
    const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))</script>
</body>

</html>