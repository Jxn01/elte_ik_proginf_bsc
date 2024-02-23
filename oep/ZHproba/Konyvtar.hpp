#ifndef KONYVTAR_H
#define KONYVTAR_H
#include <vector>
#include <set>


//forward class declarations for cross class logic
class Konyv;
class Szemely;
class Kolcson;

class Konyvtar
{
public:

	//methods
	Konyvtar() = default;
	~Konyvtar();
	void Bevetelez(Konyv* k);
	void Belep(Szemely* sz);
	void Visszahoz(std::vector<int> lista);
	void Kolcsonoz(Szemely* sz, std::vector<int> lista, int ma);
	int Potdij(Szemely* sz, int ma);

	//getters
	std::vector<Szemely*> get_tagok() { return tagok; }
	std::vector<Konyv*> get_konyvek() { return konyvek; }
	std::vector<Kolcson*> get_kolcs() { return kolcs; }

private:

	//attributes
	int sorszam=1;
	std::vector<Szemely*> tagok;
	std::vector<Konyv*> konyvek;
	std::vector<Kolcson*> kolcs;

	//methods
	bool Tag(Szemely* sz);
	std::tuple<bool, Konyv*> Keres(int az);
};

#endif //KONYVTAR_H
