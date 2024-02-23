#ifndef DAY_H
#define DAY_H

#include <vector>
#include "Radiation.h"

class Day
{
public:

	static Day* getInstance();
	~Day() = default;

	Radiation getRadiation() { return radiation; }
	bool isTwoDaysOfNoRadiation() { return twoDaysOfNoRadiation; }

	void nextDay(std::vector<std::tuple<Radiation, int>> claims);

	void destroy() { delete ins; ins = nullptr; }
private:

	Day() = default;
	Radiation radiation = Radiation::None;
	bool twoDaysOfNoRadiation = false;
	static Day* ins;
};

#endif //DAY_H
