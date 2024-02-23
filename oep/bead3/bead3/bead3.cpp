#include <iostream>
#include "classes.h"

using namespace std;

int main()
{
	FileSystem root;
	Folder bin;
	Folder dev;
	Folder etc;
	Folder usr;
	Folder home;
	Folder man;
	Folder lib;
	Folder local;

	File file1(1);
	File file2(2);
	File file3(3);
	File file4(4);
	File file5(5);
	File file6(10);
	File file7(20);
	File file8(40);
	File file9(80);
	File file10(120);
	File file11(150);
	File file12(1000);

	root.add(&bin);
	root.add(&dev);
	root.add(&etc);
	root.add(&usr);
	root.add(&home);
	home.add(&man);
	home.add(&lib);
	home.add(&local);

	bin.add(&file1);
	bin.add(&file2);
	dev.add(&file3);
	home.add(&file4);
	home.add(&file5);
	home.add(&file6);
	man.add(&file7);
	man.add(&file8);
	lib.add(&file9);
	local.add(&file10);
	local.add(&file11);
	local.add(&file12);

	cout << "The size of root is: " << root.getSize() << endl;
	cout << "The size of root/bin is: " << bin.getSize() << endl;
	cout << "The size of root/dev is: " << dev.getSize() << endl;
	cout << "The size of root/etc is: " << etc.getSize() << endl;
	cout << "The size of root/usr is: " << usr.getSize() << endl;
	cout << "The size of root/home is: " << home.getSize() << endl;
	cout << "The size of root/home/man is: " << man.getSize() << endl;
	cout << "The size of root/home/lib is: " << lib.getSize() << endl;
	cout << "The size of root/home/local is: " << local.getSize() << endl;

    return 0;
}
