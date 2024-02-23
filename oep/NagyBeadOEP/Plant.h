#ifndef PLANT_H
#define PLANT_H

#include <string>
#include <tuple>

#include "PlantTypes.h"
#include "Radiation.h"


class Plant
{
public:
	PlantType pt;

	virtual ~Plant() = default;

	virtual void radiated(Radiation radiation) { }

	std::tuple <Radiation, int> getClaim() { return claim; }
	bool isAlive() { return alive; }

	virtual std::string toString() { return " "; }

protected:
	std::string name;
	int resource = 0;
	bool alive = true;
	std::tuple <Radiation, int> claim = std::make_tuple(Radiation::None, 0);

	virtual void isDead() { }
};

class Puffancs : public Plant
{
public:
	Puffancs(std::string n, int r);
	~Puffancs() override = default;

	void radiated(Radiation radiation) override;

	std::string toString() override;

private:
	void isDead() override;
};

class Deltafa : public Plant
{
public:
	Deltafa(std::string n, int r);
	~Deltafa() override = default;

	void radiated(Radiation radiation) override;

	std::string toString() override;

private:
	void isDead() override;
};

class Parabokor : public Plant
{
public:
	Parabokor(std::string n, int r);
	~Parabokor() override = default;

	void radiated(Radiation radiation) override;

	std::string toString() override;

private:
	void isDead() override;
};

#endif //PLANT_H