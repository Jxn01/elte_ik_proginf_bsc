#include "Mufaj.hpp"

Termeszettudomanyos* Termeszettudomanyos::ins = nullptr;
Szepirodalmi* Szepirodalmi::ins = nullptr;
Ifjusagi* Ifjusagi::ins = nullptr;

Termeszettudomanyos* Termeszettudomanyos::instance()
{
	if (ins == nullptr) { ins = new Termeszettudomanyos(); }
	return ins;
}

void Termeszettudomanyos::destroy()
{
	delete ins;
}

Szepirodalmi* Szepirodalmi::instance()
{
	if (ins == nullptr) { ins = new Szepirodalmi(); }
	return ins;
}

void Szepirodalmi::destroy()
{
	delete ins;
}

Ifjusagi* Ifjusagi::instance()
{
	if (ins == nullptr) { ins = new Ifjusagi(); }
	return ins;
}

void Ifjusagi::destroy()
{
	delete ins;
}

