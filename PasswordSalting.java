import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class PasswordSalting
{
    ArrayList<String> UID = new ArrayList<String>();
    ArrayList<String> HashPassword = new ArrayList<String>();

    public void SetUID(String pathname) throws IOException
    {
        UID.add(pathname);
    }

    // saves the hashed pw+salt into the arraylist
    public void SetHashPassword(String pathname) throws IOException, NoSuchAlgorithmException
    {
        HashPassword.add(Hashfunction(pathname));
    }

    // H(password||salt)
    public static String Hashfunction(String PasswordAndSalt) throws NoSuchAlgorithmException
    {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(PasswordAndSalt.getBytes(StandardCharsets.UTF_8));
        byte[] digest = m.digest();
        return bytesToHex1(digest);
    }

    // converts a byte array into a String
    private static String bytesToHex1(byte[] md5Array)
    {
        StringBuilder sb = new StringBuilder();
        for(byte b : md5Array)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean Verify(String input1, String input2)
    {
        return input1.equals(input2);
    }

    // CrackerSystem
    public static String[] BruteForce(String HashPassword) throws FileNotFoundException, NoSuchAlgorithmException
    {
        File pw = new File("PWsample.txt");
        String[] result = new String[2];    // 2: password + salt
        Scanner p = new Scanner(pw);
        while(p.hasNextLine())
        {
            File uid = new File("UID.txt");
            Scanner u = new Scanner(uid);
            String hp = p.nextLine();
            //Scanner h = new Scanner(HashPassword);
            for(int i = 0; i < 100; i++)
            {
                hp = u.nextLine() + hp;
                //System.out.println(Hashfunction(hp) + " || " + HashPassword);
                if(Verify(Hashfunction(hp), HashPassword))
                {
                    result[0] = hp.substring(4);
                    result[1] = hp.substring(4,7);
                    System.out.println("Matched: " + result[0] + result[1]);
                }
            }
            u.close();
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException
    {
        File uid = new File("UID.txt");
        File password = new File("Password.txt");
        File salt = new File("Salt.txt");
        File hash = new File("Hash.txt");
        Scanner u = new Scanner(uid);
        Scanner pw = new Scanner(password);
        Scanner s = new Scanner(salt);
        Scanner h = new Scanner(hash);
        PasswordSalting test = new PasswordSalting();
        while(u.hasNextLine())
            test.SetUID(u.nextLine());
        while(pw.hasNextLine() && s.hasNextLine())
            test.SetHashPassword(pw.nextLine() + s.nextLine());
        int index = 0;
        while(h.hasNextLine())
        {
            if(test.Verify(test.HashPassword.get(index), h.nextLine()))
            {
                System.out.println("The input password and salt matches the hash value in the database");
            }
            else
            {
                System.out.println("The input password and salt does not match the hash value in the database");
            }
            index++;
        }
        File hash2 = new File("Hash.txt");
        Scanner h2 = new Scanner(hash2);
        while(h2.hasNextLine())
        {
            BruteForce(h2.nextLine());
        }
    }

}
