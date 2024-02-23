#include "Day.h"
#include <tuple>

using namespace std;

Day* Day::ins = nullptr;

Day* Day::getInstance()
{
	if (ins == nullptr)
	{
		ins = new Day();
	}
	return ins;
}

void Day::nextDay(std::vector<tuple<Radiation, int>> claims)
{
	if (!twoDaysOfNoRadiation)
	{
		int sumAlpha = 0;
		int sumDelta = 0;

		for (tuple<Radiation, int> elem : claims)
		{
			if (get<0>(elem) == Radiation::Alpha) { sumAlpha += get<1>(elem); }
			if (get<0>(elem) == Radiation::Delta) { sumDelta += get<1>(elem); }
		}

		if (sumAlpha - 3 >= sumDelta) {
			radiation = Radiation::Alpha;
		}
		else if (sumDelta - 3 >= sumAlpha)
		{
			radiation = Radiation::Delta;
		}
		else
		{
			if (radiation == Radiation::None) { twoDaysOfNoRadiation = true; }
			radiation = Radiation::None;
		}
	}
}
