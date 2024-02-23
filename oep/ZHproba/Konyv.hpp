#ifndef KONYV_H
#define KONYV_H
#include <string>

//forward class declarations for cross class logic
class Kolcson;
class Mufaj;

class Konyv
{
public:

	//methods
	Konyv(std::string c, std::string sz, int o, Mufaj* m): azon(0), cim(c), szerzo(sz), oldal(o), kinn(false),  mufaj(m) {}
	~Konyv();
	int Dij(int ma);

	//getters
	int get_azon() { return azon; }
	std::string get_cim() { return cim; }
	std::string get_szerzo() { return szerzo; }
	int get_oldal() { return oldal; }
	bool get_kinn() { return kinn; }
	Kolcson* get_fej() { return fej; }
	Mufaj* get_mufaj() { return mufaj; }
	//setters
	void set_azon(int a) { azon = a; }
	void set_kinn(bool b) { kinn = b; }
	void set_fej(Kolcson* k) { fej = k; }

private:

	//attributes
	int azon;
	std::string cim;
	std::string szerzo;
	int oldal;
	bool kinn;
	Kolcson* fej;
	Mufaj* mufaj;
};

#endif //KONYV_H
