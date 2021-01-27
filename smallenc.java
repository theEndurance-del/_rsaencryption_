import java.util.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.Base64;

public class smallenc
{
    public static int power(int n, int p)
    {
        int pow = 1;
        for (int i = 1; i <= p;i++)
        {
            pow *= n;
        }
        System.out.println("power: "+pow);
        return pow;
    }

    public static int[] StringToASCII(String value)
    {
        int length = value.length();
        int[] arr = new int[length];
        for(int i = 0; i < length; i++)
        {
            char ch = value.charAt(i);
            arr[i] = (int)ch;
            System.out.print(arr[i]+" ");
        }
        System.out.println();
        return arr;
    }
    public static String arrToString(char[] chrarray)
    {
        String string = String.valueOf(chrarray);
        return string;
    }

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
            int seed = rand.nextInt(128) + 1;
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

    public static int encrypt(int[] plain, int length)
    {
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();

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
        System.out.println("Public Key:  "+exponent+", "+n);
        System.out.println("Private Key: "+decrypt+", "+n);

        char[] encrypted = new char[length];

        for(int i = 0;i < length; i++)
        {
            int var = plain[i];
            int ePower = 1;
            for(int j = 1; j <= exponent; j++)
            {
                ePower = ePower * var;
                System.out.println("power: "+j);
            }
            int finalenc = ePower % n;
            encrypted[i] = (char)finalenc;
        }

        System.out.println();

        System.out.println("encrypted: "+arrToString(encrypted));

        String rSAencryptedString = arrToString(encrypted);
        String b64String = encoder.encodeToString(rSAencryptedString.getBytes());
        System.out.println("encrypted: "+b64String);

        char[] decrypted = new char[length];
        String b64dString = new String(decoder.decode(b64String));

        System.out.println(b64dString);
        
        int[] cipher = StringToASCII(b64dString);

        for(int i = 0; i < b64dString.length(); i++)
        {
            int dPower = power(cipher[i], decrypt);
            int finaldec = dPower % n;
            dPower = 0;
            decrypted[i] = (char)finaldec;
        }

        String rSAdecryptSting = arrToString(decrypted);

        System.out.println("decrypted: "+rSAdecryptSting);
        return 0;
    }

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text to be encrypted: ");
        String plainText = scanner.nextLine();
        int[] ASCII = StringToASCII(plainText);
        int length = plainText.length();
        
        System.out.println();
        encrypt(ASCII, length);
        scanner.close();
    }
}
