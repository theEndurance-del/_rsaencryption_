import java.util.*;
import java.io.IOException;
/*
This program does just the initial mathemetics for rsa encryption
I am currently stuck at generating the private key component value
Any help from anyone would be great
*/
public class smallenc
{
    public static boolean isInteger(float num)
    {
        return (num % 1 == 0);
    }
    public static int primegen()
    {
        Random rand = new Random();
        int prime = 0;
        while (true)
        {
            int seedcount = 0;
            int seed = rand.nextInt(50) + 1;
            for (int i = 1;i <= seed; i++)
            {
                int modfactor = seed % i;
                if (modfactor == 0)
                {
                    seedcount = seedcount + 1;
                }
            }
            if (seedcount == 2)
            {
                prime = seed;
                break;
            }
        }
        return prime;
    }

    public static boolean gcd(int n, int m)
    {
        int count = 0;
        for (int i = 1; i <= m; i++)
        {
            int mod1 = m % i;
            int mod2 = n % i;
            if (mod1 == 0 && mod2 == 0)
            {
                count = count + 1;
            }
        }
        if (count == 1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    public static int privatekey(int primeprod, int exp, int lambda) 
    {
        Random random = new Random();
        int var = 0;
        //euler's theorem is
        //d = (1 + x*lambda) / e
        //e = exp
        //lambda as stated
        //d is smaller than primeprod
        //d = var
        while(true)
        {
            int seed = random.nextInt(200) + 1;
            System.out.println("seed: "+seed);
            var = (1 + (seed*lambda)) / exp;
            if(isInteger(var))
            {
                break;
            }
        }
        return var;
    }
    public static int encrypt(int plain)
    {
        Random rand = new Random();
        int prime1 = primegen();
        int prime2 = primegen();
        System.out.println(prime1+", "+prime2);

        int n = prime1 * prime2;
        int lambda_n = (prime1 - 1) * (prime2 - 1);

        System.out.println(n+", "+lambda_n);
        int exponent;
        while (true)
        {
            int e = rand.nextInt(lambda_n);
            if (gcd(e, lambda_n))
            {
                exponent = e;
                break;
            }
        }
        System.out.println(exponent);

        // d.e = i mod (lambd_n)

        int decrypt = privatekey(n, exponent, lambda_n);
        System.out.println(decrypt);
        return 0;
    }
    public static void main(String[] args) 
    {
        int num = encrypt(0);
        System.out.println(num);
    }
}
