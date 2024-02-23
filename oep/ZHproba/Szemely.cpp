#include "Szemely.hpp"
#include "Konyvtar.hpp"
#include "Kolcson.hpp"

void Szemely::Rogzit(Kolcson* k)
{
	kolcs.push_back(k);
}

Szemely::~Szemely()
{
	konyvtar = nullptr;
	kolcs.clear();
}

