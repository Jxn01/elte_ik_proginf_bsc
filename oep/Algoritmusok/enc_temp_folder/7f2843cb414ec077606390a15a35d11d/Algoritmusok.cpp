#include <iostream>
#include <regex>

using namespace std;

static void clearInput()
{
    cin.clear();
    cin.ignore(999, '\n');
}

static void printArray(int A[])
{
    int n = sizeof(A);
    cout << "[";
    for (int i = 0; i < n; i++)
    {
        if (i == n - 1) { cout << A[i]; }
        else { cout << A[i] << ", "; }
    }
    cout << "]";
}

class Menu
{
private:
    int getMenuPoint()
    {
        clearInput();
        int menuPoint = 0;
        string menuPointString;
        bool hiba = false;
        do
        {
            if (hiba) {
                cout << "\n****************************************\n";
                cout << "---------KOMPLEX SZAM KALKULATOR--------\n\n";
                cout << "   A kezdeshez nyomjon le egy entert!\n";
                cout << "****************************************\n";
            }

            cout << "\n****************************************\n\n";
            cout << "0. Kilepes\n";
            cout << "1. Insertion sort\n";
            cout << "\n****************************************\n";
            cout << "\nKerem adja meg a kivant menupont szamat! (0-200)";
            cout << "\nMenupont szama: ";

            hiba = false;

            const regex regex("[0-9]*");
            getline(cin, menuPointString);

            if (!regex_match(menuPointString, regex))
            {
                cout << "\nErvenytelen bemenet, kerem egy szamot adjon meg 0-tol 200-ig a menupontok kivalasztasahoz!" << endl;
                cout << "A bemenet ujboli megadasahoz nyomjon meg egy billentyut!" << endl;
                hiba = true;
                system("pause");
                system("CLS");
            }
            else {
                menuPoint = stoi(menuPointString);
            }
        } while (hiba);
        return menuPoint;
    }

    void insertionSort()
    {
        cout << "\n****************************************\n\n";
        cout << "Insertion sort a: [2, 5, 8, 3, 6, 22, 656, 234, 2, 67, 1, 0] tombre. \n";
        bool hiba;
        int A [] = {2, 5, 8, 3, 6, 22, 656, 234, 2, 67, 1, 0};
        int n = sizeof(A);

        cout << "Kezdeti allapot: [2, 5, 8, 3, 6, 22, 656, 234, 2, 67, 1, 0] n = 12" << endl;

        for(int i = 1; i < n; i++) // struktogramban 1-tõl megy az index
        {
	        if(A[i-1] > A[i])
	        {
                int x = A[i];
                A[i] = A[i - 1];
                int j = i - 2;
                while(j > -1 && A[j] > x)
                {
                    A[j + 1] = A[j];
                    j--;
                }
                A[j + 1] = x;

	        }
        }

        cout << "Vegso allapot: ";
        printArray(A);

        system("pause");
        system("CLS");
    }

public:
    Menu() = default;
    void display()
    {
        int menuPoint;
        do {
            cout << "\n****************************************\n";
            cout << "---------ALGO SZAR--------\n\n";
            cout << "   A kezdeshez nyomjon le egy entert!\n";
            cout << "****************************************\n";
            menuPoint = getMenuPoint();
            system("CLS");
            switch (menuPoint) {
            case 1:
                insertionSort();
                break;

            default:
                cout << "\nViszlat!\n";
                exit(0);
            }
        } while (menuPoint != 0);
    }
};

int main()
{
    Menu menu;

    menu.display();

    return 0;
}