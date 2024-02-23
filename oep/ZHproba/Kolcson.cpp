#include "Kolcson.hpp"

#include "Konyv.hpp"
#include "Konyvtar.hpp"
#include "Szemely.hpp"

int Kolcson::Potdij(int ma)
{
	int result = 0;

	for(Konyv* e : tetelek)
	{
		if (e->get_kinn()) { result += e->Dij(ma); }
	}

	return result;
}

Kolcson::~Kolcson()
{
	konyvtar = nullptr;
	tag = nullptr;
	tetelek.clear();
}

