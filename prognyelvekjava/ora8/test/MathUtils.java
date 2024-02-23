package ora8.test;
class MathUtils
{
    public static double power(int base, int exp)
    {
        double result = 1;
        for (int i = 1; i <= Math.abs(exp); ++i)
        {
            result *= base;
        }

        if (exp <= 0) // ez itt hib�s -> mostm�r fail-el az eddig meg�rt unit test // helyesen exp < 0
        {
            if (base == 0)
            {
                throw new IllegalArgumentException();
            }
            result = 1.0 / result;
        }

        return result;
    }
}
