#ifndef CLASSES_H_INCLUDED
#define CLASSES_H_INCLUDED

#include <vector>

class Registration
{
public:
	virtual int getSize() { return 0; }
};

class File : public Registration
{
private:
	int size;
public:
	File(int s){ size = s; }
	int getSize() override { return size; }
};

class Folder : public Registration
{
private:
	std::vector<Registration*> items;

public:
	int getSize() override
	{
		int sum = 0;
		for (Registration* elem : items){ sum += elem->getSize(); }
		return sum;
	}

	void add(Registration* r){ items.push_back(r); }
};

class FileSystem : public Folder{};

#endif // CLASSES_H_INCLUDED