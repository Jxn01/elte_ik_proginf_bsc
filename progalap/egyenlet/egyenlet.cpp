
#include <iostream>

using namespace std;

int main()
{

    setlocale(LC_ALL, "hun");

    double a = 0.0;
    double b = 0.0;
    double x = 0.0;
    string �zenet = "";

    cout << "Hat�rozzuk meg az egy�tthat�ival megadott ax + b = 0 alak� els�fok� egyenlet megold�s�t!" << endl;
    cout << "K�rem adja meg a-t �s b-t mint val�s sz�mokat!" << endl;

    cin >> a;
    cin >> b;

    while (cin.fail() || cin.peek() != '\n') {
        cin.clear();
        cin.ignore(999, '\n');
        cout << "Hib�s �rt�keket adott meg, k�rem adja meg �ket �jb�l!" << endl;
        cin >> a;
        cin >> b;
    }

    x = -b / a;
    if(a==0 && b==0)
    {
        �zenet = "Azonoss�g";
    }else if(a==0 && b!=0)
    {
        �zenet = "Ellentmond�s";
    }

    cout << "Az egyenlet megold�sa x = " << x << "\n" << �zenet << endl;
}

