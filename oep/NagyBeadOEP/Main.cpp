#include <iostream>
#include <vector>

#include "Day.h"
#include "Plant.h"
#include "FileReader.h"

using namespace std;

string getRadiationType(Radiation radiation)
{
	string radiationType;
	switch (radiation)
	{
	case Radiation::Alpha: radiationType = "Alpha"; break;
	case Radiation::Delta: radiationType = "Delta"; break;
	case Radiation::None: radiationType = "None"; break;
	}
	return radiationType;
}

void effects(vector<Plant*>* plants, vector<tuple<Radiation, int>>* claims, Day* day)
{
	for (Plant* elem : *plants)
	{
		elem->radiated(day->getRadiation());
		if (elem->isAlive()) { claims->push_back(elem->getClaim()); }
		cout << elem->toString() << endl;
	}
}

void deleteAll(FileReader* file, vector<Plant*> plants, Day* day)
{
	for (Plant* elem : plants) { delete elem; }
	day->destroy();
	file->destroy();
}

//#define NORMAL_MODE
#ifdef NORMAL_MODE

//Feladat:       Novenyek szimulacioja
//Bemeno adatok: szoveges fajl (txt)
//Kimeno adatok: Bemeno fajl tartalma, a novenyek napi adatai
//Tevekenyseg:   A szoveges allomany beolvasasa es feldolgozasa, majd a nap es a novenyek letrehozasa,
//				 ezutan pedig a novenyek szimulacioja a napok es a sugarzas alapjan.

int main()
{
	cout << "Please specify the input file: ";
	string filename;
	cin >> filename;
	cout << endl << endl;
	FileReader* file = FileReader::getInstance(filename);
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	for (int i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	deleteAll(file, plants, day);
	return 0;
}

#else

bool isAllAlive(vector<Plant*> plants)
{
	bool result = true;
	for (auto elem = plants.begin(); elem != plants.end() && result == true; ++elem)
	{
		result = result && (*elem)->isAlive();
	}
	return result;
}

bool isAllDead(vector<Plant*> plants)
{
	bool result = false;
	for (auto elem = plants.begin(); elem < plants.end() && result == false; ++elem)
	{
		result = result || (*elem)->isAlive();
	}
	return !result;
}

bool isAllAlpha(vector<Plant*> plants)
{
	bool result = true;
	for (auto elem = plants.begin(); elem < plants.end() && result == true; ++elem)
	{
		result = result && (*elem)->pt==PlantType::Puffancs;
	}
	return result;
}

bool isAllDelta(vector<Plant*> plants)
{
	bool result = true;
	for (auto elem = plants.begin(); elem < plants.end() && result == true; ++elem)
	{
		result = result && (*elem)->pt==PlantType::Deltafa;
	}
	return result;
}

bool isAllNone(vector<Plant*> plants)
{
	bool result = true;
	for (auto elem = plants.begin(); elem < plants.end() && result == true; ++elem)
	{
		result = result && (*elem)->pt==PlantType::Parabokor;
	}
	return result;
}

bool onlyXLives(vector<Plant*> plants, PlantType x)
{
	bool result = true;
	for (auto elem = plants.begin(); elem < plants.end() && result == true; ++elem)
	{
		result = result && ((*elem)->pt == x && (*elem)->isAlive() || (*elem)->pt != x && !(*elem)->isAlive());  //XNOR
	}
	return result;
}

#define CATCH_CONFIG_MAIN
#include "catch.hpp"

TEST_CASE("1", "inp.txt")
{
	FileReader* file = FileReader::getInstance("inp.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(isAllDead(plants));
	CHECK(day->isTwoDaysOfNoRadiation());
	CHECK(i == 5);


	deleteAll(file, plants, day);
}

TEST_CASE("2", "inp_csak_alfa.txt")
{
	FileReader* file = FileReader::getInstance("inp_csak_alfa.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(isAllAlpha(plants));
	CHECK(isAllDead(plants));
	CHECK(i == 7);
	CHECK(day->isTwoDaysOfNoRadiation());


	deleteAll(file, plants, day);
}

TEST_CASE("3", "inp_csak_delta.txt")
{
	FileReader* file = FileReader::getInstance("inp_csak_delta.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(isAllAlive(plants));
	CHECK(isAllDelta(plants));
	CHECK(i == 10);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());


	deleteAll(file, plants, day);
}

TEST_CASE("4", "inp_csak_none.txt")
{
	FileReader* file = FileReader::getInstance("inp_csak_none.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(isAllNone(plants));
	CHECK(day->isTwoDaysOfNoRadiation());
	CHECK(isAllAlive(plants));
	CHECK(i == 1);

	deleteAll(file, plants, day);
}

TEST_CASE("5", "inp_csak_alfa_el.txt")
{
	FileReader* file = FileReader::getInstance("inp_csak_alfa_el.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(onlyXLives(plants, PlantType::Puffancs));
	CHECK(i == 5);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("6", "inp_csak_delta_el.txt")
{
	FileReader* file = FileReader::getInstance("inp_csak_delta_el.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(onlyXLives(plants, PlantType::Deltafa));
	CHECK(i == 10);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("7", "inp_csak_none_el.txt")
{
	FileReader* file = FileReader::getInstance("inp_csak_none_el.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(onlyXLives(plants, PlantType::Parabokor));
	CHECK(i == 1);
	CHECK(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("8", "inp_egy_nap.txt")
{
	FileReader* file = FileReader::getInstance("inp_egy_nap.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(isAllAlive(plants));
	CHECK(i == 1);
	CHECK(daysNum == 1);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("9", "inp_egy_noveny.txt")
{
	FileReader* file = FileReader::getInstance("inp_egy_noveny.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(plantsNum == 1);
	CHECK(isAllAlive(plants));
	CHECK(day->isTwoDaysOfNoRadiation());
	CHECK(isAllDelta(plants));

	deleteAll(file, plants, day);
}

TEST_CASE("10", "inp_ket_napig_nincs_radiacio.txt")
{
	FileReader* file = FileReader::getInstance("inp_ket_napig_nincs_radiacio.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(day->isTwoDaysOfNoRadiation());
	CHECK(i == 1);

	deleteAll(file, plants, day);
}

TEST_CASE("11", "inp_kihalo.txt")
{
	FileReader* file = FileReader::getInstance("inp_kihalo.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(isAllDead(plants));
	CHECK(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("12", "inp_mind_elo.txt")
{
	FileReader* file = FileReader::getInstance("inp_mind_elo.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(isAllAlive(plants));
	CHECK(i == 10);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("13", "inp_mind_halott.txt")
{
	FileReader* file = FileReader::getInstance("inp_mind_halott.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(isAllDead(plants));
	CHECK(day->isTwoDaysOfNoRadiation());
	CHECK(i == 1);

	deleteAll(file, plants, day);
}

TEST_CASE("14", "inp_nulla_nap.txt")
{
	FileReader* file = FileReader::getInstance("inp_nulla_nap.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(daysNum == 0);
	CHECK(i == 0);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("15", "inp_nulla_noveny.txt")
{
	FileReader* file = FileReader::getInstance("inp_nulla_noveny.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(i == 1);
	CHECK(plantsNum == 0);
	CHECK(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("16", "inp_onfenntarto.txt")
{
	FileReader* file = FileReader::getInstance("inp_onfenntarto.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(i == 20);
	CHECK(isAllAlive(plants));
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("17", "inp_tobb_nap.txt")
{
	FileReader* file = FileReader::getInstance("inp_tobb_nap.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(isAllDead(plants));
	CHECK(day->isTwoDaysOfNoRadiation());
	CHECK(daysNum > 1);

	deleteAll(file, plants, day);
}

TEST_CASE("18", "inp_tobb_noveny.txt")
{
	FileReader* file = FileReader::getInstance("inp_tobb_noveny.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(day->isTwoDaysOfNoRadiation());
	CHECK(i == 5);
	CHECK(plantsNum > 1);
	CHECK_FALSE(isAllAlive(plants));
	CHECK_FALSE(isAllDead(plants));

	deleteAll(file, plants, day);
}

TEST_CASE("19", "inp_van_alfa_rad.txt")
{
	FileReader* file = FileReader::getInstance("inp_van_alfa_rad.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int alphas = 0;
	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		if(day->getRadiation()==Radiation::Alpha)
		{
			alphas++;
		}
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}
	CHECK(alphas > 0);
	CHECK_FALSE(isAllAlive(plants));
	CHECK_FALSE(isAllDead(plants));
	CHECK(i == 4);
	CHECK(day->isTwoDaysOfNoRadiation());


	deleteAll(file, plants, day);
}

TEST_CASE("20", "inp_van_delta_rad.txt")
{
	FileReader* file = FileReader::getInstance("inp_van_delta_rad.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int deltas = 0;
	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		if (day->getRadiation() == Radiation::Delta)
		{
			deltas++;
		}
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(deltas > 0);
	CHECK(i == 20);
	CHECK(isAllAlive(plants));
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("21", "inp_van_none_rad.txt")
{
	FileReader* file = FileReader::getInstance("inp_van_none_rad.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int nones = 0;
	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		if (day->getRadiation() == Radiation::None)
		{
			nones++;
		}
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK(nones > 0);
	CHECK(isAllAlive(plants));
	CHECK(i == 3);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("22", "inp_van_elo.txt")
{
	FileReader* file = FileReader::getInstance("inp_van_elo.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK_FALSE(isAllDead(plants));
	CHECK_FALSE(isAllAlive(plants));
	CHECK(i == 6);
	CHECK(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

TEST_CASE("23", "inp_van_halott.txt")
{
	FileReader* file = FileReader::getInstance("inp_van_halott.txt");
	tuple<vector<Plant*>, int, int> processedFile = file->process();
	vector<Plant*> plants = get<0>(processedFile);
	int daysNum = get<1>(processedFile);
	int plantsNum = get<2>(processedFile);
	Day* day = Day::getInstance();

	int i;
	for (i = 0; i < daysNum && !day->isTwoDaysOfNoRadiation(); i++)
	{
		vector<tuple<Radiation, int>> claims;
		cout << "\nDay " << i + 1 << ": " << getRadiationType(day->getRadiation()) << endl;
		effects(&plants, &claims, day);
		day->nextDay(claims);
		if (day->isTwoDaysOfNoRadiation()) { cout << "\nSimulation ended because two consecutive days of no radiation!" << endl; }
	}

	CHECK_FALSE(isAllDead(plants));
	CHECK_FALSE(isAllAlive(plants));
	CHECK(i == 20);
	CHECK_FALSE(day->isTwoDaysOfNoRadiation());

	deleteAll(file, plants, day);
}

#endif // NORMAL_MODE