## Importok
import numpy as np
import pandas as pd
from matplotlib import pyplot as plt
from sklearn import model_selection
from sklearn import preprocessing
from sklearn import metrics

from sklearn.datasets import make_blobs, make_circles,fetch_20newsgroups,make_classification

from sklearn.pipeline import make_pipeline

from sklearn import *
from sklearn.model_selection import *

from sklearn import svm
from sklearn.naive_bayes import MultinomialNB
from sklearn.tree import DecisionTreeClassifier

from sklearn.feature_extraction.text import CountVectorizer,TfidfVectorizer

from sklearn.metrics import confusion_matrix, accuracy_score,ConfusionMatrixDisplay,silhouette_score

from sklearn.cluster import AgglomerativeClustering, KMeans, linkage_tree
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA

from sklearn.ensemble import BaggingClassifier,RandomForestClassifier, AdaBoostClassifier

# Gyak 9
arr = np.array([1,2,3,4,5,6])
arr[0]
arr[0:3]

# Tömb elemeinek a szűrése feltétellel
arr < 3
arr[arr < 3]

# Pandas series - 1 dimenziós adatszerkezet, indexelhető
numbers_pd = pd.Series(arr)
numbers_pd
numbers_pd[0]

# Műveletek series-eken
print(numbers_pd[1:3]) # intervallum lekérdezése
print('-----------------------------')
print(numbers_pd.iloc[-1]) # a végétől kezdeni a legkrédezést
print('-----------------------------')
print(numbers_pd.iloc[-3:]) # a végétől intervallumot lekérdezni
print('-----------------------------')

# További műveletek
print(numbers_pd.min())
print('-----------------------------')
print(numbers_pd.max())
print('-----------------------------')
print(numbers_pd.value_counts())
print('-----------------------------')
print(numbers_pd.head(2)) # tail

# Apply - minden elemre végrehajtunk egy függvényt (mint Spark esetében)
numbers_sq = numbers_pd.apply(lambda x: x**2)
numbers_sq
numbers_df = pd.DataFrame({'szamok': numbers_pd, 'negyzetek': numbers_sq})
numbers_df
numbers_df.head(3)
numbers_df.info()
numbers_df.describe()
print(numbers_df.shape)
print(numbers_df.columns)

# Apply a dataframre. Az axis=1 azt mondja meg, hogy a sorokon menjünk végig
numbers_df['kobok'] = numbers_df.apply(lambda x: x['szamok']**3, axis=1)
numbers_df

# Ha axis=0, akkor az oszlopokon megyünk végig
numbers_df.apply(lambda x: x.sum(), axis=0)

# Sorok szűrése
idx = numbers_df['kobok'] % 2 == 0
idx

# Sorok szűrése
numbers_df[idx]

# Számokkal is indexelhetünk
numbers_df.take([0,4])

# Intervallum lekérdezése
numbers_df[2:4]

# Adathalmaz beolvasása pandas dataframe-be
df = pd.read_csv("Pokemon.csv")
df.head()

# Dobjuk el az első oszlopot
df.drop(['#'], axis=1)

# Néhány feladat

# 1. feladat
# Adjuk meg azokat a pokemonokat, amelyek a második generációba tartoznak 
# és az Defense értékük nagyobb legalább kétszerese az Attacknak
idx1 = df['Generation'] == 2
idx2 = df['Defense'] > df['Attack']*2
df[idx & idx2]

# 2. feladat
# Adjuk meg azokat a pokemonokat, amelyek sebessége átlag fölötti
idx1 = df['Speed'] > df['Speed'].mean()
df[idx1]

# Csoportosítás
df.groupby(by='Generation').mean()[['Total']]

# Csoportosítás több oszlop alapján
df.groupby(by=['Generation', 'Legendary']).mean()[['HP', 'Attack']]

# Egyéb műveletek
# https://pandas.pydata.org/Pandas_Cheat_Sheet.pdf

## Adatok megjelenítése
# Ábra kirajzolása
# Nézzük meg hogyan függ össze két attribútum értéke az adathalmazunkban (scatter plot)
df.plot.scatter(x='Speed', y='Defense')

# A pontok binelése hexagonálisan
df.plot.hexbin(x='Speed', y='Defense', gridsize=20)

# Az előző ábra átalakítva
# figsize - az ábra mérete
# s - pontok mérete
# c - pontok színe
# marker - pontok alakja
# alpha - átlátszóság
plt.figure(figsize=(12,6))
plt.scatter(df['Speed'], df['Defense'], s=25, c='red', marker='x', alpha=0.5)
plt.xlabel("Speed")
plt.ylabel("Defense")
plt.show()

# Attribútumok generációnként
stats_by_generation = df.groupby('Generation').mean()[['HP', 'Attack', 'Defense', 'Speed']]
stats_by_generation.plot.line()
stats_by_gen_leg = df.groupby(by=['Generation', 'Legendary']).mean()[['HP', 'Attack']]
stats_by_gen_leg.plot.bar()
stats_by_gen_leg.plot.bar(stacked=True)
sel = df[['HP','Attack','Defense', 'Speed']]
clist = [x for x in sel.columns if x!='HP']
colors = ['red', 'blue', 'green']
fig, ax = plt.subplots(len(clist), 1, figsize=(10,10))
for i in range(len(clist)):
    cn = clist[i]
    ax[i].scatter(sel['HP'],sel[cn], alpha=0.5, label=cn, c=colors[i])
    ax[i].set_xlabel('HP')
    ax[i].set_ylabel(cn)
    ax[i].set_ylim(0,200)
    ax[i].legend()
plt.show()

# Plot cheat sheet
# https://www.kaggle.com/code/themlphdstudent/cheatsheet-matplotlib-charts/notebook

# Plotolás feladatok

# 1. feladat
# Add meg, hogy milyen típusú pokémonok rendelkeznek átlagosan a legnagyobb támadó értékkel
# Ábrázolt az eredményt barchart segítségével
type_group = df.groupby(by='Type 1').mean()[['Attack']]
type_group.plot.bar()

# 2. feladat
# Add meg, hogy az egyes generációkhoz hány pokémon tartozik.
# Jelenítsd meg az eredményt tetszőleges típusú ábrával.
generation_group = df.groupby(by='Generation').size()
generation_group.plot.line(marker='o')

#Gyak 10
## Adattisztítás
# Az adatfeldolgozás első lépése az adatot olyan feldolgozható formára hozni.
# Ennek a része a megfelelő oszlopok kiválasztása, átalakítása, sorok szűrése és az adattisztítás is.

# Az adattisztítás során a következőket vizsgáljuk meg:
# Vannak-e hiányzó értékek?
# Vannak-e kiugró értékek?
# Vannak-e inkonzisztens adatok?
df = pd.read_csv("Pokemon_cleaning.csv")
df.head()

## Feladat 1
#Az adathalmaz tartalmaz 3 furcsaságot, amelyeket jó lenne megtalálni és kijavítani mielőtt elkezdünk dolgozni!
#Mik ezek és mit tudunk tenni?

# Érdemes megvizsgálni az adatunk alapvető statisztikáit inkonzisztencia után kutatva
df.describe()

# Megvan a problémás sor!

# Mit tegyünk?
# Dobjuk el a sort
# Helyettesítsük valamilyen konstans értékkel
# Próbáljuk kitalálni nagyjából milyen érték szerepelhet ott

df[df['Speed'] == -1]
# Javjtsuk ki a hibát!
# Helyettesítsük az átlaggal

df.at[407, 'Speed'] = df['Speed'].mean()
# Rajzoljuk ki a HP oszlop hisztogramját
# Itt látszik, hogy van egy nagyon kiugró értékünk

plt.hist(df['HP'])
# Megvan a problémás sor!

df[df['HP'] > 150]
# Javjtsuk ki a hibát!

df.at[89, 'HP'] = df['HP'].mean()
# Mostmár jól néz ki a hisztogram

plt.hist(df['HP'])
# Vizsgáljuk meg vannak-e hiányzó értékek

# Mit tegyünk?
# Dobjuk el a sorokat
# Töltsük fel valamilyen konstans értékkel
# Próbáljuk kitalálni nagyjából milyen érték szerepelhet ott

df.isnull().sum()
# Ezeket most nem hajtom végre, később kezelem a hiányzó értékeket!

# Konstanssal való kitöltés
df.fillna(value=0)
# A rákövetkező lévő sorban lévő értékkel kitöltése
df.fillna(method='ffill')
# Az oszlop átlagával történő kitöltés
df['HP'].fillna(df['HP'].mean())
# Ha a fillna() függvénynek megadjuk az inplace=True paramétert, akkor módosítja a dataframe-et

## Adatok átalakítása
# Könyvtár gépi tanulás algorimtusokhoz
# Preprocessing - előfeldolgozási algoritmusok
# A típus különböző kategóriákat jelöl. Hogyan lehetne ezt numerikus típusú oszloppá alakítani?

df['Type 1']
# OrdinalEncoder
# A különböző típusokat számokkal helyettesíti

enc = preprocessing.OrdinalEncoder()
encoded = enc.fit_transform(df[['Type 1']])
encoded[:15]
# One Hot Encoding pandassal
# Egy oszlopból annyi oszlopot készít, ahány különböző érték előfordul benne
# get_dummies()

cols = pd.get_dummies(df['Type 1'], prefix='Type1')
cols
# One Hot Encoding sklearnnel

enc = preprocessing.OneHotEncoder()
encoded = enc.fit_transform(df[['Type 1']])
encoded.toarray()[:10]
# Adatok binelése (diszkretizálás) kvantilisek alapján

my_labels = ['Elso', 'Masodik', 'Harmadik', 'Negyedik']
pd.qcut(df['Attack'], q=4, labels=my_labels)
# Binelés a határok megadásával

my_bins = [0, 50, 100, 150, 200]
pd.cut(df['Attack'], bins=my_bins, labels=my_labels)
## Modell készítése
# Vállasszuk ki azokat az attribútumokat, amelyekre valóban szükségünk van
# Határozzuk meg melyik attribútum lesz az osztálycímke

features = ['HP', 'Attack', 'Defense', 'Speed', 'Generation']
label = 'Legendary'
feat_data = df[features].copy()
label_data = df[label].copy()
feat_data
# Kezeljük a hiányzó értékeket

feat_data[features].isnull().sum()
# Hiányzó érték helyettesítése az oszlop átlagával

feat_data['Defense'].fillna(feat_data['Defense'].mean(), inplace=True)
feat_data['Speed'].fillna(feat_data['Speed'].mean(), inplace=True)
feat_data['HP'].fillna(feat_data['Speed'].mean(), inplace=True)
# Eltűntek a hiányzó értékek

feat_data.isnull().sum()
# Az adatunkat felbontjuk tanító és tesztelő halmazra

from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(feat_data, label_data, test_size=0.2, random_state=42)
print(X_train.shape) # 640 tanító rekord
print(X_test.shape) # 160 tesztelő rekord
# K-legközelebbi szomszéd osztályozó létrehozása

from sklearn import neighbors

n_neighbors = 10

clf = neighbors.KNeighborsClassifier(n_neighbors)
clf.fit(X_train, y_train)
clf
# Egy sor osztályozása
# clf.predict([[106,110,90,130,1]])

data = pd.DataFrame({'HP': [106], 'Attack': [110], 'Defense': [90], 'Speed': [130], 'Generation': [1]})
clf.predict(data)
# Osztályozzuk a tesztelésre elkülönített adatunkat

prediction = clf.predict(X_test)
prediction
## Feladat (accuracyt implementálni)

# Írjunk egy programot, ami megadja, hogy az esetek hány százalékában sikerült helyesen osztályozni!
# Ehhez hasonlítsuk össze a predikciókat a biztosan helyen értékekkel.
helyes = 0
hamis = 0
for i in range(len(prediction)):
    if prediction[i] == y_test.iloc[i]:
        helyes += 1
    else:
        hamis += 1
acc = helyes/(hamis+helyes)
print('Accuracy: ' + str(acc))
# Sklearn implementáció accuracy-ra

from sklearn import metrics as ms

print ("Accuracy:", ms.accuracy_score(y_test.values, prediction))

# Vagy használhatjuk a KNN beépített függvényét
# clf.score(X_test, y_test)
# KNN osztályozónk paraméterezése
# A weights paraméternek megadhatjuk, hogy súlyozza-e a szomszédokat és ha igen, akkor hogyan
# A metric paraméterbenm egadhatjuk a távolságfüggvényt
# A p paraméter a távolságfüggvényt paraméterezi

n_neighbors = 5
clf = neighbors.KNeighborsClassifier(n_neighbors, weights='distance', metric='minkowski', p=1)
clf.fit(X_train, y_train)
clf.score(X_test, y_test)
## Feladat 1.

# Írjunk egy programot, amelyek elkészít egy KNN osztályozót különböző szomszédság számossággal (pl. 1-15).
# Nézzük meg, hogy milyen pontos az osztályozó az egyes esetekben.
# Mutassuk meg az eredményt ábra segítségével.

## Feladat 2.

# Az előző feladatban ne csak a szomszédság méretét változtassuk, hanem a súlyozást és a távolságmetrikát is.
# Hogyan tudjuk elérni a legnagyobb pontosságot?

## Feladat 3.

# Vizsgáljuk meg, hogy további attribútomok alapján tudunk-e pontosabb osztályozót készíteni.

# Pontok kirajzolása az osztálycímke alapján

n_neighbors = 5
clf = neighbors.KNeighborsClassifier(n_neighbors, weights='distance', metric='minkowski', p=2)
clf.fit(X_train, y_train)

new_pokemon = pd.DataFrame({'HP': [106], 'Attack': [110], 'Defense': [90], 'Speed': [130], 'Generation': [1]})
pred_label = clf.predict(new_pokemon)

colors = y_train.apply(lambda c: 'blue' if c is True else 'red')

plt.figure(figsize=(10,6))
plt.scatter(X_train['HP'], X_train['Attack'], c=colors, alpha=0.3, s=70)
plt.scatter(new_pokemon.loc[0]['HP'], new_pokemon.loc[0]['Attack'], c='green', s=70)
print(pred_label)
# Döntési fa létrehozása

from sklearn.tree import DecisionTreeClassifier

clf = DecisionTreeClassifier(max_depth=3)
clf.fit(X_train, y_train)
features
# Döntési fa kirajzolása

from sklearn import tree

fig = plt.figure(figsize=(15,10))
_ = tree.plot_tree(clf, 
                   feature_names=features,  
                   class_names=['Legendary', 'Not legendary'],
                   filled=True)
# Pontosság számolás

prediction = clf.predict(X_test)
print ("Accuracy:", ms.accuracy_score(y_test.values, prediction))
# Komplexebb kiértékelés - cross-validation

from sklearn.model_selection import cross_val_score

clf = DecisionTreeClassifier(max_depth=3)
scores = cross_val_score(clf, X_train, y_train, cv=10)
scores.sort()
accuracy = scores.mean()

print(scores)
print(accuracy)

#Gyak 11

# Pokémon adathalmaz beolvasása
df = pd.read_csv("Pokemon.csv")
df.head()
# Az adatunkat felbontjuk tanító és tesztelő halmazra

features = ['HP', 'Attack', 'Defense', 'Speed', 'Generation']
label = 'Legendary'
feat_data = df[features].copy()
label_data = df[label].copy()
X_train, X_test, y_train, y_test = train_test_split(feat_data, label_data, test_size=0.2, random_state=42)
# Döntési fa létrehozása

# max_dept - a döntési fa maximális mélysége
# criterion - mi alapján vizsgálja a döntések jóságát

clf = DecisionTreeClassifier(max_depth=3, criterion='gini')
clf.fit(X_train, y_train)
# Döntési fa kirajzolása

fig = plt.figure(figsize=(15,10))
_ = tree.plot_tree(clf, 
                   feature_names=features,  
                   class_names=['Not Legendary', 'Legendary'],
                   filled=True)
# Pontosság számolás

prediction = clf.predict(X_test)
print ("Accuracy:", metrics.accuracy_score(y_test.values, prediction))
# Komplexebb kiértékelés - cross-validation

clf = DecisionTreeClassifier(max_depth=3)
scores = cross_val_score(clf, X_train, y_train, cv=10)
scores.sort()
accuracy = scores.mean()

print(scores)
print(accuracy)

features2 = ['HP', 'Attack']
label2 = 'Legendary'
feat_data2 = df[features2].copy()
label_data2 = df[label2].copy()
X_train, X_test, y_train, y_test = train_test_split(feat_data2, label_data2, test_size=0.2, random_state=42)

clf = DecisionTreeClassifier(max_depth=3, criterion='gini')
clf.fit(X_train, y_train)

h = 5  # step size in the mesh
x_min, x_max = feat_data2['HP'].min() - 1, feat_data2['HP'].max() + 1
y_min, y_max = feat_data2['Attack'].min() - 1, feat_data2['Attack'].max() + 1
xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                     np.arange(y_min, y_max, h))

Z = clf.predict(np.c_[xx.flatten(), yy.flatten()])

Z = Z.reshape(xx.shape)

plt.figure(figsize=(12,8))
plt.contourf(xx, yy, Z, cmap=plt.get_cmap("brg"), alpha=0.5)
plt.scatter(feat_data2['HP'], feat_data2['Attack'], c=label_data2, cmap=plt.get_cmap("brg"), edgecolors='black')
plt.xlabel('HP')
plt.ylabel('Attack')
# Készítsünk új adathalmazt, random generált pontokból
# Ezzel a SVM jól bemutatható

X, y = make_blobs(n_samples = 50, centers=2, random_state=0, cluster_std=0.6)

plt.scatter(X[:,0], X[:,1], c=y, s=50, cmap='autumn')

# Készítsük el az SVM osztályozónkat
# Az SVM esetén nagyon fontos, hogy milyen kernelt használunk
# kernel='linear' - lineáris kernel, egy hipersíkkal próbálja szeparálni az adatokat

#rbf_svc = svm.SVC(kernel='rbf', gamma=0.7).fit(X, y)
#poly_svc = svm.SVC(kernel='poly', degree=3).fit(X, y)
#lin_svc = svm.LinearSVC(C=C).fit(X, y)

clf = svm.SVC(kernel='linear')
clf.fit(X, y)
# Ez a függfény kirajzolja számunkra az SVM margin vektorait

def plot_svc_df(model):
    ax = plt.gca()
    xlim = ax.get_xlim()
    ylim = ax.get_ylim()
    
    x = np.linspace(xlim[0], xlim[1], 30)
    y = np.linspace(ylim[0], ylim[1], 30)
    
    X, Y = np.meshgrid(x, y)
    xy = np.c_[X.flatten(), Y.flatten()]
    
    p = model.decision_function(xy).reshape(X.shape)
    ax.contour(X, Y, p, colors='k', levels=[-1,0,1], alpha=0.5, linestyles=['--','-','--'] )
    ax.scatter(model.support_vectors_[:,0], model.support_vectors_[:,1], s=300, linewidth=3, facecolors='none', edgecolors='black')
    
    ax.set_xlim(xlim)
    ax.set_ylim(ylim)
# Nézzük meg a szeparáló egyenest és a hozzá tartozó margókat az előbbi random generált adaton

plt.figure(figsize=(12,6))
plt.scatter(X[:,0], X[:,1], c=y, cmap=plt.get_cmap("brg"), edgecolors='black')
plot_svc_df(clf)
# Készítsünk új adathalmazt, amelyben a két osztály körökben helyezkedik el
# Mit fog ezzel kezdeni az SVM?

X, y = make_circles(100, factor=0.1, noise=0.1)
plt.scatter(X[:,0], X[:,1], c=y, s=50, cmap='autumn')
# Lineáris kernellel nem tudjuk jól szeparálni

clf3 = svm.SVC(kernel='linear').fit(X,y)

plt.figure(figsize=(12,6))
plt.scatter(X[:,0], X[:,1], c=y, s=50, cmap='autumn')
plot_svc_df(clf3)
# Az rbf kernel már megoldja

clf3 = svm.SVC(kernel='rbf').fit(X,y)

plt.figure(figsize=(12,6))
plt.scatter(X[:,0], X[:,1], c=y, s=50, cmap='autumn')
plot_svc_df(clf3)
### Különböző kernelek a Support Vector osztályozóhoz
## Szöveg osztályozás Naive Byes módszerrel
# Hozzunk létre szöveges adatot és készítsünk belőle vektorokat
corpus = [
    'This is the first document.',
    'This document is the second document.',
    'And this is the third one.',
    'Is this the first document or the second doument or the third document?',
]

vectorizer = CountVectorizer()
X = vectorizer.fit_transform(corpus)
print(vectorizer.get_feature_names())
print(X.toarray())
# Alakítsuk át pandas dataframmé
# Így már könnyebben olvasható

x_df = pd.DataFrame(X.toarray(), columns=vectorizer.get_feature_names())
x_df
# Most nézzük meg a TFIDF reprezentációt is
# A TFIDF sokkal jobban jellemzi a dokumentumokat

vec = TfidfVectorizer()
X = vec.fit_transform(corpus)
pd.DataFrame(X.toarray(), columns=vec.get_feature_names())
from sklearn.datasets import fetch_20newsgroups
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import make_pipeline
from sklearn.metrics import confusion_matrix, accuracy_score
from scipy.cluster.hierarchy import dendrogram
# Új adathalmazt töltünk be: newsgroups
# Ez az sklearn.datasets moduljából be is tudjuk tölteni, nem kell fájlból beolvasni
# Szöveges posztokat tartalmaz, mindegyikhez tartozik egy kategória, ahová be lett küldve (amelyikhez kapcsolódik)
# Ebben az esetben a feature-jeink a szöveg, a cíkéink (label) a kategória

dataset = fetch_20newsgroups()
print(len(dataset.data))
dataset.data[0]
# Ezek a kategóriák fordulnak elő

text_categories = dataset.target_names
text_categories
# Válasszunk most ki néhány kategóriát, amit használni fogunk

category = ['talk.religion.misc', 'soc.religion.christian', 'sci.space', 'comp.graphics']

# A modulból külön ekérdezhető egy tanítő és tesztelő részhalmaz a megadott kategóriákhoz

train_data = fetch_20newsgroups(subset="train", categories=category)
test_data = fetch_20newsgroups(subset="test", categories=category)
# Néhény információ az adatunkról

print("Címkék: {}".format(len(category)))
print("Training számosság: {}".format(len(train_data.data)))
print("Test számosság {}".format(len(test_data.data)))
# Hozzunk létre egy pipelinet, aminek adjuk meg a tfidf átalakítást és a bayes modell építést

model = make_pipeline(TfidfVectorizer(), MultinomialNB())
model.fit(train_data.data, train_data.target)
predicted_categories = model.predict(test_data.data)
# A teszt adatokhoz prediktált kategóriák

predicted_categories
# A kategóriák szövegesen megjelenítve

print(np.array(test_data.target_names)[predicted_categories])
# Rajzoljuk ki a confusion matrixot
# A confusion mátrix segítségével megnézhetjük hány elemet osztályoztunk jól és rosszul

mat = confusion_matrix(test_data.target, predicted_categories)
disp = ConfusionMatrixDisplay(confusion_matrix=mat, display_labels=model.classes_)
fig, ax = plt.subplots(figsize=(8,8))
disp.plot(ax=ax)
# Osztályozzuk a saját szövegeinket

def predict_cat(s):
    pred = model.predict([s])
    return train_data.target_names[pred[0]]
print(predict_cat("sending payload to ISS"))
print(predict_cat("determining the screen resolution"))
## Készítsünk szintetikus adatot

# informative- olyan osztalyok amik befolyasoljak a kimenetet
X, y = make_classification(n_samples=1000, n_features=20, n_informative=15, n_redundant=5, random_state=5)

print('Shape: ', X.shape)

# Nézzük meg az első két sorát az adatunknak és a hozzá tartozó osztály címkéket
print(X[:2, :])
print(y[:2])
# Hozzunk létre egy bagging classifiert és nézzük meg a pontosságát...

clf = svm.SVC(kernel='linear')
model = BaggingClassifier(base_estimator=clf, n_estimators=10, random_state=7)

results = model_selection.cross_val_score(model, X, y, cv=10)
print(results.mean())
# ...és egy másikat is

dt = DecisionTreeClassifier()
model = BaggingClassifier(base_estimator=dt, n_estimators=10, random_state=7)

results = model_selection.cross_val_score(model, X, y, cv=10)
print(results.mean())
# Viszgáljuk meg a hiperparamétereket: osztályozók száma (néhány percig fut, az ezres miatt)

models = {}
n_trees = [1, 5, 10, 20, 50, 100]
for n in n_trees:
    models[str(n)] = BaggingClassifier(DecisionTreeClassifier(), n_estimators=n)

results = []
names = []

for name, model in models.items():
    scores = model_selection.cross_val_score(model, X, y, cv=10)
    results.append(scores)
    names.append(name)
    print('param: ', name, ', mean accuracy: ', scores.mean(), ', std: ', scores.std())
# Rajzoljuk ki az eredményt
plt.figure(figsize=(10,8))
plt.boxplot(results, labels=names, showmeans=True)
plt.show()
## Nézzük meg a random forest osztályozót! (először legyen az N és a max_depth alacsony, és nézzük meg, hogy javul a szám)

rfc = RandomForestClassifier(n_estimators = 50, max_depth=5, random_state=0)

results = model_selection.cross_val_score(rfc, X, y, cv=10)
print(results.mean())
print(results.std())
# Végül nézzünk meg egy AdaBoost osztályozót, amely eddig nem látott minősgű eredményt fog adni a megfelelő paraméterekkel

dt = DecisionTreeClassifier(max_depth=5)
clf = AdaBoostClassifier(base_estimator=dt, n_estimators=50, random_state=0)
results = model_selection.cross_val_score(clf, X, y, cv=10)
print(results.mean())
print(results.std())

#Gyak 12
pingvin = pd.read_csv('pingvin.csv')
pingvin.isnull().sum()
pingvin.drop('sex', axis=1, inplace=True)
pingvin.drop('year', axis=1, inplace=True)
pingvin.dropna(inplace=True)
pingvin.describe()
plt.figure(figsize=(6,4))
plt.xlabel('len')
plt.ylabel('mass')
plt.scatter(pingvin['flipper_length_mm'], pingvin['body_mass_g'], s=50, alpha=0.7)
_ = pd.plotting.scatter_matrix(pingvin, alpha=0.8, figsize=(8,8))
pingvin.head()

enc = preprocessing.OrdinalEncoder()
encoded = enc.fit_transform(pingvin[['island']])
pingvin['island2'] = encoded
pingvin.drop('island', axis=1, inplace=True)
pingvin.head()

scaler = StandardScaler()
pingvin_sc = scaler.fit_transform(pingvin.values)
pingvin_sc

cl = KMeans(n_clusters=2, init='random', n_init=10, max_iter=200)
cl.fit(pingvin_sc)
clusters = cl.predict(pingvin_sc)
clusters
_ = pd.plotting.scatter_matrix(pingvin.loc[:, pingvin.columns != 'island2'], alpha=0.8, figsize=(8,8), c=clusters)
cl = KMeans(n_clusters=3, init='random', n_init=10, max_iter=200)
cl.fit(pingvin_sc)
clusters = cl.predict(pingvin_sc)
_ = pd.plotting.scatter_matrix(pingvin.loc[:, pingvin.columns != 'island2'], alpha=0.8, figsize=(8,8), c=clusters)
# Inertia - a pontok távolságának átlaga a klaszter közepétől
# A klaszterezés akkor jó, ha minél kevesebb klaszterünk van, és minél kissebb az inertia érték

cl.inertia_
# A silhouette azt méri, hogy az azonos klaszterben lévő pontok mennyire hasonlítanak a klaszterben lévő többi ponthoz
# és mennyire különböznek más klaszterekben lévő pontoktól
# Minél nagyobb a silhouette érték, annál jobb a klaszterezésünk

silhouette_score(pingvin_sc, labels=clusters)

pca = PCA(n_components=2)
stats = pca.fit_transform(pingvin_sc)
print(pca.explained_variance_ratio_)
plt.figure(figsize=(8,6))
plt.xlabel('K')
plt.ylabel('D')
plt.scatter(stats[:, 0], stats[:, 1], s=50, c=clusters, alpha=0.7)
def generate_models(K_array, train):
    models = []
    for k in K_array:
        kmod = KMeans(n_clusters=k, n_init=5)
        kmod.fit(train)
        models.append(kmod)
    return models

k_array = np.arange(1,12)
models = generate_models(k_array, data)
plt.figure(figsize=(10,3))
plt.plot(k_array, [m.inertia_ for m in models], marker='o', markersize=6, 
      markeredgewidth=2, markeredgecolor='r', markerfacecolor='None')
plt.grid(True)
plt.xlabel('Number of clusters')
plt.ylabel('Inertia')
plt.show()
def get_silhouette_scores(train, model_array):
    return [silhouette_score(train, labels=model.predict(train), sample_size=100) for model in model_array]

plt.figure(figsize=(10,3))
plt.plot(k_array[1:], get_silhouette_scores(data, models[1:]), marker='x', markersize=6, 
      markeredgewidth=2, markeredgecolor='b', markerfacecolor='None')
plt.grid(True)
plt.xlabel('Number of clusters')
plt.ylabel('Silhouette score')
plt.show()
plt.figure(figsize=(18,6))
linkage_data = linkage_tree(pingvin_sc, method='average', metric='euclidean')
dendrogram(linkage_data)
plt.show()
hc = AgglomerativeClustering(n_clusters=3, metric='euclidean', linkage='average')
clusters = hc.fit_predict(pingvin_sc)
clusters
_ = pd.plotting.scatter_matrix(pingvin.loc[:, pingvin.columns != 'island2'], alpha=0.8, figsize=(8,8), c=clusters)
url="https://vargadaniel.web.elte.hu/bigdata24/pingvin_full.csv"
pingvin_full=pd.read_csv(url)
pingvin_full.head()
gt = pingvin_full.drop(['sex', 'year'], axis=1).dropna()['species']
matrix = pd.DataFrame({ 'clusters': clusters, 'species': gt })
ct = pd.crosstab(matrix['clusters'], matrix['species'])
print(ct)

#ZH Gyakorlás
## Spotify data betöltése
#https://www.kaggle.com/datasets/joebeachcapital/30000-spotify-songs/
spotify = pd.read_csv('spotify_songs.csv')
spotify.head()

## Null értékek
spotify.isnull().sum()
spotify['mode'].fillna(spotify['mode'].median(),inplace=True)
spotify.dropna(inplace=True)

## Binelés
#A 'track_popularity' oszlopot alakítsuk át kategória oszlopra. 3 kategória legyen az értékek alapján: 'Unalmas', 'Hallgatható', 'Trendi'
my_labels = ['Unalmas', 'Hallgatható', 'Trendi']
spotify['track_popularity']= pd.qcut(spotify['track_popularity'], q=3, labels=my_labels)
spotify.head()

## Plotolás
#Készíts histogramot a track_popularity oszlopból!
#Készíts scatter plotot a 'track_popularity' és 'duration_ms' oszlopokból!
#Készíts bar plotot a "playlist_genre"-re csoportosítva és a következő adatokat jelenítsd meg:
'playlist_genre','danceability', 'energy', 'speechiness', 'acousticness', 'instrumentalness', 'liveness', 'valence'
plt.hist(spotify['track_popularity'])
plt.scatter(spotify['track_popularity'], spotify['duration_ms'])
features = ['playlist_genre','danceability', 'energy',
     'speechiness', 'acousticness', 'instrumentalness',
       'liveness', 'valence']
df_group = spotify[features].groupby(["playlist_genre"]).mean()
df_group.plot(kind='bar',figsize=(10,5))

## Kategória oszlop készítése
#A 'track_popularity' oszopból készítsünk új oszlopokat 'pop' prefixszel!
#Konkatenáld össze az új oszlookat az eredeti adathalmazzal!
#Dobd el a 'track_popularity' és 'track_id' oszlopokat!
cols = pd.get_dummies(spotify['track_popularity'], prefix='pop')
df = pd.concat([spotify, cols], axis=1)
df.drop(['track_popularity', 'track_id'], axis=1, inplace=True)
df.head()
df.columns

## Naive Bayes
#Készíts egy Naive Bayes osztályozót, amely a 'playlist_genre'-t akarja predikálni a 'track_name' alapján!
#A szöveg vektorizálásához használd a TfidfVectorizert!
#Tanításhoz- ellenőrzéshez 80-20 arányban szeparáld az adatahalmazt!
#Az eredményt egy confusion_matrix-on jelenítsd meg!
featuresNB = 'track_name'
label = 'playlist_genre'
feat_data = df[featuresNB].copy()
label_data = df[label].copy()


print(feat_data.shape[0])
print(label_data.shape[0])

X_train, X_test, y_train, y_test = train_test_split(feat_data, label_data, test_size=0.2, random_state=42)

print(y_test.value_counts())

model = make_pipeline(TfidfVectorizer(), MultinomialNB())
model.fit(X_train, y_train)
predicted_categories = model.predict(X_test)
mat = confusion_matrix(y_test, predicted_categories)
labs = list(label_data.unique())
labs.sort()
disp = ConfusionMatrixDisplay(confusion_matrix=mat, display_labels=labs)
fig, ax = plt.subplots(figsize=(8,8))
disp.plot(ax=ax)

## Klaszterezés
#Készíts egy K-means klaszterezést a 'liveness', 'acousticness' paraméterekre és a K legyen 3!
#Az eredményt egy scatter ploton jelenítsd meg!
df_c = df[['liveness', 'acousticness']]
cl = KMeans(n_clusters=3, init='random', n_init=10, max_iter=200)
cl.fit(df_c)
clusters = cl.predict(df_c)
plt.figure(figsize=(12,6))
plt.xlabel('liveness')
plt.ylabel('acousticness')
plt.scatter(df['liveness'], df['acousticness'], s=50, c=clusters, alpha=0.7)

## Osztályozás
#Készíts egy DecisionTree oszályozást a 'playlist_genre'-re a következő feature-ök felhasználásával:
#'danceability', 'energy', 'key', 'loudness', 'speechiness', 'acousticness', 'instrumentalness', 'liveness', 'valence', 'tempo', 'duration_ms', 'pop_Unalmas','pop_Hallgatható', 'pop_Trendi'
#A tanítás-teszt aránya legyen 80-20
#Az eredményt cross_val_score átlagával add meg!
#Készíts AdaBoost osztályozót ugyanerre a döntési fára és nézd meg hogy jobb eredményt ad-e!
features = ['danceability', 'energy', 'key',
       'loudness', 'speechiness', 'acousticness', 'instrumentalness',
       'liveness', 'valence', 'tempo', 'duration_ms', 'pop_Unalmas',
       'pop_Hallgatható', 'pop_Trendi']

label = 'playlist_genre'
feat_data = df[features].copy()
label_data = df[label].copy()

print(df[label].unique())

print(feat_data.shape[0])
print(label_data.shape[0])

X_train, X_test, y_train, y_test = train_test_split(feat_data, label_data, test_size=0.2, random_state=42)


clf = DecisionTreeClassifier(max_depth=5)
clf.fit(X_train, y_train)


scores = cross_val_score(clf, X_train, y_train, cv=10)
scores.sort()
accuracy = scores.mean()

print(scores)
print(accuracy)
dt = DecisionTreeClassifier(max_depth=5)
clf = AdaBoostClassifier(estimator=dt, n_estimators=5, random_state=0)
results = model_selection.cross_val_score(clf, X_train, y_train, cv=10)
print(results.mean())
print(results.std())


