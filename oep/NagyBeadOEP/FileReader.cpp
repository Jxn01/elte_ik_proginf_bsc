#include <iostream>
#include <sstream>
#include "FileReader.h"

using namespace std;

FileReader* FileReader::ins = nullptr;

FileReader* FileReader::getInstance(std::string fn)
{
	if(ins == nullptr)
	{
		ins = new FileReader(fn);
	}
	return ins;
}

void FileReader::read()
{
	fs.open(fileName);

	if (fs.fail()) { throw FileErrorException(); }

	string line;

	while (!fs.eof())
	{
		getline(fs, line);
		lines.push_back(line);
	}

	lines.pop_back();

	fs.close();
}

tuple<vector<Plant*>, int, int> FileReader::process()
{
	vector<Plant*> plants;

	try
	{
		read();
	}
	catch (FileErrorException&)
	{
		cerr << "File can't be opened! " << endl;
		exit(1);
	}

	for (unsigned int i = 1; i < lines.size() - 1; i++)
	{
		istringstream s(lines.at(i));
		string name;
		string type;
		int resource;

		s >> name >> type >> resource;

		if (type == "p")
		{
			Plant* plant = new Puffancs(name, resource);
			plants.push_back(plant);
		}
		else if (type == "b")
		{
			Plant* plant = new Parabokor(name, resource);
			plants.push_back(plant);
		}
		else if (type == "d")
		{
			Plant* plant = new Deltafa(name, resource);
			plants.push_back(plant);
		}
	}
	int daysNum = stoi(lines.at(lines.size()-1));
	int plantsNum = stoi(lines.at(0));

	cout << "File name: " << fileName << endl;
	cout << "Content:" << endl;

	for (string line : lines)
	{
		cout << line << endl;
	}

	tuple<vector<Plant*>, int, int> result = make_tuple(plants, daysNum, plantsNum);
	return result;
}
