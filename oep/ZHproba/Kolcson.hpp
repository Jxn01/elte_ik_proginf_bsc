#ifndef KOLCSON_H
#define KOLCSON_H
#include <vector>

//forward class declarations for cross class logic
class Konyvtar;
class Szemely;
class Konyv;

class Kolcson
{
public:

	//methods
	Kolcson(Konyvtar* k, Szemely* sz, int d): datum(d), konyvtar(k), tag(sz) {}
	~Kolcson();
	int Potdij(int ma);

	//getters
	int get_datum() { return datum; }
	Konyvtar* get_konyvtar() { return konyvtar; }
	std::vector<Konyv*> get_tetelek() { return tetelek; }
	Szemely* get_tag() { return tag; }
	//setters
	void push_tetelek(Konyv* k) { tetelek.push_back(k); }

private:

	//attributes
	int datum;
	Konyvtar* konyvtar;
	std::vector<Konyv*> tetelek;
	Szemely* tag;
};

#endif //KOLCSON_H

