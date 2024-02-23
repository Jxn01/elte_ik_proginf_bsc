<?php
session_start();

$user = $_SESSION['user'] ?? null;
$errors = [];

$question = $_POST['question'] ?? '';
$options = $_POST['options'] ?? [];
$deadline = $_POST['deadline'] ?? '';
$isMultiple = $_POST['isMultiple'] ?? false;
$create = $_POST['create'] ?? false;

if ($user === null || $user['isAdmin'] == false) {
  header('Location: index.php');
  exit;
}

if ($create) {
  if ($question === '') {
    $errors['question'] = 'A kérdés nem lehet üres!';
  }

  if (count(explode(";", $_POST['options'])) < 2) {
    $errors['options'] = 'Legalább 2 választási lehetőséget kell megadni!';
  }

  if ($deadline === '') {
    $errors['deadline'] = 'A szavazás lejárati ideje nem lehet üres!';
  }

  if($deadline < date("Y-m-d")) {
    $errors['deadline'] = 'A szavazás lejárati ideje nem lehet múltbeli dátum!';
  }

  if (count($errors) == 0) {
    $polls = json_decode(file_get_contents('polls.json'), true);

    $reversedPolls = array_reverse($polls);
    $id = $reversedPolls[0]['id'] + 1;

    $poll = [
      'id' => strval($id),
      'question' => $_POST['question'] ?? '',
      'options' => explode(";", $_POST['options']) ?? [],
      'isMultiple' => $_POST['isMultiple'] ?? false,
      'createdAt' => date("Y-m-d"),
      'deadline' => $_POST['deadline'] ?? '',
      'answers' => [],
      'voted' => []
    ];

    foreach ($poll['options'] as $option) {
      $poll['answers'][] = [
        'option' => $option,
        'count' => 0
      ];
    }

    $polls[] = $poll;

    file_put_contents('polls.json', json_encode($polls));

    header('Location: index.php');
    exit;
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
  <title>Új szavazás létrehozása</title>
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
            <li class="nav-item">
              <a class="nav-link active" href="/phpBead/votecreate.php">Új szavazás</a>
            </li>
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
          <h2 class="text-center mt-3">Új szavazás létrehozása</h2>
        </div>
      </div>

      <form action="votecreate.php" method="post" novalidate>
        <div class="row">
          <div class="col-12">
            <div class="mb-3">
              <label for="description" class="form-label">Szavazás leírása</label>
              <textarea class="form-control <?php if (isset($errors['question'])): ?> is-invalid <?php endif; ?>"
                id="description" name="question" rows="3"></textarea>
              <div class="invalid-feedback">
                <?= $errors['question'] ?>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="mb-3">
              <label for="options" class="form-label">Szavazási lehetőségek</label>
              <textarea class="form-control <?php if (isset($errors['options'])): ?> is-invalid <?php endif; ?>"
                id="options" name="options" rows="3" aria-describedby="optionsHelp"></textarea>
              <div class="invalid-feedback">
                <?= $errors['options'] ?>
              </div>
              <div id="optionsHelp" class="form-text">A lehetőségek pontosvesszővel legyenek elválasztva!</div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="mb-3 form-check">
              <input type="checkbox" class="form-check-input" name="isMultiple" id="multipleCheck">
              <label class="form-check-label" for="multipleCheck">Több opciót is megjelölhet</label>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="mb-3">
              <label for="options" class="form-label">Szavazás lezárása</label>
              <input type="date"
                class="form-control <?php if (isset($errors['deadline'])): ?> is-invalid <?php endif; ?>"
                name="deadline" id="date" value="<?= $deadline ?>" />
              <div class="invalid-feedback">
                <?= $errors['deadline'] ?>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="mb-3">
              <input type="hidden" name="create" value="true" />
              <button class="btn btn-outline-success" type="submit">Szavazás létrehozása</button>
            </div>
          </div>
        </div>
      </form>
  </main>
  <script src="script/bootstrap.bundle.js"></script>
</body>

</html>