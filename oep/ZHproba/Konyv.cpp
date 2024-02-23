#include "Konyv.hpp"

#include "Kolcson.hpp"
#include "Mufaj.hpp"

int Konyv::Dij(int ma)
{
	int keses = ma - (fej->get_datum() + 30);
	if (keses > 0){ return keses * mufaj->Dij(); }
	//else
	return mufaj->Dij();
}

Konyv::~Konyv()
{
	fej = nullptr;
	mufaj = nullptr;
}

