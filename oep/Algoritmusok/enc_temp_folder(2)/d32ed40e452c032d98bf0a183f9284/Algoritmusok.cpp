#include <iostream>
#include <regex>
#include <vector>

using namespace std;

static void clearInput()
{
    cin.clear();
    cin.ignore(999, '\n');
}

static void printArray(vector<int> A)
{
    int n = A.size();
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
                cout << "-----------------ALGO SZAR-----------------\n\n";
                cout << "   A kezdeshez nyomjon le egy entert!\n";
                cout << "****************************************\n";
            }

            cout << "\n****************************************\n\n";
            cout << "0. Kilepes\n";
            cout << "1. Insertion sort\n";
            cout << "2. Merge sort\n";
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
        vector<int> A= {2, 5, 8, 3, 6, 22, 656, 234, 2, 67, 1, 0};
        int n = A.size();

        cout << "Kezdeti allapot: [2, 5, 8, 3, 6, 22, 656, 234, 2, 67, 1, 0] n = 12" << endl;

        for(int i = 1; i < n; i++) // struktogramban 1-tõl megy az index
        {
            cout << "\n" << i << ". iteracio: ";
            printArray(A);
            cout << endl;

            string igaz = (A[i - 1] > A[i]) ? "Igaz" : "Hamis";
            cout << "A[" << (i - 1) << "] > A[" << i << "]: " << igaz << endl;
	        if(A[i-1] > A[i])
	        {
                cout << "Csere " << "A[" << (i - 1) << "] es A[" << i << "] kozott: [.., " << A[i - 1] << ", " << A[i] << ", ..] -> [.., " << A[i] << ", " << A[i - 1] << ", ..]" << endl;
                int x = A[i];
                A[i] = A[i - 1];
                int j = i - 2;
                while(j > -1 && A[j] > x)
                {
                    cout << "Csere " << "A[" << (j+1) << "] es A[" << j << "] kozott: [.., " << A[j + 1] << ", " << A[j] << ", ..] -> [.., " << A[j] << ", " << A[j + 1] << ", ..]" << endl;
                    A[j + 1] = A[j];
                    j--;
                }
                A[j + 1] = x;
	        }
        }

        cout << "Vegso allapot: ";
        printArray(A);
        cout << endl;

        system("pause");
        system("CLS");
    }

    void mergeSort()
    {
        cout << "\n****************************************\n\n";
        cout << "Merge sort a: [5, 3, 1, 6, 8, 3, 2] tombre. \n";
        bool hiba;
        vector<int> A = { 5, 3, 1, 6, 8, 3, 2 };
        int n = A.size();

        cout << "Kezdeti allapot: [5, 3, 1, 6, 8, 3, 2] n = 7" << endl;

        ms(&A, 0, n);

        cout << "Vegso allapot: ";
        printArray(A);
        cout << endl;

        system("pause");
        system("CLS");
    }

    void ms(vector<int> *A, int u, int v)
    {
	    if(u < v - 1)
	    {
            int m = (u + v) / 2;
            ms(A, u, m);
            ms(A, m, v);
            merge(A, u, m, v);
	    }
    }

    void merge(vector<int> *A, int u, int m, int v)
    {
        int d = m - u;
        vector<int> Z;
        for(int i = 0; i < d; i++){Z.push_back(A->at(i));}
        int k = u;
        int i;
        int j;
        for(i = m, j = 0; i < v && j < d; k++)
        {
	        if(A->at(i) < Z[j])
	        {
                A->at(k) = A->at(i);
                i++;
	        }else
	        {
                A->at(k) = Z[j];
                j++;
	        }
        }
        while(j < d)
        {
            A->at(k) = Z[j];
            k++;
            j++;
        }
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

            case 2:
                mergeSort();
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