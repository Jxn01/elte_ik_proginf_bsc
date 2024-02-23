#ifndef MUFAJ_H
#define MUFAJ_H

class Mufaj //abstract class
{
public:

	//methods
	virtual ~Mufaj() = default;
	virtual int Dij() = 0;

protected:
	Mufaj() = default;
};

class Termeszettudomanyos : public Mufaj //singleton
{
public:
	
	//methods
	int Dij() override { return 100; }
	static void destroy();

	//getters
	static Termeszettudomanyos* instance();

private:

	//attributes
	static Termeszettudomanyos* ins;
};

class Szepirodalmi : public Mufaj //singleton
{
public:

	//methods
	int Dij() override { return 50; }
	static void destroy();

	//getters
	static Szepirodalmi* instance();

private:

	//attributes
	static Szepirodalmi* ins;
};

class Ifjusagi : public Mufaj //singleton
{
public:

	//methods
	int Dij() override { return 20; }
	static void destroy();

	//getters
	static Ifjusagi* instance();

private:

	//attributes
	static Ifjusagi* ins;
};

#endif //MUFAJ_H