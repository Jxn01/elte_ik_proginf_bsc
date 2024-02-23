//Saját név: Oláh Norbert
//Gyakorlatvezető neve: Törley Gábor
#include <iostream>
#include <vector>

using namespace std;

int main()
{
    setlocale(LC_ALL, "hun");
    int dokuDb;
    bool odsVan = false;
    int odtHasznalokDb = 0;
    bool hiba = false;
    cout << "Kérem adja meg a dokumentumok számát!" << endl;
    do{
        hiba = false;
        cout << "Dokumentumok száma: ";
        cin >> dokuDb;

        if(cin.fail()||cin.peek()!='\n'||dokuDb < 0 || dokuDb > 10){
            cout << "Rossz adatot adott meg!" << endl;
            hiba = true;

            if(cin.fail()||cin.peek()!='\n'){
                cout << "Valamit elrontott, valószínűleg nem adott meg adatot, vagy nem számot adott meg!" << endl;
            }else{
                if(dokuDb < 0){
                    cout << "A dokumentumok számának minimum 1-nek kell lennie." <<endl;
                }

                if(dokuDb > 10){
                    cout << "A dokumentumok száma maximum 10 lehet." << endl;
                }
            }

            cin.clear();
            cin.ignore(999,'\n');
            cout << "Kérem adja meg újból az adatokat!" << endl;
        }
    }while(hiba);

    vector<string> Dokuk;
    Dokuk.reserve(dokuDb);

    do{
        for(int i=0; i<dokuDb; i++){
            hiba = false;
            cout << i+1 <<". kolléga utolsó dokumentuma: ";
            cin >> Dokuk[i];

            if(cin.fail()||cin.peek()!='\n'||(Dokuk[i]!="odf"&&Dokuk[i]!="ods"&&Dokuk[i]!="odp"&&Dokuk[i]!="odg"&&Dokuk[i]!="odb"&&Dokuk[i]!="odt")){
                cout << "Rossz adatot adott meg!" << endl;
                hiba = true;

                if(cin.fail()||cin.peek()!='\n'){
                    cout << "Valamit elrontott, valószínűleg nem adott meg adatot!" << endl;
                }else{
                    if(Dokuk[i]!="odf"&&Dokuk[i]!="ods"&&Dokuk[i]!="odp"&&Dokuk[i]!="odg"&&Dokuk[i]!="odb"&&Dokuk[i]!="odt"){
                        cout << "Rossz dokumentum-kiterjeszt�st adott meg!\nA dokumentum kiterjeszt�se a k�vetkez�k lehetnek: odf, ods, odp, odg, odb, odt" <<endl;
                    }
                }

                cin.clear();
                cin.ignore(999,'\n');
                cout << "Kérem adja meg újból az adatokat!" << endl;
            }
        }
    }while(hiba);

    vector<int> odtHasznalok;
    odtHasznalok.reserve(odtHasznalokDb);

    for(int i=0; i<dokuDb;i++){
        if(Dokuk[i]=="odt"){
            odtHasznalokDb++;
            odtHasznalok.push_back(i+1);
        }
    }

    int i=1;
    while(i<dokuDb && Dokuk[i]!="ods"){
        i++;
    }

    odsVan = (i<=dokuDb) ? true : false;

    cout << odtHasznalokDb << " ember használt utolsóként nyílt szöveges dokumentumot." << endl;

    if(odtHasznalokDb>0){
        cout << "Ezek az emberek pedig:" << endl;

        for(int elem : odtHasznalok){
            cout << "\t" << elem << endl;
        }
    }

    if(odsVan){
        cout << "Volt olyan ember aki nyílt táblázat-dokumentumot (ods) használt utoljára." << endl;
    }else{
        cout << "Nem volt olyan ember aki nyílt táblázat-dokumentumot (ods) használt utoljára." << endl;
    }

    return 0;
}
