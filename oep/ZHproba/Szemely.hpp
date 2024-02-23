#ifndef SZEMELY_H
#define SZEMELY_H
#include <string>
#include <vector>

//forward class declarations for cross class logic
class Kolcson;
class Konyvtar;

class Szemely
{
public:

	//methods
	Szemely(std::string n): nev(n) {}
	~Szemely();
	void Rogzit(Kolcson* k);

	//getters
	std::string get_nev() { return nev; }
	Konyvtar* get_konyvtar() { return konyvtar; }
	std::vector<Kolcson*> get_kolcs() { return kolcs; }
	//setters
	void set_konyvtar(Konyvtar* k) { konyvtar = k; }

private:

	//attributes
	std::string nev;
	Konyvtar* konyvtar;
	std::vector<Kolcson*> kolcs;
};

#endif //SZEMELY_H
