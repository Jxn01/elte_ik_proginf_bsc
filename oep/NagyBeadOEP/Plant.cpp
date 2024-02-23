#include "Plant.h"

using namespace std;

Puffancs::Puffancs(string n, int r)
{
	pt = PlantType::Puffancs;
	name = n;
	resource = r;
	alive = (resource <= 0 || resource >= 10) ? false : true;
}

void Puffancs::isDead()
{
	alive = (resource <= 0 || resource > 10) ? false : true;
	resource = (alive) ? resource : 0;
}

void Puffancs::radiated(Radiation radiation)
{
	if (alive)
	{
		switch (radiation)
		{
		case Radiation::Alpha:
			resource += 2;
			break;
		case Radiation::Delta:
			resource -= 2;
			break;
		case Radiation::None:
			resource -= 1;
			break;
		}

		isDead();
		claim = (alive) ? make_tuple(Radiation::Alpha, 10) : claim;
	}
}

string Puffancs::toString()
{
	string sAlive = (alive) ? "alive" : "dead";
	return "Name: " + name + ", type: Puffancs" + ", resource: " + to_string(resource) + ", " + sAlive;
}

Deltafa::Deltafa(string n, int r)
{
	pt = PlantType::Deltafa;
	name = n;
	resource = r;
	alive = (resource <= 0) ? false : true;
}

void Deltafa::isDead()
{
	alive = (resource <= 0) ? false : true;
	resource = (alive) ? resource : 0;
}

void Deltafa::radiated(Radiation radiation)
{
	if (alive)
	{
		switch (radiation)
		{
		case Radiation::Alpha:
			resource -= 3;
			break;
		case Radiation::Delta:
			resource += 4;
			break;
		case Radiation::None:
			resource -= 1;
			break;
		}

		isDead();
		if (alive)
		{
			if (resource < 5)
			{
				claim = make_tuple(Radiation::Delta, 4);
			}
			else if (resource >= 5 && resource <= 10)
			{
				claim = make_tuple(Radiation::Delta, 1);
			}
		}
	}
}

string Deltafa::toString()
{
	string sAlive = (alive) ? "alive" : "dead";
	return "Name: " + name + ", type: Deltafa" + ", resource: " + to_string(resource) + ", " + sAlive;
}

Parabokor::Parabokor(string n, int r)
{
	pt = PlantType::Parabokor;
	name = n;
	resource = r;
	alive = (resource <= 0) ? false : true;
}

void Parabokor::isDead()
{
	alive = (resource <= 0) ? false : true;
	resource = (alive) ? resource : 0;
}

void Parabokor::radiated(Radiation radiation)
{
	if (alive)
	{
		switch (radiation)
		{
		case Radiation::Alpha:
			resource += 1;
			break;
		case Radiation::Delta:
			resource += 1;
			break;
		case Radiation::None:
			resource -= 1;
			break;
		}

		isDead();
	}
}

string Parabokor::toString()
{
	string sAlive = (alive) ? "alive" : "dead";
	return "Name: " + name + ", type: Parabokor" + ", resource: " + to_string(resource) + ", " + sAlive;
}










