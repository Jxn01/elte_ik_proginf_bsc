#include <iostream>
#include "classes.h"

using namespace std;

int main()
{
	FileSystem *root = new FileSystem();
	Folder *bin = new Folder();
	Folder *dev = new Folder();
	Folder *etc = new Folder();
	Folder *usr = new Folder();
	Folder *home = new Folder();
	Folder *man = new Folder();
	Folder *lib = new Folder();
	Folder *local = new Folder();

	File *file1 = new File(1);
	File *file2 = new File(2);
	File *file3 = new File(4);
	File *file4 = new File(8);
	File *file5 = new File(16);
	File *file6 = new File(32);
	File *file7 = new File(64);
	File *file8 = new File(128);
	File *file9 = new File(256);
	File *file10 = new File(512);
	File *file11 = new File(1024);
	File *file12 = new File(2048);

	root->add(bin);
	root->add(dev);
	root->add(etc);
	root->add(usr);
	root->add(home);
	home->add(man);
	home->add(lib);
	home->add(local);

	bin->add(file1);
	bin->add(file2);
	dev->add(file3);
	home->add(file4);
	home->add(file5);
	home->add(file6);
	man->add(file7);
	man->add(file8);
	lib->add(file9);
	local->add(file10);
	local->add(file11);
	local->add(file12);

	cout << "The size of root is: " << root->getSize() << endl;
	cout << "The size of root/bin is: " << bin->getSize() << endl;
	cout << "The size of root/dev is: " << dev->getSize() << endl;
	cout << "The size of root/etc is: " << etc->getSize() << endl;
	cout << "The size of root/usr is: " << usr->getSize() << endl;
	cout << "The size of root/home is: " << home->getSize() << endl;
	cout << "The size of root/home/man is: " << man->getSize() << endl;
	cout << "The size of root/home/lib is: " << lib->getSize() << endl;
	cout << "The size of root/home/local is: " << local->getSize() << endl;

	delete root;

    return 0;
}
