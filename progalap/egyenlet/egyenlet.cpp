
#include <iostream>

using namespace std;

int main()
{

    setlocale(LC_ALL, "hun");

    double a = 0.0;
    double b = 0.0;
    double x = 0.0;
    string üzenet = "";

    cout << "Határozzuk meg az együtthatóival megadott ax + b = 0 alakú elsõfokú egyenlet megoldását!" << endl;
    cout << "Kérem adja meg a-t és b-t mint valós számokat!" << endl;

    cin >> a;
    cin >> b;

    while (cin.fail() || cin.peek() != '\n') {
        cin.clear();
        cin.ignore(999, '\n');
        cout << "Hibás értékeket adott meg, kérem adja meg õket újból!" << endl;
        cin >> a;
        cin >> b;
    }

    x = -b / a;
    if(a==0 && b==0)
    {
        üzenet = "Azonosság";
    }else if(a==0 && b!=0)
    {
        üzenet = "Ellentmondás";
    }

    cout << "Az egyenlet megoldása x = " << x << "\n" << üzenet << endl;
}

