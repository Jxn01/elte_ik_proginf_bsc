#ifndef FILEREADER_H
#define FILEREADER_H
#include <fstream>
#include <vector>
#include <tuple>
#include "Plant.h"

class FileReader
{
public:

	static FileReader* getInstance(std::string fn);

	~FileReader() = default;

	std::string getFileName() { return fileName; }

	std::tuple<std::vector<Plant*>, int, int> process();

	class FileErrorException : public std::exception {};

	std::vector<std::string> getLines() { return lines; }

	void destroy(){ delete ins; ins = nullptr; }

private:
	FileReader(std::string fn) { fileName = fn; }
	void read();
	std::ifstream fs;
	std::string fileName;
	std::vector<std::string> lines;
	static FileReader* ins;
};

#endif //FILEREADER_H
