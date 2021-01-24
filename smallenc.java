import java.util.*;
import java.io.IOException;
import java.util.Scanner;

public class smallenc
{
    public static boolean isInteger(double num)
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
            int seed = rand.nextInt(1024) + 1;
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

    public static int privatekey(double primeprod, double exp, double lambda) 
    {
        double var = 0;
        int x = 1;
        while(x != 0)
        {
            var = (1 + (x*lambda)) / exp;
            if(isInteger(var) && var < primeprod)
            {
                break;
            }
            x = x + 1;
        }
        int finalvar = (int)var;
        return finalvar;
    }
    public static int encrypt(int[] plain)
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
        int decrypt = privatekey(n, exponent, lambda_n);
        System.out.println(decrypt);
        return 0;
    }

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text to be encrypted: ");
        String plainText = scanner.nextLine();
        int[] ASCII = new int[plainText.length()];
        int length = plainText.length();
        for(int i = 0; i < length; i++)
        {
            char ch = plainText.charAt(i);
            ASCII[i] = (int)ch;
        }
        for(int i = 0 ; i < length; i++)
        {
            System.out.print(ASCII[i]+", ");
        }
        System.out.println();
        encrypt(ASCII);
        scanner.close();
    }
}
