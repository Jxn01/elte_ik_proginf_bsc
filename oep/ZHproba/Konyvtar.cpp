#include "Konyvtar.hpp"

#include "Konyv.hpp"
#include "Szemely.hpp"
#include <tuple>

#include "Kolcson.hpp"

void Remove(std::vector<Konyv*>, Konyv*);

void Konyvtar::Bevetelez(Konyv* k)
{
	k->set_azon(sorszam++);
	konyvek.push_back(k);
}

void Konyvtar::Belep(Szemely* sz)
{
	if(!Tag(sz))
	{
		tagok.push_back(sz);
		sz->set_konyvtar(this);
	}
}

bool Konyvtar::Tag(Szemely* sz)
{
	for(Szemely* e : tagok)
	{
		if (e->get_nev() == sz->get_nev()) { return true; }
	}
	return false;
}

std::tuple<bool, Konyv*> Konyvtar::Keres(int az)
{
	bool volt_e = false;
	Konyv* konyv = nullptr;

	for(Konyv* e : konyvek)
	{
		if (e->get_azon() == az) { volt_e = true; konyv = e; break; }
	}

	return { volt_e, konyv };
}

void Konyvtar::Visszahoz(std::vector<int> lista)
{
	for(int az : lista)
	{
		auto a = Keres(az);
		bool l = a._Myfirst._Val;
		Konyv* k = a._Get_rest()._Myfirst._Val;
		if(l && k->get_kinn())
		{

			Remove(k->get_fej()->get_tetelek(), k);
			k->set_kinn(false);
		}
	}
}

void Konyvtar::Kolcsonoz(Szemely* sz, std::vector<int> lista, int ma)
{
	if (!Tag(sz) && lista.size() > 5) { throw new std::exception(); }
	Kolcson* kg = new Kolcson(this, sz, ma);
	for(int az : lista)
	{
		auto a = Keres(az);
		bool l = a._Myfirst._Val;
		Konyv* k = a._Get_rest()._Myfirst._Val;
		if(l && !k->get_kinn())
		{
			kg->push_tetelek(k);
			k->set_kinn(true);
			k->set_fej(kg);
		}
	}
	kolcs.push_back(kg);
	sz->Rogzit(kg);
}

int Konyvtar::Potdij(Szemely* sz, int ma)
{
	int result = 0;

	for(Kolcson* e : kolcs)
	{
		if (e->get_tag() == sz) { result += e->Potdij(ma); }
	}

	return result;
}

Konyvtar::~Konyvtar()
{
	tagok.clear();
	konyvek.clear();
	for (Kolcson* k : kolcs) { if (k != nullptr) { delete k; k = nullptr; } }
}


void Remove(std::vector<Konyv*> tetelek, Konyv* k)
{
	std::vector<Konyv*> result;

	for(Konyv* elem : tetelek)
	{
		if(elem != k)
		{
			result.push_back(elem);
		}
	}

	tetelek = result;
}