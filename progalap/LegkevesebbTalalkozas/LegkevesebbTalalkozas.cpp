#include <iostream>
#include <vector>
using namespace std;

class Intervallum
{

public:
    int begin;
    int end;

    Intervallum(int begin, int end)
        : begin(begin),
        end(end)
    {
    }

    Intervallum() = default;
};

int main()
{

    const bool cerrmode = true;
    ios_base::sync_with_stdio(false);

    int n;
    int p;
    int max = 0;
    int minn = 10000;
    cin >> n;
    cin >> p;
    vector<Intervallum> masEmberek;
    vector<Intervallum> lehetsegesek;
    masEmberek.reserve(n);

    for (int i = 0; i < n; i++)
    {
        int begin;
        int end;

        cin >> begin;
        cin >> end;

        if (max < end)
        {
            max = end;
        }

        if (minn > begin)
        {
            minn = begin;
        }

        masEmberek.emplace_back(begin, end);
    }

    if (cerrmode)
        cerr << "\nLehetseges intervallumok: " << endl;

    lehetsegesek.reserve(max - p);
    for (int i = minn; i <= max - p + 1; i++)
    {
        lehetsegesek.emplace_back(i, i + p - 1);

        if (cerrmode)
            cerr << "\n" << i << " : " << i + p - 1 << endl;
    }

    int index = 0;
    vector<Intervallum> nemvoltmeg;
    int min = 10000;
    int emberekSzama = 0;

    if (cerrmode)
        cerr << "\nIntervallumok tesztelese: " << endl;

    for (Intervallum lehetseges : lehetsegesek)
    {

        bool breakBool = false;
        if (cerrmode)
            cerr << "Uj intervallumot tesztelunk: " << lehetseges.begin << " : " << lehetseges.end << endl;

        for (Intervallum masEmber : masEmberek)
        {

            if (masEmber.end >= lehetseges.begin)
            {
                nemvoltmeg.push_back(masEmber);

                if (cerrmode)
                    cerr << lehetseges.begin << " : " << lehetseges.end << " intervallumot nezzuk a " << masEmber.begin << " : " << masEmber.end << " intervallummal" << endl;

                if (lehetseges.begin >= masEmber.begin && lehetseges.begin <= masEmber.end || lehetseges.end <= masEmber.end && lehetseges.end >= masEmber.begin || lehetseges.begin <= masEmber.begin && lehetseges.end >= masEmber.end)
                {
                    emberekSzama++;

                    if (cerrmode)
                        cerr << "Benne van" << endl;

                }
                else
                {
                    if (cerrmode)
                        cerr << "Nincs benne" << endl;
                }
            }

        }

        masEmberek = nemvoltmeg;
        nemvoltmeg.clear();


        if (cerrmode)
            cerr << "\nEbben az intervallumban levo emberek szama: " << emberekSzama << endl;

        if (emberekSzama < min)
        {
            if (cerrmode)
                cerr << "Ez a szam kisebb mint az elozo minimum (" << min << "), igy az uj minimum ertek es hely: " << endl;

            min = emberekSzama;
            index = lehetseges.begin;

            if (cerrmode)
                cerr << "Ertek: " << min << "\nHely: " << index << endl;
        }
        else
        {
            if (cerrmode)
                cerr << "Ez a szam nem kisebb mint az elozo minimum (" << min << "), igy a jelnlegi minimum hely es ertek marad: \nErtek: " << min << "\nHely: " << index << endl;
        }


        if (cerrmode)
            cerr << endl;

        emberekSzama = 0;
    }

    if (cerrmode)
        cerr << "A legvegso hely es ertek paros vegul:\nHely: ";

    cout << index << endl;

    if (cerrmode)
        cerr << "Ertek: ";

    cout << min << endl;

    return 0;
}